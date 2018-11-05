package cn.platalk.brtmap.route.core_v3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateArrays;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.linearref.LinearLocation;
import com.vividsolutions.jts.linearref.LocationIndexedLine;
import com.vividsolutions.jts.operation.distance.DistanceOp;
import com.vividsolutions.jts.operation.linemerge.LineMerger;

import cn.platalk.brtmap.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.TYIMapInfo;
import cn.platalk.brtmap.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.brtmap.entity.base.TYLocalPoint;

class IPServerRouteNetworkDatasetV3 {
	static GeometryFactory factory = new GeometryFactory();

	private List<TYIMapInfo> allMapInfoArray = new ArrayList<TYIMapInfo>();
	private List<TYIMapDataFeatureRecord> allMapDataArray = new ArrayList<TYIMapDataFeatureRecord>();

	private List<IPServerLinkV3> m_linkArray = new ArrayList<IPServerLinkV3>();
	public List<IPServerNodeV3> m_nodeArray = new ArrayList<IPServerNodeV3>();

	private Map<Integer, List<IPServerLinkV3>> m_allLinkCollectionDict = new HashMap<Integer, List<IPServerLinkV3>>();

	private Map<String, IPServerNodeV3> m_allNodeDict = new HashMap<String, IPServerNodeV3>();
	private Map<Integer, List<IPServerNodeV3>> m_allSwitchingNodeDict = new HashMap<Integer, List<IPServerNodeV3>>();

	private Map<Integer, Geometry> m_allUnionLineDict = new HashMap<Integer, Geometry>();
	private Map<String, Geometry> m_allRoomIDLineDict = new HashMap<String, Geometry>();

	// Temporary Links and Nodes
	private List<IPServerNodeV3> m_tempStartNodeArray = new ArrayList<IPServerNodeV3>();
	private List<IPServerLinkV3> m_tempStartLinkArray = new ArrayList<IPServerLinkV3>();
	private List<IPServerLinkV3> m_replacedStartLinkArray = new ArrayList<IPServerLinkV3>();

	private List<IPServerNodeV3> m_tempEndNodeArray = new ArrayList<IPServerNodeV3>();
	private List<IPServerLinkV3> m_tempEndLinkArray = new ArrayList<IPServerLinkV3>();
	private List<IPServerLinkV3> m_replacedEndLinkArray = new ArrayList<IPServerLinkV3>();

	int m_tempNodeIndex;
	int m_tempLinkIndex;
	int m_virtualLinkIndex;

	int targetStartFloor = -100000;
	int targetEndFloor = -100000;

	String targetStartRoomID = null;
	String targetEndRoomID = null;

	private int m_usedLinkType = 1;
	private List<String> ignoredNodeList = new ArrayList<String>();
	private boolean useSameFloor = false;
	private Calendar requestTime;
	private boolean enableRouteLevel = false;

	public IPServerRouteNetworkDatasetV3(List<TYIMapInfo> mapInfos, List<TYIRouteNodeRecordV3> nodes,
			List<TYIRouteLinkRecordV3> links, List<TYIMapDataFeatureRecord> mapdata) {
		allMapInfoArray.addAll(mapInfos);
		// allMapDataArray.addAll(mapdata);
		for (TYIMapDataFeatureRecord record : mapdata) {
			if (record.getLayer() == 2 && !record.getCategoryID().equals("000800")) {
				allMapDataArray.add(record);
			}
		}

		m_tempNodeIndex = 0;
		m_tempLinkIndex = 0;
		m_virtualLinkIndex = 0;

		extractNodes(nodes);
		processSwitchingNodes();
		extractLinks(links);
		processNodesAndLinks();
		processSwitchingNodesAndLinks();

		for (int floor : m_allLinkCollectionDict.keySet()) {
			List<IPServerLinkV3> list = m_allLinkCollectionDict.get(floor);
			List<LineString> linkLineVector = new ArrayList<LineString>();
			for (int i = 0; i < list.size(); i++) {
				IPServerLinkV3 link = list.get(i);
				if (link.m_allowSnap) {
					linkLineVector.add(link.getLine());
				}
			}
			LineMerger lineMerger = new LineMerger();
			lineMerger.add(linkLineVector);
			Geometry unionLine = factory.buildGeometry(lineMerger.getMergedLineStrings());
			m_allUnionLineDict.put(floor, unionLine);
		}

		Map<String, List<IPServerLinkV3>> roomIDLinkMap = new HashMap<String, List<IPServerLinkV3>>();
		for (IPServerLinkV3 link : m_linkArray) {
			if (link.m_roomID != null) {
				List<IPServerLinkV3> list = roomIDLinkMap.get(link.m_roomID);
				if (list == null) {
					list = new ArrayList<IPServerLinkV3>();
					roomIDLinkMap.put(link.m_roomID, list);
				}
				list.add(link);
			}
		}

		for (String roomID : roomIDLinkMap.keySet()) {
			List<IPServerLinkV3> list = roomIDLinkMap.get(roomID);
			List<LineString> linkLineVector = new ArrayList<LineString>();
			for (int i = 0; i < list.size(); i++) {
				IPServerLinkV3 link = list.get(i);
				if (link.m_allowSnap) {
					linkLineVector.add(link.getLine());
				}
			}
			if (linkLineVector.size() == 0) {
				continue;
			}
			LineMerger lineMerger = new LineMerger();
			lineMerger.add(linkLineVector);
			Geometry unionLine = factory.buildGeometry(lineMerger.getMergedLineStrings());
			m_allRoomIDLineDict.put(roomID, unionLine);
		}
	}

	public synchronized IPServerRouteResultObjectV3 getShorestPathV3(TYLocalPoint startPoint, TYLocalPoint endPoint,
			TYServerRouteOptions options) {
		// System.out.println("getShorestPath");
		targetStartFloor = startPoint.getFloor();
		targetEndFloor = endPoint.getFloor();
		requestTime = Calendar.getInstance();

		// process options
		if (options != null) {
			m_usedLinkType = options.getLinkType();
			ignoredNodeList.clear();
			ignoredNodeList.addAll(options.getIgnoredNodeCategoryList());
			useSameFloor = options.isUseSameFloor();
			enableRouteLevel = options.isEnableRouteLevel();
		} else {
			m_usedLinkType = 0;
			ignoredNodeList.clear();
			useSameFloor = false;
			enableRouteLevel = false;
		}
		// System.out.println("useSameFloor: " + useSameFloor);

		reset();

		if (m_linkArray.size() == 0 || m_nodeArray.size() == 0) {
			return null;
		}

		IPServerNodeV3 startNode = processTempNodeForStart(startPoint);
		IPServerNodeV3 endNode = processTempNodeForEnd(endPoint);
		targetStartRoomID = startNode.m_roomID;
		targetEndRoomID = endNode.m_roomID;
		computePaths(startNode);
		List<IPServerRouteElement> elements = getShorestPathToNodeV3(startNode, endNode);

		resetTempNodeForEnd();
		resetTempNodeForStart();

		// 只有终点elememt，endNode.previousNode=null
		if (elements == null || elements.size() <= 1) {
			return null;
		}
		return new IPServerRouteResultObjectV3(startPoint, endPoint, allMapInfoArray, elements);
	}

	public void computePaths(IPServerNodeV3 source) {
		// System.out.println("computePaths");
		// System.out.println("Start Room: " + targetStartRoomID);
		// System.out.println("End Room: " + targetEndRoomID);

		source.minDistance = 0;
		PriorityQueue<IPServerNodeV3> nodeQueue = new PriorityQueue<IPServerNodeV3>();
		nodeQueue.add(source);

		while (!nodeQueue.isEmpty()) {
			IPServerNodeV3 u = nodeQueue.poll();

			// 考虑节点通断
			if (!u.m_open) {
				// System.out.println("Node not open: " + u.m_nodeID);
				continue;
			}

			// 考虑通断时间
			if (u.m_timeWindows != null) {
				if (!u.m_timeWindows.contains(requestTime)) {
					// System.out.println("Node not in open time: " +
					// u.m_nodeID);
					continue;
				}
			}

			// 考虑同层优先
			if (useSameFloor) {
				if (u.m_floor != targetStartFloor && u.m_floor != targetEndFloor) {
					continue;
				}
			}

			// check ignoring nodes
			if (u.m_categoryID != null && ignoredNodeList.contains(u.m_categoryID)) {
				continue;
			}

			for (IPServerLinkV3 e : u.adjacencies) {
				if (!e.m_open) {
					// System.out.println("Link not open: " + e.m_linkID);
					continue;
				}

				// 考虑通断时间
				if (e.m_timeWindows != null) {
					if (!e.m_timeWindows.contains(requestTime)) {
						// System.out.println("Link not in open time: " +
						// e.m_linkID);
						continue;
					}
				}

				// 考虑路段等级
				if (enableRouteLevel) {
					if (e.m_level > 0) {
						boolean inStartOrEndRoom = false;
						if (e.m_roomID != null) {
							inStartOrEndRoom = e.m_roomID.equals(targetStartRoomID)
									|| e.m_roomID.equals(targetEndRoomID);
						}

						if (!inStartOrEndRoom) {
							continue;
						}
					}
				}

				// 考虑同层优先
				if (useSameFloor) {
					if (e.m_floor != targetStartFloor && e.m_floor != targetEndFloor) {
						continue;
					}
				}

				{
					// check link type
					String linkType = e.m_linkType;
					if (linkType.length() <= m_usedLinkType) {
						continue;
					} else if (Integer.parseInt(linkType.substring(m_usedLinkType, m_usedLinkType + 1)) == 0) {
						continue;
					}
				}

				IPServerNodeV3 v = e.nextNode;
				double length = e.m_length;

				double distanceThroughU = u.minDistance + length;
				if (distanceThroughU < v.minDistance) {
					nodeQueue.remove(v);

					v.minDistance = distanceThroughU;
					v.previousNode = u;
					nodeQueue.add(v);
				}
			}
		}
	}

	public List<IPServerRouteElement> getShorestPathToNodeV3(IPServerNodeV3 start, IPServerNodeV3 target) {
		// System.out.println("================ getShorestPathToNodeV3
		// ================");
		// System.out.println("Start node: " + start.getNodeID() + ", Floor: " +
		// start.m_floor);
		// System.out.println("End node: " + target.getNodeID() + ", Floor: " +
		// target.m_floor);

		List<IPServerRouteElement> resultList = new ArrayList<IPServerRouteElement>();

		List<IPServerNodeV3> path = new ArrayList<IPServerNodeV3>();
		for (IPServerNodeV3 node = target; node != null; node = node.previousNode) {
			path.add(node);
			// System.out.println("Mid node: " + node.getNodeID());
			// System.out.println(node.getPos());
			// System.out.println(node.adjacencies.size());
		}
		Collections.reverse(path);

		// for (int i = 0; i < path.size(); ++i) {
		// System.out.print("--> " + path.get(i).getNodeID() + " ");
		// }

		if (path.get(0).getNodeID() != start.getNodeID()) {
			// System.out.println("No Route");
			return null;
		} else {
			// System.out.println("Equal");
		}

		for (int i = 0; i < path.size(); ++i) {
			IPServerNodeV3 node = path.get(i);
			if (node != null && node.previousNode != null) {
				for (IPServerLinkV3 link : node.previousNode.adjacencies) {
					if (link.nextNode == node) {
						resultList.add(IPServerRouteElementLink.fromLink(link));
						break;
					}
				}
			}
			resultList.add(IPServerRouteElementNode.fromNode(node));
		}
		return resultList;
	}

	protected List<Object> findNodeOnRouteNetwork(TYLocalPoint lp) {
		List<Object> resultList = new ArrayList<Object>();

		Point point = factory.createPoint(new Coordinate(lp.getX(), lp.getY()));
		boolean isInRoom = false;
		String roomID = null;
		for (TYIMapDataFeatureRecord record : allMapDataArray) {
			if (record.getFloorNumber() == lp.getFloor() && record.getGeometryData().contains(point)) {
				isInRoom = true;
				roomID = record.getPoiID();
				// System.out.println("In Room: " + roomID);
				break;
			}
		}

		Geometry unionLine = null;
		if (isInRoom && roomID != null) {
			unionLine = m_allRoomIDLineDict.get(roomID);
			// System.out.println("room line");
		}

		if (unionLine == null) {
			// System.out.println("room line is null");
			unionLine = m_allUnionLineDict.get(lp.getFloor());
		}

		// Geometry unionLine = m_allUnionLineDict.get(lp.getFloor());
		DistanceOp distanceOp = new DistanceOp(unionLine, point);
		Coordinate[] closestCoordinates = distanceOp.nearestPoints();
		Point npOnUnionLine = factory.createPoint(closestCoordinates[0]);

		for (int i = 0; i < m_nodeArray.size(); i++) {
			IPServerNodeV3 node = m_nodeArray.get(i);
			if (node.m_floor != lp.getFloor()) {
				continue;
			}
			Coordinate c = node.getPos().getCoordinate();
			if (npOnUnionLine.getCoordinate().equals(c)) {
				// System.out.println("Equal");
				resultList.add(true);
				resultList.add(node);
				return resultList;
			}
		}

		IPServerNodeV3 newTempNode = new IPServerNodeV3(createTempNodeID());
		newTempNode.setPos(npOnUnionLine);
		newTempNode.m_floor = lp.getFloor();
		newTempNode.m_open = true;
		if (isInRoom) {
			newTempNode.m_roomID = roomID;
		}

		resultList.add(false);
		resultList.add(newTempNode);
		return resultList;
	}

	protected List<List<IPServerLinkV3>> findTempLinksOnRouteNetwork(IPServerNodeV3 tempNode) {
		List<List<IPServerLinkV3>> resultList = new ArrayList<List<IPServerLinkV3>>();
		List<IPServerLinkV3> tempLinkArray = new ArrayList<IPServerLinkV3>();
		resultList.add(tempLinkArray);
		List<IPServerLinkV3> replacedLinkArray = new ArrayList<IPServerLinkV3>();
		resultList.add(replacedLinkArray);

		int floor = tempNode.m_floor;
		Point pos = tempNode.m_pos;

		for (int i = 0; i < m_linkArray.size(); ++i) {
			IPServerLinkV3 link = m_linkArray.get(i);
			if (link.m_floor != floor) {
				continue;
			}

			if (link.getLine().contains(pos)) {

			} else {
				double distance = DistanceOp.distance(pos, link.getLine());
				if (distance < 0.001 && distance > 0) {
					// System.out.println("distance < 0.001 && distance > 0");
				} else {
					continue;
				}
			}

			DistanceOp distanceOpOnLink = new DistanceOp(link.getLine(), pos);
			Coordinate[] closestCoordinatesOnLink = distanceOpOnLink.nearestPoints();
			Point npOnLink = factory.createPoint(closestCoordinatesOnLink[0]);

			Coordinate coord = npOnLink.getCoordinate();
			LocationIndexedLine indexedLine = new LocationIndexedLine(link.getLine());
			LinearLocation linearLocation = indexedLine.indexOf(coord);
			int index = linearLocation.getSegmentIndex();

			List<Coordinate> firstPartCoordinateList = new ArrayList<Coordinate>();
			List<Coordinate> secondPartCoordinateList = new ArrayList<Coordinate>();

			secondPartCoordinateList.add(coord);
			for (int j = 0; j < link.getLine().getNumPoints(); ++j) {
				if (j <= index) {
					firstPartCoordinateList.add(link.getLine().getCoordinateN(j));
				} else {
					secondPartCoordinateList.add(link.getLine().getCoordinateN(j));
				}
			}
			firstPartCoordinateList.add(coord);

			Coordinate[] firstPartSequence = (Coordinate[]) firstPartCoordinateList
					.toArray(new Coordinate[firstPartCoordinateList.size()]);
			Coordinate[] secondPartSequence = (Coordinate[]) secondPartCoordinateList
					.toArray(new Coordinate[secondPartCoordinateList.size()]);

			firstPartSequence = CoordinateArrays.removeRepeatedPoints(firstPartSequence);
			secondPartSequence = CoordinateArrays.removeRepeatedPoints(secondPartSequence);

			// Special Treatment
			if (firstPartSequence.length == 1 || secondPartSequence.length == 1) {
				// System.out.println("continue");
				continue;
			}

			LineString firstPartLineString = factory.createLineString(firstPartSequence);
			LineString secondPartLineString = factory.createLineString(secondPartSequence);

			IPServerLinkV3 firstPartLink = new IPServerLinkV3(createTempLinkID());
			firstPartLink.m_currentNodeID = link.m_currentNodeID;
			firstPartLink.m_nextNodeID = tempNode.getNodeID();
			firstPartLink.m_length = firstPartLineString.getLength();
			firstPartLink.setLine(firstPartLineString);
			firstPartLink.copyAttribute(link);

			IPServerLinkV3 secondPartLink = new IPServerLinkV3(createTempLinkID());
			secondPartLink.m_currentNodeID = tempNode.getNodeID();
			secondPartLink.m_nextNodeID = link.m_nextNodeID;
			secondPartLink.m_length = secondPartLineString.getLength();
			secondPartLink.setLine(secondPartLineString);
			secondPartLink.copyAttribute(link);

			tempLinkArray.add(firstPartLink);
			tempLinkArray.add(secondPartLink);
			replacedLinkArray.add(link);
		}
		return resultList;
	}

	protected IPServerNodeV3 processTempNodeForStart(TYLocalPoint startPoint) {
		// System.out.println("processTempNodeForStart");
		m_tempStartLinkArray.clear();
		m_tempStartNodeArray.clear();
		m_replacedStartLinkArray.clear();

		List<Object> nodeObjectList = findNodeOnRouteNetwork(startPoint);
		Boolean isExistingNode = (Boolean) nodeObjectList.get(0);
		if (isExistingNode) {
			return (IPServerNodeV3) nodeObjectList.get(1);
		}

		IPServerNodeV3 newTempNode = (IPServerNodeV3) nodeObjectList.get(1);
		List<List<IPServerLinkV3>> linkObjectList = findTempLinksOnRouteNetwork(newTempNode);
		m_tempStartNodeArray.add(newTempNode);
		m_tempStartLinkArray.addAll(linkObjectList.get(0));
		m_replacedStartLinkArray.addAll(linkObjectList.get(1));

		for (IPServerNodeV3 tempNode : m_tempStartNodeArray) {
			m_allNodeDict.put(tempNode.getNodeID(), tempNode);
		}

		for (IPServerLinkV3 newLink : m_tempStartLinkArray) {
			IPServerNodeV3 headNode = m_allNodeDict.get(newLink.m_currentNodeID);
			headNode.addLink(newLink);
			newLink.nextNode = m_allNodeDict.get(newLink.m_nextNodeID);
			m_linkArray.add(newLink);
		}

		for (IPServerLinkV3 replacedLink : m_replacedStartLinkArray) {
			IPServerNodeV3 headNode = m_allNodeDict.get(replacedLink.m_currentNodeID);
			headNode.removeLink(replacedLink);
			m_linkArray.remove(replacedLink);
		}
		// System.out.println(newTempNode);
		return newTempNode;
	}

	protected IPServerNodeV3 processTempNodeForEnd(TYLocalPoint endPoint) {
		// System.out.println("processTempNodeForEnd");
		m_tempEndLinkArray.clear();
		m_tempEndNodeArray.clear();
		m_replacedEndLinkArray.clear();

		List<Object> nodeObjectList = findNodeOnRouteNetwork(endPoint);
		Boolean isExistingNode = (Boolean) nodeObjectList.get(0);
		if (isExistingNode) {
			return (IPServerNodeV3) nodeObjectList.get(1);
		}

		IPServerNodeV3 newTempNode = (IPServerNodeV3) nodeObjectList.get(1);
		List<List<IPServerLinkV3>> linkObjectList = findTempLinksOnRouteNetwork(newTempNode);
		m_tempEndNodeArray.add(newTempNode);
		m_tempEndLinkArray.addAll(linkObjectList.get(0));
		m_replacedEndLinkArray.addAll(linkObjectList.get(1));

		for (IPServerNodeV3 tempNode : m_tempEndNodeArray) {
			m_allNodeDict.put(tempNode.getNodeID(), tempNode);
		}

		for (IPServerLinkV3 newLink : m_tempEndLinkArray) {
			IPServerNodeV3 headNode = m_allNodeDict.get(newLink.m_currentNodeID);
			headNode.addLink(newLink);
			newLink.nextNode = m_allNodeDict.get(newLink.m_nextNodeID);
			m_linkArray.add(newLink);
		}

		for (IPServerLinkV3 replacedLink : m_replacedEndLinkArray) {
			IPServerNodeV3 headNode = m_allNodeDict.get(replacedLink.m_currentNodeID);
			headNode.removeLink(replacedLink);
			m_linkArray.remove(replacedLink);
		}
		// System.out.println(newTempNode);
		return newTempNode;
	}

	protected void resetTempNodeForStart() {
		// System.out.println("resetTempNodeForStart");
		for (IPServerLinkV3 replacedLink : m_replacedStartLinkArray) {
			IPServerNodeV3 headNode = m_allNodeDict.get(replacedLink.m_currentNodeID);
			headNode.addLink(replacedLink);

			m_linkArray.add(replacedLink);
		}

		for (IPServerLinkV3 newLink : m_tempStartLinkArray) {
			IPServerNodeV3 headNode = m_allNodeDict.get(newLink.m_currentNodeID);
			headNode.removeLink(newLink);
			newLink.nextNode = null;
			newLink.nextNode = m_allNodeDict.get(newLink.m_nextNodeID);

			m_linkArray.remove(newLink);
		}

		for (IPServerNodeV3 node : m_tempStartNodeArray) {
			m_allNodeDict.remove(node.getNodeID());
		}

		m_replacedStartLinkArray.clear();
		m_tempStartLinkArray.clear();
		m_tempStartNodeArray.clear();
	}

	protected void resetTempNodeForEnd() {
		// System.out.println("resetTempNodeForEnd");
		for (IPServerLinkV3 replacedLink : m_replacedEndLinkArray) {
			IPServerNodeV3 headNode = m_allNodeDict.get(replacedLink.m_currentNodeID);
			headNode.addLink(replacedLink);

			m_linkArray.add(replacedLink);
		}

		for (IPServerLinkV3 newLink : m_tempEndLinkArray) {
			IPServerNodeV3 headNode = m_allNodeDict.get(newLink.m_currentNodeID);
			headNode.removeLink(newLink);
			newLink.nextNode = null;
			newLink.nextNode = m_allNodeDict.get(newLink.m_nextNodeID);

			m_linkArray.remove(newLink);
		}

		for (IPServerNodeV3 node : m_tempEndNodeArray) {
			m_allNodeDict.remove(node.getNodeID());
		}

		m_replacedEndLinkArray.clear();
		m_tempEndLinkArray.clear();
		m_tempEndNodeArray.clear();
	}

	public void reset() {
		for (IPServerNodeV3 node : m_nodeArray) {
			node.reset();
		}
	}

	protected void extractNodes(List<TYIRouteNodeRecordV3> nodes) {
		// System.out.println("extractNodes");
		for (int i = 0; i < nodes.size(); i++) {
			TYIRouteNodeRecordV3 nodeRecord = nodes.get(i);
			IPServerNodeV3 node = IPServerNodeV3.fromNodeRecord(nodeRecord);
			m_allNodeDict.put(node.getNodeID(), node);
			m_nodeArray.add(node);
			// System.out.println(node);
		}
	}

	protected void extractLinks(List<TYIRouteLinkRecordV3> links) {
		// System.out.println("extractLinks");
		for (int i = 0; i < links.size(); ++i) {
			TYIRouteLinkRecordV3 linkRecord = links.get(i);
			int floor = linkRecord.getFloor();

			List<IPServerLinkV3> list = m_allLinkCollectionDict.get(floor);
			if (list == null) {
				list = new ArrayList<IPServerLinkV3>();
				m_allLinkCollectionDict.put(floor, list);
			}

			{
				IPServerLinkV3 forwardLink = IPServerLinkV3.fromLinkRecord(linkRecord, true);
				// m_allLinkDict.put(forwardLink.getLinkKey(), forwardLink);
				m_linkArray.add(forwardLink);
				list.add(forwardLink);
				// System.out.println(forwardLink);
			}

			{
				IPServerLinkV3 reverseLink = IPServerLinkV3.fromLinkRecord(linkRecord, false);
				if (reverseLink != null) {
					// m_allLinkDict.put(reverseLink.getLinkKey(), reverseLink);
					m_linkArray.add(reverseLink);
					list.add(reverseLink);
					// System.out.println(reverseLink);
				}
			}
		}
	}

	protected void processSwitchingNodes() {
		// System.out.println("processSwitchingNodes");
		for (IPServerNodeV3 node : m_nodeArray) {
			if (node.m_isSwitching) {
				int switchingID = node.m_switchingID;
				List<IPServerNodeV3> list = m_allSwitchingNodeDict.get(switchingID);
				if (list == null) {
					list = new ArrayList<IPServerNodeV3>();
					m_allSwitchingNodeDict.put(switchingID, list);
				}
				list.add(node);
			}
		}

		for (int switchingID : m_allSwitchingNodeDict.keySet()) {
			List<IPServerNodeV3> list = m_allSwitchingNodeDict.get(switchingID);
			List<Integer> floorList = new ArrayList<Integer>();
			List<IPServerNodeV3> sortedList = new ArrayList<IPServerNodeV3>();
			Map<Integer, IPServerNodeV3> floorMap = new HashMap<Integer, IPServerNodeV3>();
			for (int i = 0; i < list.size(); ++i) {
				IPServerNodeV3 node = list.get(i);
				floorList.add(node.m_floor);
				floorMap.put(node.m_floor, node);
			}
			Collections.sort(floorList);

			for (int i = 0; i < floorList.size(); ++i) {
				sortedList.add(floorMap.get(floorList.get(i)));
			}
			m_allSwitchingNodeDict.put(switchingID, sortedList);
		}

		// System.out.println(m_allSwitchingNodeDict);
	}

	static final double costOfLengthForSwitching = 5;

	protected void processSwitchingNodesAndLinks() {
		// System.out.println("processSwitchingNodesAndLinks");
		for (int switchingID : m_allSwitchingNodeDict.keySet()) {
			List<IPServerNodeV3> list = m_allSwitchingNodeDict.get(switchingID);

			for (int i = 0; i < list.size(); ++i) {
				IPServerNodeV3 thisNode = list.get(i);
				for (int j = 0; j < list.size(); ++j) {
					if (i == j) {
						continue;
					}
					IPServerNodeV3 otherNode = list.get(j);

					// 0 for in/out, 1 for in only, 2 for out only
					if (thisNode.m_direction != 2 && otherNode.m_direction != 1) {
						IPServerLinkV3 vLink = new IPServerLinkV3(createVirtualLinkID());
						thisNode.addLink(vLink);
						vLink.m_floor = thisNode.m_floor;
						vLink.nextNode = otherNode;
						vLink.m_open = true;
						double distance = thisNode.m_pos.distance(otherNode.m_pos);
						vLink.m_length = distance + costOfLengthForSwitching;
						// vLink.m_length = 0;
						vLink.m_currentNodeID = thisNode.getNodeID();
						vLink.m_nextNodeID = otherNode.getNodeID();
						vLink.m_linkType = "11111111";
					}
				}
			}
		}
	}

	protected void processNodesAndLinks() {
		// System.out.println("processNodesAndLinks");
		for (IPServerLinkV3 link : m_linkArray) {
			IPServerNodeV3 headNode = m_allNodeDict.get(link.m_currentNodeID);
			IPServerNodeV3 endNode = m_allNodeDict.get(link.m_nextNodeID);

			headNode.addLink(link);
			link.nextNode = endNode;
		}
	}

	private String createTempLinkID() {
		return String.format("TLK%04d", ++m_tempLinkIndex);
	}

	private String createTempNodeID() {
		return String.format("TNE%04d", ++m_tempNodeIndex);
	}

	private String createVirtualLinkID() {
		return String.format("VLK%04d", ++m_virtualLinkIndex);
	}

	@Override
	public String toString() {
		return String.format("Route Network Dataset: %d Links and %d Nodes", m_linkArray.size(), m_nodeArray.size());
	}
}

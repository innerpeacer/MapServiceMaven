package cn.platalk.map.route.core;

import java.util.ArrayList;
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
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.linearref.LinearLocation;
import com.vividsolutions.jts.linearref.LocationIndexedLine;
import com.vividsolutions.jts.operation.distance.DistanceOp;
import com.vividsolutions.jts.operation.linemerge.LineMerger;

import cn.platalk.map.entity.base.TYIRouteLinkRecord;
import cn.platalk.map.entity.base.TYIRouteNodeRecord;

class IPServerRouteNetworkDataset {

	private List<IPServerLink> m_linkArray = new ArrayList<IPServerLink>();
	private List<IPServerLink> m_virtualLinkArray = new ArrayList<IPServerLink>();
	public List<IPServerNode> m_nodeArray = new ArrayList<IPServerNode>();
	private List<IPServerNode> m_virtualNodeArray = new ArrayList<IPServerNode>();

	private Map<String, IPServerLink> m_allLinkDict = new HashMap<String, IPServerLink>();
	private Map<Integer, IPServerNode> m_allNodeDict = new HashMap<Integer, IPServerNode>();

	private Geometry m_unionLine = null;

	GeometryFactory factory = new GeometryFactory();

	// Temporary Links and Nodes
	private List<IPServerNode> m_tempStartNodeArray = new ArrayList<IPServerNode>();
	private List<IPServerLink> m_tempStartLinkArray = new ArrayList<IPServerLink>();
	private List<IPServerLink> m_replacedStartLinkArray = new ArrayList<IPServerLink>();

	private List<IPServerNode> m_tempEndNodeArray = new ArrayList<IPServerNode>();
	private List<IPServerLink> m_tempEndLinkArray = new ArrayList<IPServerLink>();
	private List<IPServerLink> m_replacedEndLinkArray = new ArrayList<IPServerLink>();

	int m_tempNodeID;
	int m_tempLinkID;

	public IPServerRouteNetworkDataset(List<TYIRouteNodeRecord> nodes, List<TYIRouteLinkRecord> links) {
		m_tempNodeID = 60000;
		m_tempLinkID = 80000;

		extractNodes(nodes);
		extractLinks(links);
		processNodesAndLinks();

		List<LineString> linkLineVector = new ArrayList<LineString>();
		for (int i = 0; i < m_linkArray.size(); i++) {
			linkLineVector.add(m_linkArray.get(i).getLine());
		}
		LineMerger lineMerger = new LineMerger();
		lineMerger.add(linkLineVector);
		m_unionLine = factory.buildGeometry(lineMerger.getMergedLineStrings());
		// System.out.println("UnionLine: " + m_unionLine);
	}

	public synchronized LineString getShorestPath(Point start, Point end) {
		reset();

		if (m_linkArray.size() + m_virtualLinkArray.size() == 0
				|| m_nodeArray.size() + m_virtualNodeArray.size() == 0) {
			return null;
		}

		IPServerNode startNode = processTempNodeForStart(start);
		IPServerNode endNode = processTempNodeForEnd(end);

		computePaths(startNode);
		LineString nodePath = getShorestPathToNode(endNode);

		resetTempNodeForEnd();
		resetTempNodeForStart();

		if (nodePath.getNumPoints() == 0) {
			System.out.println("nodePath.getNumPoints() == 0");
			return null;
		}

		List<Coordinate> coordinateList = new ArrayList<Coordinate>();
		if (DistanceOp.distance(start, startNode.getPos()) > 0) {
			coordinateList.add(start.getCoordinate());
		}
		Collections.addAll(coordinateList, nodePath.getCoordinates());
		if (DistanceOp.distance(end, endNode.getPos()) > 0) {
			coordinateList.add(end.getCoordinate());
		}

		Coordinate[] coordinates = (Coordinate[]) coordinateList.toArray(new Coordinate[coordinateList.size()]);
		coordinates = CoordinateArrays.removeRepeatedPoints(coordinates);

		return factory.createLineString(coordinates);
	}

	@Override
	public String toString() {
		return String.format("Route Network Dataset: %d Links and %d Nodes",
				m_linkArray.size() + m_virtualLinkArray.size(), m_nodeArray.size() + m_virtualNodeArray.size());
	}

	protected void extractNodes(List<TYIRouteNodeRecord> nodes) {
		WKBReader reader = new WKBReader();
		try {
			for (int i = 0; i < nodes.size(); i++) {
				TYIRouteNodeRecord nodeRecord = nodes.get(i);
				// IPServerNode node = new IPServerNode(nodeRecord.nodeID,
				// nodeRecord.isVirtual);
				//
				// Point pos = (Point) reader.read(nodeRecord.nodeGeometryData);
				IPServerNode node = new IPServerNode(nodeRecord.getNodeID(), nodeRecord.isVirtual());

				Point pos = (Point) reader.read(nodeRecord.getGeometryData());
				node.setPos(pos);

				m_allNodeDict.put(node.getNodeID(), node);

				if (node.isVirtual()) {
					m_virtualNodeArray.add(node);
				} else {
					m_nodeArray.add(node);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void extractLinks(List<TYIRouteLinkRecord> links) {
		WKBReader reader = new WKBReader();
		try {
			for (int i = 0; i < links.size(); ++i) {
				// TYRouteLinkRecord linkRecord = links.get(i);
				// LineString line = (LineString) reader
				// .read(linkRecord.linkGeometryData);
				//
				// IPServerLink forwardLink = new
				// IPServerLink(linkRecord.linkID,
				// linkRecord.isVirtual);
				// forwardLink.currentNodeID = linkRecord.headNode;
				// forwardLink.nextNodeID = linkRecord.endNode;
				// forwardLink.length = linkRecord.length;

				TYIRouteLinkRecord linkRecord = links.get(i);
				LineString line = (LineString) reader.read(linkRecord.getGeometryData());

				IPServerLink forwardLink = new IPServerLink(linkRecord.getLinkID(), linkRecord.isVirtual());
				forwardLink.currentNodeID = linkRecord.getHeadNode();
				forwardLink.nextNodeID = linkRecord.getEndNode();
				forwardLink.length = linkRecord.getLength();

				forwardLink.setLine(line);

				String forwardLinkKey = forwardLink.currentNodeID + "" + forwardLink.nextNodeID;
				m_allLinkDict.put(forwardLinkKey, forwardLink);

				if (forwardLink.isVirtual()) {
					m_virtualLinkArray.add(forwardLink);
				} else {
					m_linkArray.add(forwardLink);
				}

				// if (!linkRecord.isOneWay) {
				// IPServerLink reverseLink = new IPServerLink(
				// linkRecord.linkID, linkRecord.isVirtual);
				// reverseLink.currentNodeID = linkRecord.endNode;
				// reverseLink.nextNodeID = linkRecord.headNode;
				// reverseLink.length = linkRecord.length;
				if (!linkRecord.isOneWay()) {
					IPServerLink reverseLink = new IPServerLink(linkRecord.getLinkID(), linkRecord.isVirtual());
					reverseLink.currentNodeID = linkRecord.getEndNode();
					reverseLink.nextNodeID = linkRecord.getHeadNode();
					reverseLink.length = linkRecord.getLength();
					reverseLink.setLine((LineString) line.reverse());

					String reverseLinkKey = reverseLink.currentNodeID + "" + reverseLink.nextNodeID;
					m_allLinkDict.put(reverseLinkKey, reverseLink);

					if (reverseLink.isVirtual()) {
						m_virtualLinkArray.add(reverseLink);
					} else {
						m_linkArray.add(reverseLink);
					}

				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void processNodesAndLinks() {
		for (IPServerLink link : m_allLinkDict.values()) {
			IPServerNode headNode = m_allNodeDict.get(link.currentNodeID);
			IPServerNode endNode = m_allNodeDict.get(link.nextNodeID);

			headNode.addLink(link);
			link.nextNode = endNode;
		}
	}

	public void computePaths(IPServerNode source) {
		source.minDistance = 0;
		PriorityQueue<IPServerNode> nodeQueue = new PriorityQueue<IPServerNode>();
		nodeQueue.add(source);

		while (!nodeQueue.isEmpty()) {
			IPServerNode u = nodeQueue.poll();

			for (IPServerLink e : u.adjacencies) {
				IPServerNode v = e.nextNode;
				double length = e.length;
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

	public LineString getShorestPathToNode(IPServerNode target) {
		List<IPServerNode> path = new ArrayList<IPServerNode>();
		for (IPServerNode node = target; node != null; node = node.previousNode) {
			path.add(node);
		}
		Collections.reverse(path);

		List<Coordinate> coordinateList = new ArrayList<Coordinate>();
		for (int i = 0; i < path.size(); ++i) {
			IPServerNode node = path.get(i);
			if (node != null && node.previousNode != null) {
				String linkKey = node.previousNode.getNodeID() + "" + node.getNodeID();
				IPServerLink link = m_allLinkDict.get(linkKey);
				LineString linkLine = link.getLine();
				Collections.addAll(coordinateList, linkLine.getCoordinates());
			}
		}
		Coordinate[] coordinates = (Coordinate[]) coordinateList.toArray(new Coordinate[coordinateList.size()]);
		coordinates = CoordinateArrays.removeRepeatedPoints(coordinates);
		return factory.createLineString(coordinates);
	}

	public void reset() {
		for (IPServerNode node : m_nodeArray) {
			node.reset();
		}

		for (IPServerNode node : m_virtualNodeArray) {
			node.reset();
		}
	}

	protected IPServerNode processTempNodeForStart(Point startPoint) {
		m_tempStartLinkArray.clear();
		m_tempStartNodeArray.clear();
		m_replacedStartLinkArray.clear();

		DistanceOp distanceOp = new DistanceOp(m_unionLine, startPoint);
		// Coordinate[] closestCoordinates = distanceOp.closestPoints();
		Coordinate[] closestCoordinates = distanceOp.nearestPoints();
		Point npOnUnionLine = factory.createPoint(closestCoordinates[0]);

		for (int i = 0; i < m_nodeArray.size(); i++) {
			IPServerNode node = m_nodeArray.get(i);
			Coordinate c = node.getPos().getCoordinate();
			if (npOnUnionLine.getCoordinate().equals(c)) {
				// System.out.println("Equal");
				return node;
			}
		}

		IPServerNode newTempNode = new IPServerNode(m_tempNodeID, false);
		m_tempNodeID++;
		newTempNode.setPos(npOnUnionLine);
		m_tempStartNodeArray.add(newTempNode);

		for (int i = 0; i < m_linkArray.size(); ++i) {
			IPServerLink link = m_linkArray.get(i);
			if (link.getLine().contains(npOnUnionLine)) {

			} else {
				double distance = DistanceOp.distance(npOnUnionLine, link.getLine());
				if (distance < 0.001 && distance > 0) {
					// System.out.println("distance < 0.001 && distance > 0");
				} else {
					continue;
				}
			}

			DistanceOp distanceOpOnLink = new DistanceOp(link.getLine(), npOnUnionLine);
			// Coordinate[] closestCoordinatesOnLink = distanceOpOnLink
			// .closestPoints();
			Coordinate[] closestCoordinatesOnLink = distanceOpOnLink.nearestPoints();
			Point npOnLink = factory.createPoint(closestCoordinatesOnLink[0]);

			Coordinate coord = npOnLink.getCoordinate();
			// GeometryLocation location = new GeometryLocation(link.getLine(),
			// coord);
			// int index = location.getSegmentIndex();
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
			// System.out.println("Length: ");
			// System.out.println(firstPartSequence.length);
			// System.out.println(secondPartSequence.length);

			// Special Treatment
			if (firstPartSequence.length == 1 || secondPartSequence.length == 1) {
				// System.out.println("continue");
				continue;
			}

			LineString firstPartLineString = factory.createLineString(firstPartSequence);
			LineString secondPartLineString = factory.createLineString(secondPartSequence);

			IPServerLink firstPartLink = new IPServerLink(m_tempLinkID, false);
			firstPartLink.currentNodeID = link.currentNodeID;
			firstPartLink.nextNodeID = newTempNode.getNodeID();
			firstPartLink.length = firstPartLineString.getLength();
			firstPartLink.setLine(firstPartLineString);

			IPServerLink secondPartLink = new IPServerLink(m_tempLinkID, false);
			secondPartLink.currentNodeID = newTempNode.getNodeID();
			secondPartLink.nextNodeID = link.nextNodeID;
			secondPartLink.length = secondPartLineString.getLength();
			secondPartLink.setLine(secondPartLineString);

			m_tempLinkID++;

			m_tempStartLinkArray.add(firstPartLink);
			m_tempStartLinkArray.add(secondPartLink);
			m_replacedStartLinkArray.add(link);
		}

		for (IPServerNode tempNode : m_tempStartNodeArray) {
			m_allNodeDict.put(tempNode.getNodeID(), tempNode);
		}

		for (IPServerLink newLink : m_tempStartLinkArray) {
			IPServerNode headNode = m_allNodeDict.get(newLink.currentNodeID);
			headNode.addLink(newLink);
			newLink.nextNode = m_allNodeDict.get(newLink.nextNodeID);

			String newLinkKey = newLink.currentNodeID + "" + newLink.nextNodeID;
			m_allLinkDict.put(newLinkKey, newLink);

			m_linkArray.add(newLink);
		}

		for (IPServerLink replacedLink : m_replacedStartLinkArray) {
			IPServerNode headNode = m_allNodeDict.get(replacedLink.currentNodeID);
			headNode.removeLink(replacedLink);

			String replacedLinkKey = replacedLink.currentNodeID + "" + replacedLink.nextNodeID;
			m_allLinkDict.remove(replacedLinkKey);
			m_linkArray.remove(replacedLink);
		}
		return newTempNode;
	}

	protected IPServerNode processTempNodeForEnd(Point endPoint) {
		m_tempEndLinkArray.clear();
		m_tempEndNodeArray.clear();
		m_replacedEndLinkArray.clear();

		DistanceOp distanceOp = new DistanceOp(m_unionLine, endPoint);
		// Coordinate[] closestCoordinates = distanceOp.closestPoints();
		Coordinate[] closestCoordinates = distanceOp.nearestPoints();

		Point npOnUnionLine = factory.createPoint(closestCoordinates[0]);

		for (int i = 0; i < m_nodeArray.size(); i++) {
			IPServerNode node = m_nodeArray.get(i);
			Coordinate c = node.getPos().getCoordinate();
			if (npOnUnionLine.getCoordinate().equals(c)) {
				// System.out.println("Equal");
				return node;
			}
		}

		IPServerNode newTempNode = new IPServerNode(m_tempNodeID, false);
		m_tempNodeID++;
		newTempNode.setPos(npOnUnionLine);
		m_tempEndNodeArray.add(newTempNode);

		for (int i = 0; i < m_linkArray.size(); ++i) {
			IPServerLink link = m_linkArray.get(i);
			if (link.getLine().contains(npOnUnionLine)) {

			} else {
				double distance = DistanceOp.distance(npOnUnionLine, link.getLine());
				if (distance < 0.001 && distance > 0) {

				} else {
					continue;
				}
			}

			DistanceOp distanceOpOnLink = new DistanceOp(link.getLine(), npOnUnionLine);
			// Coordinate[] closestCoordinatesOnLink = distanceOpOnLink
			// .closestPoints();
			Coordinate[] closestCoordinatesOnLink = distanceOpOnLink.nearestPoints();
			Point npOnLink = factory.createPoint(closestCoordinatesOnLink[0]);

			Coordinate coord = npOnLink.getCoordinate();
			// GeometryLocation location = new GeometryLocation(link.getLine(),
			// coord);
			// int index = location.getSegmentIndex();
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

			IPServerLink firstPartLink = new IPServerLink(m_tempLinkID, false);
			firstPartLink.currentNodeID = link.currentNodeID;
			firstPartLink.nextNodeID = newTempNode.getNodeID();
			firstPartLink.length = firstPartLineString.getLength();
			firstPartLink.setLine(firstPartLineString);

			IPServerLink secondPartLink = new IPServerLink(m_tempLinkID, false);
			secondPartLink.currentNodeID = newTempNode.getNodeID();
			secondPartLink.nextNodeID = link.nextNodeID;
			secondPartLink.length = secondPartLineString.getLength();
			secondPartLink.setLine(secondPartLineString);

			m_tempLinkID++;

			m_tempEndLinkArray.add(firstPartLink);
			m_tempEndLinkArray.add(secondPartLink);
			m_replacedEndLinkArray.add(link);
		}

		for (IPServerNode tempNode : m_tempEndNodeArray) {
			m_allNodeDict.put(tempNode.getNodeID(), tempNode);
		}

		for (IPServerLink newLink : m_tempEndLinkArray) {
			IPServerNode headNode = m_allNodeDict.get(newLink.currentNodeID);
			headNode.addLink(newLink);
			newLink.nextNode = m_allNodeDict.get(newLink.nextNodeID);

			String newLinkKey = newLink.currentNodeID + "" + newLink.nextNodeID;
			m_allLinkDict.put(newLinkKey, newLink);

			m_linkArray.add(newLink);
		}

		for (IPServerLink replacedLink : m_replacedEndLinkArray) {
			IPServerNode headNode = m_allNodeDict.get(replacedLink.currentNodeID);
			headNode.removeLink(replacedLink);

			String replacedLinkKey = replacedLink.currentNodeID + "" + replacedLink.nextNodeID;
			m_allLinkDict.remove(replacedLinkKey);
			m_linkArray.remove(replacedLink);
		}
		return newTempNode;
	}

	protected void resetTempNodeForStart() {
		for (IPServerLink replacedLink : m_replacedStartLinkArray) {
			IPServerNode headNode = m_allNodeDict.get(replacedLink.currentNodeID);
			headNode.addLink(replacedLink);

			String replacedLinkKey = replacedLink.currentNodeID + "" + replacedLink.nextNodeID;
			m_allLinkDict.put(replacedLinkKey, replacedLink);
			m_linkArray.add(replacedLink);
		}

		for (IPServerLink newLink : m_tempStartLinkArray) {
			IPServerNode headNode = m_allNodeDict.get(newLink.currentNodeID);
			headNode.removeLink(newLink);
			newLink.nextNode = null;
			newLink.nextNode = m_allNodeDict.get(newLink.nextNodeID);

			String newLinkKey = newLink.currentNodeID + "" + newLink.nextNodeID;
			m_allLinkDict.remove(newLinkKey);
			m_linkArray.remove(newLink);
		}

		for (IPServerNode node : m_tempStartNodeArray) {
			m_allNodeDict.remove(node.getNodeID());
		}

		m_replacedStartLinkArray.clear();
		m_tempStartLinkArray.clear();
		m_tempStartNodeArray.clear();
	}

	protected void resetTempNodeForEnd() {
		for (IPServerLink replacedLink : m_replacedEndLinkArray) {
			IPServerNode headNode = m_allNodeDict.get(replacedLink.currentNodeID);
			headNode.addLink(replacedLink);

			String replacedLinkKey = replacedLink.currentNodeID + "" + replacedLink.nextNodeID;
			m_allLinkDict.put(replacedLinkKey, replacedLink);
			m_linkArray.add(replacedLink);
		}

		for (IPServerLink newLink : m_tempEndLinkArray) {
			IPServerNode headNode = m_allNodeDict.get(newLink.currentNodeID);
			headNode.removeLink(newLink);
			newLink.nextNode = null;
			newLink.nextNode = m_allNodeDict.get(newLink.nextNodeID);

			String newLinkKey = newLink.currentNodeID + "" + newLink.nextNodeID;
			m_allLinkDict.remove(newLinkKey);
			m_linkArray.remove(newLink);
		}

		for (IPServerNode node : m_tempEndNodeArray) {
			m_allNodeDict.remove(node.getNodeID());
		}

		m_replacedEndLinkArray.clear();
		m_tempEndLinkArray.clear();
		m_tempEndNodeArray.clear();
	}

	// protected IPServerNode getTempNode(Point point) {
	// return null;
	// }
	//
	// List<IPServerLink> getTempLinks(Point point) {
	// return null;
	// }

}

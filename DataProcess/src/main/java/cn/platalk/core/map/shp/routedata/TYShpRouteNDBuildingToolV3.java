package cn.platalk.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vividsolutions.jts.geom.Point;

import cn.platalk.map.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.map.entity.base.impl.TYRouteLinkRecordV3;
import cn.platalk.map.entity.base.impl.TYRouteNodeRecordV3;

public class TYShpRouteNDBuildingToolV3 {
	final TYShpRouteDataGroupV3 shpDataGroup;

	final List<TYShpBuildingLinkV3> allLinkArray = new ArrayList<>();
	final List<TYShpBuildingNodeV3> allNodeArray = new ArrayList<>();

	List<TYIRouteLinkRecordV3> resultLinkList = new ArrayList<>();
	List<TYIRouteNodeRecordV3> resultNodeList = new ArrayList<>();

	private int errorLinkCount = 0;
	private int errorNodeCount = 0;

	public TYShpRouteNDBuildingToolV3(TYShpRouteDataGroupV3 dataGroup) {
		this.shpDataGroup = dataGroup;
	}

	private void checkNodeDuplicate() {
		System.out.println("checkNodeDuplicate");
		Set<String> nodeCoordSet = new HashSet<>();
		for (TYShpBuildingNodeV3 node : shpDataGroup.nodeArray) {
			String cs = String.format("%d-%f%f", node.floor, node.pos.getX(), node.pos.getY());
			nodeCoordSet.add(cs);
		}

		Map<String, List<TYShpBuildingNodeV3>> nodeMap = new HashMap<>();
		for (TYShpBuildingNodeV3 node : shpDataGroup.nodeArray) {
			String cs = String.format("%d-%f%f", node.floor, node.pos.getX(), node.pos.getY());
			List<TYShpBuildingNodeV3> nodeList = nodeMap.get(cs);
			if (nodeList == null) {
				nodeList = new ArrayList<>();
				nodeMap.put(cs, nodeList);
			}
			nodeList.add(node);
		}

		if (nodeCoordSet.size() != shpDataGroup.nodeArray.size()) {
			for (List<TYShpBuildingNodeV3> nodeList : nodeMap.values()) {
				if (nodeList.size() > 1) {
					errorNodeCount++;
					System.out.println("-------- Duplicate Node---------------");
					for (TYShpBuildingNodeV3 node : nodeList) {
						System.out.println(node.nodeID);
					}
				}
				// break;
			}
			System.out.println("Error: " + errorNodeCount + " Duplicate Node!");
		} else {
			System.out.println("No Duplicate Node");
		}
	}

	private List<TYShpBuildingNodeV3> noneDuplicatedNodes(List<TYShpBuildingNodeV3> list) {
		List<TYShpBuildingNodeV3> resultList = new ArrayList<>();

		Set<String> nodeCoordSet = new HashSet<>();
		for (TYShpBuildingNodeV3 node : list) {
			String cs = String.format("%d-%f%f", node.floor, node.pos.getX(), node.pos.getY());
			if (!nodeCoordSet.contains(cs)) {
				resultList.add(node);
				nodeCoordSet.add(cs);
			}
		}
		return resultList;
	}

	public void buildNetworkDataset() {
		System.out.println("buildNetworkDataset");
		errorLinkCount = 0;
		errorNodeCount = 0;

		checkNodeDuplicate();

		allNodeArray.clear();
		// allNodeArray.addAll(shpDataGroup.nodeArray);
		allNodeArray.addAll(noneDuplicatedNodes(shpDataGroup.nodeArray));

		allLinkArray.clear();
		allLinkArray.addAll(shpDataGroup.linkArray);

		// for (TYBrtShpBuildingLinkV3 link : allLinkArray) {
		// System.out.println(link);
		// }
		//
		// for (TYBrtShpBuildingNodeV3 node : allNodeArray) {
		// System.out.println(node);
		// }

		for (TYShpBuildingLinkV3 link : allLinkArray) {
			Point linkHeadPoint = link.line.getPointN(0);
			Point linkEndPoint = link.line.getPointN(link.line.getNumPoints() - 1);

			TYShpBuildingNodeV3 headNode = null;
			TYShpBuildingNodeV3 endNode = null;

			boolean headFound = false;
			boolean endFound = false;

			for (TYShpBuildingNodeV3 node : allNodeArray) {
				if (node.floor != link.floor) {
					continue;
				}

				double headDistance = linkHeadPoint.distance(node.pos);
				double endDistance = linkEndPoint.distance(node.pos);

				if (headDistance == 0) {
					headNode = node;
					headFound = true;
				} else if (endDistance == 0) {
					endNode = node;
					endFound = true;
				}

				if (headFound && endFound) {
					break;
				}
			}

			if (headFound) {
				link.headNodeID = headNode.nodeID;
				headNode.addLink(link);
			}

			if (endFound) {
				link.endNodeID = endNode.nodeID;
				endNode.addLink(link);
			}

			if (!headFound || !endFound) {
				errorLinkCount++;
				System.out.println("Error In Link: " + link.linkID + ", Floor: " + link.floor);
				Throwable e = new Throwable("Error In Link: " + link.linkID);
				notifyFailedBuilding(e);
			}
//			else {
				// System.out.println("Found Link: " + link.linkID + ", Floor: "
				// + link.floor);
//			}
		}

		System.out.println(errorLinkCount + " Error Links!");
		resultLinkList = new ArrayList<>();
		resultNodeList = new ArrayList<>();

		for (TYShpBuildingLinkV3 link : allLinkArray) {
			resultLinkList.add(link.toLink(new TYRouteLinkRecordV3()));
		}

		for (TYShpBuildingNodeV3 node : allNodeArray) {
			resultNodeList.add(node.toNode(new TYRouteNodeRecordV3()));
		}

		List<TYIRouteLinkRecordV3> toRemove = new ArrayList<>();
		for (TYIRouteLinkRecordV3 link : resultLinkList) {
			// System.out.println(link);
			if (link.getHeadNode() == null || link.getEndNode() == null) {
				toRemove.add(link);
			}
		}
		resultLinkList.removeAll(toRemove);

		// for (TYRouteNodeRecordV3 node : resultNodeList) {
		// System.out.println(node);
		// }

		// if (success) {
		// notifyFinishBuilding(resultLinkList, resultNodeList);
		// } else {
		// Throwable e = new Throwable(errorLinkCount + " error links, " +
		// errorNodeCount + " error nodes!");
		// notifyFailedBuilding(e);
		// }
		notifyFinishBuilding(resultLinkList, resultNodeList);
	}

	private final List<TYBrtRouteNDBuildingListenerV3> listeners = new ArrayList<>();

	public void addRouteNDBuildingListener(TYBrtRouteNDBuildingListenerV3 listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeRouteNDBuildingListener(TYBrtRouteNDBuildingListenerV3 listener) {
        listeners.remove(listener);
	}

	private void notifyFinishBuilding(List<TYIRouteLinkRecordV3> linkList, List<TYIRouteNodeRecordV3> nodeList) {
		for (TYBrtRouteNDBuildingListenerV3 listener : listeners) {
			listener.didFinishBuildingRouteND(linkList, nodeList);
		}
	}

	private void notifyFailedBuilding(Throwable error) {
		for (TYBrtRouteNDBuildingListenerV3 listener : listeners) {
			listener.didFailedBuildingRouteND(error);
		}
	}

	public interface TYBrtRouteNDBuildingListenerV3 {
		void didFinishBuildingRouteND(List<TYIRouteLinkRecordV3> linkList, List<TYIRouteNodeRecordV3> nodeList);

		void didFailedBuildingRouteND(Throwable error);
	}
}

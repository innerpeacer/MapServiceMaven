package cn.platalk.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.impl.map.TYRouteLinkRecord;
import cn.platalk.map.entity.base.impl.map.TYRouteNodeRecord;
import com.vividsolutions.jts.geom.Point;

public class TYShpRouteNDBuildingTool {

	final TYShpRouteDataGroup shpDataGroup;

	final List<TYShpBuildingLink> allLinkArray = new ArrayList<>();
	final List<TYShpBuildingNode> allNodeArray = new ArrayList<>();

	List<TYRouteLinkRecord> resultLinkList;
	List<TYRouteNodeRecord> resultNodeList;

	public TYShpRouteNDBuildingTool(TYShpRouteDataGroup dataGroup) {
		this.shpDataGroup = dataGroup;
	}

	public void buildNetworkDataset() {
		// System.out.println("buildNetworkDataset");

		allNodeArray.clear();
		allNodeArray.addAll(shpDataGroup.nodeArray);
		allNodeArray.addAll(shpDataGroup.virtualNodeArray);
		allNodeArray.addAll(shpDataGroup.junctionArray);

		allLinkArray.clear();
		allLinkArray.addAll(shpDataGroup.linkArray);
		allLinkArray.addAll(shpDataGroup.virtualLinkArray);

		for (TYShpBuildingLink link : allLinkArray) {
			// Point linkHeadPoint = link.line.getPoint(0);
			// Point linkEndPoint = link.line
			// .getPoint(link.line.getPointCount() - 1);
			Point linkHeadPoint = link.line.getPointN(0);
			Point linkEndPoint = link.line
					.getPointN(link.line.getNumPoints() - 1);

			TYShpBuildingNode headNode = null;
			TYShpBuildingNode endNode = null;

			boolean headFound = false;
			boolean endFound = false;

			for (TYShpBuildingNode node : allNodeArray) {
				// double headDistance = GeometryEngine.distance(linkHeadPoint,
				// node.pos, null);
				// double endDistance = GeometryEngine.distance(linkEndPoint,
				// node.pos, null);

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
				System.out.println("Error In Link: " + link.linkID);
				Throwable e = new Throwable("Error In Link: " + link.linkID);
				notifyFailedBuilding(e);
			}
		}

		resultLinkList = new ArrayList<>();
		resultNodeList = new ArrayList<>();

		for (TYShpBuildingLink link : allLinkArray) {
			TYRouteLinkRecord linkRecord = new TYRouteLinkRecord();
			linkRecord.linkID = link.linkID;
			linkRecord.linkGeometryData = link.geometryData;
			linkRecord.length = link.length;
			linkRecord.headNode = link.headNodeID;
			linkRecord.endNode = link.endNodeID;
			linkRecord.isVirtual = link.isVirtual;
			linkRecord.isOneWay = link.isOneWay;
			resultLinkList.add(linkRecord);
		}

		for (TYShpBuildingNode node : allNodeArray) {
			TYRouteNodeRecord nodeRecord = new TYRouteNodeRecord();
			nodeRecord.nodeID = node.nodeID;
			nodeRecord.nodeGeometryData = node.geometryData;
			nodeRecord.isVirtual = node.isVirtual;
			resultNodeList.add(nodeRecord);
		}

		notifyFinishBuilding(resultLinkList, resultNodeList);
	}

	private final List<TYBrtRouteNDBuildingListener> listeners = new ArrayList<>();

	public void addRouteNDBuildingListener(TYBrtRouteNDBuildingListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeRouteNDBuildingListener(
			TYBrtRouteNDBuildingListener listener) {
		listeners.remove(listener);
	}

	private void notifyFinishBuilding(List<TYRouteLinkRecord> linkList,
			List<TYRouteNodeRecord> nodeList) {
		for (TYBrtRouteNDBuildingListener listener : listeners) {
			listener.didFinishBuildingRouteND(linkList, nodeList);
		}
	}

	private void notifyFailedBuilding(Throwable error) {
		for (TYBrtRouteNDBuildingListener listener : listeners) {
			listener.didFailedBuildingRouteND(error);
		}
	}

	public interface TYBrtRouteNDBuildingListener {
		void didFinishBuildingRouteND(List<TYRouteLinkRecord> linkList,
									  List<TYRouteNodeRecord> nodeList);

		void didFailedBuildingRouteND(Throwable error);
	}

}

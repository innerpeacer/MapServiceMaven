package cn.platalk.brtmap.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Point;

import cn.platalk.map.entity.base.impl.TYRouteLinkRecord;
import cn.platalk.map.entity.base.impl.TYRouteNodeRecord;

public class TYBrtShpRouteNDBuildingTool {

	TYBrtShpRouteDataGroup shpDataGroup;

	List<TYBrtShpBuildingLink> allLinkArray = new ArrayList<TYBrtShpBuildingLink>();
	List<TYBrtShpBuildingNode> allNodeArray = new ArrayList<TYBrtShpBuildingNode>();

	List<TYRouteLinkRecord> resultLinkList;
	List<TYRouteNodeRecord> resultNodeList;

	public TYBrtShpRouteNDBuildingTool(TYBrtShpRouteDataGroup dataGroup) {
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

		for (TYBrtShpBuildingLink link : allLinkArray) {
			// Point linkHeadPoint = link.line.getPoint(0);
			// Point linkEndPoint = link.line
			// .getPoint(link.line.getPointCount() - 1);
			Point linkHeadPoint = link.line.getPointN(0);
			Point linkEndPoint = link.line
					.getPointN(link.line.getNumPoints() - 1);

			TYBrtShpBuildingNode headNode = null;
			TYBrtShpBuildingNode endNode = null;

			boolean headFound = false;
			boolean endFound = false;

			for (TYBrtShpBuildingNode node : allNodeArray) {
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

		resultLinkList = new ArrayList<TYRouteLinkRecord>();
		resultNodeList = new ArrayList<TYRouteNodeRecord>();

		for (TYBrtShpBuildingLink link : allLinkArray) {
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

		for (TYBrtShpBuildingNode node : allNodeArray) {
			TYRouteNodeRecord nodeRecord = new TYRouteNodeRecord();
			nodeRecord.nodeID = node.nodeID;
			nodeRecord.nodeGeometryData = node.geometryData;
			nodeRecord.isVirtual = node.isVirutal;
			resultNodeList.add(nodeRecord);
		}

		notifyFinishBuilding(resultLinkList, resultNodeList);
	}

	private List<TYBrtRouteNDBuildingListener> listeners = new ArrayList<TYBrtShpRouteNDBuildingTool.TYBrtRouteNDBuildingListener>();

	public void addRouteNDBuildingListener(TYBrtRouteNDBuildingListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeRouteNDBuildingListener(
			TYBrtRouteNDBuildingListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
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
		public void didFinishBuildingRouteND(List<TYRouteLinkRecord> linkList,
				List<TYRouteNodeRecord> nodeList);

		public void didFailedBuildingRouteND(Throwable error);
	}

}

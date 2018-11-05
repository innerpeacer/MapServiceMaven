package cn.platalk.brtmap.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.brtmap.core.map.base.TYIBrtShpDataManager;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteDataGroupV3.TYBrtShpRouteDataGroupListenerV3;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteNDBuildingToolV3.TYBrtRouteNDBuildingListenerV3;
import cn.platalk.brtmap.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.impl.TYMapInfo;

public class TYBrtShpRouteTaskV3 implements TYBrtShpRouteDataGroupListenerV3, TYBrtRouteNDBuildingListenerV3 {
	static WKBReader wkb = new WKBReader();
	TYIBrtShpDataManager shpDataManager;

	List<TYIRouteLinkRecordV3> linkRecords;
	List<TYIRouteNodeRecordV3> nodeRecords;

	List<TYMapDataFeatureRecord> mapDataList = new ArrayList<TYMapDataFeatureRecord>();

	TYBrtShpRouteDataGroupV3 shpDataGroup;
	TYBrtShpRouteNDBuildingToolV3 buildingTool;

	public TYBrtShpRouteTaskV3(TYIBrtShpDataManager manager) {
		this.shpDataManager = manager;

		shpDataGroup = new TYBrtShpRouteDataGroupV3();
		shpDataGroup.setShpDataManager(this.shpDataManager);
		shpDataGroup.addDataGroupListener(this);
	}

	public void setMapInfos(List<TYMapInfo> infos) {
		this.shpDataGroup.setMapInfos(infos);
	}

	public void setMapData(List<TYMapDataFeatureRecord> mapData) {
		mapDataList.addAll(mapData);
	}

	// public void applyMapData(List<TYMapDataFeatureRecord> mapRecords) {
	// mapDataList.addAll(mapRecords);
	// }

	public void startProcessRouteShp() {
		// System.out.println("startProcessRouteShp");
		// shpDataGroup = new TYBrtShpRouteDataGroupV3();
		// shpDataGroup.setShpDataManager(this.shpDataManager);
		// shpDataGroup.addDataGroupListener(this);
		shpDataGroup.startParsingRouteShp();
	}

	@Override
	public void didFinishFetchDataGroup() {
		System.out.println("didFinishFetchDataGroup");
		buildingTool = new TYBrtShpRouteNDBuildingToolV3(shpDataGroup);
		buildingTool.addRouteNDBuildingListener(this);
		buildingTool.buildNetworkDataset();
	}

	private Geometry getLinkRecordGeometry(TYIRouteLinkRecordV3 record) {
		Geometry geometry = null;
		byte[] linkGeometryData = record.getGeometryData();
		if (linkGeometryData != null) {
			try {
				geometry = wkb.read(linkGeometryData);
			} catch (ParseException e) {
				geometry = null;
				e.printStackTrace();
			}
		}
		return geometry;
	}

	private Geometry getNodeRecordGeomtry(TYIRouteNodeRecordV3 record) {
		Geometry geometry = null;
		byte[] nodeGeometryData = record.getGeometryData();
		if (nodeGeometryData != null) {
			try {
				geometry = wkb.read(nodeGeometryData);
			} catch (ParseException e) {
				geometry = null;
				e.printStackTrace();
			}
		}
		return geometry;
	}

	private void applyMapData(List<TYIRouteLinkRecordV3> linkList, List<TYIRouteNodeRecordV3> nodeList,
			List<TYMapDataFeatureRecord> recordList) {
		Map<String, Geometry> geometryMap = new HashMap<String, Geometry>();
		for (TYMapDataFeatureRecord record : recordList) {
			if (record.layer == 2 && !record.categoryID.equals("000800")) {
				geometryMap.put(record.poiID, record.getGeometryData().buffer(0.1));
			}
		}

		for (TYIRouteLinkRecordV3 link : linkList) {
			for (TYMapDataFeatureRecord record : recordList) {
				if (link.getFloor() != record.floorNumber || record.layer != 2)
					continue;

				Geometry linkGeometry = getLinkRecordGeometry(link);
				// Geometry roomGeometry = record.getGeometryData();
				Geometry roomGeometry = geometryMap.get(record.poiID);

				if (linkGeometry != null && roomGeometry != null) {
					if (roomGeometry.covers(linkGeometry)) {
						// link.roomID = record.poiID;
						link.setRoomID(record.poiID);
					}
				}
			}
		}

		for (TYIRouteNodeRecordV3 node : nodeList) {
			for (TYMapDataFeatureRecord record : recordList) {
				if (node.getFloor() != record.floorNumber || record.layer != 2)
					continue;

				Geometry nodeGeometry = getNodeRecordGeomtry(node);
				// Geometry roomGeometry = record.getGeometryData();
				Geometry roomGeometry = geometryMap.get(record.poiID);

				if (nodeGeometry != null && roomGeometry != null) {
					if (roomGeometry.contains(nodeGeometry)) {
						// node.getRoomID() = record.poiID;
						node.setRoomID(record.poiID);
					}
				}
			}
		}
	}

	@Override
	public void didFinishBuildingRouteND(List<TYIRouteLinkRecordV3> linkList, List<TYIRouteNodeRecordV3> nodeList) {
		// System.out.println("didFinishBuildingRouteND");

		linkRecords = new ArrayList<TYIRouteLinkRecordV3>();
		linkRecords.addAll(linkList);

		nodeRecords = new ArrayList<TYIRouteNodeRecordV3>();
		nodeRecords.addAll(nodeList);

		applyMapData(linkList, nodeList, mapDataList);

		notifyFinishRouteTask(linkRecords, nodeRecords);
	}

	@Override
	public void didFailedBuildingRouteND(Throwable error) {
		notifyFailedRouteTask(error);
	}

	private List<TYBrtRouteShpTaskListenerV3> listeners = new ArrayList<TYBrtShpRouteTaskV3.TYBrtRouteShpTaskListenerV3>();

	public void addTaskListener(TYBrtRouteShpTaskListenerV3 listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeTaskListener(TYBrtRouteShpTaskListenerV3 listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	private void notifyFinishRouteTask(List<TYIRouteLinkRecordV3> links, List<TYIRouteNodeRecordV3> nodes) {
		for (TYBrtRouteShpTaskListenerV3 listener : listeners) {
			listener.didFinishRouteTask(links, nodes);
		}
	}

	private void notifyFailedRouteTask(Throwable error) {
		for (TYBrtRouteShpTaskListenerV3 listener : listeners) {
			listener.didFailedRouteTask(error);
		}
	}

	public interface TYBrtRouteShpTaskListenerV3 {
		public void didFinishRouteTask(List<TYIRouteLinkRecordV3> links, List<TYIRouteNodeRecordV3> nodes);

		public void didFailedRouteTask(Throwable error);
	}
}

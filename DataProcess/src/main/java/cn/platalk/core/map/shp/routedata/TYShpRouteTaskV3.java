package cn.platalk.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.core.map.base.TYIShpDataManager;
import cn.platalk.core.map.shp.routedata.TYShpRouteDataGroupV3.TYBrtShpRouteDataGroupListenerV3;
import cn.platalk.core.map.shp.routedata.TYShpRouteNDBuildingToolV3.TYBrtRouteNDBuildingListenerV3;
import cn.platalk.map.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.map.entity.base.impl.TYMapInfo;

public class TYShpRouteTaskV3 implements TYBrtShpRouteDataGroupListenerV3, TYBrtRouteNDBuildingListenerV3 {
	static WKBReader wkb = new WKBReader();
	TYIShpDataManager shpDataManager;

	List<TYIRouteLinkRecordV3> linkRecords;
	List<TYIRouteNodeRecordV3> nodeRecords;

	List<TYMapDataFeatureRecord> mapDataList = new ArrayList<TYMapDataFeatureRecord>();

	TYShpRouteDataGroupV3 shpDataGroup;
	TYShpRouteNDBuildingToolV3 buildingTool;

	public TYShpRouteTaskV3(TYIShpDataManager manager) {
		this.shpDataManager = manager;

		shpDataGroup = new TYShpRouteDataGroupV3();
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
		buildingTool = new TYShpRouteNDBuildingToolV3(shpDataGroup);
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

	// use join in jdk8
	private String join(List<String> list) {
		if (list == null || list.size() == 0) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < list.size(); ++i) {
			if (i != 0) {
				buffer.append(",");
			}
			buffer.append(list.get(i));
		}
		return buffer.toString();
	}

	private void applyMapData(List<TYIRouteLinkRecordV3> linkList, List<TYIRouteNodeRecordV3> nodeList,
			List<TYMapDataFeatureRecord> recordList) {
		Map<String, Geometry> geometryMap = new HashMap<String, Geometry>();

		for (TYMapDataFeatureRecord record : recordList) {
			// if (record.layer == 2 && !record.categoryID.equals("000800")) {
			if (record.layer == 2 && !record.categoryID.equals("000600") && !record.categoryID.equals("000700")) {
				geometryMap.put(record.poiID, record.getGeometryData().buffer(0.01));
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
			List<String> roomIDList = new ArrayList<String>();
			for (TYMapDataFeatureRecord record : recordList) {
				if (node.getFloor() != record.floorNumber || record.layer != 2)
					continue;

				Geometry nodeGeometry = getNodeRecordGeomtry(node);
				Geometry roomGeometry = geometryMap.get(record.poiID);
				if (nodeGeometry != null && roomGeometry != null && roomGeometry.contains(nodeGeometry)) {
					roomIDList.add(record.poiID);
				}
			}

			if (roomIDList.size() > 0) {
				node.setRoomID(join(roomIDList));
				if (roomIDList.size() >= 3) {
					System.out.println("Fire a error here!");
					System.out.println(node.getNodeID());
					System.out.println("Size: " + roomIDList.size());
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

	private List<TYBrtRouteShpTaskListenerV3> listeners = new ArrayList<TYShpRouteTaskV3.TYBrtRouteShpTaskListenerV3>();

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

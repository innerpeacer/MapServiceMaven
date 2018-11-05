package com.test;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.db.map.TYBuildingDBAdapter;
import cn.platalk.brtmap.db.map.TYMapDataDBAdapter;
import cn.platalk.brtmap.db.map.TYMapInfoDBAdapter;
import cn.platalk.brtmap.db.map.TYRouteDBAdapterV3;
import cn.platalk.brtmap.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.TYIMapInfo;
import cn.platalk.brtmap.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.brtmap.entity.base.TYLocalPoint;
import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.route.core_v3.IPServerRouteResultObjectV3;
import cn.platalk.brtmap.route.core_v3.TYServerRouteManagerV3;
import cn.platalk.route.core.config.TYServerEnviroment;

public class RouteTestV3 {

	// static String buildingID = "07550023";
	static String buildingID = "05120002";

	public static void main(String[] args) {
		System.out.println("RouteTestV3");
		TYServerEnviroment.initialize();

		TYBuildingDBAdapter buildingDB = new TYBuildingDBAdapter();
		buildingDB.connectDB();
		TYBuilding currentBuilding = buildingDB.getBuilding(buildingID);
		System.out.println(currentBuilding);
		buildingDB.disconnectDB();

		TYMapInfoDBAdapter mapInfoDB = new TYMapInfoDBAdapter();
		mapInfoDB.connectDB();
		List<TYIMapInfo> mapInfoList = new ArrayList<TYIMapInfo>();
		mapInfoList.addAll(mapInfoDB.getMapInfos(buildingID));
		mapInfoDB.disconnectDB();
		System.out.println("Floors: " + mapInfoList.size());
		// System.out.println(mapInfoList);

		TYLocalPoint start = new TYLocalPoint(13432170, 3651745, 1);
		TYLocalPoint end = new TYLocalPoint(13432199, 3651702, 2);

		TYRouteDBAdapterV3 routeDB = new TYRouteDBAdapterV3(currentBuilding.getBuildingID());
		routeDB.connectDB();
		List<TYIRouteNodeRecordV3> nodeList = new ArrayList<TYIRouteNodeRecordV3>();
		nodeList.addAll(routeDB.getAllNodeRecords());
		List<TYIRouteLinkRecordV3> linkList = new ArrayList<TYIRouteLinkRecordV3>();
		linkList.addAll(routeDB.getAllLinkRecords());
		routeDB.disconnectDB();

		TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(currentBuilding.getBuildingID());
		mapDB.connectDB();
		List<TYIMapDataFeatureRecord> mapRecordList = new ArrayList<TYIMapDataFeatureRecord>();
		mapRecordList.addAll(mapDB.getAllMapDataRecords());
		mapDB.disconnectDB();

		System.out.println("Nodes: " + nodeList.size());
		System.out.println("Links: " + linkList.size());

		TYServerRouteManagerV3 routeManager = new TYServerRouteManagerV3(currentBuilding, mapInfoList, nodeList,
				linkList, mapRecordList);
		IPServerRouteResultObjectV3 routeObject = routeManager.requestRoute(start, end, null);

		System.out.println(routeObject.routeResult);
		System.out.println(routeObject.routeResult.buildJson().toString());

	}
}

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
import cn.platalk.brtmap.route.core_v3.TYServerMultiRouteManagerV3;
import cn.platalk.brtmap.route.core_v3.TYServerMultiRouteResultV3;
import cn.platalk.brtmap.route.core_v3.TYServerRouteOptions;
import cn.platalk.route.core.config.TYServerEnviroment;

public class MultiRouteTestV3 {
	static String buildingID = "05710010";

	public static void main(String[] args) {
		System.out.println("MultiRouteTestV3");
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

		TYLocalPoint start = new TYLocalPoint(13388872.552793643, 3527735.4764030357, 5);
		TYLocalPoint end = new TYLocalPoint(13389009.876737963, 3527735.842600839, 5);
		List<TYLocalPoint> stops = new ArrayList<TYLocalPoint>();
		// stops.add(new TYLocalPoint(13432195.546067534, 3651722.8146107513,
		// 1));

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

		TYServerMultiRouteManagerV3 routeManager = new TYServerMultiRouteManagerV3(currentBuilding, mapInfoList,
				nodeList, linkList, mapRecordList);
		TYServerMultiRouteResultV3 routeResult = routeManager.requestRoute(start, end, stops,
				new TYServerRouteOptions());
		System.out.println(routeResult.detailedDescription());
		System.out.println(routeResult.buildJson().toString());
	}
}

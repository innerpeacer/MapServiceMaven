package com.test;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.db.map.TYBuildingDBAdapter;
import cn.platalk.brtmap.db.map.TYMapInfoDBAdapter;
import cn.platalk.brtmap.db.map.TYRouteDBAdapter;
import cn.platalk.brtmap.entity.base.TYIMapInfo;
import cn.platalk.brtmap.entity.base.TYIRouteLinkRecord;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecord;
import cn.platalk.brtmap.entity.base.TYLocalPoint;
import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.route.core.TYServerMultiRouteManager;
import cn.platalk.brtmap.route.core.TYServerMultiRouteResult;
import cn.platalk.route.core.config.TYServerEnviroment;

public class MultiRoute {
	static String buildingID = "07550023";

	public static void main(String[] args) {
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
		// System.out.println(mapInfoList);

		TYLocalPoint start = new TYLocalPoint(12686153.929319, 2560963.396062, -4);
		TYLocalPoint end = new TYLocalPoint(12686399.051673, 2561075.729366, -4);

		List<TYLocalPoint> stopPoints = new ArrayList<TYLocalPoint>();
		stopPoints.add(new TYLocalPoint(12686231.591840, 2561060.474221, -4));
		stopPoints.add(new TYLocalPoint(12686273.543486, 2560994.946460, -4));

		TYRouteDBAdapter routeDB = new TYRouteDBAdapter(currentBuilding.getBuildingID());
		routeDB.connectDB();
		List<TYIRouteNodeRecord> nodeList = new ArrayList<TYIRouteNodeRecord>();
		nodeList.addAll(routeDB.getAllNodeRecords());
		List<TYIRouteLinkRecord> linkList = new ArrayList<TYIRouteLinkRecord>();
		linkList.addAll(routeDB.getAllLinkRecords());
		routeDB.disconnectDB();

		TYServerMultiRouteManager routeManager = new TYServerMultiRouteManager(currentBuilding, mapInfoList, nodeList,
				linkList);
		TYServerMultiRouteResult routeResult = routeManager.requestRoute(start, end, stopPoints, true);
		// System.out.println(routeResult.getDetailedResult());
		System.out.println(routeResult.buildJson().toString());
	}
}

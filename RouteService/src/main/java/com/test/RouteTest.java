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
import cn.platalk.brtmap.route.core.TYServerRouteManager;
import cn.platalk.brtmap.route.core.TYServerRouteResult;
import cn.platalk.route.core.config.TYServerEnviroment;

public class RouteTest {
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
		System.out.println(mapInfoList);

		TYLocalPoint start = new TYLocalPoint(12686220.561437858, 2560924.902856261, 2);
		TYLocalPoint end = new TYLocalPoint(12686227.885530997, 2560997.927129774, 1);

		TYRouteDBAdapter routeDB = new TYRouteDBAdapter(currentBuilding.getBuildingID());
		routeDB.connectDB();
		List<TYIRouteNodeRecord> nodeList = new ArrayList<TYIRouteNodeRecord>();
		nodeList.addAll(routeDB.getAllNodeRecords());
		List<TYIRouteLinkRecord> linkList = new ArrayList<TYIRouteLinkRecord>();
		linkList.addAll(routeDB.getAllLinkRecords());
		routeDB.disconnectDB();

		TYServerRouteManager routeManager = new TYServerRouteManager(currentBuilding, mapInfoList, nodeList, linkList);
		TYServerRouteResult routeResult = routeManager.requestRoute(start, end);
		System.out.println(routeResult);
		System.out.println(routeResult.buildJson().toString());

	}
}

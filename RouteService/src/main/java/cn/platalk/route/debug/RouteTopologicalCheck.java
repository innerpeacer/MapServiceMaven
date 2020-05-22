package cn.platalk.route.debug;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.TYIRouteLinkRecord;
import cn.platalk.map.entity.base.TYIRouteNodeRecord;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYLocalPoint;
import cn.platalk.mysql.TYMysqlDBHelper;

public class RouteTopologicalCheck {
	// static String buildingID = "07550023";

	// static String buildingID = "00220001";
	static final String buildingID = "05800001";

	// static String buildingID = "00210100";

	public static void main(String[] args) {
		TYBuilding currentBuilding = TYMysqlDBHelper.getBuilding(buildingID);
		// List<TYIMapInfo> mapInfoList = new
		// ArrayList<TYIMapInfo>(TYMysqlDBHelper.getMapInfos(buildingID));

		// TYLocalPoint start = new TYLocalPoint(12686153.929319,
		// 2560963.396062, -4);
		// TYLocalPoint end = new TYLocalPoint(12686399.051673, 2561075.729366,
		// -4);

		List<TYLocalPoint> stopPoints = new ArrayList<>();
		stopPoints.add(new TYLocalPoint(12686231.591840, 2561060.474221, -4));
		stopPoints.add(new TYLocalPoint(12686273.543486, 2560994.946460, -4));

		List<TYIRouteNodeRecord> nodeList = TYMysqlDBHelper.getAllRouteNodeRecord(currentBuilding.getBuildingID());
		List<TYIRouteLinkRecord> linkList = TYMysqlDBHelper.getAllRouteLinkRecord(currentBuilding.getBuildingID());

		IPCheckRouteNetworkDataset networkDataset = new IPCheckRouteNetworkDataset(nodeList, linkList);
		networkDataset.topologicalCheck();
	}
}

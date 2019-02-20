package cn.platalk.route.core_v3.routemanager;

import java.util.HashMap;
import java.util.Map;

import cn.platalk.map.route.core_v3.TYServerRouteManagerV3;

public class TYRouteManagerCollectionV3 {
	public static Map<String, TYServerRouteManagerV3> managerCollection = new HashMap<String, TYServerRouteManagerV3>();

	public static void AddRouteManager(String buildingID,
			TYServerRouteManagerV3 manager) {
		managerCollection.put(buildingID, manager);
	}

	public static TYServerRouteManagerV3 GetRouteManager(String buildingID) {
		return managerCollection.get(buildingID);
	}

	public static boolean ExistRouteManager(String buildingID) {
		return managerCollection.containsKey(buildingID);
	}

}

package cn.platalk.route.core_v3.routemanager;

import java.util.HashMap;
import java.util.Map;

import cn.platalk.map.route.core_v3.TYServerMultiRouteManagerV3;

public class TYMultiRouteManagerCollectionV3 {
	public static Map<String, TYServerMultiRouteManagerV3> managerCollection = new HashMap<String, TYServerMultiRouteManagerV3>();

	public static void AddRouteManager(String buildingID,
			TYServerMultiRouteManagerV3 manager) {
		managerCollection.put(buildingID, manager);
	}

	public static TYServerMultiRouteManagerV3 GetRouteManager(String buildingID) {
		return managerCollection.get(buildingID);
	}

	public static boolean ExistRouteManager(String buildingID) {
		return managerCollection.containsKey(buildingID);
	}
}

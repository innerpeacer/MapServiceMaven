package cn.platalk.route.core.routemanager;

import java.util.HashMap;
import java.util.Map;

import cn.platalk.map.route.core.TYServerMultiRouteManager;

public class TYMultiRouteManagerCollection {
	public static Map<String, TYServerMultiRouteManager> managerCollection = new HashMap<String, TYServerMultiRouteManager>();

	public static void AddRouteManager(String buildingID,
			TYServerMultiRouteManager manager) {
		managerCollection.put(buildingID, manager);
	}

	public static TYServerMultiRouteManager GetRouteManager(String buildingID) {
		return managerCollection.get(buildingID);
	}

	public static boolean ExistRouteManager(String buildingID) {
		return managerCollection.containsKey(buildingID);
	}
}

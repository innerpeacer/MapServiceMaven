package cn.platalk.route.core.routemanager;

import java.util.HashMap;
import java.util.Map;

import cn.platalk.brtmap.route.core.TYServerRouteManager;

public class TYRouteManagerCollection {
	public static Map<String, TYServerRouteManager> managerCollection = new HashMap<String, TYServerRouteManager>();

	public static void AddRouteManager(String buildingID,
			TYServerRouteManager manager) {
		managerCollection.put(buildingID, manager);
	}

	public static TYServerRouteManager GetRouteManager(String buildingID) {
		return managerCollection.get(buildingID);
	}

	public static boolean ExistRouteManager(String buildingID) {
		return managerCollection.containsKey(buildingID);
	}

}

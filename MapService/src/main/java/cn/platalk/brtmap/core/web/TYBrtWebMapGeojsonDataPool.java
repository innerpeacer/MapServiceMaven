package cn.platalk.brtmap.core.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class TYBrtWebMapGeojsonDataPool {
	public static Map<String, JSONObject> webDataPool = new HashMap<String, JSONObject>();

	public static boolean existWebMapData(String mapID) {
		return webDataPool.containsKey(mapID);
	}

	public static void resetWebMapDataByID(String mapID) {
		webDataPool.remove(mapID);
	}

	public static void resetWebMapDataByBuildingID(String buildingID) {
		List<String> toRemove = new ArrayList<String>();
		System.out.println(webDataPool.keySet());
		for (String mapID : webDataPool.keySet()) {
			if (buildingID.equals(mapID.substring(0, 8))) {
				toRemove.add(mapID);
			}
		}
		for (String mapID : toRemove) {
			webDataPool.remove(mapID);
		}
	}

	public static JSONObject getWebMapData(String mapID) {
		return webDataPool.get(mapID);
	}

	public static void setWebMapData(String mapID, JSONObject data) {
		webDataPool.put(mapID, data);
	}

	public static void reset() {
		webDataPool.clear();
	}

}

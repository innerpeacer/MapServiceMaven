package cn.platalk.map.core.web.beacon;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class TYWebBeaconGeojsonDataPool {
	public static Map<String, JSONObject> webDataPool = new HashMap<String, JSONObject>();

	public static boolean existWebBeaconData(String buildingID) {
		return webDataPool.containsKey(buildingID);
	}

	public static void resetWebBeaconDataByID(String buildingID) {
		webDataPool.remove(buildingID);
	}

	public static void resetWebBeaconDataByBuildingID(String buildingID) {
		webDataPool.remove(buildingID);
	}

	public static JSONObject getWebBeaconData(String buildingID) {
		return webDataPool.get(buildingID);
	}

	public static void setWebBeaconpData(String buildingID, JSONObject data) {
		webDataPool.put(buildingID, data);
	}

	public static void reset() {
		webDataPool.clear();
	}
}

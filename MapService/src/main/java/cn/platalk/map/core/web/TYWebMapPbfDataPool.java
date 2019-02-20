package cn.platalk.map.core.web;

import innerpeacer.mapdata.pbf.TYMapDataPbf.TYIndoorDataPbf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TYWebMapPbfDataPool {
	public static Map<String, TYIndoorDataPbf> webDataPool = new HashMap<String, TYIndoorDataPbf>();

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

	public static TYIndoorDataPbf getWebMapData(String mapID) {
		return webDataPool.get(mapID);
	}

	public static void setWebMapData(String mapID, TYIndoorDataPbf data) {
		webDataPool.put(mapID, data);
	}

	public static void reset() {
		webDataPool.clear();
	}
}

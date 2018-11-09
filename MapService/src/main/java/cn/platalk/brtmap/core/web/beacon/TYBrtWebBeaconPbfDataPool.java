package cn.platalk.brtmap.core.web.beacon;

import java.util.HashMap;
import java.util.Map;

import innerpeacer.beacon.pbf.TYBeaconPbf.TYLocatingBeaconListPbf;

public class TYBrtWebBeaconPbfDataPool {
	public static Map<String, TYLocatingBeaconListPbf> beaconDataPool = new HashMap<String, TYLocatingBeaconListPbf>();

	public static boolean existBeaconData(String mapID) {
		return beaconDataPool.containsKey(mapID);
	}

	public static void resetBeaconDataByID(String buildingID) {
		beaconDataPool.remove(buildingID);
	}

	public static void resetBeaconDataByBuildingID(String buildingID) {
		beaconDataPool.remove(buildingID);
	}

	public static TYLocatingBeaconListPbf getBeaconData(String buildingID) {
		return beaconDataPool.get(buildingID);
	}

	public static void setBeaconData(String buildingID, TYLocatingBeaconListPbf data) {
		beaconDataPool.put(buildingID, data);
	}

	public static void reset() {
		beaconDataPool.clear();
	}
}

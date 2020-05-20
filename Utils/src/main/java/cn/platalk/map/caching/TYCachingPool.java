package cn.platalk.map.caching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TYCachingPool {
	private static final Map<String, Object> _sharedCachingPool = new HashMap<>();

	private static String getKey(String dataID, TYCachingType type) {
		return String.format("%s-%s", type.getName(), dataID);
	}

	public static boolean existDataID(String dataID, TYCachingType type) {
		return _sharedCachingPool.containsKey(getKey(dataID, type));
	}

	public static void resetDataByID(String dataID, TYCachingType type) {
		_sharedCachingPool.remove(getKey(dataID, type));
	}

	public static void resetDataByBuildingID(String buildingID, TYCachingType type) {
		List<String> toRemove = new ArrayList<>();
		String checkingKey = getKey(buildingID, type);
		for (String key : _sharedCachingPool.keySet()) {
			if (key.contains(checkingKey)) {
				toRemove.add(key);
			}
		}
		for (String key : toRemove) {
			_sharedCachingPool.remove(key);
		}
	}

	public static Object getCachingData(String dataID, TYCachingType type) {
		return _sharedCachingPool.get(getKey(dataID, type));
	}

	public static void setCachingData(String dataID, Object data, TYCachingType type) {
		_sharedCachingPool.put(getKey(dataID, type), data);
	}

}

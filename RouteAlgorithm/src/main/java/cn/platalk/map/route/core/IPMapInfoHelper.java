package cn.platalk.map.route.core;

import cn.platalk.map.entity.base.map.TYIMapInfo;

import java.util.List;

class IPMapInfoHelper {
	public static TYIMapInfo searchMapInfoFromArray(List<TYIMapInfo> array, int floor) {
		for (TYIMapInfo info : array) {
			if (floor == info.getFloorNumber()) {
				return info;
			}
		}
		return null;
	}
}

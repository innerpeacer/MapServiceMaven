package cn.platalk.brtmap.route.core_v3;

import java.util.List;

import cn.platalk.map.entity.base.TYIMapInfo;

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

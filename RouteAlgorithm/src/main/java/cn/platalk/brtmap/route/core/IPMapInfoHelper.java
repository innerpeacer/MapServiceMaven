package cn.platalk.brtmap.route.core;

import java.util.List;

import cn.platalk.brtmap.entity.base.TYIMapInfo;

class IPMapInfoHelper {
	public static TYIMapInfo searchMapInfoFromArray(List<TYIMapInfo> array,
			int floor) {
		for (TYIMapInfo info : array) {
			if (floor == info.getFloorNumber()) {
				return info;
			}
		}
		return null;
	}
}

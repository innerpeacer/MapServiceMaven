package cn.platalk.map.entity.base;

import cn.platalk.common.TYIGeojsonFeature;
import cn.platalk.common.TYIJsonFeature;
import cn.platalk.map.entity.base.impl.TYLocalPoint;

public interface TYILocatingBeacon extends TYIBeacon, TYIGeojsonFeature, TYIJsonFeature {
	String getRoomID();

	TYLocalPoint getLocation();

	String getMapID();

	String getBuildingID();

	String getCityID();
}

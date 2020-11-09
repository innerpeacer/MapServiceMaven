package cn.platalk.map.entity.base.beacon;

import cn.platalk.common.TYIGeojsonFeature;
import cn.platalk.common.TYIJsonFeature;
import cn.platalk.map.entity.base.impl.map.TYLocalPoint;

public interface TYILocatingBeacon extends TYIBeacon, TYIGeojsonFeature, TYIJsonFeature {
	String getRoomID();

	TYLocalPoint getLocation();

	String getMapID();

	String getBuildingID();

	String getCityID();
}

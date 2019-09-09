package cn.platalk.map.entity.base;

import cn.platalk.common.TYIGeojsonFeature;
import cn.platalk.common.TYIJsonFeature;
import cn.platalk.map.entity.base.impl.TYLocalPoint;

public interface TYILocatingBeacon extends TYIBeacon, TYIGeojsonFeature, TYIJsonFeature {
	public String getRoomID();

	public TYLocalPoint getLocation();

	public String getMapID();

	public String getBuildingID();

	public String getCityID();
}

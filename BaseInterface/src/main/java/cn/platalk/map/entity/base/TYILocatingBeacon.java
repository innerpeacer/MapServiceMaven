package cn.platalk.map.entity.base;

import cn.platalk.common.TYIGeojsonFeature;
import cn.platalk.map.entity.base.impl.TYLocalPoint;

public interface TYILocatingBeacon extends TYIBeacon, TYIGeojsonFeature {
	public String getRoomID();

	public TYLocalPoint getLocation();

	public String getMapID();

	public String getBuildingID();

	public String getCityID();
}

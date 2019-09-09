package cn.platalk.map.entity.base;

import cn.platalk.common.TYIJsonFeature;

public interface TYIMapInfo extends TYIJsonFeature {

	public String getCityID();

	public String getBuildingID();

	public String getMapID();

	public String getFloorName();

	public int getFloorNumber();

	public TYIMapSize getMapSize();

	public TYIMapExtent getMapExtent();

}

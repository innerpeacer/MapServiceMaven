package cn.platalk.map.entity.base;

import cn.platalk.common.TYIJsonFeature;

public interface TYIMapInfo extends TYIJsonFeature {

	String getCityID();

	String getBuildingID();

	String getMapID();

	String getFloorName();

	int getFloorNumber();

	TYIMapSize getMapSize();

	TYIMapExtent getMapExtent();

}

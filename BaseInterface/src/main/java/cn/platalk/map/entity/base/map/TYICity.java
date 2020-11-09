package cn.platalk.map.entity.base.map;

import cn.platalk.common.TYIJsonFeature;

public interface TYICity extends TYIJsonFeature {
	String getCityID();

	String getName();

	String getSname();

	double getLongitude();

	double getLatitude();

	int getStatus();
}

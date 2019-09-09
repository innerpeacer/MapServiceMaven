package cn.platalk.map.entity.base;

import cn.platalk.common.TYIJsonFeature;

public interface TYICity extends TYIJsonFeature {
	public String getCityID();

	public String getName();

	public String getSname();

	public double getLongitude();

	public double getLatitude();

	public int getStatus();
}

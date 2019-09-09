package cn.platalk.map.entity.base;

import org.json.JSONObject;

public interface TYICity {
	public String getCityID();

	public String getName();

	public String getSname();

	public double getLongitude();

	public double getLatitude();

	public int getStatus();

	public JSONObject toJson();
}

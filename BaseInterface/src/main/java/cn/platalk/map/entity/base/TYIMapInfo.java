package cn.platalk.map.entity.base;

import org.json.JSONObject;

public interface TYIMapInfo {

	public String getCityID();

	public String getBuildingID();

	public String getMapID();

	public String getFloorName();

	public int getFloorNumber();

	public TYIMapSize getMapSize();

	public TYIMapExtent getMapExtent();

	public JSONObject toJson();
}

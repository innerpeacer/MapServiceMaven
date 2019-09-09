package cn.platalk.map.entity.base;

import org.json.JSONObject;

public interface TYILocalPoint {
	public double getX();

	public double getY();

	public int getFloor();

	public JSONObject toJson();

}

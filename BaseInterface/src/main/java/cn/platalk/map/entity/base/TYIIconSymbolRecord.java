package cn.platalk.map.entity.base;

import org.json.JSONObject;

public interface TYIIconSymbolRecord {
	public int getSymbolID();

	public String getIcon();

	public JSONObject toJson();

}

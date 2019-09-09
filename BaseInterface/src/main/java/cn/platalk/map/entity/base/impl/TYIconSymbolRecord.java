package cn.platalk.map.entity.base.impl;

import org.json.JSONObject;

import cn.platalk.map.entity.base.TYIIconSymbolRecord;

public class TYIconSymbolRecord implements TYIIconSymbolRecord {
	static final String KEY_JSON_ICON_SYMBOL_ID = "symbolID";
	static final String KEY_JSON_ICON_SYMBOL_NAME = "icon";

	public int symbolID;
	public String icon;

	public TYIconSymbolRecord() {

	}

	@Override
	public int getSymbolID() {
		return symbolID;
	}

	public void setSymbolID(int symbolID) {
		this.symbolID = symbolID;
	}

	@Override
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public JSONObject toJson() {
		JSONObject symbolObject = new JSONObject();
		symbolObject.put(KEY_JSON_ICON_SYMBOL_ID, getSymbolID());
		symbolObject.put(KEY_JSON_ICON_SYMBOL_NAME, getIcon().trim());
		return symbolObject;
	}

	@Override
	public String toString() {
		return String.format("IconSymbol-%d-%s", symbolID, icon);
	}
}

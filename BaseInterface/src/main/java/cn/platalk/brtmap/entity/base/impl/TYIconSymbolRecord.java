package cn.platalk.brtmap.entity.base.impl;

import cn.platalk.brtmap.entity.base.TYIIconSymbolRecord;

public class TYIconSymbolRecord implements TYIIconSymbolRecord {

	// static final String FIELD_MAP_SYMBOL_ICON_0_PRIMARY_KEY = "_id";
	// static final String FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID = "SYMBOL_ID";
	// static final String FIELD_MAP_SYMBOL_ICON_2_ICON = "ICON";
	// static final String FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID = "BUILDING_ID";

	public int symbolID;
	public String icon;

	public TYIconSymbolRecord() {

	}

	public int getSymbolID() {
		return symbolID;
	}

	public void setSymbolID(int symbolID) {
		this.symbolID = symbolID;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return String.format("IconSymbol-%d-%s", symbolID, icon);
	}
}

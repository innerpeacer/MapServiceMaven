package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYIIconSymbolRecord;

public class TYIconSymbolRecord implements TYIIconSymbolRecord {
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

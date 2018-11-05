package cn.platalk.brtmap.entity.base.impl;

import cn.platalk.brtmap.entity.base.TYIFillSymbolRecord;

public class TYFillSymbolRecord implements TYIFillSymbolRecord {

	// static final String FIELD_FILL_SYMBOL_0_PRIMARY_KEY = "_id";
	// static final String FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID = "SYMBOL_ID";
	// static final String FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR = "FILL";
	// static final String FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR = "OUTLINE";
	// static final String FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH = "LINE_WIDTH";
	// static final String FIELD_MAP_SYMBOL_FILL_5_BUILDING_ID = "BUILDING_ID";

	public int symbolID;
	public String fillColor;
	public String outlineColor;
	public double lineWidth;

	public TYFillSymbolRecord() {

	}

	public void setSymbolID(int symbolID) {
		this.symbolID = symbolID;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	public void setOutlineColor(String outlineColor) {
		this.outlineColor = outlineColor;
	}

	public void setLineWidth(double lineWidth) {
		this.lineWidth = lineWidth;
	}

	public int getSymbolID() {
		return symbolID;
	}

	public String getFillColor() {
		return fillColor;
	}

	public String getOutlineColor() {
		return outlineColor;
	}

	public double getLineWidth() {
		return lineWidth;
	}

	@Override
	public String toString() {
		return String.format("FillSymbol-%d-%s", symbolID, fillColor);
	}
}

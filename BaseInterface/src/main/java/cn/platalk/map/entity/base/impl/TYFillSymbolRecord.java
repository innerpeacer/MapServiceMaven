package cn.platalk.map.entity.base.impl;

import org.json.JSONObject;

import cn.platalk.map.entity.base.TYIFillSymbolRecord;

public class TYFillSymbolRecord implements TYIFillSymbolRecord {
	static final String KEY_JSON_FILL_SYMBOL_ID = "symbolID";
	static final String KEY_JSON_FILL_SYMBOL_FILL_COLOR = "fillColor";
	static final String KEY_JSON_FILL_SYMBOL_OUTLINE_COLOR = "outlineColor";
	static final String KEY_JSON_FILL_SYMBOL_OUTLINE_WIDTH = "outlineWidth";
	static final String KEY_JSON_FILL_SYMBOL_LEVEL_MIN = "levelMin";
	static final String KEY_JSON_FILL_SYMBOL_LEVEL_MAX = "levelMax";
	static final String KEY_JSON_FILL_SYMBOL_UID = "UID";
	static final String KEY_JSON_FILL_SYMBOL_VISIBLE = "visible";

	public int UID;
	public int symbolID;
	public String fillColor;
	public String outlineColor;
	public double lineWidth;
	public double levelMin;
	public double levelMax;
	private boolean visible = true;

	public TYFillSymbolRecord() {

	}

	public void setUID(int uID) {
		UID = uID;
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

	public void setLevelMin(double levelMin) {
		this.levelMin = levelMin;
	}

	public void setLevelMax(double levelMax) {
		this.levelMax = levelMax;
	}

	@Override
	public int getUID() {
		return UID;
	}

	@Override
	public int getSymbolID() {
		return symbolID;
	}

	@Override
	public String getFillColor() {
		return fillColor;
	}

	@Override
	public String getOutlineColor() {
		return outlineColor;
	}

	@Override
	public double getLineWidth() {
		return lineWidth;
	}

	@Override
	public double getLevelMin() {
		return levelMin;
	}

	@Override
	public double getLevelMax() {
		return levelMax;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}

	@Override
	public JSONObject toJson() {
		JSONObject symbolObject = new JSONObject();
		symbolObject.put(KEY_JSON_FILL_SYMBOL_ID, getSymbolID());
		symbolObject.put(KEY_JSON_FILL_SYMBOL_FILL_COLOR, getFillColor().trim());
		symbolObject.put(KEY_JSON_FILL_SYMBOL_OUTLINE_COLOR, getOutlineColor().trim());
		symbolObject.put(KEY_JSON_FILL_SYMBOL_OUTLINE_WIDTH, getLineWidth());
		symbolObject.put(KEY_JSON_FILL_SYMBOL_LEVEL_MIN, getLevelMin());
		symbolObject.put(KEY_JSON_FILL_SYMBOL_LEVEL_MAX, getLevelMax());
		symbolObject.put(KEY_JSON_FILL_SYMBOL_UID, getUID());
		symbolObject.put(KEY_JSON_FILL_SYMBOL_VISIBLE, isVisible());
		return symbolObject;
	}

	@Override
	public String toString() {
		return String.format("FillSymbol-%d-%s", symbolID, fillColor);
	}
}

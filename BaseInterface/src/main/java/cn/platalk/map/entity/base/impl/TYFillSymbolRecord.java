package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYIFillSymbolRecord;

public class TYFillSymbolRecord implements TYIFillSymbolRecord {
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

	public int getUID() {
		return UID;
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

	public double getLevelMin() {
		return levelMin;
	}

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
	public String toString() {
		return String.format("FillSymbol-%d-%s", symbolID, fillColor);
	}
}

package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYIFillSymbolRecord;

public class TYFillSymbolRecord implements TYIFillSymbolRecord {
	public int symbolID;
	public String fillColor;
	public String outlineColor;
	public double lineWidth;
	public double levelMin;
	public double levelMax;

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

	public void setLevelMin(double levelMin) {
		this.levelMin = levelMin;
	}

	public void setLevelMax(double levelMax) {
		this.levelMax = levelMax;
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

	@Override
	public String toString() {
		return String.format("FillSymbol-%d-%s", symbolID, fillColor);
	}
}

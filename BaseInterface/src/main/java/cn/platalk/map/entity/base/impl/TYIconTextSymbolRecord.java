package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYIIconTextSymbolRecord;

public class TYIconTextSymbolRecord implements TYIIconTextSymbolRecord {
	public int symbolID;

	public boolean iconVisible;
	public double iconSize;
	public double iconRotate;
	public double iconOffsetX;
	public double iconOffsetY;

	public boolean textVisible;
	public double textSize;
	public String textFont;
	public String textColor;
	public double textRotate;
	public double textOffsetX;
	public double textOffsetY;

	public double levelMin;
	public double levelMax;

	public String otherPaint;
	public String otherLayout;

	public String description;

	public void setSymbolID(int symbolID) {
		this.symbolID = symbolID;
	}

	public void setIconVisible(boolean iconVisible) {
		this.iconVisible = iconVisible;
	}

	public void setIconSize(double iconSize) {
		this.iconSize = iconSize;
	}

	public void setIconRotate(double iconRotate) {
		this.iconRotate = iconRotate;
	}

	public void setIconOffsetX(double iconOffsetX) {
		this.iconOffsetX = iconOffsetX;
	}

	public void setIconOffsetY(double iconOffsetY) {
		this.iconOffsetY = iconOffsetY;
	}

	public void setTextVisible(boolean textVisible) {
		this.textVisible = textVisible;
	}

	public void setTextSize(double textSize) {
		this.textSize = textSize;
	}

	public void setTextFont(String textFont) {
		this.textFont = textFont;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public void setTextRotate(double textRotate) {
		this.textRotate = textRotate;
	}

	public void setTextOffsetX(double textOffsetX) {
		this.textOffsetX = textOffsetX;
	}

	public void setTextOffsetY(double textOffsetY) {
		this.textOffsetY = textOffsetY;
	}

	public void setLevelMin(double levelMin) {
		this.levelMin = levelMin;
	}

	public void setLevelMax(double levelMax) {
		this.levelMax = levelMax;
	}

	public void setOtherPaint(String otherPaint) {
		this.otherPaint = otherPaint;
	}

	public void setOtherLayout(String otherLayout) {
		this.otherLayout = otherLayout;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSymbolID() {
		return symbolID;
	}

	public boolean isIconVisible() {
		return iconVisible;
	}

	public double getIconSize() {
		return iconSize;
	}

	public double getIconRotate() {
		return iconRotate;
	}

	public double getIconOffsetX() {
		return iconOffsetX;
	}

	public double getIconOffsetY() {
		return iconOffsetY;
	}

	public boolean isTextVisible() {
		return textVisible;
	}

	public double getTextSize() {
		return textSize;
	}

	public String getTextFont() {
		return textFont;
	}

	public String getTextColor() {
		return textColor;
	}

	public double getTextRotate() {
		return textRotate;
	}

	public double getTextOffsetX() {
		return textOffsetX;
	}

	public double getTextOffsetY() {
		return textOffsetY;
	}

	public double getLevelMin() {
		return levelMin;
	}

	public double getLevelMax() {
		return levelMax;
	}

	public String getOtherPaint() {
		return otherPaint;
	}

	public String getOtherLayout() {
		return otherLayout;
	}

	public String getDescription() {
		return description;
	}

}

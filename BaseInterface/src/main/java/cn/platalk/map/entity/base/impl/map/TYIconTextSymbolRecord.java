package cn.platalk.map.entity.base.impl.map;

import org.json.JSONObject;

import cn.platalk.map.entity.base.map.TYIIconTextSymbolRecord;

public class TYIconTextSymbolRecord implements TYIIconTextSymbolRecord {
	static final String KEY_JSON_ICON_TEXT_SYMBOL_ID = "symbolID";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_ICON_VISIBLE = "iconVisible";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_ICON_SIZE = "iconSize";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_ICON_ROTATE = "iconRotate";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_ICON_OFFSET_X = "iconOffsetX";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_ICON_OFFSET_Y = "iconOffsetY";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_TEXT_VISIBLE = "textVisible";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_TEXT_SIZE = "textSize";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_TEXT_FONT = "textFont";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_TEXT_COLOR = "textColor";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_TEXT_ROTATE = "textRotate";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_TEXT_OFFSET_X = "textOffsetX";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_TEXT_OFFSET_Y = "textOffsetY";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_LEVEL_MIN = "levelMin";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_LEVEL_MAX = "levelMax";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_OTHER_PAINT = "otherPaint";
	static final String KEY_JSON_ICON_TEXT_SYMBOL_OTHER_LAYOUT = "otherLayout";
	// static final String KEY_JSON_ICON_TEXT_SYMBOL_DESCRIPTION =
	// "description";
	// // Not Needed!
	static final String KEY_JSON_ICON_TEXT_SYMBOL_PRIORITY = "priority";

	static final String KEY_JSON_ICON_TEXT_UID = "UID";

	public int UID;
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
	public int priority;

	public void setUID(int uID) {
		UID = uID;
	}

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

	public void setPriority(int priority) {
		this.priority = priority;
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
	public boolean isIconVisible() {
		return iconVisible;
	}

	@Override
	public double getIconSize() {
		return iconSize;
	}

	@Override
	public double getIconRotate() {
		return iconRotate;
	}

	@Override
	public double getIconOffsetX() {
		return iconOffsetX;
	}

	@Override
	public double getIconOffsetY() {
		return iconOffsetY;
	}

	@Override
	public boolean isTextVisible() {
		return textVisible;
	}

	@Override
	public double getTextSize() {
		return textSize;
	}

	@Override
	public String getTextFont() {
		return textFont;
	}

	@Override
	public String getTextColor() {
		return textColor;
	}

	@Override
	public double getTextRotate() {
		return textRotate;
	}

	@Override
	public double getTextOffsetX() {
		return textOffsetX;
	}

	@Override
	public double getTextOffsetY() {
		return textOffsetY;
	}

	@Override
	public double getLevelMin() {
		return levelMin;
	}

	@Override
	public double getLevelMax() {
		return levelMax;
	}

	@Override
	public String getOtherPaint() {
		return otherPaint;
	}

	@Override
	public String getOtherLayout() {
		return otherLayout;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public JSONObject toJson() {
		JSONObject symbolObject = new JSONObject();
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_ID, getSymbolID());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_ICON_VISIBLE, isIconVisible());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_ICON_SIZE, getIconSize());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_ICON_ROTATE, getIconRotate());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_ICON_OFFSET_X, getIconOffsetX());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_ICON_OFFSET_Y, getIconOffsetY());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_TEXT_VISIBLE, isTextVisible());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_TEXT_SIZE, getTextSize());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_TEXT_FONT, getTextFont());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_TEXT_COLOR, getTextColor());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_TEXT_ROTATE, getTextRotate());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_TEXT_OFFSET_X, getTextOffsetX());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_TEXT_OFFSET_Y, getTextOffsetY());

		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_LEVEL_MIN, getLevelMin());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_LEVEL_MAX, getLevelMax());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_OTHER_PAINT, getOtherPaint());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_OTHER_LAYOUT, getOtherLayout());
		symbolObject.put(KEY_JSON_ICON_TEXT_UID, getUID());
		symbolObject.put(KEY_JSON_ICON_TEXT_SYMBOL_PRIORITY, getPriority());

		return symbolObject;
	}

}

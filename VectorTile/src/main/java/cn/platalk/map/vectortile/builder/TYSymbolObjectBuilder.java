package cn.platalk.map.vectortile.builder;

import org.json.JSONObject;

import cn.platalk.map.entity.base.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.TYIIconSymbolRecord;
import cn.platalk.map.entity.base.TYIIconTextSymbolRecord;

public class TYSymbolObjectBuilder {

	public static JSONObject generateFillJson(TYIFillSymbolRecord symbol) {
		JSONObject symbolObject = new JSONObject();
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_ID, symbol.getSymbolID());
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_FILL_COLOR, symbol.getFillColor().trim());
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_OUTLINE_COLOR, symbol.getOutlineColor().trim());
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_OUTLINE_WIDTH, symbol.getLineWidth());
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_LEVEL_MIN, symbol.getLevelMin());
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_LEVEL_MAX, symbol.getLevelMax());
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_UID, symbol.getUID());
		return symbolObject;
	}

	public static JSONObject generateIconJson(TYIIconSymbolRecord symbol) {
		JSONObject symbolObject = new JSONObject();
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_SYMBOL_ID, symbol.getSymbolID());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_SYMBOL_NAME, symbol.getIcon().trim());
		return symbolObject;
	}

	public static JSONObject generateIconTextJson(TYIIconTextSymbolRecord symbol) {
		JSONObject symbolObject = new JSONObject();
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_ID, symbol.getSymbolID());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_ICON_VISIBLE, symbol.isIconVisible());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_ICON_SIZE, symbol.getIconSize());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_ICON_ROTATE, symbol.getIconRotate());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_ICON_OFFSET_X, symbol.getIconOffsetX());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_ICON_OFFSET_Y, symbol.getIconOffsetY());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_TEXT_VISIBLE, symbol.isTextVisible());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_TEXT_SIZE, symbol.getTextSize());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_TEXT_FONT, symbol.getTextFont());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_TEXT_COLOR, symbol.getTextColor());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_TEXT_ROTATE, symbol.getTextRotate());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_TEXT_OFFSET_X, symbol.getTextOffsetX());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_TEXT_OFFSET_Y, symbol.getTextOffsetY());

		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_LEVEL_MIN, symbol.getLevelMin());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_LEVEL_MAX, symbol.getLevelMax());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_OTHER_PAINT, symbol.getOtherPaint());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_OTHER_LAYOUT, symbol.getOtherLayout());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_UID, symbol.getUID());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOL_PRIORITY, symbol.getPriority());

		return symbolObject;
	}
}

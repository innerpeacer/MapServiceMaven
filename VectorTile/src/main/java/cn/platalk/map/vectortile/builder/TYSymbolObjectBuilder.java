package cn.platalk.map.vectortile.builder;

import org.json.JSONObject;

import cn.platalk.map.entity.base.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.TYIIconSymbolRecord;

public class TYSymbolObjectBuilder {

	public static JSONObject generateFillJson(TYIFillSymbolRecord symbol) {
		JSONObject symbolObject = new JSONObject();
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_ID, symbol.getSymbolID());
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_FILL_COLOR, symbol.getFillColor().trim());
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_OUTLINE_COLOR, symbol.getOutlineColor().trim());
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_OUTLINE_WIDTH, symbol.getLineWidth());
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_LEVEL_MIN, symbol.getLevelMin());
		symbolObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOL_LEVEL_MAX, symbol.getLevelMax());
		return symbolObject;
	}

	public static JSONObject generateIconJson(TYIIconSymbolRecord symbol) {
		JSONObject symbolObject = new JSONObject();
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_SYMBOL_ID, symbol.getSymbolID());
		symbolObject.put(TYSymbolFields.KEY_WEB_ICON_SYMBOL_NAME, symbol.getIcon().trim());
		return symbolObject;
	}
}

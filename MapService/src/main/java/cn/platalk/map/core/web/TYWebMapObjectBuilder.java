package cn.platalk.map.core.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.map.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.map.TYIconSymbolRecord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TYWebMapObjectBuilder {

	public static JSONObject generateSymbolJson(List<TYFillSymbolRecord> fillList, List<TYIconSymbolRecord> iconList)
			throws JSONException {
		JSONObject symbolObject = new JSONObject();

		Map<Integer, TYFillSymbolRecord> fillDict = new HashMap<>();
		for (TYFillSymbolRecord record : fillList) {
			fillDict.put(record.symbolID, record);
		}
		Map<Integer, TYIconSymbolRecord> iconDict = new HashMap<>();
		for (TYIconSymbolRecord record : iconList) {
			iconDict.put(record.symbolID, record);
		}

		JSONObject defaultSymbol = new JSONObject();
		{
			Integer[] defaultArray = { 9999, 9998, 9997, 9996 };
			String[] defaultKey = { TYWebMapFields.KEY_WEB_DEFAULT_FILL_SYMBOL,
					TYWebMapFields.KEY_WEB_DEFAULT_HIGHLIGHT_FILL_SYMBOL, TYWebMapFields.KEY_WEB_DEFAULT_LINE_SYMBOL,
					TYWebMapFields.KEY_WEB_DEFAULT_HIGHLIGHT_LINE_SYMBOL };
			for (int i = 0; i < defaultArray.length; ++i) {
				int defaultID = defaultArray[i];
				if (fillDict.containsKey(defaultID)) {
					TYFillSymbolRecord defaultFillRecord = fillDict.get(defaultID);
					defaultSymbol.put(defaultKey[i], buildWebFillObject(defaultFillRecord));
					fillDict.remove(defaultID);
				}
			}
		}
		symbolObject.put(TYWebMapFields.KEY_WEB_DEFAULT_SYMBOL, defaultSymbol);

		JSONArray fillSymbol = new JSONArray();
		{
			for (TYFillSymbolRecord fillRecord : fillDict.values()) {
				fillSymbol.put(buildWebFillObject(fillRecord));
			}
		}
		symbolObject.put(TYWebMapFields.KEY_WEB_FILL_SYMBOL, fillSymbol);

		JSONArray iconSymbol = new JSONArray();
		{
			for (TYIconSymbolRecord iconRecord : iconDict.values()) {
				iconSymbol.put(buildWebIconObject(iconRecord));
			}
		}
		symbolObject.put(TYWebMapFields.KEY_WEB_ICON_SYMBOL, iconSymbol);
		return symbolObject;
	}

	public static JSONObject buildWebFillObject(TYFillSymbolRecord record) throws JSONException {
		JSONObject fillObject = new JSONObject();
		fillObject.put(TYWebMapFields.KEY_WEB_SYMBOL_ID, record.symbolID);
		fillObject.put(TYWebMapFields.KEY_WEB_FILL_COLOR, record.fillColor);
		fillObject.put(TYWebMapFields.KEY_WEB_OUTLINE_COLOR, record.outlineColor);
		fillObject.put(TYWebMapFields.KEY_WEB_LINE_WIDTH, record.lineWidth);
		return fillObject;
	}

	public static JSONObject buildWebIconObject(TYIconSymbolRecord record) throws JSONException {
		JSONObject iconObject = new JSONObject();
		iconObject.put(TYWebMapFields.KEY_WEB_SYMBOL_ID, record.symbolID);
		iconObject.put(TYWebMapFields.KEY_WEB_ICON, record.icon);
		return iconObject;
	}

}

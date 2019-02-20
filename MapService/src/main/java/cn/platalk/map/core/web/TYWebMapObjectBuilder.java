package cn.platalk.map.core.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYCity;
import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.TYMapInfo;

public class TYWebMapObjectBuilder {

	public static JSONObject generateCityJson(TYCity city) throws JSONException {
		JSONObject cityObject = new JSONObject();
		cityObject.put(TYWebMapFields.KEY_WEB_CITY_ID, city.getCityID());
		cityObject.put(TYWebMapFields.KEY_WEB_CITY_NAME, city.getName());
		cityObject.put(TYWebMapFields.KEY_WEB_CITY_SHORT_NAME, city.getSname());
		cityObject.put(TYWebMapFields.KEY_WEB_CITY_LONGITUDE, city.getLongitude());
		cityObject.put(TYWebMapFields.KEY_WEB_CITY_LATITUDE, city.getLatitude());
		cityObject.put(TYWebMapFields.KEY_WEB_CITY_STATUS, city.getStatus());
		return cityObject;
	}

	public static JSONObject generateBuildingJson(TYBuilding building) throws JSONException {
		JSONObject buildingObject = new JSONObject();
		buildingObject.put(TYWebMapFields.KEY_WEB_BUILDING_CITY_ID, building.getCityID());
		buildingObject.put(TYWebMapFields.KEY_WEB_BUILDING_ID, building.getBuildingID());
		buildingObject.put(TYWebMapFields.KEY_WEB_BUILDING_NAME, building.getName());
		buildingObject.put(TYWebMapFields.KEY_WEB_BUILDING_LONGITUDE, building.getLongitude());
		buildingObject.put(TYWebMapFields.KEY_WEB_BUILDING_LATITUDE, building.getLatitude());
		buildingObject.put(TYWebMapFields.KEY_WEB_BUILDING_ADDRESS, building.getAddress());
		buildingObject.put(TYWebMapFields.KEY_WEB_BUILDING_INIT_ANGLE, building.getInitAngle());
		buildingObject.put(TYWebMapFields.KEY_WEB_BUILDING_ROUTE_URL, building.getRouteURL());
		buildingObject.put(TYWebMapFields.KEY_WEB_BUILDING_OFFSET_X, building.getOffset().x);
		buildingObject.put(TYWebMapFields.KEY_WEB_BUILDING_OFFSET_Y, building.getOffset().y);
		buildingObject.put(TYWebMapFields.KEY_WEB_BUILDING_STATUS, building.getStatus());
		return buildingObject;
	}

	public static JSONObject generateMapInfoJson(TYMapInfo mapInfo) throws JSONException {
		JSONObject mapInfoObject = new JSONObject();
		mapInfoObject.put(TYWebMapFields.KEY_WEB_MAPINFO_CITYID, mapInfo.getCityID());
		mapInfoObject.put(TYWebMapFields.KEY_WEB_MAPINFO_BUILDINGID, mapInfo.getBuildingID());
		mapInfoObject.put(TYWebMapFields.KEY_WEB_MAPINFO_MAPID, mapInfo.getMapID());
		mapInfoObject.put(TYWebMapFields.KEY_WEB_MAPINFO_FLOOR, mapInfo.getFloorName());
		mapInfoObject.put(TYWebMapFields.KEY_WEB_MAPINFO_FLOOR_INDEX, mapInfo.getFloorNumber());
		mapInfoObject.put(TYWebMapFields.KEY_WEB_MAPINFO_SIZEX, mapInfo.getMapSize().x);
		mapInfoObject.put(TYWebMapFields.KEY_WEB_MAPINFO_SIZEY, mapInfo.getMapSize().y);
		mapInfoObject.put(TYWebMapFields.KEY_WEB_MAPINFO_XMIN, mapInfo.getMapExtent().getXmin());
		mapInfoObject.put(TYWebMapFields.KEY_WEB_MAPINFO_XMAX, mapInfo.getMapExtent().getXmax());
		mapInfoObject.put(TYWebMapFields.KEY_WEB_MAPINFO_YMIN, mapInfo.getMapExtent().getYmin());
		mapInfoObject.put(TYWebMapFields.KEY_WEB_MAPINFO_YMAX, mapInfo.getMapExtent().getYmax());
		return mapInfoObject;
	}

	public static JSONObject generateSymbolJson(List<TYFillSymbolRecord> fillList, List<TYIconSymbolRecord> iconList)
			throws JSONException {
		JSONObject symbolObject = new JSONObject();

		Map<Integer, TYFillSymbolRecord> fillDict = new HashMap<Integer, TYFillSymbolRecord>();
		for (TYFillSymbolRecord record : fillList) {
			fillDict.put(record.symbolID, record);
		}
		Map<Integer, TYIconSymbolRecord> iconDict = new HashMap<Integer, TYIconSymbolRecord>();
		for (TYIconSymbolRecord record : iconList) {
			iconDict.put(record.symbolID, record);
		}

		JSONObject defaultSymbol = new JSONObject();
		{
			Integer[] defaultArray = { 9999, 9998, 9997, 9996 };
			String[] defaultKey = { TYWebMapFields.KEY_WEB_DEFAULT_FILL_SYMBOL,
					TYWebMapFields.KEY_WEB_DEFAULT_HIGHLIGHT_FILL_SYMBOL,
					TYWebMapFields.KEY_WEB_DEFAULT_LINE_SYMBOL,
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

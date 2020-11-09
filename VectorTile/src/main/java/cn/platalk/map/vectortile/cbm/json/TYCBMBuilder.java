package cn.platalk.map.vectortile.cbm.json;

import java.io.File;
import java.util.List;

import cn.platalk.map.entity.base.map.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.map.vectortile.builder.TYVectorTileSettings;
import cn.platalk.utils.third.TYFileUtils;

public class TYCBMBuilder {
	static String FILE_CBM_JSON = "%s.json";

	static final String KEY_WEB_CITIES = "Cities";
	static final String KEY_WEB_BUILDINGS = "Buildings";
	static final String KEY_WEB_MAPINFOS = "MapInfo";
	static final String KEY_WEB_FILL_SYMBOLS = "FillSymbols";
	static final String KEY_WEB_ICON_SYMBOLS = "IconSymbols";
	static final String KEY_WEB_ICON_TEXT_SYMBOLS = "IconTextSymbols";

	private static String getCBMJsonPath(String buildingID) {
		String fileName = String.format(FILE_CBM_JSON, buildingID);
		String cbmDir = TYVectorTileSettings.GetCBMDir();
		return new File(cbmDir, fileName).toString();
	}

	public static void generateCBMJson(TYICity city, TYIBuilding building, List<TYIMapInfo> mapInfoList,
									   List<TYIMapDataFeatureRecord> mapDataRecords, List<TYIFillSymbolRecord> fillSymbols,
									   List<TYIIconSymbolRecord> iconSymbols, List<TYIIconTextSymbolRecord> iconTextSymbols) {

		JSONArray cityJsonArray = new JSONArray();
		cityJsonArray.put(city.toJson());

		JSONArray buildingJsonArray = new JSONArray();
		buildingJsonArray.put(building.toJson());

		JSONArray mapInfoJsonArray = new JSONArray();
		for (TYIMapInfo mapInfo : mapInfoList) {
			mapInfoJsonArray.put(mapInfo.toJson());
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(KEY_WEB_CITIES, cityJsonArray);
			jsonObject.put(KEY_WEB_BUILDINGS, buildingJsonArray);
			jsonObject.put(KEY_WEB_MAPINFOS, mapInfoJsonArray);

			if ("V4".equals(building.getRouteURL())) {
				JSONArray fillJsonArray = new JSONArray();
				for (TYIFillSymbolRecord fillSymbol : fillSymbols) {
					fillJsonArray.put(fillSymbol.toJson());
				}
				jsonObject.put(KEY_WEB_FILL_SYMBOLS, fillJsonArray);

				JSONArray iconTextJsonArray = new JSONArray();
				for (TYIIconTextSymbolRecord iconTextSymbol : iconTextSymbols) {
					iconTextJsonArray.put(iconTextSymbol.toJson());
				}
				jsonObject.put(KEY_WEB_ICON_TEXT_SYMBOLS, iconTextJsonArray);

				jsonObject.put("Symbols",
						TYSymbolExtractor.extractSymbolJson(mapDataRecords, fillSymbols, iconTextSymbols));

				JSONObject descriptionObject = new JSONObject();
				descriptionObject.put("cities", 1);
				descriptionObject.put("buildings", 1);
				descriptionObject.put("mapInfos", mapInfoList.size());
				descriptionObject.put("fills", fillSymbols.size());
				descriptionObject.put("iconTexts", iconTextSymbols.size());
				jsonObject.put("Description", descriptionObject);
			}
			jsonObject.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String cbmJsonPath = getCBMJsonPath(building.getBuildingID());
		System.out.println(cbmJsonPath);
		TYFileUtils.makeFolders(cbmJsonPath);
		TYFileUtils.writeFile(cbmJsonPath, jsonObject.toString());
	}
}

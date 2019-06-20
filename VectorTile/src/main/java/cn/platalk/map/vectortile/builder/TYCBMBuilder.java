package cn.platalk.map.vectortile.builder;

import java.io.File;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.map.entity.base.TYIBuilding;
import cn.platalk.map.entity.base.TYICity;
import cn.platalk.map.entity.base.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.TYIIconSymbolRecord;
import cn.platalk.map.entity.base.TYIIconTextSymbolRecord;
import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.map.entity.base.TYIMapInfo;

class TYCBMBuilder {
	static String FILE_CBM_JSON = "%s.json";

	private static String getCBMJsonPath(String buildingID) {
		String fileName = String.format(FILE_CBM_JSON, buildingID);
		String cbmDir = TYVectorTileSettings.GetCBMDir();
		return new File(cbmDir, fileName).toString();
	}

	public static void generateCBMJson(TYICity city, TYIBuilding building, List<TYIMapInfo> mapInfoList,
			List<TYIMapDataFeatureRecord> mapDataRecords, List<TYIFillSymbolRecord> fillSymbols,
			List<TYIIconSymbolRecord> iconSymbols, List<TYIIconTextSymbolRecord> iconTextSymbols) {

		JSONArray cityJsonArray = new JSONArray();
		cityJsonArray.put(TYCBMObjectBuilder.generateCityJson(city));

		JSONArray buildingJsonArray = new JSONArray();
		buildingJsonArray.put(TYCBMObjectBuilder.generateBuildingJson(building));

		JSONArray mapInfoJsonArray = new JSONArray();
		for (int i = 0; i < mapInfoList.size(); ++i) {
			mapInfoJsonArray.put(TYCBMObjectBuilder.generateMapInfoJson(mapInfoList.get(i)));
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(TYCBMFields.KEY_WEB_CITIES, cityJsonArray);
			jsonObject.put(TYCBMFields.KEY_WEB_BUILDINGS, buildingJsonArray);
			jsonObject.put(TYCBMFields.KEY_WEB_MAPINFOS, mapInfoJsonArray);

			if ("V4".equals(building.getRouteURL())) {
				JSONArray fillJsonArray = new JSONArray();
				for (int i = 0; i < fillSymbols.size(); ++i) {
					fillJsonArray.put(TYSymbolObjectBuilder.generateFillJson(fillSymbols.get(i)));
				}
				jsonObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOLS, fillJsonArray);

				// JSONArray iconJsonArray = new JSONArray();
				// for (int i = 0; i < iconSymbols.size(); ++i) {
				// iconJsonArray.put(TYSymbolObjectBuilder.generateIconJson(iconSymbols.get(i)));
				// }
				// jsonObject.put(TYSymbolFields.KEY_WEB_ICON_SYMBOLS,
				// iconJsonArray);

				JSONArray iconTextJsonArray = new JSONArray();
				for (int i = 0; i < iconTextSymbols.size(); ++i) {
					iconTextJsonArray.put(TYSymbolObjectBuilder.generateIconTextJson(iconTextSymbols.get(i)));
				}
				jsonObject.put(TYSymbolFields.KEY_WEB_ICON_TEXT_SYMBOLS, iconTextJsonArray);

				jsonObject.put("Symbols", TYSymbolExtractor.extractSymbolJson(mapDataRecords));

				JSONObject descriptionObject = new JSONObject();
				descriptionObject.put("cities", 1);
				descriptionObject.put("buildings", 1);
				descriptionObject.put("mapInfos", mapInfoList.size());
				descriptionObject.put("fills", fillSymbols.size());
				descriptionObject.put("icons", iconSymbols.size());
				jsonObject.put("description", descriptionObject);
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

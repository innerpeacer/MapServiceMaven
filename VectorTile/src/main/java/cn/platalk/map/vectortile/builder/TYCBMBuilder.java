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
			List<TYIIconSymbolRecord> iconSymbols) {

		JSONArray cityJsonArray = new JSONArray();
		cityJsonArray.put(TYCBMObjectBuilder.generateCityJson(city));

		JSONArray buildingJsonArray = new JSONArray();
		buildingJsonArray.put(TYCBMObjectBuilder.generateBuildingJson(building));

		JSONArray mapInfoJsonArray = new JSONArray();
		for (int i = 0; i < mapInfoList.size(); ++i) {
			mapInfoJsonArray.put(TYCBMObjectBuilder.generateMapInfoJson(mapInfoList.get(i)));
		}

		JSONArray fillJsonArray = new JSONArray();
		for (int i = 0; i < fillSymbols.size(); ++i) {
			fillJsonArray.put(TYSymbolObjectBuilder.generateFillJson(fillSymbols.get(i)));
		}

		JSONArray iconJsonArray = new JSONArray();
		for (int i = 0; i < iconSymbols.size(); ++i) {
			iconJsonArray.put(TYSymbolObjectBuilder.generateIconJson(iconSymbols.get(i)));
		}

		JSONObject descriptionObject = new JSONObject();
		descriptionObject.put("cities", 1);
		descriptionObject.put("buildings", 1);
		descriptionObject.put("mapInfos", mapInfoList.size());
		descriptionObject.put("fills", fillSymbols.size());
		descriptionObject.put("icons", iconSymbols.size());

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(TYCBMFields.KEY_WEB_CITIES, cityJsonArray);
			jsonObject.put(TYCBMFields.KEY_WEB_BUILDINGS, buildingJsonArray);
			jsonObject.put(TYCBMFields.KEY_WEB_MAPINFOS, mapInfoJsonArray);
			jsonObject.put(TYSymbolFields.KEY_WEB_FILL_SYMBOLS, fillJsonArray);
			jsonObject.put(TYSymbolFields.KEY_WEB_ICON_SYMBOLS, iconJsonArray);
			jsonObject.put("Symbols", TYSymbolExtractor.extractSymbolJson(mapDataRecords, fillSymbols, iconSymbols));
			jsonObject.put("description", descriptionObject);
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

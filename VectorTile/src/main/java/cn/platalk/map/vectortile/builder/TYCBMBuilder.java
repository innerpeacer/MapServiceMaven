package cn.platalk.map.vectortile.builder;

import java.io.File;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.map.entity.base.TYIBuilding;
import cn.platalk.map.entity.base.TYICity;
import cn.platalk.map.entity.base.TYIMapInfo;

class TYCBMBuilder {
	static String FILE_CBM_JSON = "%s.json";

	private static String getCBMJsonPath(String buildingID) {
		String fileName = String.format(FILE_CBM_JSON, buildingID);
		String cbmDir = TYVectorTileSettings.GetCBMDir();
		return new File(cbmDir, fileName).toString();
	}

	public static void generateCBMJson(TYICity city, TYIBuilding building, List<TYIMapInfo> mapInfoList) {

		JSONArray cityJsonArray = new JSONArray();
		JSONObject cityObject;
		try {
			cityObject = TYCBMObjectBuilder.generateCityJson(city);
			cityJsonArray.put(cityObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONArray buildingJsonArray = new JSONArray();
		JSONObject buildingObject;
		try {
			buildingObject = TYCBMObjectBuilder.generateBuildingJson(building);
			buildingJsonArray.put(buildingObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONArray mapInfoJsonArray = new JSONArray();
		try {
			for (int i = 0; i < mapInfoList.size(); ++i) {
				TYIMapInfo mapInfo = mapInfoList.get(i);
				JSONObject mapInfoObject = TYCBMObjectBuilder.generateMapInfoJson(mapInfo);
				mapInfoJsonArray.put(mapInfoObject);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(TYCBMFields.KEY_WEB_CITIES, cityJsonArray);
			jsonObject.put(TYCBMFields.KEY_WEB_BUILDINGS, buildingJsonArray);
			jsonObject.put(TYCBMFields.KEY_WEB_MAPINFOS, mapInfoJsonArray);
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

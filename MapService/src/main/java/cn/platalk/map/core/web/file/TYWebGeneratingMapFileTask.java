package cn.platalk.map.core.web.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.core.pbf.mapdata.TYMapDataPbf.TYIndoorDataPbf;
import cn.platalk.core.pbf.mapdata.wrapper.TYIndoorDataPbfBuilder;
import cn.platalk.core.pbf.poi.TYPoiPbf;
import cn.platalk.core.pbf.poi.wrapper.TYPoiPbfBuilder;
import cn.platalk.map.core.web.TYWebMapFields;
import cn.platalk.map.core.web.TYWebMapGeojsonDataBuilder;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYCity;
import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.map.entity.base.impl.TYMapInfo;
import cn.platalk.mysql.map.TYCityDBAdapter;
import cn.platalk.mysql.map.TYMapDataDBAdapter;
import cn.platalk.mysql.map.TYMapInfoDBAdapter;
import cn.platalk.mysql.map.TYSymbolDBAdapter;
import cn.platalk.utils.third.TYFileUtils;

public class TYWebGeneratingMapFileTask {
	final TYBuilding targetBuilding;

	public static void main(String[] args) {
		String cityID = "0755";
		String buildingID = "07550023";

		TYBuilding building = new TYBuilding();
		building.setCityID(cityID);
		building.setBuildingID(buildingID);

		TYWebGeneratingMapFileTask task = new TYWebGeneratingMapFileTask(building);
		task.startTask();
	}

	public TYWebGeneratingMapFileTask(TYBuilding building) {
		this.targetBuilding = building;
	}

	public void startTask() {
		TYWebPbfPathManager.MakeBuildingFolder(targetBuilding);
		generateCBMJson();
		generateMapDataPbf();
		// generateMapDataGeojson();
		generatePoiPbf();
	}

	private void generateCBMJson() {
		TYCityDBAdapter cityDB = new TYCityDBAdapter();
		cityDB.connectDB();
		TYCity city = cityDB.getCity(targetBuilding.getCityID());
		cityDB.disconnectDB();

		TYMapInfoDBAdapter db = new TYMapInfoDBAdapter();
		db.connectDB();
		List<TYMapInfo> mapInfoList = db.getMapInfos(targetBuilding.getBuildingID());
		db.disconnectDB();

		JSONArray cityJsonArray = new JSONArray();
		cityJsonArray.put(city.toJson());

		JSONArray buildingJsonArray = new JSONArray();
		buildingJsonArray.put(targetBuilding.toJson());

		JSONArray mapInfoJsonArray = new JSONArray();
		try {
			for (TYMapInfo mapInfo : mapInfoList) {
				JSONObject mapInfoObject = mapInfo.toJson();
				mapInfoJsonArray.put(mapInfoObject);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(TYWebMapFields.KEY_WEB_CITIES, cityJsonArray);
			jsonObject.put(TYWebMapFields.KEY_WEB_BUILDINGS, buildingJsonArray);
			jsonObject.put(TYWebMapFields.KEY_WEB_MAPINFOS, mapInfoJsonArray);
			jsonObject.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String cbmJsonPath = TYWebCBMPathManager.getCBMJsonPath(targetBuilding.getBuildingID());
		System.out.println(cbmJsonPath);
		TYFileUtils.makeFolders(cbmJsonPath);
		TYFileUtils.writeFile(cbmJsonPath, jsonObject.toString());
	}

	private void generatePoiPbf() {
		System.out.println("generatePoiPbf");

		String buildingID = targetBuilding.getBuildingID();
		TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(buildingID);
		mapDB.connectDB();
		List<TYMapDataFeatureRecord> mapDataRecordList = mapDB.getAllMapDataRecords();
		mapDB.disconnectDB();

		System.out.println(mapDataRecordList.size() + " poi");

		TYPoiPbf.PoiCollectionPbf collectionPbf = TYPoiPbfBuilder.generatePoiCollectionObject(mapDataRecordList);
		String pbfPath = TYWebPbfPathManager.getPoiPbfPath(targetBuilding.getCityID(), targetBuilding.getBuildingID());
		TYFileUtils.makeFolders(pbfPath);
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(pbfPath);
			collectionPbf.writeTo(output);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Finish generatePoiPbf");
	}

	private void generateMapDataPbf() {
		System.out.println("generateMapDataPbf");
		TYMapInfoDBAdapter mapInfoDB = new TYMapInfoDBAdapter();
		mapInfoDB.connectDB();
		List<TYMapInfo> mapInfoList = mapInfoDB.getMapInfos(targetBuilding.getBuildingID());
		mapInfoDB.disconnectDB();

		for (TYMapInfo info : mapInfoList) {
			System.out.println(info.toString());
			String cityID = targetBuilding.getCityID();
			String buildingID = targetBuilding.getBuildingID();
			String mapID = info.getMapID();
			TYIndoorDataPbf dataPbf;

			TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(buildingID);
			mapDB.connectDB();
			List<TYMapDataFeatureRecord> mapDataRecordList = mapDB.getAllMapDataRecords(mapID);
			mapDB.disconnectDB();

			TYSymbolDBAdapter symbolDB = new TYSymbolDBAdapter();
			symbolDB.connectDB();
			List<TYFillSymbolRecord> fillSymbolList = symbolDB.getFillSymbolRecords(buildingID);
			List<TYIconSymbolRecord> iconSymbolList = symbolDB.getIconSymbolRecords(buildingID);
			symbolDB.disconnectDB();
			dataPbf = TYIndoorDataPbfBuilder.generateMapDataObject(mapID, mapDataRecordList, fillSymbolList,
					iconSymbolList);

			String pbfPath = TYWebPbfPathManager.getMapDataPbfPath(cityID, buildingID, mapID);
			TYFileUtils.makeFolders(pbfPath);
			FileOutputStream output = null;
			try {
				output = new FileOutputStream(pbfPath);
				dataPbf.writeTo(output);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (output != null) {
						output.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Finish generateMapDataPbf");
	}

	void generateMapDataGeojson() {
		System.out.println("generateMapDataGeojson");
		TYMapInfoDBAdapter mapInfoDB = new TYMapInfoDBAdapter();
		mapInfoDB.connectDB();
		List<TYMapInfo> mapInfoList = mapInfoDB.getMapInfos(targetBuilding.getBuildingID());
		mapInfoDB.disconnectDB();

		for (TYMapInfo info : mapInfoList) {
			System.out.println(info.toString());
			String cityID = targetBuilding.getCityID();
			String buildingID = targetBuilding.getBuildingID();
			String mapID = info.getMapID();

			TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(buildingID);
			mapDB.connectDB();
			List<TYMapDataFeatureRecord> mapDataRecordList = mapDB.getAllMapDataRecords(mapID);
			mapDB.disconnectDB();

			TYSymbolDBAdapter symbolDB = new TYSymbolDBAdapter();
			symbolDB.connectDB();
			List<TYFillSymbolRecord> fillSymbolList = symbolDB.getFillSymbolRecords(buildingID);
			List<TYIconSymbolRecord> iconSymbolList = symbolDB.getIconSymbolRecords(buildingID);
			symbolDB.disconnectDB();

			JSONObject mapDataObject;
			try {
				mapDataObject = TYWebMapGeojsonDataBuilder.generateMapDataObject(mapDataRecordList, fillSymbolList,
						iconSymbolList);
				String geojsonPath = TYWebGeojsonPathManager.getMapDataGeojsonPath(cityID, buildingID, mapID);
				System.out.println(geojsonPath);
				TYFileUtils.makeFolders(geojsonPath);
				TYFileUtils.writeFile(geojsonPath, mapDataObject.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Finish generateMapDataGeojson");
	}
}

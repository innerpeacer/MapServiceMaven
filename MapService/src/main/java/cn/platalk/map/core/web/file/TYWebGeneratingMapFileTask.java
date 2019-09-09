package cn.platalk.map.core.web.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.map.core.pbf.TYWebMapPbfDataBuilder;
import cn.platalk.map.core.pbf.TYWebMapPoiDataBuilder;
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
import innerpeacer.mapdata.pbf.TYMapDataPbf.TYIndoorDataPbf;
import innerpeacer.mapdata.pbf.TYPoiPbf;

public class TYWebGeneratingMapFileTask {
	TYBuilding targetBuilding;

	public static void main(String[] args) throws IOException {
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
			for (int i = 0; i < mapInfoList.size(); ++i) {
				TYMapInfo mapInfo = mapInfoList.get(i);
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
		TYMapDataDBAdapter mapdb = new TYMapDataDBAdapter(buildingID);
		mapdb.connectDB();
		List<TYMapDataFeatureRecord> mapDataRecordList = mapdb.getAllMapDataRecords();
		mapdb.disconnectDB();

		System.out.println(mapDataRecordList.size() + " pois");

		TYPoiPbf.PoiCollectionPbf collectionPbf = TYWebMapPoiDataBuilder.generatePoiCollectionObject(mapDataRecordList);
		String pbfPath = TYWebPbfPathManager.getPoiPbfPath(targetBuilding.getCityID(), targetBuilding.getBuildingID());
		TYFileUtils.makeFolders(pbfPath);
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(pbfPath);
			collectionPbf.writeTo(output);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
			TYIndoorDataPbf dataPbf = null;

			TYMapDataDBAdapter mapdb = new TYMapDataDBAdapter(buildingID);
			mapdb.connectDB();
			List<TYMapDataFeatureRecord> mapDataRecordList = mapdb.getAllMapDataRecords(mapID);
			mapdb.disconnectDB();

			TYSymbolDBAdapter symboldb = new TYSymbolDBAdapter();
			symboldb.connectDB();
			List<TYFillSymbolRecord> fillSymbolList = symboldb.getFillSymbolRecords(buildingID);
			List<TYIconSymbolRecord> iconSymbolList = symboldb.getIconSymbolRecords(buildingID);
			symboldb.disconnectDB();
			dataPbf = TYWebMapPbfDataBuilder.generateMapDataObject(mapID, mapDataRecordList, fillSymbolList,
					iconSymbolList);

			String pbfPath = TYWebPbfPathManager.getMapDataPbfPath(cityID, buildingID, mapID);
			TYFileUtils.makeFolders(pbfPath);
			FileOutputStream output = null;
			try {
				output = new FileOutputStream(pbfPath);
				dataPbf.writeTo(output);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
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

			TYMapDataDBAdapter mapdb = new TYMapDataDBAdapter(buildingID);
			mapdb.connectDB();
			List<TYMapDataFeatureRecord> mapDataRecordList = mapdb.getAllMapDataRecords(mapID);
			mapdb.disconnectDB();

			TYSymbolDBAdapter symboldb = new TYSymbolDBAdapter();
			symboldb.connectDB();
			List<TYFillSymbolRecord> fillSymbolList = symboldb.getFillSymbolRecords(buildingID);
			List<TYIconSymbolRecord> iconSymbolList = symboldb.getIconSymbolRecords(buildingID);
			symboldb.disconnectDB();

			JSONObject mapDataObject = null;
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

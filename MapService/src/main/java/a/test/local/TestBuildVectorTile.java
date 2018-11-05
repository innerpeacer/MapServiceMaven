package a.test.local;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.core.config.TYBrtMapEnvironment;
import cn.platalk.brtmap.core.config.TYServerEnviroment;
import cn.platalk.brtmap.db.map.TYBuildingDBAdapter;
import cn.platalk.brtmap.db.map.TYCityDBAdapter;
import cn.platalk.brtmap.db.map.TYMapDataDBAdapter;
import cn.platalk.brtmap.db.map.TYMapInfoDBAdapter;
import cn.platalk.brtmap.db.map.TYSymbolDBAdapter;
import cn.platalk.brtmap.entity.base.TYIFillSymbolRecord;
import cn.platalk.brtmap.entity.base.TYIIconSymbolRecord;
import cn.platalk.brtmap.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.TYIMapInfo;
import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYCity;
import cn.platalk.brtmap.utils.third.TYFileUtils;
import cn.platalk.brtmap.vectortile.builder.TYVectorTileBuilder;
import cn.platalk.brtmap.vectortile.builder.TYVectorTileSettings;

public class TestBuildVectorTile {
	public static void main(String[] args) {
		TYServerEnviroment.initialize();

		String buildingID = "00270005";
		// String buildingID = "00270001";
		TYBuildingDBAdapter db = new TYBuildingDBAdapter();
		db.connectDB();
		TYBuilding building = db.getBuilding(buildingID);
		db.disconnectDB();

		TYVectorTileSettings.SetTileRoot(TYBrtMapEnvironment.GetVectorTileRoot());
		TYVectorTileSettings.SetDefaultMaxZoom(19);
		// TYVectorTileSettings.setDefaultMinZoom(15);

		File tileDir = new File(TYBrtMapEnvironment.GetVectorTileRoot(), buildingID);
		TYFileUtils.deleteFile(tileDir.toString());

		TYVectorTileBuilder builder = new TYVectorTileBuilder(buildingID);

		TYCityDBAdapter cityDB = new TYCityDBAdapter();
		cityDB.connectDB();
		TYCity city = cityDB.getCity(building.getCityID());
		cityDB.disconnectDB();

		TYMapInfoDBAdapter infoDB = new TYMapInfoDBAdapter();
		infoDB.connectDB();
		List<TYIMapInfo> mapInfos = new ArrayList<TYIMapInfo>();
		mapInfos.addAll(infoDB.getMapInfos(buildingID));
		infoDB.disconnectDB();

		// for (int i = 0; i < mapInfos.size(); ++i) {
		// System.out.println(mapInfos.get(i));
		// }

		List<TYIFillSymbolRecord> fillSymbols = new ArrayList<TYIFillSymbolRecord>();
		List<TYIIconSymbolRecord> iconSymbols = new ArrayList<TYIIconSymbolRecord>();
		TYSymbolDBAdapter symbolDB = new TYSymbolDBAdapter();
		symbolDB.connectDB();
		fillSymbols.addAll(symbolDB.getFillSymbolRecords(buildingID));
		iconSymbols.addAll(symbolDB.getIconSymbolRecords(buildingID));
		symbolDB.disconnectDB();

		List<TYIMapDataFeatureRecord> mapDataRecords = new ArrayList<TYIMapDataFeatureRecord>();
		TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(buildingID);
		mapDB.connectDB();
		mapDataRecords.addAll(mapDB.getAllMapDataRecords());
		mapDB.disconnectDB();

		// TYIMapDataFeatureRecord record = mapDataRecords.get(0);
		// Coordinate[] cs = record.getGeometryData().getCoordinates();
		// System.out.println(cs[0]);

		builder.addData(city, building, mapInfos, mapDataRecords, fillSymbols, iconSymbols);
		try {
			builder.buildTile();
			System.out.println("Build Vector Tile for BuildingID: " + buildingID);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed Building Vector Tile for BuildingID: " + buildingID);
		}
	}
}

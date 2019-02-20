package cn.platalk.brtmap.core.web.file;

import java.io.File;

import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.utils.third.TYFileUtils;

public class TYBrtWebGeojsonPathManager {
	static String FILE_MAP_DATA_GEOJSON = "%s.geojson";

	private static String rootDir = TYBrtWebFilePathManager.BRT_MAPDATA_GEOJSON_ROOT;

	public static void setRootDir(String dir) {
		rootDir = dir;
	}

	public static String getMapDataGeojsonPath(String cityID,
			String buildingID, String mapID) {
		String buildingDir = getBuildingFolder(cityID, buildingID);
		String fileName = String.format(FILE_MAP_DATA_GEOJSON, mapID);
		return new File(buildingDir, fileName).toString();
	}

	public static String getRootDir() {
		return rootDir;
	}

	public static String getCityFolder(TYBuilding building) {
		return getCityFolder(building.getCityID(), building.getBuildingID());
	}

	public static String getBuildingFolder(TYBuilding building) {
		return getBuildingFolder(building.getCityID(), building.getBuildingID());
	}

	public static void MakeRootFolder(TYBuilding building) {
		String folder = rootDir + File.separator;
		TYFileUtils.makeFolders(folder);
	}

	public static void MakeCityFolder(TYBuilding building) {
		MakeCityFolder(building.getCityID(), building.getBuildingID());
	}

	public static void MakeBuildingFolder(TYBuilding building) {
		MakeBuildingFolder(building.getCityID(), building.getBuildingID());
	}

	private static void MakeCityFolder(String cityID, String buildingID) {
		String folder = getCityFolder(cityID, buildingID) + File.separator;
		TYFileUtils.makeFolders(folder);
	}

	private static void MakeBuildingFolder(String cityID, String buildingID) {
		String folder = getBuildingFolder(cityID, buildingID) + File.separator;
		TYFileUtils.makeFolders(folder);
	}

	private static String getCityFolder(String cityID, String buildingID) {
		return new File(rootDir, cityID).toString();
	}

	private static String getBuildingFolder(String cityID, String buildingID) {
		File cityFolder = new File(rootDir, cityID);
		return new File(cityFolder.toString(), buildingID).toString();
	}
}

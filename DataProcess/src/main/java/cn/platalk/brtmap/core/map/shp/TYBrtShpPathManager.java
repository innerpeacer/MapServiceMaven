package cn.platalk.brtmap.core.map.shp;

import java.io.File;

import cn.platalk.brtmap.core.map.base.TYIBrtShpDataManager;

public class TYBrtShpPathManager implements TYIBrtShpDataManager {
	private static final String MAP_DB_PATH = "TYMap.db";
	private static final String MAPINFO_JSON_PATH = "MapInfo_Building_%s.json";
	private static final String MAP_SHP_DIR = "MAP_SHP";
	private static final String MAP_SHP_PATH = "%s_%s.shp";

	private static final String ROUTE_SHP_DIR = "ROUTE_SHP";
	private static final String ROUTE_SHP_PATH = "%s.shp";

	private static final String SYMBOL_DB_PATH = "SYMBOL.db";

	private String buildingID;
	private String root;

	public TYBrtShpPathManager(String root, String buildingID) {
		this.root = root;
		this.buildingID = buildingID;
	}

	public String getMapDBPath() {
		return new File(root, MAP_DB_PATH).toString();
	}

	public String getMapInfoJsonPath() {
		return new File(root, String.format(MAPINFO_JSON_PATH, buildingID)).toString();
	}

	public String getSymbolDBPath() {
		return new File(root, SYMBOL_DB_PATH).toString();
	}

	public String getFloorDir(String floor) {
		return new File(getMapShpDir(), floor).toString();
	}

	public String getMapShpDir() {
		return new File(root, MAP_SHP_DIR).toString();
	}

	public String getRouteShpDir() {
		return new File(root, ROUTE_SHP_DIR).toString();
	}

	public String getMapShpPath(String layer, String floor) {
		String floorDir = getFloorDir(floor);
		return new File(floorDir, String.format(MAP_SHP_PATH, floor, layer)).toString();
	}

	public String getRouteShpPath(String layer) {
		return new File(getRouteShpDir(), String.format(ROUTE_SHP_PATH, layer)).toString();
	}

	@Override
	public String getRouteShpPathV3(String layer, String floor) {
		return null;
	}
}

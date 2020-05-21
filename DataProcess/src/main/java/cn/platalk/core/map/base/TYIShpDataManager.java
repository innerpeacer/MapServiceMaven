package cn.platalk.core.map.base;

public interface TYIShpDataManager {

	String getMapDBPath();

	String getMapInfoJsonPath();

	String getSymbolDBPath();

	String getFloorDir(String floor);

	String getMapShpDir();

	String getRouteShpDir();

	String getMapShpPath(String layer, String floor);

	String getRouteShpPath(String layer);

	String getRouteShpPathV3(String layer, String floor);
}

package cn.platalk.brtmap.core.map.base;

public interface TYIBrtShpDataManager {

	public String getMapDBPath();

	public String getMapInfoJsonPath();

	public String getSymbolDBPath();

	public String getFloorDir(String floor);

	public String getMapShpDir();

	public String getRouteShpDir();

	public String getMapShpPath(String layer, String floor);

	public String getRouteShpPath(String layer);

	public String getRouteShpPathV3(String layer, String floor);
}

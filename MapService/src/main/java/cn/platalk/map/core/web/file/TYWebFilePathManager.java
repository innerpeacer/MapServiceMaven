package cn.platalk.map.core.web.file;

import cn.platalk.map.core.config.TYMapEnvironment;

public class TYWebFilePathManager {

	public static String BRT_MAPDATA_ROOT = TYMapEnvironment.BRT_RESOURCE_ROOT
			+ "/mapdata";

	public static String BRT_MAPDATA_GEOJSON_ROOT = BRT_MAPDATA_ROOT
			+ "/geojson";
	public static String BRT_MAPDATA_PBF_ROOT = BRT_MAPDATA_ROOT + "/pbf";

	public static String BRT_MAPDATA_CBM_ROOT = BRT_MAPDATA_ROOT + "/cbm";

}

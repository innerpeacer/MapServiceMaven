package cn.platalk.brtmap.core.web.file;

import cn.platalk.brtmap.core.config.TYBrtMapEnvironment;

public class TYBrtWebFilePathManager {

	public static String BRT_MAPDATA_ROOT = TYBrtMapEnvironment.BRT_RESOURCE_ROOT
			+ "/mapdata";

	public static String BRT_MAPDATA_GEOJSON_ROOT = BRT_MAPDATA_ROOT
			+ "/geojson";
	public static String BRT_MAPDATA_PBF_ROOT = BRT_MAPDATA_ROOT + "/pbf";

	public static String BRT_MAPDATA_CBM_ROOT = BRT_MAPDATA_ROOT + "/cbm";

}

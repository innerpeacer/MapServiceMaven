package cn.platalk.map.core.web.file;

import java.io.File;

public class TYWebCBMPathManager {
	static final String FILE_CBM_JSON = "%s.json";

	private static String rootDir = TYWebFilePathManager.BRT_MAPDATA_CBM_ROOT;

	public static void setRootDir(String dir) {
		rootDir = dir;
	}

	public static String getCBMJsonPath(String buildingID) {
		String fileName = String.format(FILE_CBM_JSON, buildingID);
		return new File(rootDir, fileName).toString();
	}
}

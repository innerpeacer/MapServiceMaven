package cn.platalk.brtmap.core.web.file;

import java.io.File;

public class TYBrtWebCBMPathManager {
	static String FILE_CBM_JSON = "%s.json";

	private static String rootDir = TYBrtWebFilePathManager.BRT_MAPDATA_CBM_ROOT;

	public static void setRootDir(String dir) {
		rootDir = dir;
	}

	public static String getCBMJsonPath(String buildingID) {
		String fileName = String.format(FILE_CBM_JSON, buildingID);
		return new File(rootDir, fileName).toString();
	}
}

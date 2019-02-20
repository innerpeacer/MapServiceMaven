package cn.platalk.map.core.config;

import java.io.File;

import cn.platalk.map.res.TYIResourceManager;

public class TYMapEnvironment {
	static TYIResourceManager resourceManager = TYServerEnviroment.resourceManager;

	public static String GetFontDir() {
		return resourceManager.getFontDir();
	}

	public static String GetFontminDir() {
		return resourceManager.getFontminDir();
	}

	public static String GetNodePath() {
		return resourceManager.getNodePath();
	}

	public static String GetFontScriptPath() {
		return resourceManager.getFontScriptPath();
	}

	public static String GetGlyphsDir() {
		return resourceManager.getGlyphsDir();
	}

	public static String GetGlyphsScriptPath() {
		return resourceManager.getGlyphsScriptPath();
	}

	// public static String FONT_DIR =
	// "/Users/innerpeacer/BrtMapProject/BrtMapTools/FontTool/Resources/fonts";
	// public static String FONTMIN_DIR =
	// "/Users/innerpeacer/BrtMapProject/BrtMapTools/FontTool/Resources/fonts-min";
	// public static String FONTMIN_SCRIPT_PATH =
	// "/Users/innerpeacer/BrtMapProject/BrtMapTools/FontTool/BrtFontTool/script/node-fontmin.js";
	// public static String GLYPHS_DIR =
	// "/Users/innerpeacer/BrtMapProject/BrtMapTools/FontTool/Resources/glyphs";
	// public static String GLYPHS_DIR =
	// "/Users/innerpeacer/Dev/apache-tomcat-7.0.59/webapps/BrtMapResource/glyphs";
	// public static String GLYPHS_SCRIPT_PATH =
	// "/Users/innerpeacer/BrtMapProject/BrtMapTools/FontTool/BrtFontTool/script/node-glyphs.js";

	// public static String FONT_DIR =
	// "/Users/innerpeacer/BrtMapProject/BrtMapTools/FontTool/Resources/fonts-min";
	// public static String FONT_OUTPUT_DIR =
	// "/Users/innerpeacer/BrtMapProject/BrtMapTools/FontTool/Resources/glyphs";

	// private static final String RAW_DATA_ROOT_LOCAL =
	// "/home/innerpeacer/Brt/BrtMapRawData";
	// private static final String RAW_DATA_ROOT_REMOTE =
	// "/Users/innerpeacer/BrtMapProject/BrtMapRawData";

	// private static final String RAW_DATA_ROOT_REMOTE = "D:/BrtMapRawData";
	public static String BRT_RESOURCE_ROOT = "/Users/innerpeacer/Dev/apache-tomcat-7.0.59/webapps/BrtMapResource";

	public static String GetVectorTileRoot() {
		return resourceManager.getVectorTileRoot();
	}

	public static String GetRawDataRootDir() {
		return resourceManager.getRawDataRootDir();
	}

	public static String GetShpRootDir(String buildingID) {
		return new File(GetRawDataRootDir(), buildingID).toString();
	}
}

package cn.platalk.map.res;

public class TYLinuxResourceManager implements TYIResourceManager {
	private static final String RAW_DATA_ROOT_LINUX = "/wt_res/WTMapProject/WTMapRawData";
	private static final String VECTOR_TILE_ROOT_LINUX = "/usr/local/apache-tomcat-7.0.81/webapps/WTMapResource/mapdata";

	private static String NODE_PATH_LINUX = "/usr/local/bin/node";

	private static String FONT_DIR_LINUX = "/wt_res/WTMapProject/WTMapRawData";
	private static String FONTMIN_DIR_LINUX = "/wt_res/WTMapProject/Fontmin";
	private static String GLYPHS_DIR_LINUX = "/usr/local/apache-tomcat-7.0.81/webapps/WTMapResource/glyphs";
	private static String FONTMIN_SCRIPT_PATH_LINUX = "/wt_res/WTMapProject/NodeJS/WTFontTool/script/node-fontmin.js";
	// private static String GLYPHS_SCRIPT_PATH_LINUX =
	// "/wt_res/WTMapProject/NodeJS/WTFontTool/script/node-glyphs.js";
	private static String GLYPHS_SCRIPT_PATH_LINUX = "/wt_res/WTMapProject/NodeJS/WTFontTool/script/node-glyphs-ranged.js";

	private static String DB_HOST_NAME = "localhost:3306";
	private static String DB_USER_NAME = "root";
	// private static String DB_PASSWORD = "Brt@2017";
	// private static String DB_PASSWORD = "Zflb@2018";
	private static String DB_PASSWORD = "chen@2018";

	@Override
	public String getRawDataRootDir() {
		return RAW_DATA_ROOT_LINUX;
	}

	@Override
	public String getVectorTileRoot() {
		return VECTOR_TILE_ROOT_LINUX;
	}

	public String getNodePath() {
		return NODE_PATH_LINUX;
	}

	@Override
	public String getFontDir() {
		return FONT_DIR_LINUX;
	}

	@Override
	public String getFontminDir() {
		return FONTMIN_DIR_LINUX;
	}

	@Override
	public String getGlyphsDir() {
		return GLYPHS_DIR_LINUX;
	}

	@Override
	public String getFontScriptPath() {
		return FONTMIN_SCRIPT_PATH_LINUX;
	}

	@Override
	public String getGlyphsScriptPath() {
		return GLYPHS_SCRIPT_PATH_LINUX;
	}

	public String getDBHost() {
		return DB_HOST_NAME;
	}

	public String getDBUserName() {
		return DB_USER_NAME;
	}

	public String getDBPassword() {
		return DB_PASSWORD;
	}
}

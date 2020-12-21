package cn.platalk.map.res;

public class TYLinuxResourceManager implements TYIResourceManager {
	static final String RESOURCE_ROOT_LINUX = "/usr/local/apache-tomcat-7.0.81/webapps/WTMapResource";
	static final String RAW_DATA_ROOT_LINUX = "/wt_res/WTMapProject/WTMapRawData";
	static final String VECTOR_TILE_ROOT_LINUX = RESOURCE_ROOT_LINUX + "/mapdata";

	static final String THEME_ROOT_LINUX = RESOURCE_ROOT_LINUX + "/theme";

	static final String NODE_PATH_LINUX = "/usr/local/bin/node";

	static final String FONT_DIR_LINUX = "/wt_res/WTMapProject/WTMapRawData";
	static final String FONTMIN_DIR_LINUX = "/wt_res/WTMapProject/Fontmin";
	static final String GLYPHS_DIR_LINUX = RESOURCE_ROOT_LINUX + "/glyphs";
	static final String FONTMIN_SCRIPT_PATH_LINUX = "/wt_res/WTMapProject/NodeJS/WTFontTool/script/node-fontmin.js";
	// private static String GLYPHS_SCRIPT_PATH_LINUX =
	// "/wt_res/WTMapProject/NodeJS/WTFontTool/script/node-glyphs.js";
	static final String GLYPHS_SCRIPT_PATH_LINUX = "/wt_res/WTMapProject/NodeJS/WTFontTool/script/node-glyphs-ranged.js";

	static final String DB_HOST_NAME = "localhost:3306";
	static final String DB_USER_NAME = "root";
	// private static String DB_PASSWORD = "Brt@2017";
	// private static String DB_PASSWORD = "Zflb@2018";
	static final String DB_PASSWORD = "chen@2018";

	@Override
	public String getResourceRootDir() {
		return RESOURCE_ROOT_LINUX;
	}

	@Override
	public String getThemeRootDir() {
		return THEME_ROOT_LINUX;
	}

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

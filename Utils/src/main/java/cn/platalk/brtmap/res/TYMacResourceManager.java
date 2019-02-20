package cn.platalk.brtmap.res;

import cn.platalk.brtmap.res.TYIResourceManager;

public class TYMacResourceManager implements TYIResourceManager {
	private static final String RAW_DATA_ROOT_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapRawData";
	private static final String VECTOR_TILE_ROOT_LOCAL = "/Users/innerpeacer/Dev/apache-tomcat-7.0.59/webapps/WTMapResource/mapdata";

	private static String NODE_PATH_LOCAL = "/Users/innerpeacer/.nvm/versions/node/v4.1.0/bin/node";

	private static String FONT_DIR_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/Resources/fonts";
	private static String FONTMIN_DIR_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/Resources/fonts-min";
	private static String GLYPHS_DIR_LOCAL = "/Users/innerpeacer/Dev/apache-tomcat-7.0.59/webapps/WTMapResource/glyphs";
	private static String FONTMIN_SCRIPT_PATH_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/WTFontTool/script/node-fontmin.js";
	private static String GLYPHS_SCRIPT_PATH_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/WTFontTool/script/node-glyphs.js";

	private static String DB_HOST_NAME = "localhost:3306";
	private static String DB_USER_NAME = "root";
	private static String DB_PASSWORD = "Brt@2017";

	@Override
	public String getRawDataRootDir() {
		return RAW_DATA_ROOT_LOCAL;
	}

	@Override
	public String getVectorTileRoot() {
		return VECTOR_TILE_ROOT_LOCAL;
	}

	public String getNodePath() {
		return NODE_PATH_LOCAL;
	}

	@Override
	public String getFontDir() {
		return FONT_DIR_LOCAL;
	}

	@Override
	public String getFontminDir() {
		return FONTMIN_DIR_LOCAL;
	}

	@Override
	public String getGlyphsDir() {
		return GLYPHS_DIR_LOCAL;
	}

	@Override
	public String getFontScriptPath() {
		return FONTMIN_SCRIPT_PATH_LOCAL;
	}

	@Override
	public String getGlyphsScriptPath() {
		return GLYPHS_SCRIPT_PATH_LOCAL;
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

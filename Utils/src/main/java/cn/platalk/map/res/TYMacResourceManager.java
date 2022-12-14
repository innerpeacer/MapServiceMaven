package cn.platalk.map.res;

public class TYMacResourceManager implements TYIResourceManager {
    private static final String TOMCAT_VERSION = "9.0.64";
    static final String RESOURCE_ROOT_LOCAL = "/Users/innerpeacer/Dev/apache-tomcat-" + TOMCAT_VERSION + "/webapps/WTMapResource";

    static final String RAW_DATA_ROOT_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapRawData";
    static final String VECTOR_TILE_ROOT_LOCAL = RESOURCE_ROOT_LOCAL + "/mapdata";

    static final String THEME_ROOT_LOCAL = RESOURCE_ROOT_LOCAL + "/theme";

    static final String NODE_PATH_LOCAL = "/Users/innerpeacer/.nvm/versions/node/v4.1.0/bin/node";

    static final String FONT_DIR_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/Resources/fonts";
    static final String FONTMIN_DIR_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/Resources/fonts-min";
    static final String GLYPHS_DIR_LOCAL = RESOURCE_ROOT_LOCAL + "/glyphs";
    static final String FONTMIN_SCRIPT_PATH_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/WTFontTool/script/node-fontmin.js";
    // private static String GLYPHS_SCRIPT_PATH_LOCAL =
    // "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/WTFontTool/script/node-glyphs.js";
    static final String GLYPHS_SCRIPT_PATH_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/WTFontTool/script/node-glyphs-ranged.js";

    static final String DB_HOST_NAME = "localhost:3306";
    static final String DB_USER_NAME = "root";
    static final String DB_PASSWORD = "Chen2022";

    @Override
    public String getResourceRootDir() {
        return RESOURCE_ROOT_LOCAL;
    }

    @Override
    public String getThemeRootDir() {
        return THEME_ROOT_LOCAL;
    }

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

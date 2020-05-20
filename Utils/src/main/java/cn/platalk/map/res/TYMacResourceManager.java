package cn.platalk.map.res;

public class TYMacResourceManager implements TYIResourceManager {
    static final String RAW_DATA_ROOT_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapRawData";
    static final String VECTOR_TILE_ROOT_LOCAL = "/Users/innerpeacer/Dev/apache-tomcat-7.0.59/webapps/WTMapResource/mapdata";

    static final String NODE_PATH_LOCAL = "/Users/innerpeacer/.nvm/versions/node/v4.1.0/bin/node";

    static final String FONT_DIR_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/Resources/fonts";
    static final String FONTMIN_DIR_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/Resources/fonts-min";
    static final String GLYPHS_DIR_LOCAL = "/Users/innerpeacer/Dev/apache-tomcat-7.0.59/webapps/WTMapResource/glyphs";
    static final String FONTMIN_SCRIPT_PATH_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/WTFontTool/script/node-fontmin.js";
    // private static String GLYPHS_SCRIPT_PATH_LOCAL =
    // "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/WTFontTool/script/node-glyphs.js";
    static final String GLYPHS_SCRIPT_PATH_LOCAL = "/Users/innerpeacer/WTMapProject/WTMapTools/FontTool/WTFontTool/script/node-glyphs-ranged.js";

    static final String DB_HOST_NAME = "localhost:3306";
    static final String DB_USER_NAME = "root";
    static final String DB_PASSWORD = "Brt@2017";

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

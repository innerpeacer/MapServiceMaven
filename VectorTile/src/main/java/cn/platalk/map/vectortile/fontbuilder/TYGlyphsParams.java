package cn.platalk.map.vectortile.fontbuilder;

class TYGlyphsParams {

	public static String GetGlyphsScript(String fontFileName) {
		// return String.format("%s %s %s %s %s", TYBrtFontSettings.nodePath,
		// TYBrtMapEnvironment.GetGlyphsScriptPath(), fontFileName,
		// TYBrtMapEnvironment.GetFontminDir(),
		// TYBrtMapEnvironment.GetGlyphsDir());

		return String.format("%s %s %s %s %s", TYFontSettings.nodePath,
				TYFontSettings.glyphsScriptPath, fontFileName,
				TYFontSettings.tempFontFileDir, TYFontSettings.outputGlyphsDir);
	}
}

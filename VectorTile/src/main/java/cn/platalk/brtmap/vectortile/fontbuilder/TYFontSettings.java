package cn.platalk.brtmap.vectortile.fontbuilder;

public class TYFontSettings {
	protected static String nodePath = null;
	protected static String fontminScriptPath = null;
	protected static String inputFontFileDir = null;
	protected static String tempFontFileDir = null;

	protected static String outputGlyphsDir = null;
	protected static String glyphsScriptPath = null;

	public static void setScriptPath(String node, String fontmin, String glyphs) {
		nodePath = node;
		fontminScriptPath = fontmin;
		glyphsScriptPath = glyphs;
	}

	public static void setFontDirs(String input, String temp, String output) {
		inputFontFileDir = input;
		tempFontFileDir = temp;
		outputGlyphsDir = output;
	}
}

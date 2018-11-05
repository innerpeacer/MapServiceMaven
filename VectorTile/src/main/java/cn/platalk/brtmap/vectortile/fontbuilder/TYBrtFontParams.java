package cn.platalk.brtmap.vectortile.fontbuilder;

import java.io.File;

class TYBrtFontParams {

	private String fontName;
	private String fontType;
	private String fontFileName;
	private String fontminFileName;
	private final String buildingID;

	public TYBrtFontParams(String fontName, String type, String buildingID) {
		this.fontName = fontName;
		this.fontType = type;
		this.buildingID = buildingID;

		this.fontFileName = String.format("%s.%s", fontName, fontType);
		this.fontminFileName = String.format("%s-%s.%s", fontName, buildingID,
				fontType);
	}

	public String getFontScript(String str) {
		// return String.format("%s %s %s %s %s", NodeParams.GetNodeCommand(),
		// TYBrtMapEnvironment.GetFontScriptPath(),
		// this.getOriginalFontPath(),
		// TYBrtMapEnvironment.GetFontminDir(), str);
		return String.format("%s %s %s %s %s", TYFontSettings.nodePath,
				TYFontSettings.fontminScriptPath, this.getOriginalFontPath(),
				TYFontSettings.tempFontFileDir, str);
	}

	public String getBuildingID() {
		return buildingID;
	}

	public String getFontName() {
		return fontName;
	}

	public String getFontFileName() {
		return fontFileName;
	}

	public String getFontminFileName() {
		return fontminFileName;
	}

	// public String getOriginalFontPath() {
	// return new File(TYBrtMapEnvironment.GetFontDir(), fontFileName)
	// .toString();
	// }
	//
	// public String getIntermediateFontPath() {
	// return new File(TYBrtMapEnvironment.GetFontminDir(), fontFileName)
	// .toString();
	// }
	//
	// public String getFontminPath() {
	// return new File(TYBrtMapEnvironment.GetFontminDir(), fontminFileName)
	// .toString();
	// }
	//
	// public String getGlyphFolder() {
	// return new File(TYBrtMapEnvironment.GetGlyphsDir(), String.format(
	// "%s-%s", fontName, buildingID)).toString();
	// }

	public String getOriginalFontPath() {
		return new File(TYFontSettings.inputFontFileDir, fontFileName)
				.toString();
	}

	public String getIntermediateFontPath() {
		return new File(TYFontSettings.tempFontFileDir, fontFileName)
				.toString();
	}

	public String getFontminPath() {
		return new File(TYFontSettings.tempFontFileDir, fontminFileName)
				.toString();
	}

	public String getGlyphFolder() {
		return new File(TYFontSettings.outputGlyphsDir, String.format("%s-%s",
				fontName, buildingID)).toString();
	}
}

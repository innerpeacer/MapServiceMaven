package cn.platalk.map.vectortile.cbm.pbf;

import cn.platalk.core.pbf.cbm.TYThemePbf;
import cn.platalk.core.pbf.cbm.TYThemePbf.ThemePbf;
import cn.platalk.core.pbf.cbm.wrapper.TYTheme2PbfUtils;
import cn.platalk.map.entity.base.impl.map.TYTheme;
import cn.platalk.map.entity.base.map.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.map.TYIIconTextSymbolRecord;
import cn.platalk.map.vectortile.builder.TYVectorTileSettings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TYThemePbfBuilder {
	static String FILE_THEME_PBF = "Theme_%s.pbf";

	private static String THEME_ROOT = null;

	public static void SetThemeRoot(String root) {
		THEME_ROOT = root;
	}

	public static String GetThemeRoot() {
		return THEME_ROOT;
	}

	public static String getThemePbfPath(String themeID) {
		String fileName = String.format(FILE_THEME_PBF, themeID);
		String pbfDir = GetThemeRoot();
		return new File(pbfDir, fileName).toString();
	}

	public static void generateThemePbf(TYTheme theme, List<TYIFillSymbolRecord> fillSymbolRecords, List<TYIIconTextSymbolRecord> iconTextSymbolRecords) throws IOException {
		ThemePbf pbf = TYTheme2PbfUtils.themeToPbf(theme, fillSymbolRecords, iconTextSymbolRecords);
		String pbfPath = getThemePbfPath(theme.getThemeID());
		FileOutputStream file = new FileOutputStream(pbfPath);
		pbf.writeTo(file);
	}
}

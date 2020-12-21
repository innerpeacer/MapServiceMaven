package cn.platalk.core.pbf.cbm.wrapper;

import cn.platalk.core.pbf.cbm.TYThemePbf.ThemePbf;
import cn.platalk.map.entity.base.impl.map.TYTheme;
import cn.platalk.map.entity.base.map.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.map.TYIIconSymbolRecord;
import cn.platalk.map.entity.base.map.TYIIconTextSymbolRecord;

import java.util.List;

public class TYTheme2PbfUtils {

	public static ThemePbf themeToPbf(TYTheme theme, List<TYIFillSymbolRecord> fillSymbols, List<TYIIconTextSymbolRecord> iconTextSymbols) {
		ThemePbf.Builder builder = ThemePbf.newBuilder();
		builder.setThemeID(theme.getThemeID());
		builder.setThemeName(theme.getThemeName());
		builder.setSpriteName(theme.getSpriteName());

		for (TYIFillSymbolRecord fill : fillSymbols) {
			builder.addFillSymbols(TYSymbol2PbfUtils.fillSymbolToPbf(fill));
		}

		for (TYIIconTextSymbolRecord iconText : iconTextSymbols) {
			builder.addIconTextSymbols(TYSymbol2PbfUtils.iconTextSymbolToPbf(iconText));
		}
		return builder.build();
	}
}

package cn.platalk.mysql.map;

import cn.platalk.map.entity.base.impl.map.TYTheme;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IPMysqlThemeParams {
	static final String TABLE_THEME = "THEME";

	static final String FIELD_THEME_1_ID = "THEME_ID";
	static final String FIELD_THEME_2_NAME = "THEME_NAME";
	static final String FIELD_THEME_3_SPRITE_NAME = "SPRITE_NAME";

	private static final List<IPSqlField> themeFieldList = new ArrayList<>();

	static {
		themeFieldList
				.add(new IPSqlField(FIELD_THEME_1_ID, new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		themeFieldList.add(
				new IPSqlField(FIELD_THEME_2_NAME, new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		themeFieldList.add(
				new IPSqlField(FIELD_THEME_3_SPRITE_NAME, new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
	}

	public static IPSqlTable CreateTable() {
		return new IPSqlTable(TABLE_THEME, GetThemeFieldList(), null);
	}

	public static List<IPSqlField> GetThemeFieldList() {
		return themeFieldList;
	}

	public static List<TYTheme> ThemeListFromRecords(List<IPSqlRecord> records) {
		List<TYTheme> themeList = new ArrayList<>();
		for (IPSqlRecord record : records) {
			TYTheme theme = new TYTheme();
			theme.setThemeID(record.getString(FIELD_THEME_1_ID));
			theme.setThemeName(record.getString(FIELD_THEME_2_NAME));
			theme.setSpriteName(record.getString(FIELD_THEME_3_SPRITE_NAME));
			themeList.add(theme);
		}
		return themeList;
	}

	public static Map<String, Object> DataMapFromTheme(TYTheme theme) {
		Map<String, Object> data = new HashMap<>();
		data.put(FIELD_THEME_1_ID, theme.getThemeID());
		data.put(FIELD_THEME_2_NAME, theme.getThemeName());
		data.put(FIELD_THEME_3_SPRITE_NAME, theme.getSpriteName());
		return data;
	}
}

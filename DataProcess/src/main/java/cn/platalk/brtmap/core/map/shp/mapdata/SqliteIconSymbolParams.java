package cn.platalk.brtmap.core.map.shp.mapdata;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.sqlhelper.sqlite.SqliteField;
import cn.platalk.sqlhelper.sqlite.SqliteRecord;

class SqliteIconSymbolParams {
	static final String TABLE_ICON_SYMBOL = "ICON_SYMBOL";

	private static final String FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID = "SYMBOL_ID";
	private static final String FIELD_MAP_SYMBOL_ICON_2_ICON = "ICON";

	private static List<SqliteField> iconSymbolFieldList = null;

	public static List<SqliteField> GetIconSymbolFieldList() {
		if (iconSymbolFieldList == null) {
			iconSymbolFieldList = new ArrayList<SqliteField>();

			iconSymbolFieldList.add(new SqliteField(FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID, Integer.class.getName(), false));
			iconSymbolFieldList.add(new SqliteField(FIELD_MAP_SYMBOL_ICON_2_ICON, String.class.getName(), false));
		}
		return iconSymbolFieldList;
	}

	public static List<TYIconSymbolRecord> IconSymbolListFromRecords(List<SqliteRecord> records) {
		List<TYIconSymbolRecord> iconSymbolList = new ArrayList<TYIconSymbolRecord>();
		for (SqliteRecord record : records) {
			TYIconSymbolRecord iconSymbol = new TYIconSymbolRecord();
			iconSymbol.setSymbolID((Integer) record.getValue(FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID));
			iconSymbol.setIcon((String) record.getValue(FIELD_MAP_SYMBOL_ICON_2_ICON));
			iconSymbolList.add(iconSymbol);
		}
		return iconSymbolList;
	}
}

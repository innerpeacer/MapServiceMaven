package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.sqlhelper.sql.SqlField;
import cn.platalk.sqlhelper.sql.SqlFieldType;
import cn.platalk.sqlhelper.sql.SqlRecord;

class IPSqliteIconSymbolParams {
	static final String TABLE_ICON_SYMBOL = "ICON_SYMBOL";

	private static final String FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID = "SYMBOL_ID";
	private static final String FIELD_MAP_SYMBOL_ICON_2_ICON = "ICON";

	private static List<SqlField> iconSymbolFieldList = null;

	public static List<SqlField> GetIconSymbolFieldList() {
		if (iconSymbolFieldList == null) {
			iconSymbolFieldList = new ArrayList<SqlField>();

			iconSymbolFieldList.add(new SqlField(FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID,
					SqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));
			iconSymbolFieldList.add(new SqlField(FIELD_MAP_SYMBOL_ICON_2_ICON,
					SqlFieldType.FieldTypeFromClass(String.class.getName()), false));
		}
		return iconSymbolFieldList;
	}

	public static List<TYIconSymbolRecord> IconSymbolListFromRecords(List<SqlRecord> records) {
		List<TYIconSymbolRecord> iconSymbolList = new ArrayList<TYIconSymbolRecord>();
		for (SqlRecord record : records) {
			TYIconSymbolRecord iconSymbol = new TYIconSymbolRecord();
			iconSymbol.setSymbolID(record.getInteger(FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID));
			iconSymbol.setIcon(record.getString(FIELD_MAP_SYMBOL_ICON_2_ICON));
			iconSymbolList.add(iconSymbol);
		}
		return iconSymbolList;
	}
}

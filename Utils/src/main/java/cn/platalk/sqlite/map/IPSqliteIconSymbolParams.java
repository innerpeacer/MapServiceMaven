package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.impl.map.TYIconSymbolRecord;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;

class IPSqliteIconSymbolParams {
	static final String TABLE_ICON_SYMBOL = "ICON_SYMBOL";

	private static final String FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID = "SYMBOL_ID";
	private static final String FIELD_MAP_SYMBOL_ICON_2_ICON = "ICON";

	private static List<IPSqlField> iconSymbolFieldList = null;

	public static List<IPSqlField> GetIconSymbolFieldList() {
		if (iconSymbolFieldList == null) {
			iconSymbolFieldList = new ArrayList<>();

			iconSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID,
					IPSqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));
			iconSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_2_ICON,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
		}
		return iconSymbolFieldList;
	}

	public static List<TYIconSymbolRecord> IconSymbolListFromRecords(List<IPSqlRecord> records) {
		List<TYIconSymbolRecord> iconSymbolList = new ArrayList<>();
		for (IPSqlRecord record : records) {
			TYIconSymbolRecord iconSymbol = new TYIconSymbolRecord();
			iconSymbol.setSymbolID(record.getInteger(FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID));
			iconSymbol.setIcon(record.getString(FIELD_MAP_SYMBOL_ICON_2_ICON));
			iconSymbolList.add(iconSymbol);
		}
		return iconSymbolList;
	}
}

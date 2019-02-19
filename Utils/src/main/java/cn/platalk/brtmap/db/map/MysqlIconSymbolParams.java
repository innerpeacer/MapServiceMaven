package cn.platalk.brtmap.db.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.sqlhelper.sql.SqlField;
import cn.platalk.sqlhelper.sql.SqlFieldType;
import cn.platalk.sqlhelper.sql.SqlRecord;
import cn.platalk.sqlhelper.sql.SqlTable;

public class MysqlIconSymbolParams {
	static final String TABLE_ICON_SYMBOL = "ICON_SYMBOL";

	static final String FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID = "SYMBOL_ID";
	static final String FIELD_MAP_SYMBOL_ICON_2_ICON = "ICON";
	static final String FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID = "BUILDING_ID";

	private static List<SqlField> iconSymbolFieldList = null;

	public static SqlTable CreateTable() {
		return new SqlTable(TABLE_ICON_SYMBOL, GetIconSymbolFieldList(), null);
	}

	public static List<SqlField> GetIconSymbolFieldList() {
		if (iconSymbolFieldList == null) {
			iconSymbolFieldList = new ArrayList<SqlField>();
			iconSymbolFieldList.add(new SqlField(FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID,
					new SqlFieldType(Integer.class.getName(), "INT"), false));
			iconSymbolFieldList.add(new SqlField(FIELD_MAP_SYMBOL_ICON_2_ICON,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			iconSymbolFieldList.add(new SqlField(FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		}
		return iconSymbolFieldList;
	}

	public static List<TYIconSymbolRecord> IconSymbolListFromRecords(List<SqlRecord> records) {
		List<TYIconSymbolRecord> iconList = new ArrayList<TYIconSymbolRecord>();
		for (SqlRecord record : records) {
			TYIconSymbolRecord icon = new TYIconSymbolRecord();
			icon.setSymbolID(record.getInteger(FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID));
			icon.setIcon(record.getString(FIELD_MAP_SYMBOL_ICON_2_ICON));
			iconList.add(icon);
		}
		return iconList;
	}

	public static Map<String, Object> DataMapFromIconSymbol(TYIconSymbolRecord icon, String buildingID) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID, icon.getSymbolID());
		data.put(FIELD_MAP_SYMBOL_ICON_2_ICON, icon.getIcon());
		data.put(FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID, buildingID);
		return data;
	}
}

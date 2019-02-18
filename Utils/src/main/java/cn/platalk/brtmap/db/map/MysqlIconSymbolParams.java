package cn.platalk.brtmap.db.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.sqlhelper.mysql.MysqlField;
import cn.platalk.sqlhelper.mysql.MysqlFieldType;
import cn.platalk.sqlhelper.mysql.MysqlRecord;
import cn.platalk.sqlhelper.mysql.MysqlTable;

public class MysqlIconSymbolParams {
	static final String TABLE_ICON_SYMBOL = "ICON_SYMBOL";

	static final String FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID = "SYMBOL_ID";
	static final String FIELD_MAP_SYMBOL_ICON_2_ICON = "ICON";
	static final String FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID = "BUILDING_ID";

	private static List<MysqlField> iconSymbolFieldList = null;

	public static MysqlTable CreateTable() {
		return new MysqlTable(TABLE_ICON_SYMBOL, GetIconSymbolFieldList(), null);
	}

	public static List<MysqlField> GetIconSymbolFieldList() {
		if (iconSymbolFieldList == null) {
			iconSymbolFieldList = new ArrayList<MysqlField>();
			iconSymbolFieldList.add(new MysqlField(FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID,
					new MysqlFieldType(Integer.class.getName(), "INT"), false));
			iconSymbolFieldList.add(new MysqlField(FIELD_MAP_SYMBOL_ICON_2_ICON,
					new MysqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			iconSymbolFieldList.add(new MysqlField(FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID,
					new MysqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		}
		return iconSymbolFieldList;
	}

	public static List<TYIconSymbolRecord> IconSymbolListFromRecords(List<MysqlRecord> records) {
		List<TYIconSymbolRecord> iconList = new ArrayList<TYIconSymbolRecord>();
		for (MysqlRecord record : records) {
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

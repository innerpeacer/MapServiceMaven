package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;

class IPSqliteFillSymbolParams {
	static final String TABLE_FILL_SYMBOL = "FILL_SYMBOL";

	private static final String FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID = "SYMBOL_ID";
	private static final String FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR = "FILL";
	private static final String FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR = "OUTLINE";
	private static final String FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH = "LINE_WIDTH";
	private static final String FIELD_MAP_SYMBOL_FILL_5_LEVEL_MIN = "LEVEL_MIN";
	private static final String FIELD_MAP_SYMBOL_FILL_6_LEVEL_MAX = "LEVEL_MAX";
	private static final String FIELD_MAP_SYMBOL_FILL_7_UID = "UID";

	private static List<IPSqlField> fillSymbolFieldList = null;

	public static List<IPSqlField> GetFillSymbolFieldList() {
		if (fillSymbolFieldList == null) {
			fillSymbolFieldList = new ArrayList<IPSqlField>();

			fillSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID,
					IPSqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));
			fillSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			fillSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			fillSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			fillSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_FILL_5_LEVEL_MIN,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			fillSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_FILL_6_LEVEL_MAX,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			fillSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_FILL_7_UID,
					IPSqlFieldType.FieldTypeFromClass(Integer.class.getName()), true));
		}
		return fillSymbolFieldList;
	}

	public static List<TYFillSymbolRecord> FillSymbolListFromRecords(List<IPSqlRecord> records) {
		List<TYFillSymbolRecord> fillSymbolList = new ArrayList<TYFillSymbolRecord>();
		for (IPSqlRecord record : records) {
			TYFillSymbolRecord fillSymbol = new TYFillSymbolRecord();
			fillSymbol.setSymbolID(record.getInteger(FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID));
			fillSymbol.setFillColor(record.getString(FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR));
			fillSymbol.setOutlineColor(record.getString(FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR));
			fillSymbol.setLineWidth(record.getDouble(FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH));
			fillSymbol.setLevelMin(record.getDouble(FIELD_MAP_SYMBOL_FILL_5_LEVEL_MIN, 0.0));
			fillSymbol.setLevelMax(record.getDouble(FIELD_MAP_SYMBOL_FILL_6_LEVEL_MAX, 0.0));
			fillSymbol.setUID(record.getInteger(FIELD_MAP_SYMBOL_FILL_7_UID, 0));
			fillSymbolList.add(fillSymbol);
		}
		return fillSymbolList;
	}
}

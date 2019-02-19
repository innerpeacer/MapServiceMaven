package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.sqlhelper.sql.SqlField;
import cn.platalk.sqlhelper.sql.SqlFieldType;
import cn.platalk.sqlhelper.sql.SqlRecord;

class IPSqliteFillSymbolParams {
	static final String TABLE_FILL_SYMBOL = "FILL_SYMBOL";

	private static final String FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID = "SYMBOL_ID";
	private static final String FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR = "FILL";
	private static final String FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR = "OUTLINE";
	private static final String FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH = "LINE_WIDTH";

	private static List<SqlField> fillSymbolFieldList = null;

	public static List<SqlField> GetFillSymbolFieldList() {
		if (fillSymbolFieldList == null) {
			fillSymbolFieldList = new ArrayList<SqlField>();

			fillSymbolFieldList.add(new SqlField(FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID,
					SqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));
			fillSymbolFieldList.add(new SqlField(FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR,
					SqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			fillSymbolFieldList.add(new SqlField(FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR,
					SqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			fillSymbolFieldList.add(new SqlField(FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH,
					SqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
		}
		return fillSymbolFieldList;
	}

	public static List<TYFillSymbolRecord> FillSymbolListFromRecords(List<SqlRecord> records) {
		List<TYFillSymbolRecord> fillSymbolList = new ArrayList<TYFillSymbolRecord>();
		for (SqlRecord record : records) {
			TYFillSymbolRecord fillSymbol = new TYFillSymbolRecord();
			fillSymbol.setSymbolID(record.getInteger(FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID));
			fillSymbol.setFillColor(record.getString(FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR));
			fillSymbol.setOutlineColor(record.getString(FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR));
			fillSymbol.setLineWidth(record.getDouble(FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH));
			fillSymbolList.add(fillSymbol);
		}
		return fillSymbolList;
	}
}

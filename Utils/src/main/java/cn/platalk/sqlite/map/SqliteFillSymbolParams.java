package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.sqlhelper.sqlite.SqliteField;
import cn.platalk.sqlhelper.sqlite.SqliteRecord;

class SqliteFillSymbolParams {
	static final String TABLE_FILL_SYMBOL = "FILL_SYMBOL";

	private static final String FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID = "SYMBOL_ID";
	private static final String FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR = "FILL";
	private static final String FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR = "OUTLINE";
	private static final String FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH = "LINE_WIDTH";

	private static List<SqliteField> fillSymbolFieldList = null;

	public static List<SqliteField> GetFillSymbolFieldList() {
		if (fillSymbolFieldList == null) {
			fillSymbolFieldList = new ArrayList<SqliteField>();

			fillSymbolFieldList.add(new SqliteField(FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID, Integer.class.getName(), false));
			fillSymbolFieldList.add(new SqliteField(FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR, String.class.getName(), false));
			fillSymbolFieldList
					.add(new SqliteField(FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR, String.class.getName(), false));
			fillSymbolFieldList.add(new SqliteField(FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH, Double.class.getName(), false));
		}
		return fillSymbolFieldList;
	}

	public static List<TYFillSymbolRecord> FillSymbolListFromRecords(List<SqliteRecord> records) {
		List<TYFillSymbolRecord> fillSymbolList = new ArrayList<TYFillSymbolRecord>();
		for (SqliteRecord record : records) {
			TYFillSymbolRecord fillSymbol = new TYFillSymbolRecord();
			fillSymbol.setSymbolID((Integer) record.getValue(FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID));
			fillSymbol.setFillColor((String) record.getValue(FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR));
			fillSymbol.setOutlineColor((String) record.getValue(FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR));
			fillSymbol.setLineWidth((Double) record.getValue(FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH));
			fillSymbolList.add(fillSymbol);
		}
		return fillSymbolList;
	}
}

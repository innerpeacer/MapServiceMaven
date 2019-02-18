package cn.platalk.brtmap.db.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.sqlhelper.mysql.MysqlField;
import cn.platalk.sqlhelper.mysql.MysqlFieldType;
import cn.platalk.sqlhelper.mysql.MysqlRecord;
import cn.platalk.sqlhelper.mysql.MysqlTable;

public class MysqlFillSymbolParams {
	static final String TABLE_FILL_SYMBOL = "FILL_SYMBOL";

	static final String FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID = "SYMBOL_ID";
	static final String FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR = "FILL";
	static final String FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR = "OUTLINE";
	static final String FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH = "LINE_WIDTH";
	static final String FIELD_MAP_SYMBOL_FILL_5_BUILDING_ID = "BUILDING_ID";

	private static List<MysqlField> fillSymbolFieldList = null;

	public static MysqlTable CreateTable() {
		return new MysqlTable(TABLE_FILL_SYMBOL, GetFillSymbolFieldList(), null);
	}

	public static List<MysqlField> GetFillSymbolFieldList() {
		if (fillSymbolFieldList == null) {
			fillSymbolFieldList = new ArrayList<MysqlField>();
			fillSymbolFieldList.add(new MysqlField(FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID,
					new MysqlFieldType(Integer.class.getName(), "INT"), false));
			fillSymbolFieldList.add(new MysqlField(FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR,
					new MysqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			fillSymbolFieldList.add(new MysqlField(FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR,
					new MysqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			fillSymbolFieldList.add(new MysqlField(FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH,
					new MysqlFieldType(Double.class.getName(), "DOUBLE"), false));
			fillSymbolFieldList.add(new MysqlField(FIELD_MAP_SYMBOL_FILL_5_BUILDING_ID,
					new MysqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		}
		return fillSymbolFieldList;
	}

	public static List<TYFillSymbolRecord> FillSymbolListFromRecords(List<MysqlRecord> records) {
		List<TYFillSymbolRecord> fillList = new ArrayList<TYFillSymbolRecord>();
		for (MysqlRecord record : records) {
			TYFillSymbolRecord fill = new TYFillSymbolRecord();
			fill.setSymbolID(record.getInteger(FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID));
			fill.setFillColor(record.getString(FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR));
			fill.setOutlineColor(record.getString(FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR));
			fill.setLineWidth(record.getDouble(FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH));
			fillList.add(fill);
		}
		return fillList;
	}

	public static Map<String, Object> DataMapFromFillSymbol(TYFillSymbolRecord fill, String buildingID) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID, fill.getSymbolID());
		data.put(FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR, fill.getFillColor());
		data.put(FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR, fill.getOutlineColor());
		data.put(FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH, fill.getLineWidth());
		data.put(FIELD_MAP_SYMBOL_FILL_5_BUILDING_ID, buildingID);
		return data;
	}
}

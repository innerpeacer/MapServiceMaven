package cn.platalk.mysql.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.TYIconTextSymbolRecord;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlIconTextSymbolParams {
	static final String TABLE_ICON_TEXT_SYMBOL = "ICON_TEXT_SYMBOL";

	static final String FIELD_MAP_SYMBOL_ICON_TEXT_100_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_1_SYMBOL_ID = "SYMBOL_ID";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_2_ICON_VISIBLE = "ICON_VISIBLE";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_3_ICON_SIZE = "ICON_SIZE";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_4_ICON_ROTATE = "ICON_ROTATE";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_5_ICON_OFFSET_X = "ICON_OFFSET_X";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_6_ICON_OFFSET_Y = "ICON_OFFSET_Y";

	static final String FIELD_MAP_SYMBOL_ICON_TEXT_7_TEXT_VISIBLE = "TEXT_VISIBLE";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_8_TEXT_SIZE = "TEXT_SIZE";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_9_TEXT_FONT = "TEXT_FONT";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_10_TEXT_COLOR = "TEXT_COLOR";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_11_TEXT_ROTATE = "TEXT_ROTATE";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_12_TEXT_OFFSET_X = "TEXT_OFFSET_X";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_13_TEXT_OFFSET_Y = "TEXT_OFFSET_Y";

	static final String FIELD_MAP_SYMBOL_ICON_TEXT_14_LEVEL_MIN = "LEVEL_MIN";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_15_LEVEL_MAX = "LEVEL_MAX";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_16_OTHER_PAINT = "OTHER_PAINT";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_17_OTHER_LAYOUT = "OTHER_LAYOUT";
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_18_DESCRIPTION = "DESCRIPTION";

	static final String FIELD_MAP_SYMBOL_ICON_TEXT_19_UID = "UID";

	private static List<IPSqlField> iconTextSymbolFieldList = new ArrayList<IPSqlField>();

	static {
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_100_BUILDING_ID,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));

		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_1_SYMBOL_ID,
				new IPSqlFieldType(Integer.class.getName(), "INT"), false));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_2_ICON_VISIBLE,
				new IPSqlFieldType(Boolean.class.getName(), "INTEGER(1)"), false));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_3_ICON_SIZE,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_4_ICON_ROTATE,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_5_ICON_OFFSET_X,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_6_ICON_OFFSET_Y,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));

		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_7_TEXT_VISIBLE,
				new IPSqlFieldType(Boolean.class.getName(), "INTEGER(1)"), false));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_8_TEXT_SIZE,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_9_TEXT_FONT,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_10_TEXT_COLOR,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_11_TEXT_ROTATE,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_12_TEXT_OFFSET_X,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_13_TEXT_OFFSET_Y,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));

		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_14_LEVEL_MIN,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_15_LEVEL_MAX,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));

		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_16_OTHER_PAINT,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(2000)"), true));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_17_OTHER_LAYOUT,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(2000)"), true));
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_18_DESCRIPTION,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(2000)"), true));

		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_19_UID,
				new IPSqlFieldType(Integer.class.getName(), "INT"), true));
	}

	public static IPSqlTable CreateTable() {
		return new IPSqlTable(TABLE_ICON_TEXT_SYMBOL, GetIconTextSymbolFieldList(), null);
	}

	public synchronized static List<IPSqlField> GetIconTextSymbolFieldList() {
		return iconTextSymbolFieldList;
	}

	public static List<TYIconTextSymbolRecord> IconTextSymbolListFromRecords(List<IPSqlRecord> records) {
		List<TYIconTextSymbolRecord> iconTextList = new ArrayList<TYIconTextSymbolRecord>();
		for (IPSqlRecord record : records) {
			TYIconTextSymbolRecord iconText = new TYIconTextSymbolRecord();
			iconText.setSymbolID(record.getInteger(FIELD_MAP_SYMBOL_ICON_TEXT_1_SYMBOL_ID));
			iconText.setIconVisible(record.getBoolean(FIELD_MAP_SYMBOL_ICON_TEXT_2_ICON_VISIBLE));
			iconText.setIconSize(record.getDouble(FIELD_MAP_SYMBOL_ICON_TEXT_3_ICON_SIZE));
			iconText.setIconRotate(record.getDouble(FIELD_MAP_SYMBOL_ICON_TEXT_4_ICON_ROTATE));
			iconText.setIconOffsetX(record.getDouble(FIELD_MAP_SYMBOL_ICON_TEXT_5_ICON_OFFSET_X));
			iconText.setIconOffsetY(record.getDouble(FIELD_MAP_SYMBOL_ICON_TEXT_6_ICON_OFFSET_Y));

			iconText.setTextVisible(record.getBoolean(FIELD_MAP_SYMBOL_ICON_TEXT_7_TEXT_VISIBLE));
			iconText.setTextSize(record.getDouble(FIELD_MAP_SYMBOL_ICON_TEXT_8_TEXT_SIZE));
			iconText.setTextFont(record.getString(FIELD_MAP_SYMBOL_ICON_TEXT_9_TEXT_FONT));
			iconText.setTextColor(record.getString(FIELD_MAP_SYMBOL_ICON_TEXT_10_TEXT_COLOR));
			iconText.setTextRotate(record.getDouble(FIELD_MAP_SYMBOL_ICON_TEXT_11_TEXT_ROTATE));
			iconText.setTextOffsetX(record.getDouble(FIELD_MAP_SYMBOL_ICON_TEXT_12_TEXT_OFFSET_X));
			iconText.setTextOffsetY(record.getDouble(FIELD_MAP_SYMBOL_ICON_TEXT_13_TEXT_OFFSET_Y));
			iconText.setLevelMin(record.getDouble(FIELD_MAP_SYMBOL_ICON_TEXT_14_LEVEL_MIN));
			iconText.setLevelMax(record.getDouble(FIELD_MAP_SYMBOL_ICON_TEXT_15_LEVEL_MAX));

			iconText.setOtherPaint(record.getString(FIELD_MAP_SYMBOL_ICON_TEXT_16_OTHER_PAINT));
			iconText.setOtherLayout(record.getString(FIELD_MAP_SYMBOL_ICON_TEXT_17_OTHER_LAYOUT));
			iconText.setDescription(record.getString(FIELD_MAP_SYMBOL_ICON_TEXT_18_DESCRIPTION));

			iconText.setUID(record.getInteger(FIELD_MAP_SYMBOL_ICON_TEXT_19_UID));

			iconTextList.add(iconText);
		}
		return iconTextList;
	}

	public static Map<String, Object> DataMapFromIconTextSymbol(TYIconTextSymbolRecord iconText, String buildingID) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_1_SYMBOL_ID, iconText.symbolID);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_2_ICON_VISIBLE, iconText.iconVisible);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_3_ICON_SIZE, iconText.iconSize);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_4_ICON_ROTATE, iconText.iconRotate);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_5_ICON_OFFSET_X, iconText.iconOffsetX);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_6_ICON_OFFSET_Y, iconText.iconOffsetY);

		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_7_TEXT_VISIBLE, iconText.textVisible);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_8_TEXT_SIZE, iconText.textSize);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_9_TEXT_FONT, iconText.textFont);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_10_TEXT_COLOR, iconText.textColor);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_11_TEXT_ROTATE, iconText.textRotate);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_12_TEXT_OFFSET_X, iconText.textOffsetX);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_13_TEXT_OFFSET_Y, iconText.textOffsetY);

		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_14_LEVEL_MIN, iconText.levelMin);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_15_LEVEL_MAX, iconText.levelMax);

		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_16_OTHER_PAINT, iconText.otherPaint);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_17_OTHER_LAYOUT, iconText.otherLayout);

		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_18_DESCRIPTION, iconText.description);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_19_UID, iconText.UID);

		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_100_BUILDING_ID, buildingID);
		return data;
	}
}

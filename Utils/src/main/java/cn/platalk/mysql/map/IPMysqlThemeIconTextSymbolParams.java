package cn.platalk.mysql.map;

import cn.platalk.map.entity.base.impl.map.TYIconTextSymbolRecord;
import cn.platalk.map.entity.base.map.TYIIconTextSymbolRecord;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IPMysqlThemeIconTextSymbolParams {
	static final String TABLE_THEME_ICON_TEXT_SYMBOL = "THEME_ICON_TEXT_SYMBOL";

	static final String FIELD_MAP_SYMBOL_ICON_TEXT_101_THEME_ID = "THEME_ID";
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
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_20_PRIORITY = "PRIORITY";

	private static final List<IPSqlField> iconTextSymbolFieldList = new ArrayList<>();

	static {
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_101_THEME_ID,
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
		iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_20_PRIORITY,
				new IPSqlFieldType(Integer.class.getName(), "INT"), true));
	}

	public static IPSqlTable CreateTable() {
		return new IPSqlTable(TABLE_THEME_ICON_TEXT_SYMBOL, GetIconTextSymbolFieldList(), null);
	}

	public synchronized static List<IPSqlField> GetIconTextSymbolFieldList() {
		return iconTextSymbolFieldList;
	}

	public static List<TYIIconTextSymbolRecord> IconTextSymbolListFromRecords(List<IPSqlRecord> records) {
		List<TYIIconTextSymbolRecord> iconTextList = new ArrayList<>();
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
			iconText.setPriority(record.getInteger(FIELD_MAP_SYMBOL_ICON_TEXT_20_PRIORITY));

			iconTextList.add(iconText);
		}
		return iconTextList;
	}

	public static Map<String, Object> DataMapFromIconTextSymbol(TYIIconTextSymbolRecord iconText, String themeID) {
		Map<String, Object> data = new HashMap<>();
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_101_THEME_ID, themeID);
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_1_SYMBOL_ID, iconText.getSymbolID());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_2_ICON_VISIBLE, iconText.isIconVisible());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_3_ICON_SIZE, iconText.getIconSize());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_4_ICON_ROTATE, iconText.getIconRotate());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_5_ICON_OFFSET_X, iconText.getIconOffsetX());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_6_ICON_OFFSET_Y, iconText.getIconOffsetY());

		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_7_TEXT_VISIBLE, iconText.isTextVisible());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_8_TEXT_SIZE, iconText.getTextSize());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_9_TEXT_FONT, iconText.getTextFont());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_10_TEXT_COLOR, iconText.getTextColor());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_11_TEXT_ROTATE, iconText.getTextRotate());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_12_TEXT_OFFSET_X, iconText.getTextOffsetX());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_13_TEXT_OFFSET_Y, iconText.getTextOffsetY());

		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_14_LEVEL_MIN, iconText.getLevelMin());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_15_LEVEL_MAX, iconText.getLevelMax());

		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_16_OTHER_PAINT, iconText.getOtherPaint());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_17_OTHER_LAYOUT, iconText.getOtherLayout());

		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_18_DESCRIPTION, iconText.getDescription());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_19_UID, iconText.getUID());
		data.put(FIELD_MAP_SYMBOL_ICON_TEXT_20_PRIORITY, iconText.getPriority());
		return data;
	}
}

package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.impl.map.TYIconTextSymbolRecord;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;

public class IPSqliteIconTextSymbolParams {
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
	static final String FIELD_MAP_SYMBOL_ICON_TEXT_20_PRIORITY = "PRIORITY";

	private static List<IPSqlField> iconTextSymbolFieldList = null;

	public static List<IPSqlField> GetIconTextSymbolFieldList() {
		if (iconTextSymbolFieldList == null) {
			iconTextSymbolFieldList = new ArrayList<>();
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_1_SYMBOL_ID,
					IPSqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_2_ICON_VISIBLE,
					IPSqlFieldType.FieldTypeFromClass(Boolean.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_3_ICON_SIZE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_4_ICON_ROTATE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_5_ICON_OFFSET_X,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_6_ICON_OFFSET_Y,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_7_TEXT_VISIBLE,
					IPSqlFieldType.FieldTypeFromClass(Boolean.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_8_TEXT_SIZE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_9_TEXT_FONT,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_10_TEXT_COLOR,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_11_TEXT_ROTATE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_12_TEXT_OFFSET_X,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_13_TEXT_OFFSET_Y,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_14_LEVEL_MIN,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_15_LEVEL_MAX,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_16_OTHER_PAINT,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), true));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_17_OTHER_LAYOUT,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), true));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_18_DESCRIPTION,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), true));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_19_UID,
					IPSqlFieldType.FieldTypeFromClass(Integer.class.getName()), true));
			iconTextSymbolFieldList.add(new IPSqlField(FIELD_MAP_SYMBOL_ICON_TEXT_20_PRIORITY,
					IPSqlFieldType.FieldTypeFromClass(Integer.class.getName()), true));
		}
		return iconTextSymbolFieldList;
	}

	public static List<TYIconTextSymbolRecord> IconTextSymbolListFromRecords(List<IPSqlRecord> records) {
		List<TYIconTextSymbolRecord> iconTextList = new ArrayList<>();
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
			iconText.setPriority(record.getInteger(FIELD_MAP_SYMBOL_ICON_TEXT_20_PRIORITY, 0));

			iconTextList.add(iconText);
		}
		return iconTextList;
	}
}

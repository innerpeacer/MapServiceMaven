package cn.platalk.mysql.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlMapDataParams {
	static final String TABLE_MAP_DATA = "MAPDATA_%s";

	static final String FIELD_MAP_DATA_1_OBJECT_ID = "OBJECT_ID";
	static final String FIELD_MAP_DATA_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_MAP_DATA_3_GEO_ID = "GEO_ID";
	static final String FIELD_MAP_DATA_4_POI_ID = "POI_ID";
	static final String FIELD_MAP_DATA_5_FLOOR_ID = "FLOOR_ID";
	static final String FIELD_MAP_DATA_6_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_MAP_DATA_7_CATEGORY_ID = "CATEGORY_ID";
	static final String FIELD_MAP_DATA_8_NAME = "NAME";
	static final String FIELD_MAP_DATA_9_SYMBOL_ID = "SYMBOL_ID";
	static final String FIELD_MAP_DATA_10_FLOOR_NUMBER = "FLOOR_NUMBER";
	static final String FIELD_MAP_DATA_11_FLOOR_NAME = "FLOOR_NAME";
	static final String FIELD_MAP_DATA_12_SHAPE_LENGTH = "SHAPE_LENGTH";
	static final String FIELD_MAP_DATA_13_SHAPE_AREA = "SHAPE_AREA";
	static final String FIELD_MAP_DATA_14_LABEL_X = "LABEL_X";
	static final String FIELD_MAP_DATA_15_LABEL_Y = "LABEL_Y";
	static final String FIELD_MAP_DATA_16_LAYER = "LAYER";
	static final String FIELD_MAP_DATA_17_LEVEL_MAX = "LEVEL_MAX";
	static final String FIELD_MAP_DATA_18_LEVEL_MIN = "LEVEL_MIN";

	static final String FIELD_MAP_DATA_19_EXTRUSION = "EXTRUSION";
	static final String FIELD_MAP_DATA_20_EXTRUSION_HEIGHT = "EXTRUSION_HEIGHT";
	static final String FIELD_MAP_DATA_21_EXTRUSION_BASE = "EXTRUSION_BASE";
	static final String FIELD_MAP_DATA_22_NAME_EN = "NAME_EN";
	static final String FIELD_MAP_DATA_23_NAME_OTHER = "NAME_OTHER";

	static final String FIELD_MAP_DATA_24_VISIBLE = "VISIBLE";
	static final String FIELD_MAP_DATA_25_ICON = "ICON";

	private static List<IPSqlField> mapDataFieldList = new ArrayList<IPSqlField>();
	static {
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_1_OBJECT_ID,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_2_GEOMETRY,
				new IPSqlFieldType(byte[].class.getName(), "MediumBlob"), false));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_3_GEO_ID,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_4_POI_ID,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_5_FLOOR_ID,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_6_BUILDING_ID,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_7_CATEGORY_ID,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		mapDataFieldList.add(
				new IPSqlField(FIELD_MAP_DATA_8_NAME, new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
		mapDataFieldList.add(
				new IPSqlField(FIELD_MAP_DATA_9_SYMBOL_ID, new IPSqlFieldType(Integer.class.getName(), "INT"), false));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_10_FLOOR_NUMBER,
				new IPSqlFieldType(Integer.class.getName(), "INT"), false));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_11_FLOOR_NAME,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_12_SHAPE_LENGTH,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_13_SHAPE_AREA,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		mapDataFieldList.add(
				new IPSqlField(FIELD_MAP_DATA_14_LABEL_X, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		mapDataFieldList.add(
				new IPSqlField(FIELD_MAP_DATA_15_LABEL_Y, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		mapDataFieldList.add(
				new IPSqlField(FIELD_MAP_DATA_16_LAYER, new IPSqlFieldType(Integer.class.getName(), "INT"), false));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_17_LEVEL_MAX,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), true));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_18_LEVEL_MIN,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), true));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_19_EXTRUSION,
				new IPSqlFieldType(Boolean.class.getName(), "INTEGER(1)"), true, 0));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_20_EXTRUSION_HEIGHT,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), true, 0));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_21_EXTRUSION_BASE,
				new IPSqlFieldType(Double.class.getName(), "DOUBLE"), true, 0));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_22_NAME_EN,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(200)"), true));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_23_NAME_OTHER,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(200)"), true));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_24_VISIBLE,
				new IPSqlFieldType(Boolean.class.getName(), "INTEGER(1)"), true, 1));
		mapDataFieldList.add(new IPSqlField(FIELD_MAP_DATA_25_ICON,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
	}

	public static IPSqlTable CreateTable(String buildingID) {
		return new IPSqlTable(String.format(TABLE_MAP_DATA, buildingID), GetMapDataFieldList(), null);
	}

	public synchronized static List<IPSqlField> GetMapDataFieldList() {
		return mapDataFieldList;
	}

	public static List<TYMapDataFeatureRecord> MapDataListFromRecords(List<IPSqlRecord> records) {
		List<TYMapDataFeatureRecord> mapdataList = new ArrayList<TYMapDataFeatureRecord>();
		for (IPSqlRecord record : records) {
			TYMapDataFeatureRecord mapdata = new TYMapDataFeatureRecord();
			mapdata.setObjectID(record.getString(FIELD_MAP_DATA_1_OBJECT_ID));
			mapdata.setGeometry(record.getBlob(FIELD_MAP_DATA_2_GEOMETRY));
			mapdata.setGeoID(record.getString(FIELD_MAP_DATA_3_GEO_ID));
			mapdata.setPoiID(record.getString(FIELD_MAP_DATA_4_POI_ID));
			mapdata.setFloorID(record.getString(FIELD_MAP_DATA_5_FLOOR_ID));
			mapdata.setBuildingID(record.getString(FIELD_MAP_DATA_6_BUILDING_ID));
			mapdata.setCategoryID(record.getString(FIELD_MAP_DATA_7_CATEGORY_ID));
			mapdata.setName(record.getString(FIELD_MAP_DATA_8_NAME));
			mapdata.setSymbolID(record.getInteger(FIELD_MAP_DATA_9_SYMBOL_ID));
			mapdata.setFloorNumber(record.getInteger(FIELD_MAP_DATA_10_FLOOR_NUMBER));
			mapdata.setFloorName(record.getString(FIELD_MAP_DATA_11_FLOOR_NAME));
			mapdata.setShapeLength(record.getDouble(FIELD_MAP_DATA_12_SHAPE_LENGTH));
			mapdata.setShapeArea(record.getDouble(FIELD_MAP_DATA_13_SHAPE_AREA));
			mapdata.setLabelX(record.getDouble(FIELD_MAP_DATA_14_LABEL_X));
			mapdata.setLabelY(record.getDouble(FIELD_MAP_DATA_15_LABEL_Y));
			mapdata.setLayer(record.getInteger(FIELD_MAP_DATA_16_LAYER));
			mapdata.setLevelMax(record.getDouble(FIELD_MAP_DATA_17_LEVEL_MAX));
			mapdata.setLevelMin(record.getDouble(FIELD_MAP_DATA_18_LEVEL_MIN));
			mapdata.setExtrusion(record.getBoolean(FIELD_MAP_DATA_19_EXTRUSION));
			mapdata.setExtrusionHeight(record.getDouble(FIELD_MAP_DATA_20_EXTRUSION_HEIGHT));
			mapdata.setExtrusionBase(record.getDouble(FIELD_MAP_DATA_21_EXTRUSION_BASE));
			mapdata.setNameEn(record.getString(FIELD_MAP_DATA_22_NAME_EN));
			mapdata.setNameOther(record.getString(FIELD_MAP_DATA_23_NAME_OTHER));
			mapdata.setVisible(record.getBoolean(FIELD_MAP_DATA_24_VISIBLE));
			mapdata.setIcon(record.getString(FIELD_MAP_DATA_25_ICON));
			mapdataList.add(mapdata);
		}
		return mapdataList;
	}

	public static Map<String, Object> DataMapFromMapDataFeatureRecord(TYMapDataFeatureRecord record) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_MAP_DATA_1_OBJECT_ID, record.getObjectID());
		data.put(FIELD_MAP_DATA_2_GEOMETRY, record.getGeometry());
		data.put(FIELD_MAP_DATA_3_GEO_ID, record.getGeoID());
		data.put(FIELD_MAP_DATA_4_POI_ID, record.getPoiID());
		data.put(FIELD_MAP_DATA_5_FLOOR_ID, record.getFloorID());
		data.put(FIELD_MAP_DATA_6_BUILDING_ID, record.getBuildingID());
		data.put(FIELD_MAP_DATA_7_CATEGORY_ID, record.getCategoryID());
		data.put(FIELD_MAP_DATA_8_NAME, record.getName());
		data.put(FIELD_MAP_DATA_9_SYMBOL_ID, record.getSymbolID());
		data.put(FIELD_MAP_DATA_10_FLOOR_NUMBER, record.getFloorNumber());
		data.put(FIELD_MAP_DATA_11_FLOOR_NAME, record.getFloorName());
		data.put(FIELD_MAP_DATA_12_SHAPE_LENGTH, record.getShapeLength());
		data.put(FIELD_MAP_DATA_13_SHAPE_AREA, record.getShapeArea());
		data.put(FIELD_MAP_DATA_14_LABEL_X, record.getLabelX());
		data.put(FIELD_MAP_DATA_15_LABEL_Y, record.getLabelY());
		data.put(FIELD_MAP_DATA_16_LAYER, record.getLayer());
		data.put(FIELD_MAP_DATA_17_LEVEL_MAX, record.getLevelMax());
		data.put(FIELD_MAP_DATA_18_LEVEL_MIN, record.getLevelMin());
		data.put(FIELD_MAP_DATA_19_EXTRUSION, record.isExtrusion());
		data.put(FIELD_MAP_DATA_20_EXTRUSION_HEIGHT, record.getExtrusionHeight());
		data.put(FIELD_MAP_DATA_21_EXTRUSION_BASE, record.getExtrusionBase());
		data.put(FIELD_MAP_DATA_22_NAME_EN, record.getNameEn());
		data.put(FIELD_MAP_DATA_23_NAME_OTHER, record.getNameOther());
		data.put(FIELD_MAP_DATA_24_VISIBLE, record.isVisible());
		data.put(FIELD_MAP_DATA_25_ICON, record.getIcon());
		return data;
	}

}

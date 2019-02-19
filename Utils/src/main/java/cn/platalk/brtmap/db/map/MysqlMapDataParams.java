package cn.platalk.brtmap.db.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.sqlhelper.sql.SqlField;
import cn.platalk.sqlhelper.sql.SqlFieldType;
import cn.platalk.sqlhelper.sql.SqlRecord;
import cn.platalk.sqlhelper.sql.SqlTable;

public class MysqlMapDataParams {
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

	private static List<SqlField> mapDataFieldList = null;

	public static SqlTable CreateTable(String buildingID) {
		return new SqlTable(String.format(TABLE_MAP_DATA, buildingID), GetMapDataFieldList(), null);
	}

	public static List<SqlField> GetMapDataFieldList() {
		if (mapDataFieldList == null) {
			mapDataFieldList = new ArrayList<SqlField>();

			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_1_OBJECT_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_2_GEOMETRY,
					new SqlFieldType(byte[].class.getName(), "MediumBlob"), false));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_3_GEO_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_4_POI_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_5_FLOOR_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_6_BUILDING_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_7_CATEGORY_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			mapDataFieldList.add(
					new SqlField(FIELD_MAP_DATA_8_NAME, new SqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
			mapDataFieldList.add(
					new SqlField(FIELD_MAP_DATA_9_SYMBOL_ID, new SqlFieldType(Integer.class.getName(), "INT"), false));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_10_FLOOR_NUMBER,
					new SqlFieldType(Integer.class.getName(), "INT"), false));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_11_FLOOR_NAME,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_12_SHAPE_LENGTH,
					new SqlFieldType(Double.class.getName(), "DOUBLE"), false));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_13_SHAPE_AREA,
					new SqlFieldType(Double.class.getName(), "DOUBLE"), false));
			mapDataFieldList.add(
					new SqlField(FIELD_MAP_DATA_14_LABEL_X, new SqlFieldType(Double.class.getName(), "DOUBLE"), false));
			mapDataFieldList.add(
					new SqlField(FIELD_MAP_DATA_15_LABEL_Y, new SqlFieldType(Double.class.getName(), "DOUBLE"), false));
			mapDataFieldList.add(
					new SqlField(FIELD_MAP_DATA_16_LAYER, new SqlFieldType(Integer.class.getName(), "INT"), false));
			mapDataFieldList.add(
					new SqlField(FIELD_MAP_DATA_17_LEVEL_MAX, new SqlFieldType(Integer.class.getName(), "INT"), false));
			mapDataFieldList.add(
					new SqlField(FIELD_MAP_DATA_18_LEVEL_MIN, new SqlFieldType(Integer.class.getName(), "INT"), false));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_19_EXTRUSION,
					new SqlFieldType(Boolean.class.getName(), "INTEGER(1)"), true, 0));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_20_EXTRUSION_HEIGHT,
					new SqlFieldType(Double.class.getName(), "DOUBLE"), true, 0));
			mapDataFieldList.add(new SqlField(FIELD_MAP_DATA_21_EXTRUSION_BASE,
					new SqlFieldType(Double.class.getName(), "DOUBLE"), true, 0));

		}
		return mapDataFieldList;
	}

	public static List<TYMapDataFeatureRecord> MapDataListFromRecords(List<SqlRecord> records) {
		List<TYMapDataFeatureRecord> mapdataList = new ArrayList<TYMapDataFeatureRecord>();
		for (SqlRecord record : records) {
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
			mapdata.setLevelMax(record.getInteger(FIELD_MAP_DATA_17_LEVEL_MAX));
			mapdata.setLevelMin(record.getInteger(FIELD_MAP_DATA_18_LEVEL_MIN));
			mapdata.setExtrusion(record.getBoolean(FIELD_MAP_DATA_19_EXTRUSION));
			// Object isExtrusion =
			// record.getValue(FIELD_MAP_DATA_19_EXTRUSION);
			// mapdata.setExtrusion((isExtrusion != null && ((Integer)
			// isExtrusion) != 0) ? true : false);

			mapdata.setExtrusionHeight(record.getDouble(FIELD_MAP_DATA_20_EXTRUSION_HEIGHT));
			mapdata.setExtrusionBase(record.getDouble(FIELD_MAP_DATA_21_EXTRUSION_BASE));
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
		return data;
	}

}

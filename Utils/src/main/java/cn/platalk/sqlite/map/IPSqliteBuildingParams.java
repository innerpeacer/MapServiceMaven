package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYMapSize;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;

class IPSqliteBuildingParams {
	static final String TABLE_BUILDING = "BUILDING";

	private static final String FIELD_BUILDING_1_CITY_ID = "CITY_ID";
	private static final String FIELD_BUILDING_2_ID = "BUILDING_ID";
	private static final String FIELD_BUILDING_3_NAME = "NAME";
	private static final String FIELD_BUILDING_4_LONGITUDE = "LONGITUDE";
	private static final String FIELD_BUILDING_5_LATITUDE = "LATITUDE";
	private static final String FIELD_BUILDING_6_ADDRESS = "ADDRESS";
	private static final String FIELD_BUILDING_7_INIT_ANGLE = "INIT_ANGLE";
	private static final String FIELD_BUILDING_8_ROUTE_URL = "ROUTE_URL";
	private static final String FIELD_BUILDING_9_OFFSET_X = "OFFSET_X";
	private static final String FIELD_BUILDING_10_OFFSET_Y = "OFFSET_Y";
	private static final String FIELD_BUILDING_11_STATUS = "STATUS";
	private static final String FIELD_BUILDING_12_INIT_FLOOR_INDEX = "INIT_FLOOR_INDEX";

	private static final String FIELD_BUILDING_13_XMIN = "XMIN";
	private static final String FIELD_BUILDING_14_YMIN = "YMIN";
	private static final String FIELD_BUILDING_15_XMAX = "XMAX";
	private static final String FIELD_BUILDING_16_YMAX = "YMAX";

	private static List<IPSqlField> buildingFieldList = null;

	public static List<IPSqlField> GetBuildingFieldList() {
		if (buildingFieldList == null) {
			buildingFieldList = new ArrayList<IPSqlField>();
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_1_CITY_ID,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_2_ID,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_3_NAME,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_4_LONGITUDE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_5_LATITUDE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_6_ADDRESS,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_7_INIT_ANGLE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_8_ROUTE_URL,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_9_OFFSET_X,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_10_OFFSET_Y,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_11_STATUS,
					IPSqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_12_INIT_FLOOR_INDEX,
					IPSqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));

			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_13_XMIN,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_14_YMIN,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_15_XMAX,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_16_YMAX,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
		}
		return buildingFieldList;
	}

	public static List<TYBuilding> BuildingListFromRecords(List<IPSqlRecord> records) {
		List<TYBuilding> buildingList = new ArrayList<TYBuilding>();
		for (IPSqlRecord record : records) {
			TYBuilding building = new TYBuilding();
			building.setCityID(record.getString(FIELD_BUILDING_1_CITY_ID));
			building.setBuildingID(record.getString(FIELD_BUILDING_2_ID));
			building.setName(record.getString(FIELD_BUILDING_3_NAME));
			building.setLongitude(record.getDouble(FIELD_BUILDING_4_LONGITUDE));
			building.setLatitude(record.getDouble(FIELD_BUILDING_5_LATITUDE));
			building.setAddress(record.getString(FIELD_BUILDING_6_ADDRESS));
			building.setInitAngle(record.getDouble(FIELD_BUILDING_7_INIT_ANGLE));
			building.setRouteURL(record.getString(FIELD_BUILDING_8_ROUTE_URL));
			building.setOffset(new TYMapSize(record.getDouble(FIELD_BUILDING_9_OFFSET_X),
					record.getDouble(FIELD_BUILDING_10_OFFSET_Y)));
			building.setStatus(record.getInteger(FIELD_BUILDING_11_STATUS));
			building.setInitFloorIndex(record.getInteger(FIELD_BUILDING_12_INIT_FLOOR_INDEX, 0));
			building.setXmin(record.getDouble(FIELD_BUILDING_13_XMIN, 0.0));
			building.setYmin(record.getDouble(FIELD_BUILDING_14_YMIN, 0.0));
			building.setXmax(record.getDouble(FIELD_BUILDING_15_XMAX, 0.0));
			building.setYmax(record.getDouble(FIELD_BUILDING_16_YMAX, 0.0));
			buildingList.add(building);
		}
		return buildingList;
	}
}

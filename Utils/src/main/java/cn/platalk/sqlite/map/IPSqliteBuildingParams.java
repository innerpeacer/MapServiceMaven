package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYMapSize;
import cn.platalk.sqlhelper.sql.SqlField;
import cn.platalk.sqlhelper.sql.SqlFieldType;
import cn.platalk.sqlhelper.sql.SqlRecord;

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

	private static List<SqlField> buildingFieldList = null;

	public static List<SqlField> GetBuildingFieldList() {
		if (buildingFieldList == null) {
			buildingFieldList = new ArrayList<SqlField>();
			buildingFieldList.add(new SqlField(FIELD_BUILDING_1_CITY_ID,
					SqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(
					new SqlField(FIELD_BUILDING_2_ID, SqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new SqlField(FIELD_BUILDING_3_NAME,
					SqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new SqlField(FIELD_BUILDING_4_LONGITUDE,
					SqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new SqlField(FIELD_BUILDING_5_LATITUDE,
					SqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new SqlField(FIELD_BUILDING_6_ADDRESS,
					SqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new SqlField(FIELD_BUILDING_7_INIT_ANGLE,
					SqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new SqlField(FIELD_BUILDING_8_ROUTE_URL,
					SqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new SqlField(FIELD_BUILDING_9_OFFSET_X,
					SqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new SqlField(FIELD_BUILDING_10_OFFSET_Y,
					SqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new SqlField(FIELD_BUILDING_11_STATUS,
					SqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));
		}
		return buildingFieldList;
	}

	public static List<TYBuilding> BuildingListFromRecords(List<SqlRecord> records) {
		List<TYBuilding> buildingList = new ArrayList<TYBuilding>();
		for (SqlRecord record : records) {
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
			buildingList.add(building);
		}
		return buildingList;
	}
}

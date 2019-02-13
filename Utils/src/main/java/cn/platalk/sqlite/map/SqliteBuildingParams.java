package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYMapSize;
import cn.platalk.sqlhelper.sqlite.SqliteField;
import cn.platalk.sqlhelper.sqlite.SqliteRecord;

class SqliteBuildingParams {
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

	private static List<SqliteField> buildingFieldList = null;

	public static List<SqliteField> GetBuildingFieldList() {
		if (buildingFieldList == null) {
			buildingFieldList = new ArrayList<SqliteField>();
			buildingFieldList.add(new SqliteField(FIELD_BUILDING_1_CITY_ID, String.class.getName(), false));
			buildingFieldList.add(new SqliteField(FIELD_BUILDING_2_ID, String.class.getName(), false));
			buildingFieldList.add(new SqliteField(FIELD_BUILDING_3_NAME, String.class.getName(), false));
			buildingFieldList.add(new SqliteField(FIELD_BUILDING_4_LONGITUDE, Double.class.getName(), false));
			buildingFieldList.add(new SqliteField(FIELD_BUILDING_5_LATITUDE, Double.class.getName(), false));
			buildingFieldList.add(new SqliteField(FIELD_BUILDING_6_ADDRESS, String.class.getName(), false));
			buildingFieldList.add(new SqliteField(FIELD_BUILDING_7_INIT_ANGLE, Double.class.getName(), false));
			buildingFieldList.add(new SqliteField(FIELD_BUILDING_8_ROUTE_URL, String.class.getName(), false));
			buildingFieldList.add(new SqliteField(FIELD_BUILDING_9_OFFSET_X, Double.class.getName(), false));
			buildingFieldList.add(new SqliteField(FIELD_BUILDING_10_OFFSET_Y, Double.class.getName(), false));
			buildingFieldList.add(new SqliteField(FIELD_BUILDING_11_STATUS, Integer.class.getName(), false));
		}
		return buildingFieldList;
	}

	public static List<TYBuilding> BuildingListFromRecords(List<SqliteRecord> records) {
		List<TYBuilding> buildingList = new ArrayList<TYBuilding>();
		for (SqliteRecord record : records) {
			TYBuilding building = new TYBuilding();
			building.setCityID((String) record.getValue(FIELD_BUILDING_1_CITY_ID));
			building.setBuildingID((String) record.getValue(FIELD_BUILDING_2_ID));
			building.setName((String) record.getValue(FIELD_BUILDING_3_NAME));
			building.setLongitude((Double) record.getValue(FIELD_BUILDING_4_LONGITUDE));
			building.setLatitude((Double) record.getValue(FIELD_BUILDING_5_LATITUDE));
			building.setAddress((String) record.getValue(FIELD_BUILDING_6_ADDRESS));
			building.setInitAngle((Double) record.getValue(FIELD_BUILDING_7_INIT_ANGLE));
			building.setRouteURL((String) record.getValue(FIELD_BUILDING_8_ROUTE_URL));
			building.setOffset(new TYMapSize((Double) record.getValue(FIELD_BUILDING_9_OFFSET_X),
					(Double) record.getValue(FIELD_BUILDING_10_OFFSET_Y)));
			building.setStatus((Integer) record.getValue(FIELD_BUILDING_11_STATUS));
			buildingList.add(building);
		}
		return buildingList;
	}
}

package cn.platalk.mysql.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYMapSize;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlBuildingParams {
	static final String TABLE_BUILDING = "BUILDING";

	static final String FIELD_BUILDING_0_PRIMARY_KEY = "_id";
	static final String FIELD_BUILDING_1_CITY_ID = "CITY_ID";
	static final String FIELD_BUILDING_2_ID = "BUILDING_ID";

	static final String FIELD_BUILDING_3_NAME = "NAME";
	static final String FIELD_BUILDING_4_LONGITUDE = "LONGITUDE";
	static final String FIELD_BUILDING_5_LATITUDE = "LATITUDE";

	static final String FIELD_BUILDING_6_ADDRESS = "ADDRESS";
	static final String FIELD_BUILDING_7_INIT_ANGLE = "INIT_ANGLE";
	static final String FIELD_BUILDING_8_ROUTE_URL = "ROUTE_URL";
	static final String FIELD_BUILDING_9_OFFSET_X = "OFFSET_X";
	static final String FIELD_BUILDING_10_OFFSET_Y = "OFFSET_Y";

	static final String FIELD_BUILDING_11_STATUS = "STATUS";

	private static List<IPSqlField> buildingFieldList = null;

	public static IPSqlTable CreateTable() {
		return new IPSqlTable(IPMysqlBuildingParams.TABLE_BUILDING, GetBuildingFieldList(), null);
	}

	public static List<IPSqlField> GetBuildingFieldList() {
		if (buildingFieldList == null) {
			buildingFieldList = new ArrayList<IPSqlField>();

			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_1_CITY_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			buildingFieldList.add(
					new IPSqlField(FIELD_BUILDING_2_ID, new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_3_NAME,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(255)"), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_4_LONGITUDE,
					new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
			buildingFieldList.add(
					new IPSqlField(FIELD_BUILDING_5_LATITUDE, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_6_ADDRESS,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(255)"), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_7_INIT_ANGLE,
					new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_8_ROUTE_URL,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(255)"), false));
			buildingFieldList.add(
					new IPSqlField(FIELD_BUILDING_9_OFFSET_X, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_10_OFFSET_Y,
					new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
			buildingFieldList.add(
					new IPSqlField(FIELD_BUILDING_11_STATUS, new IPSqlFieldType(Integer.class.getName(), "INT"), false));

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
			buildingList.add(building);
		}
		return buildingList;
	}

	public static Map<String, Object> DataMapFromBuilding(TYBuilding building) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_BUILDING_1_CITY_ID, building.getCityID());
		data.put(FIELD_BUILDING_2_ID, building.getBuildingID());
		data.put(FIELD_BUILDING_3_NAME, building.getName());
		data.put(FIELD_BUILDING_4_LONGITUDE, building.getLongitude());
		data.put(FIELD_BUILDING_5_LATITUDE, building.getLatitude());
		data.put(FIELD_BUILDING_6_ADDRESS, building.getAddress());
		data.put(FIELD_BUILDING_7_INIT_ANGLE, building.getInitAngle());
		data.put(FIELD_BUILDING_8_ROUTE_URL, building.getRouteURL());
		data.put(FIELD_BUILDING_9_OFFSET_X, building.getOffset().getX());
		data.put(FIELD_BUILDING_10_OFFSET_Y, building.getOffset().getY());
		data.put(FIELD_BUILDING_11_STATUS, building.getStatus());
		return data;
	}
}

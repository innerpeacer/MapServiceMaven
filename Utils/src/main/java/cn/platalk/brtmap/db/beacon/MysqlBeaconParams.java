package cn.platalk.brtmap.db.beacon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.TYILocatingBeacon;
import cn.platalk.brtmap.entity.base.TYLocalPoint;
import cn.platalk.brtmap.entity.base.impl.TYLocatingBeacon;
import cn.platalk.sqlhelper.sql.SqlField;
import cn.platalk.sqlhelper.sql.SqlFieldType;
import cn.platalk.sqlhelper.sql.SqlRecord;
import cn.platalk.sqlhelper.sql.SqlTable;

public class MysqlBeaconParams {
	static final String TABLE_BEACON = "BEACON_%s";

	static final String FIELD_BEACON_1_GEOM = "GEOM";
	static final String FIELD_BEACON_2_UUID = "UUID";
	static final String FIELD_BEACON_3_MAJOR = "MAJOR";
	static final String FIELD_BEACON_4_MINOR = "MINOR";
	static final String FIELD_BEACON_5_FLOOR = "FLOOR";
	static final String FIELD_BEACON_6_X = "X";
	static final String FIELD_BEACON_7_Y = "Y";
	static final String FIELD_BEACON_8_ROOM_ID = "ROOM_ID";
	static final String FIELD_BEACON_9_TAG = "TAG";

	static final String FIELD_BEACON_10_MAP_ID = "MAP_ID";
	static final String FIELD_BEACON_11_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_BEACON_12_CITY_ID = "CITY_ID";

	private static List<SqlField> beaconFieldList = null;

	public static SqlTable CreateTable(String buildingID) {
		return new SqlTable(String.format(TABLE_BEACON, buildingID), GetBeaconFieldList(), null);
	}

	public static List<SqlField> GetBeaconFieldList() {
		if (beaconFieldList == null) {
			beaconFieldList = new ArrayList<SqlField>();
			beaconFieldList
					.add(new SqlField(FIELD_BEACON_1_GEOM, new SqlFieldType(byte[].class.getName(), "BLOB"), false));
			beaconFieldList.add(
					new SqlField(FIELD_BEACON_2_UUID, new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			beaconFieldList.add(
					new SqlField(FIELD_BEACON_3_MAJOR, new SqlFieldType(Integer.class.getName(), "INTEGER"), false));
			beaconFieldList.add(
					new SqlField(FIELD_BEACON_4_MINOR, new SqlFieldType(Integer.class.getName(), "INTEGER"), false));
			beaconFieldList.add(
					new SqlField(FIELD_BEACON_5_FLOOR, new SqlFieldType(Integer.class.getName(), "INTEGER"), false));
			beaconFieldList
					.add(new SqlField(FIELD_BEACON_6_X, new SqlFieldType(Double.class.getName(), "DOUBLE"), false));
			beaconFieldList
					.add(new SqlField(FIELD_BEACON_7_Y, new SqlFieldType(Double.class.getName(), "DOUBLE"), false));
			beaconFieldList.add(new SqlField(FIELD_BEACON_8_ROOM_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
			beaconFieldList.add(
					new SqlField(FIELD_BEACON_9_TAG, new SqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
			beaconFieldList.add(new SqlField(FIELD_BEACON_10_MAP_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
			beaconFieldList.add(new SqlField(FIELD_BEACON_11_BUILDING_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
			beaconFieldList.add(new SqlField(FIELD_BEACON_12_CITY_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
		}
		return beaconFieldList;
	}

	public static List<TYLocatingBeacon> BeaconListFromRecords(List<SqlRecord> records) {
		List<TYLocatingBeacon> beaconList = new ArrayList<TYLocatingBeacon>();
		for (SqlRecord record : records) {
			TYLocatingBeacon lb = new TYLocatingBeacon(record.getString(FIELD_BEACON_2_UUID),
					record.getInteger(FIELD_BEACON_3_MAJOR), record.getInteger(FIELD_BEACON_4_MINOR),
					record.getString(FIELD_BEACON_9_TAG), record.getString(FIELD_BEACON_8_ROOM_ID));
			TYLocalPoint lp = new TYLocalPoint(record.getDouble(FIELD_BEACON_6_X), record.getDouble(FIELD_BEACON_7_Y));
			lb.setLocation(lp);
			lb.setMapID(record.getString(FIELD_BEACON_10_MAP_ID));
			lb.setBuildingID(record.getString(FIELD_BEACON_11_BUILDING_ID));
			lb.setCityID(record.getString(FIELD_BEACON_12_CITY_ID));
			beaconList.add(lb);
		}
		return beaconList;
	}

	public static Map<String, Object> DataMapFromBeacon(TYILocatingBeacon beacon) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_BEACON_1_GEOM, beacon.getLocation().getGeometryBytes());
		data.put(FIELD_BEACON_2_UUID, beacon.getUUID());
		data.put(FIELD_BEACON_3_MAJOR, beacon.getMajor());
		data.put(FIELD_BEACON_4_MINOR, beacon.getMinor());
		data.put(FIELD_BEACON_5_FLOOR, beacon.getLocation().getFloor());
		data.put(FIELD_BEACON_6_X, beacon.getLocation().getX());
		data.put(FIELD_BEACON_7_Y, beacon.getLocation().getY());
		data.put(FIELD_BEACON_8_ROOM_ID, beacon.getRoomID());
		data.put(FIELD_BEACON_9_TAG, beacon.getTag());
		data.put(FIELD_BEACON_10_MAP_ID, beacon.getMapID());
		data.put(FIELD_BEACON_11_BUILDING_ID, beacon.getBuildingID());
		data.put(FIELD_BEACON_12_CITY_ID, beacon.getCityID());
		return data;
	}

}

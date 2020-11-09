package cn.platalk.mysql.beacon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.beacon.TYILocatingBeacon;
import cn.platalk.map.entity.base.impl.beacon.TYLocatingBeacon;
import cn.platalk.map.entity.base.impl.map.TYLocalPoint;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlBeaconParams {
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

	private static List<IPSqlField> beaconFieldList = null;

	public static IPSqlTable CreateTable(String buildingID) {
		return new IPSqlTable(String.format(TABLE_BEACON, buildingID), GetBeaconFieldList(), null);
	}

	public static List<IPSqlField> GetBeaconFieldList() {
		if (beaconFieldList == null) {
			beaconFieldList = new ArrayList<>();
			beaconFieldList
					.add(new IPSqlField(FIELD_BEACON_1_GEOM, new IPSqlFieldType(byte[].class.getName(), "BLOB"), false));
			beaconFieldList.add(
					new IPSqlField(FIELD_BEACON_2_UUID, new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			beaconFieldList.add(
					new IPSqlField(FIELD_BEACON_3_MAJOR, new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
			beaconFieldList.add(
					new IPSqlField(FIELD_BEACON_4_MINOR, new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
			beaconFieldList.add(
					new IPSqlField(FIELD_BEACON_5_FLOOR, new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
			beaconFieldList
					.add(new IPSqlField(FIELD_BEACON_6_X, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
			beaconFieldList
					.add(new IPSqlField(FIELD_BEACON_7_Y, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
			beaconFieldList.add(new IPSqlField(FIELD_BEACON_8_ROOM_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
			beaconFieldList.add(
					new IPSqlField(FIELD_BEACON_9_TAG, new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
			beaconFieldList.add(new IPSqlField(FIELD_BEACON_10_MAP_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
			beaconFieldList.add(new IPSqlField(FIELD_BEACON_11_BUILDING_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
			beaconFieldList.add(new IPSqlField(FIELD_BEACON_12_CITY_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
		}
		return beaconFieldList;
	}

	public static List<TYLocatingBeacon> BeaconListFromRecords(List<IPSqlRecord> records) {
		List<TYLocatingBeacon> beaconList = new ArrayList<>();
		for (IPSqlRecord record : records) {
			TYLocatingBeacon lb = new TYLocatingBeacon(record.getString(FIELD_BEACON_2_UUID),
					record.getInteger(FIELD_BEACON_3_MAJOR), record.getInteger(FIELD_BEACON_4_MINOR),
					record.getString(FIELD_BEACON_9_TAG), record.getString(FIELD_BEACON_8_ROOM_ID));
			TYLocalPoint lp = new TYLocalPoint(record.getDouble(FIELD_BEACON_6_X), record.getDouble(FIELD_BEACON_7_Y), record.getInteger(FIELD_BEACON_5_FLOOR));
			lb.setLocation(lp);
			lb.setMapID(record.getString(FIELD_BEACON_10_MAP_ID));
			lb.setBuildingID(record.getString(FIELD_BEACON_11_BUILDING_ID));
			lb.setCityID(record.getString(FIELD_BEACON_12_CITY_ID));
			beaconList.add(lb);
		}
		return beaconList;
	}

	public static Map<String, Object> DataMapFromBeacon(TYILocatingBeacon beacon) {
		Map<String, Object> data = new HashMap<>();
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

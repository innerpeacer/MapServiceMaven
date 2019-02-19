package cn.platalk.brtmap.db.beacon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.TYIBeaconRegion;
import cn.platalk.brtmap.entity.base.impl.TYBeaconRegion;
import cn.platalk.sqlhelper.sql.SqlField;
import cn.platalk.sqlhelper.sql.SqlFieldType;
import cn.platalk.sqlhelper.sql.SqlRecord;
import cn.platalk.sqlhelper.sql.SqlTable;

public class MysqlBeaconRegionParams {
	static final String TABLE_BEACON_REGION = "BEACON_REGION";

	static final String FIELD_BEACON_REGION_1_CITY_ID = "CITY_ID";
	static final String FIELD_BEACON_REGION_2_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_BEACON_REGION_3_BUILDING_NAME = "BUILDING_NAME";
	static final String FIELD_BEACON_REGION_4_UUID = "UUID";
	static final String FIELD_BEACON_REGION_5_MAJOR = "MAJOR";

	private static List<SqlField> beaconRegionFieldList = null;

	public static SqlTable CreateTable() {
		return new SqlTable(TABLE_BEACON_REGION, GetBeaconRegionFieldList(), null);
	}

	public static List<SqlField> GetBeaconRegionFieldList() {
		if (beaconRegionFieldList == null) {
			beaconRegionFieldList = new ArrayList<SqlField>();
			beaconRegionFieldList.add(new SqlField(FIELD_BEACON_REGION_1_CITY_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			beaconRegionFieldList.add(new SqlField(FIELD_BEACON_REGION_2_BUILDING_ID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			beaconRegionFieldList.add(new SqlField(FIELD_BEACON_REGION_3_BUILDING_NAME,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			beaconRegionFieldList.add(new SqlField(FIELD_BEACON_REGION_4_UUID,
					new SqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			beaconRegionFieldList.add(new SqlField(FIELD_BEACON_REGION_5_MAJOR,
					new SqlFieldType(Integer.class.getName(), "INTEGER"), false));
		}
		return beaconRegionFieldList;
	}

	public static List<TYBeaconRegion> BeaconRegionListFromRecords(List<SqlRecord> records) {
		List<TYBeaconRegion> regionList = new ArrayList<TYBeaconRegion>();
		for (SqlRecord record : records) {
			TYBeaconRegion region = new TYBeaconRegion();
			region.setCityID(record.getString(FIELD_BEACON_REGION_1_CITY_ID));
			region.setBuildingID(record.getString(FIELD_BEACON_REGION_2_BUILDING_ID));
			region.setBuildingName(record.getString(FIELD_BEACON_REGION_3_BUILDING_NAME));
			region.setUuid(record.getString(FIELD_BEACON_REGION_4_UUID));
			region.setMajor(record.getInteger(FIELD_BEACON_REGION_5_MAJOR));
		}
		return regionList;
	}

	public static Map<String, Object> DataMapFromBeaconRegion(TYIBeaconRegion region) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_BEACON_REGION_1_CITY_ID, region.getCityID());
		data.put(FIELD_BEACON_REGION_2_BUILDING_ID, region.getBuildingID());
		data.put(FIELD_BEACON_REGION_3_BUILDING_NAME, region.getBuildingName());
		data.put(FIELD_BEACON_REGION_4_UUID, region.getUuid());
		data.put(FIELD_BEACON_REGION_5_MAJOR, region.getMajor());
		return data;
	}
}

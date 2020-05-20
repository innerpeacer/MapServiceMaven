package cn.platalk.mysql.beacon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.TYIBeaconRegion;
import cn.platalk.map.entity.base.impl.TYBeaconRegion;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlBeaconRegionParams {
	static final String TABLE_BEACON_REGION = "BEACON_REGION";

	static final String FIELD_BEACON_REGION_1_CITY_ID = "CITY_ID";
	static final String FIELD_BEACON_REGION_2_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_BEACON_REGION_3_BUILDING_NAME = "BUILDING_NAME";
	static final String FIELD_BEACON_REGION_4_UUID = "UUID";
	static final String FIELD_BEACON_REGION_5_MAJOR = "MAJOR";

	private static List<IPSqlField> beaconRegionFieldList = null;

	public static IPSqlTable CreateTable() {
		return new IPSqlTable(TABLE_BEACON_REGION, GetBeaconRegionFieldList(), null);
	}

	public static List<IPSqlField> GetBeaconRegionFieldList() {
		if (beaconRegionFieldList == null) {
			beaconRegionFieldList = new ArrayList<>();
			beaconRegionFieldList.add(new IPSqlField(FIELD_BEACON_REGION_1_CITY_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			beaconRegionFieldList.add(new IPSqlField(FIELD_BEACON_REGION_2_BUILDING_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			beaconRegionFieldList.add(new IPSqlField(FIELD_BEACON_REGION_3_BUILDING_NAME,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			beaconRegionFieldList.add(new IPSqlField(FIELD_BEACON_REGION_4_UUID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			beaconRegionFieldList.add(new IPSqlField(FIELD_BEACON_REGION_5_MAJOR,
					new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
		}
		return beaconRegionFieldList;
	}

	public static List<TYBeaconRegion> BeaconRegionListFromRecords(List<IPSqlRecord> records) {
		List<TYBeaconRegion> regionList = new ArrayList<>();
		for (IPSqlRecord record : records) {
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
		Map<String, Object> data = new HashMap<>();
		data.put(FIELD_BEACON_REGION_1_CITY_ID, region.getCityID());
		data.put(FIELD_BEACON_REGION_2_BUILDING_ID, region.getBuildingID());
		data.put(FIELD_BEACON_REGION_3_BUILDING_NAME, region.getBuildingName());
		data.put(FIELD_BEACON_REGION_4_UUID, region.getUuid());
		data.put(FIELD_BEACON_REGION_5_MAJOR, region.getMajor());
		return data;
	}
}

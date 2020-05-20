package cn.platalk.mysql.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.TYCity;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlCityParams {
	static final String TABLE_CITY = "CITY";

	static final String FIELD_CITY_1_ID = "CITY_ID";
	static final String FIELD_CITY_2_NAME = "NAME";
	static final String FIELD_CITY_3_SNAME = "SNAME";
	static final String FIELD_CITY_4_LONGITUDE = "LONGITUDE";
	static final String FIELD_CITY_5_LATITUDE = "LATITUDE";
	static final String FIELD_CITY_6_STATUS = "STATUS";

	private static final List<IPSqlField> cityFieldList = new ArrayList<IPSqlField>();
	static {
		cityFieldList
				.add(new IPSqlField(FIELD_CITY_1_ID, new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		cityFieldList.add(
				new IPSqlField(FIELD_CITY_2_NAME, new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		cityFieldList.add(
				new IPSqlField(FIELD_CITY_3_SNAME, new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		cityFieldList.add(
				new IPSqlField(FIELD_CITY_4_LONGITUDE, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		cityFieldList.add(
				new IPSqlField(FIELD_CITY_5_LATITUDE, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		cityFieldList
				.add(new IPSqlField(FIELD_CITY_6_STATUS, new IPSqlFieldType(Integer.class.getName(), "INT"), false));
	}

	public static IPSqlTable CreateTable() {
		return new IPSqlTable(TABLE_CITY, GetCityFieldList(), null);
	}

	public static List<IPSqlField> GetCityFieldList() {
		return cityFieldList;
	}

	public static List<TYCity> CityListFromRecords(List<IPSqlRecord> records) {
		List<TYCity> cityList = new ArrayList<TYCity>();
		for (IPSqlRecord record : records) {
			TYCity city = new TYCity();
			city.setCityID(record.getString(FIELD_CITY_1_ID));
			city.setName(record.getString(FIELD_CITY_2_NAME));
			city.setSname(record.getString(FIELD_CITY_3_SNAME));
			city.setLongitude(record.getDouble(FIELD_CITY_4_LONGITUDE));
			city.setLatitude(record.getDouble(FIELD_CITY_5_LATITUDE));
			city.setStatus(record.getInteger(FIELD_CITY_6_STATUS));
			cityList.add(city);
		}
		return cityList;
	}

	public static Map<String, Object> DataMapFromCity(TYCity city) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_CITY_1_ID, city.getCityID());
		data.put(FIELD_CITY_2_NAME, city.getName());
		data.put(FIELD_CITY_3_SNAME, city.getSname());
		data.put(FIELD_CITY_4_LONGITUDE, city.getLongitude());
		data.put(FIELD_CITY_5_LATITUDE, city.getLatitude());
		data.put(FIELD_CITY_6_STATUS, city.getStatus());
		return data;
	}
}

package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYCity;
import cn.platalk.sqlhelper.sqlite.SqliteField;
import cn.platalk.sqlhelper.sqlite.SqliteRecord;

class SqliteCityParams {
	static final String TABLE_CITY = "CITY";

	private static final String FIELD_CITY_1_ID = "CITY_ID";
	private static final String FIELD_CITY_2_NAME = "NAME";
	private static final String FIELD_CITY_3_SNAME = "SNAME";
	private static final String FIELD_CITY_4_LONGITUDE = "LONGITUDE";
	private static final String FIELD_CITY_5_LATITUDE = "LATITUDE";
	private static final String FIELD_CITY_6_STATUS = "STATUS";

	private static List<SqliteField> cityFieldList = null;

	public static List<SqliteField> GetCityFieldList() {
		if (cityFieldList == null) {
			cityFieldList = new ArrayList<SqliteField>();

			cityFieldList.add(new SqliteField(FIELD_CITY_1_ID, String.class.getName(), false));
			cityFieldList.add(new SqliteField(FIELD_CITY_2_NAME, String.class.getName(), false));
			cityFieldList.add(new SqliteField(FIELD_CITY_3_SNAME, String.class.getName(), false));
			cityFieldList.add(new SqliteField(FIELD_CITY_4_LONGITUDE, Double.class.getName(), false));
			cityFieldList.add(new SqliteField(FIELD_CITY_5_LATITUDE, Double.class.getName(), false));
			cityFieldList.add(new SqliteField(FIELD_CITY_6_STATUS, Integer.class.getName(), false));
		}
		return cityFieldList;
	}

	public static List<TYCity> CityListFromRecords(List<SqliteRecord> records) {
		List<TYCity> cityList = new ArrayList<TYCity>();
		for (SqliteRecord record : records) {
			TYCity city = new TYCity();
			city.setCityID((String) record.getValue(FIELD_CITY_1_ID));
			city.setName((String) record.getValue(FIELD_CITY_2_NAME));
			city.setSname((String) record.getValue(FIELD_CITY_3_SNAME));
			city.setLongitude((Double) record.getValue(FIELD_CITY_4_LONGITUDE));
			city.setLatitude((Double) record.getValue(FIELD_CITY_5_LATITUDE));
			city.setStatus((Integer) record.getValue(FIELD_CITY_6_STATUS));
			cityList.add(city);
		}
		return cityList;
	}

}

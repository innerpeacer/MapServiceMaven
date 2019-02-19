package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYCity;
import cn.platalk.sqlhelper.sql.SqlField;
import cn.platalk.sqlhelper.sql.SqlFieldType;
import cn.platalk.sqlhelper.sql.SqlRecord;

class IPSqliteCityParams {
	static final String TABLE_CITY = "CITY";

	private static final String FIELD_CITY_1_ID = "CITY_ID";
	private static final String FIELD_CITY_2_NAME = "NAME";
	private static final String FIELD_CITY_3_SNAME = "SNAME";
	private static final String FIELD_CITY_4_LONGITUDE = "LONGITUDE";
	private static final String FIELD_CITY_5_LATITUDE = "LATITUDE";
	private static final String FIELD_CITY_6_STATUS = "STATUS";

	private static List<SqlField> cityFieldList = null;

	public static List<SqlField> GetCityFieldList() {
		if (cityFieldList == null) {
			cityFieldList = new ArrayList<SqlField>();

			cityFieldList
					.add(new SqlField(FIELD_CITY_1_ID, SqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			cityFieldList.add(
					new SqlField(FIELD_CITY_2_NAME, SqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			cityFieldList.add(
					new SqlField(FIELD_CITY_3_SNAME, SqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			cityFieldList.add(new SqlField(FIELD_CITY_4_LONGITUDE,
					SqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			cityFieldList.add(new SqlField(FIELD_CITY_5_LATITUDE,
					SqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			cityFieldList.add(
					new SqlField(FIELD_CITY_6_STATUS, SqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));
		}
		return cityFieldList;
	}

	public static List<TYCity> CityListFromRecords(List<SqlRecord> records) {
		List<TYCity> cityList = new ArrayList<TYCity>();
		for (SqlRecord record : records) {
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

}

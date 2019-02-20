package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYCity;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;

class IPSqliteCityParams {
	static final String TABLE_CITY = "CITY";

	private static final String FIELD_CITY_1_ID = "CITY_ID";
	private static final String FIELD_CITY_2_NAME = "NAME";
	private static final String FIELD_CITY_3_SNAME = "SNAME";
	private static final String FIELD_CITY_4_LONGITUDE = "LONGITUDE";
	private static final String FIELD_CITY_5_LATITUDE = "LATITUDE";
	private static final String FIELD_CITY_6_STATUS = "STATUS";

	private static List<IPSqlField> cityFieldList = null;

	public static List<IPSqlField> GetCityFieldList() {
		if (cityFieldList == null) {
			cityFieldList = new ArrayList<IPSqlField>();

			cityFieldList
					.add(new IPSqlField(FIELD_CITY_1_ID, IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			cityFieldList.add(
					new IPSqlField(FIELD_CITY_2_NAME, IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			cityFieldList.add(
					new IPSqlField(FIELD_CITY_3_SNAME, IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			cityFieldList.add(new IPSqlField(FIELD_CITY_4_LONGITUDE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			cityFieldList.add(new IPSqlField(FIELD_CITY_5_LATITUDE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			cityFieldList.add(
					new IPSqlField(FIELD_CITY_6_STATUS, IPSqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));
		}
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

}

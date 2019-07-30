package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.TYCity;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;

class IPSqliteCityParams {
	static final String TABLE_CITY = "CITY";

	static final String FIELD_CITY_1_ID = "CITY_ID";
	static final String FIELD_CITY_2_NAME = "NAME";
	static final String FIELD_CITY_3_SNAME = "SNAME";
	static final String FIELD_CITY_4_LONGITUDE = "LONGITUDE";
	static final String FIELD_CITY_5_LATITUDE = "LATITUDE";
	static final String FIELD_CITY_6_STATUS = "STATUS";

	private static List<IPSqlField> cityFieldList = null;

	public static List<IPSqlField> GetCityFieldList() {
		if (cityFieldList == null) {
			cityFieldList = new ArrayList<IPSqlField>();

			cityFieldList.add(
					new IPSqlField(FIELD_CITY_1_ID, IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			cityFieldList.add(new IPSqlField(FIELD_CITY_2_NAME,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			cityFieldList.add(new IPSqlField(FIELD_CITY_3_SNAME,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			cityFieldList.add(new IPSqlField(FIELD_CITY_4_LONGITUDE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			cityFieldList.add(new IPSqlField(FIELD_CITY_5_LATITUDE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			cityFieldList.add(new IPSqlField(FIELD_CITY_6_STATUS,
					IPSqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));
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

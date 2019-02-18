package cn.platalk.brtmap.db.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.impl.TYCity;
import cn.platalk.sqlhelper.mysql.MysqlField;
import cn.platalk.sqlhelper.mysql.MysqlFieldType;
import cn.platalk.sqlhelper.mysql.MysqlRecord;
import cn.platalk.sqlhelper.mysql.MysqlTable;

public class MysqlCityParams {
	static final String TABLE_CITY = "CITY";

	static final String FIELD_CITY_1_ID = "CITY_ID";
	static final String FIELD_CITY_2_NAME = "NAME";
	static final String FIELD_CITY_3_SNAME = "SNAME";
	static final String FIELD_CITY_4_LONGITUDE = "LONGITUDE";
	static final String FIELD_CITY_5_LATITUDE = "LATITUDE";
	static final String FIELD_CITY_6_STATUS = "STATUS";

	private static List<MysqlField> cityFieldList = null;

	public static MysqlTable CreateTable() {
		return new MysqlTable(TABLE_CITY, GetCityFieldList(), null);
	}

	public static List<MysqlField> GetCityFieldList() {
		if (cityFieldList == null) {
			cityFieldList = new ArrayList<MysqlField>();

			cityFieldList.add(
					new MysqlField(FIELD_CITY_1_ID, new MysqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			cityFieldList.add(new MysqlField(FIELD_CITY_2_NAME,
					new MysqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			cityFieldList.add(new MysqlField(FIELD_CITY_3_SNAME,
					new MysqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			cityFieldList.add(new MysqlField(FIELD_CITY_4_LONGITUDE,
					new MysqlFieldType(Double.class.getName(), "DOUBLE"), false));
			cityFieldList.add(
					new MysqlField(FIELD_CITY_5_LATITUDE, new MysqlFieldType(Double.class.getName(), "DOUBLE"), false));
			cityFieldList.add(
					new MysqlField(FIELD_CITY_6_STATUS, new MysqlFieldType(Integer.class.getName(), " INT "), false));
		}
		return cityFieldList;
	}

	public static List<TYCity> CityListFromRecords(List<MysqlRecord> records) {
		List<TYCity> cityList = new ArrayList<TYCity>();
		for (MysqlRecord record : records) {
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

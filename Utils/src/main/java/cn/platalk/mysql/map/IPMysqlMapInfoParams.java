package cn.platalk.mysql.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.TYMapExtent;
import cn.platalk.map.entity.base.impl.TYMapInfo;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlMapInfoParams {
	static final String TABLE_MAPINFO = "MAPINFO";

	static final String FIELD_MAPINFO_1_CITY_ID = "CITY_ID";
	static final String FIELD_MAPINFO_2_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_MAPINFO_3_MAP_ID = "MAP_ID";
	static final String FIELD_MAPINFO_4_FLOOR_NAME = "FLOOR_NAME";
	static final String FIELD_MAPINFO_5_FLOOR_NUMBER = "FLOOR_NUMBER";
	static final String FIELD_MAPINFO_6_SIZE_X = "SIZE_X";
	static final String FIELD_MAPINFO_7_SIZE_Y = "SIZE_Y";
	static final String FIELD_MAPINFO_8_XMIN = "XMIN";
	static final String FIELD_MAPINFO_9_YMIN = "YMIN";
	static final String FIELD_MAPINFO_10_XMAX = "XMAX";
	static final String FIELD_MAPINFO_11_YMAX = "YMAX";

	private static final List<IPSqlField> mapInfoFieldList = new ArrayList<IPSqlField>();
	static {
		mapInfoFieldList.add(new IPSqlField(FIELD_MAPINFO_1_CITY_ID,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		mapInfoFieldList.add(new IPSqlField(FIELD_MAPINFO_2_BUILDING_ID,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		mapInfoFieldList.add(new IPSqlField(FIELD_MAPINFO_3_MAP_ID,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		mapInfoFieldList.add(new IPSqlField(FIELD_MAPINFO_4_FLOOR_NAME,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		mapInfoFieldList.add(new IPSqlField(FIELD_MAPINFO_5_FLOOR_NUMBER,
				new IPSqlFieldType(Integer.class.getName(), "INT"), false));
		mapInfoFieldList.add(
				new IPSqlField(FIELD_MAPINFO_6_SIZE_X, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		mapInfoFieldList.add(
				new IPSqlField(FIELD_MAPINFO_7_SIZE_Y, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		mapInfoFieldList
				.add(new IPSqlField(FIELD_MAPINFO_8_XMIN, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		mapInfoFieldList
				.add(new IPSqlField(FIELD_MAPINFO_9_YMIN, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		mapInfoFieldList.add(
				new IPSqlField(FIELD_MAPINFO_10_XMAX, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		mapInfoFieldList.add(
				new IPSqlField(FIELD_MAPINFO_11_YMAX, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
	}

	public static IPSqlTable CreateTable() {
		return new IPSqlTable(TABLE_MAPINFO, GetMapInfoFieldList(), null);
	}

	public synchronized static List<IPSqlField> GetMapInfoFieldList() {
		return mapInfoFieldList;
	}

	public static List<TYMapInfo> MapInfoListFromRecord(List<IPSqlRecord> records) {
		List<TYMapInfo> mapInfoList = new ArrayList<TYMapInfo>();
		for (IPSqlRecord record : records) {
			TYMapInfo mapInfo = new TYMapInfo();
			mapInfo.setCityID(record.getString(FIELD_MAPINFO_1_CITY_ID));
			mapInfo.setBuildingID(record.getString(FIELD_MAPINFO_2_BUILDING_ID));
			mapInfo.setMapID(record.getString(FIELD_MAPINFO_3_MAP_ID));
			mapInfo.setFloorName(record.getString(FIELD_MAPINFO_4_FLOOR_NAME));
			mapInfo.setFloorNumber(record.getInteger(FIELD_MAPINFO_5_FLOOR_NUMBER));
			mapInfo.setSize_x(record.getDouble(FIELD_MAPINFO_6_SIZE_X));
			mapInfo.setSize_y(record.getDouble(FIELD_MAPINFO_7_SIZE_Y));
			TYMapExtent extent = new TYMapExtent(record.getDouble(FIELD_MAPINFO_8_XMIN),
					record.getDouble(FIELD_MAPINFO_9_YMIN), record.getDouble(FIELD_MAPINFO_10_XMAX),
					record.getDouble(FIELD_MAPINFO_11_YMAX));
			mapInfo.setMapExtent(extent);
			mapInfoList.add(mapInfo);
		}
		return mapInfoList;
	}

	public static Map<String, Object> DataMapFromMapInfo(TYMapInfo mapInfo) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_MAPINFO_1_CITY_ID, mapInfo.getCityID());
		data.put(FIELD_MAPINFO_2_BUILDING_ID, mapInfo.getBuildingID());
		data.put(FIELD_MAPINFO_3_MAP_ID, mapInfo.getMapID());
		data.put(FIELD_MAPINFO_4_FLOOR_NAME, mapInfo.getFloorName());
		data.put(FIELD_MAPINFO_5_FLOOR_NUMBER, mapInfo.getFloorNumber());
		data.put(FIELD_MAPINFO_6_SIZE_X, mapInfo.getMapSize().getX());
		data.put(FIELD_MAPINFO_7_SIZE_Y, mapInfo.getMapSize().getY());
		data.put(FIELD_MAPINFO_8_XMIN, mapInfo.getMapExtent().getXmin());
		data.put(FIELD_MAPINFO_9_YMIN, mapInfo.getMapExtent().getYmin());
		data.put(FIELD_MAPINFO_10_XMAX, mapInfo.getMapExtent().getXmax());
		data.put(FIELD_MAPINFO_11_YMAX, mapInfo.getMapExtent().getYmax());
		return data;
	}

}

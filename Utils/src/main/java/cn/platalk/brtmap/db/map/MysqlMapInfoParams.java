package cn.platalk.brtmap.db.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.impl.TYMapInfo;
import cn.platalk.sqlhelper.mysql.MysqlField;
import cn.platalk.sqlhelper.mysql.MysqlFieldType;
import cn.platalk.sqlhelper.mysql.MysqlRecord;
import cn.platalk.sqlhelper.mysql.MysqlTable;

public class MysqlMapInfoParams {
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

	private static List<MysqlField> mapInfoFieldList = null;

	public static MysqlTable CreateTable() {
		return new MysqlTable(TABLE_MAPINFO, GetMapInfoFieldList(), null);
	}

	public static List<MysqlField> GetMapInfoFieldList() {
		if (mapInfoFieldList == null) {
			mapInfoFieldList = new ArrayList<MysqlField>();

			mapInfoFieldList.add(new MysqlField(FIELD_MAPINFO_1_CITY_ID,
					new MysqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			mapInfoFieldList.add(new MysqlField(FIELD_MAPINFO_2_BUILDING_ID,
					new MysqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			mapInfoFieldList.add(new MysqlField(FIELD_MAPINFO_3_MAP_ID,
					new MysqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			mapInfoFieldList.add(new MysqlField(FIELD_MAPINFO_4_FLOOR_NAME,
					new MysqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			mapInfoFieldList.add(new MysqlField(FIELD_MAPINFO_5_FLOOR_NUMBER,
					new MysqlFieldType(Integer.class.getName(), "INT"), false));
			mapInfoFieldList.add(new MysqlField(FIELD_MAPINFO_6_SIZE_X,
					new MysqlFieldType(Double.class.getName(), "DOUBLE"), false));
			mapInfoFieldList.add(new MysqlField(FIELD_MAPINFO_7_SIZE_Y,
					new MysqlFieldType(Double.class.getName(), "DOUBLE"), false));
			mapInfoFieldList.add(
					new MysqlField(FIELD_MAPINFO_8_XMIN, new MysqlFieldType(Double.class.getName(), "DOUBLE"), false));
			mapInfoFieldList.add(
					new MysqlField(FIELD_MAPINFO_9_YMIN, new MysqlFieldType(Double.class.getName(), "DOUBLE"), false));
			mapInfoFieldList.add(
					new MysqlField(FIELD_MAPINFO_10_XMAX, new MysqlFieldType(Double.class.getName(), "DOUBLE"), false));
			mapInfoFieldList.add(
					new MysqlField(FIELD_MAPINFO_11_YMAX, new MysqlFieldType(Double.class.getName(), "DOUBLE"), false));

		}
		return mapInfoFieldList;
	}

	public static List<TYMapInfo> MapInfoListFromRecord(List<MysqlRecord> records) {
		List<TYMapInfo> mapInfoList = new ArrayList<TYMapInfo>();
		for (MysqlRecord record : records) {
			TYMapInfo mapInfo = new TYMapInfo();
			mapInfo.setCityID(record.getString(FIELD_MAPINFO_1_CITY_ID));
			mapInfo.setBuildingID(record.getString(FIELD_MAPINFO_2_BUILDING_ID));
			mapInfo.setMapID(record.getString(FIELD_MAPINFO_3_MAP_ID));
			mapInfo.setFloorName(record.getString(FIELD_MAPINFO_4_FLOOR_NAME));
			mapInfo.setFloorNumber(record.getInteger(FIELD_MAPINFO_5_FLOOR_NUMBER));
			mapInfo.setSize_x(record.getDouble(FIELD_MAPINFO_6_SIZE_X));
			mapInfo.setSize_y(record.getDouble(FIELD_MAPINFO_7_SIZE_Y));
			mapInfo.setXmin(record.getDouble(FIELD_MAPINFO_8_XMIN));
			mapInfo.setYmin(record.getDouble(FIELD_MAPINFO_9_YMIN));
			mapInfo.setXmax(record.getDouble(FIELD_MAPINFO_10_XMAX));
			mapInfo.setYmax(record.getDouble(FIELD_MAPINFO_11_YMAX));
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

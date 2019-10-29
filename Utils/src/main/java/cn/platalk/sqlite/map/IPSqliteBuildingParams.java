package cn.platalk.sqlite.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYMapExtent;
import cn.platalk.map.entity.base.impl.TYMapSize;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;

class IPSqliteBuildingParams {
	static final String TABLE_BUILDING = "BUILDING";

	static final String FIELD_BUILDING_1_CITY_ID = "CITY_ID";
	static final String FIELD_BUILDING_2_ID = "BUILDING_ID";
	static final String FIELD_BUILDING_3_NAME = "NAME";
	static final String FIELD_BUILDING_4_LONGITUDE = "LONGITUDE";
	static final String FIELD_BUILDING_5_LATITUDE = "LATITUDE";
	static final String FIELD_BUILDING_6_ADDRESS = "ADDRESS";
	static final String FIELD_BUILDING_7_INIT_ANGLE = "INIT_ANGLE";
	static final String FIELD_BUILDING_8_ROUTE_URL = "ROUTE_URL";
	static final String FIELD_BUILDING_9_OFFSET_X = "OFFSET_X";
	static final String FIELD_BUILDING_10_OFFSET_Y = "OFFSET_Y";
	static final String FIELD_BUILDING_11_STATUS = "STATUS";
	static final String FIELD_BUILDING_12_INIT_FLOOR_INDEX = "INIT_FLOOR_INDEX";

	static final String FIELD_BUILDING_13_XMIN = "XMIN";
	static final String FIELD_BUILDING_14_YMIN = "YMIN";
	static final String FIELD_BUILDING_15_XMAX = "XMAX";
	static final String FIELD_BUILDING_16_YMAX = "YMAX";

	static final String FIELD_BUILDING_17_WGS84_CALIBRATION_POINT = "WGS84_CALIBRATION_POINT";
	static final String FIELD_BUILDING_18_WT_CALIBRATION_POINT = "WT_CALIBRATION_POINT";
	static final String FIELD_BUILDING_19_DATA_VERSION = "DATA_VERSION";

	static final String FIELD_BUILDING_20_CENTER = "CENTER";

	private static List<IPSqlField> buildingFieldList = null;

	public static List<IPSqlField> GetBuildingFieldList() {
		if (buildingFieldList == null) {
			buildingFieldList = new ArrayList<IPSqlField>();
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_1_CITY_ID,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_2_ID,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_3_NAME,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_4_LONGITUDE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_5_LATITUDE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_6_ADDRESS,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_7_INIT_ANGLE,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_8_ROUTE_URL,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_9_OFFSET_X,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_10_OFFSET_Y,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_11_STATUS,
					IPSqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_12_INIT_FLOOR_INDEX,
					IPSqlFieldType.FieldTypeFromClass(Integer.class.getName()), false));

			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_13_XMIN,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_14_YMIN,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_15_XMAX,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_16_YMAX,
					IPSqlFieldType.FieldTypeFromClass(Double.class.getName()), false));

			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_17_WGS84_CALIBRATION_POINT,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), true));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_18_WT_CALIBRATION_POINT,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), true));

			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_19_DATA_VERSION,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), true));
			buildingFieldList.add(new IPSqlField(FIELD_BUILDING_20_CENTER,
					IPSqlFieldType.FieldTypeFromClass(String.class.getName()), true));
		}
		return buildingFieldList;
	}

	private static String doubleArrayToString(double[] coord) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			if (i != 0) {
				buffer.append(",");
			}
			buffer.append(coord[i]);
		}
		return buffer.toString();
	}

	private static double[] stringToDoubleArray(String str) {
		String[] splitted = str.split(",");
		double[] res = new double[6];
		for (int i = 0; i < 6; i++) {
			res[i] = Double.parseDouble(splitted[i]);
		}
		return res;
	}

	public static List<TYBuilding> BuildingListFromRecords(List<IPSqlRecord> records) {
		List<TYBuilding> buildingList = new ArrayList<TYBuilding>();
		for (IPSqlRecord record : records) {
			TYBuilding building = new TYBuilding();
			building.setCityID(record.getString(FIELD_BUILDING_1_CITY_ID));
			building.setBuildingID(record.getString(FIELD_BUILDING_2_ID));
			building.setName(record.getString(FIELD_BUILDING_3_NAME));
			building.setLongitude(record.getDouble(FIELD_BUILDING_4_LONGITUDE));
			building.setLatitude(record.getDouble(FIELD_BUILDING_5_LATITUDE));
			building.setAddress(record.getString(FIELD_BUILDING_6_ADDRESS));
			building.setInitAngle(record.getDouble(FIELD_BUILDING_7_INIT_ANGLE));
			building.setRouteURL(record.getString(FIELD_BUILDING_8_ROUTE_URL));
			building.setOffset(new TYMapSize(record.getDouble(FIELD_BUILDING_9_OFFSET_X),
					record.getDouble(FIELD_BUILDING_10_OFFSET_Y)));
			building.setStatus(record.getInteger(FIELD_BUILDING_11_STATUS));
			building.setInitFloorIndex(record.getInteger(FIELD_BUILDING_12_INIT_FLOOR_INDEX, 0));
			TYMapExtent extent = new TYMapExtent(record.getDouble(FIELD_BUILDING_13_XMIN, 0.0),
					record.getDouble(FIELD_BUILDING_14_YMIN, 0.0), record.getDouble(FIELD_BUILDING_15_XMAX, 0.0),
					record.getDouble(FIELD_BUILDING_16_YMAX, 0.0));
			building.setBuildingExtent(extent);

			if (record.getString(FIELD_BUILDING_17_WGS84_CALIBRATION_POINT) != null) {
				building.setWgs84CalibrationPoint(
						stringToDoubleArray(record.getString(FIELD_BUILDING_17_WGS84_CALIBRATION_POINT)));
			}

			if (record.getString(FIELD_BUILDING_18_WT_CALIBRATION_POINT) != null) {
				building.setWtCalibrationPoint(
						stringToDoubleArray(record.getString(FIELD_BUILDING_18_WT_CALIBRATION_POINT)));
			}

			if (record.getString(FIELD_BUILDING_19_DATA_VERSION) != null) {
				building.setDataVersion(record.getString(FIELD_BUILDING_19_DATA_VERSION));
			}

			if (record.getString(FIELD_BUILDING_20_CENTER) != null) {
				String center = record.getString(FIELD_BUILDING_20_CENTER);
				String xy[] = center.split(",");
				building.setCenterX(Double.parseDouble(xy[0]));
				building.setCenterY(Double.parseDouble(xy[1]));
			}
			buildingList.add(building);
		}
		return buildingList;
	}

	public static Map<String, Object> DataMapFromBuilding(TYBuilding building) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_BUILDING_1_CITY_ID, building.getCityID());
		data.put(FIELD_BUILDING_2_ID, building.getBuildingID());
		data.put(FIELD_BUILDING_3_NAME, building.getName());
		data.put(FIELD_BUILDING_4_LONGITUDE, building.getLongitude());
		data.put(FIELD_BUILDING_5_LATITUDE, building.getLatitude());
		data.put(FIELD_BUILDING_6_ADDRESS, building.getAddress());
		data.put(FIELD_BUILDING_7_INIT_ANGLE, building.getInitAngle());
		data.put(FIELD_BUILDING_8_ROUTE_URL, building.getRouteURL());
		data.put(FIELD_BUILDING_9_OFFSET_X, building.getOffset().getX());
		data.put(FIELD_BUILDING_10_OFFSET_Y, building.getOffset().getY());
		data.put(FIELD_BUILDING_11_STATUS, building.getStatus());
		data.put(FIELD_BUILDING_12_INIT_FLOOR_INDEX, building.getInitFloorIndex());
		data.put(FIELD_BUILDING_13_XMIN, building.getBuildingExtent().getXmin());
		data.put(FIELD_BUILDING_14_YMIN, building.getBuildingExtent().getYmin());
		data.put(FIELD_BUILDING_15_XMAX, building.getBuildingExtent().getXmax());
		data.put(FIELD_BUILDING_16_YMAX, building.getBuildingExtent().getYmax());
		if (building.getWgs84CalibrationPoint() != null) {
			data.put(FIELD_BUILDING_17_WGS84_CALIBRATION_POINT,
					doubleArrayToString(building.getWgs84CalibrationPoint()));
		}
		if (building.getWtCalibrationPoint() != null) {
			data.put(FIELD_BUILDING_18_WT_CALIBRATION_POINT, doubleArrayToString(building.getWtCalibrationPoint()));
		}
		data.put(FIELD_BUILDING_19_DATA_VERSION, building.getDataVersion());
		data.put(FIELD_BUILDING_20_CENTER, String.format("%f,%f", building.getCenterX(), building.getCenterY()));

		return data;
	}
}

package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYIMapExtent;
import cn.platalk.map.entity.base.TYIMapInfo;

public class TYMapInfo implements TYIMapInfo {
	static final String TAG = TYMapInfo.class.getSimpleName();

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

	String cityID;
	String buildingID;
	String mapID;

	String floorName;
	int floorNumber;

	double size_x;
	double size_y;

	double xmin;
	double xmax;
	double ymin;
	double ymax;

	public TYMapInfo() {
		super();
	}

	// public void parseJson(JSONObject jsonObject) {
	// if (jsonObject != null) {
	// setCityID(jsonObject.optString(FIELD_MAPINFO_1_CITY_ID));
	// setBuildingID(jsonObject.optString(FIELD_MAPINFO_2_BUILDING_ID));
	// setMapID(jsonObject.optString(FIELD_MAPINFO_3_MAP_ID));
	// setFloorName(jsonObject.optString(FIELD_MAPINFO_4_FLOOR_NAME));
	// setFloorNumber(jsonObject.optInt(FIELD_MAPINFO_5_FLOOR_NUMBER));
	// setSize_x(jsonObject.optDouble(FIELD_MAPINFO_6_SIZE_X));
	// setSize_y(jsonObject.optDouble(FIELD_MAPINFO_7_SIZE_Y));
	// setXmin(jsonObject.optDouble(FIELD_MAPINFO_8_XMIN));
	// setYmin(jsonObject.optDouble(FIELD_MAPINFO_9_YMIN));
	// setXmax(jsonObject.optDouble(FIELD_MAPINFO_10_XMAX));
	// setYmax(jsonObject.optDouble(FIELD_MAPINFO_11_YMAX));
	// }
	// }
	//
	// public JSONObject buildJson() throws JSONException {
	// JSONObject jsonObject = new JSONObject();
	// jsonObject.put(FIELD_MAPINFO_1_CITY_ID, cityID);
	// jsonObject.put(FIELD_MAPINFO_2_BUILDING_ID, buildingID);
	// jsonObject.put(FIELD_MAPINFO_3_MAP_ID, mapID);
	// jsonObject.put(FIELD_MAPINFO_4_FLOOR_NAME, floorName);
	// jsonObject.put(FIELD_MAPINFO_5_FLOOR_NUMBER, floorNumber);
	// jsonObject.put(FIELD_MAPINFO_6_SIZE_X, size_x);
	// jsonObject.put(FIELD_MAPINFO_7_SIZE_Y, size_y);
	// jsonObject.put(FIELD_MAPINFO_8_XMIN, xmin);
	// jsonObject.put(FIELD_MAPINFO_9_YMIN, ymin);
	// jsonObject.put(FIELD_MAPINFO_10_XMAX, xmax);
	// jsonObject.put(FIELD_MAPINFO_11_YMAX, ymax);
	// return jsonObject;
	// }

	public String getCityID() {
		return cityID;
	}

	public String getBuildingID() {
		return buildingID;
	}

	public String getMapID() {
		return mapID;
	}

	public TYMapSize getMapSize() {
		return new TYMapSize(size_x, size_y);
	}

	public MapExtent getMapExtent() {
		return new MapExtent(xmin, ymin, xmax, ymax);
	}

	public String getFloorName() {
		return floorName;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public double getScaleX() {
		return size_x / (xmax - xmin);
	}

	public double getScaleY() {
		return size_y / (ymax - ymin);
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}

	public void setBuildingID(String buildingID) {
		this.buildingID = buildingID;
	}

	public void setMapID(String mapID) {
		this.mapID = mapID;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public void setSize_x(double size_x) {
		this.size_x = size_x;
	}

	public void setSize_y(double size_y) {
		this.size_y = size_y;
	}

	public void setXmin(double xmin) {
		this.xmin = xmin;
	}

	public void setXmax(double xmax) {
		this.xmax = xmax;
	}

	public void setYmin(double ymin) {
		this.ymin = ymin;
	}

	public void setYmax(double ymax) {
		this.ymax = ymax;
	}

	@Override
	public String toString() {
		String str = "MapID: %s, Floor:%d ,Size:(%.2f, %.2f) Extent: (%.4f, %.4f, %.4f, %.4f)";
		return String.format(str, mapID, floorNumber, size_x, size_y, xmin, ymin, xmax, ymax);
	}

	public class MapExtent implements TYIMapExtent {
		double xmin;
		double ymin;
		double xmax;
		double ymax;

		public MapExtent(double xmin, double ymin, double xmax, double ymax) {
			this.xmin = xmin;
			this.ymin = ymin;
			this.xmax = xmax;
			this.ymax = ymax;
		}

		public double getXmin() {
			return xmin;
		}

		public double getYmin() {
			return ymin;
		}

		public double getXmax() {
			return xmax;
		}

		public double getYmax() {
			return ymax;
		}
	}

}

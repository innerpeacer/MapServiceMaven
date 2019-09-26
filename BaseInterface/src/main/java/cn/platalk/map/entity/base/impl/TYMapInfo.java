package cn.platalk.map.entity.base.impl;

import org.json.JSONObject;

import cn.platalk.map.entity.base.TYIMapInfo;

public class TYMapInfo implements TYIMapInfo {
	static final String KEY_JSON_MAPINFO_CITYID = "cityID";
	static final String KEY_JSON_MAPINFO_BUILDINGID = "buildingID";
	static final String KEY_JSON_MAPINFO_MAPID = "mapID";
	static final String KEY_JSON_MAPINFO_FLOOR = "floorName";
	static final String KEY_JSON_MAPINFO_FLOOR_INDEX = "floorIndex";
	static final String KEY_JSON_MAPINFO_SIZEX = "size_x";
	static final String KEY_JSON_MAPINFO_SIZEY = "size_y";
	static final String KEY_JSON_MAPINFO_XMIN = "xmin";
	static final String KEY_JSON_MAPINFO_XMAX = "xmax";
	static final String KEY_JSON_MAPINFO_YMIN = "ymin";
	static final String KEY_JSON_MAPINFO_YMAX = "ymax";

	String cityID;
	String buildingID;
	String mapID;

	String floorName;
	int floorNumber;

	double size_x;
	double size_y;

	TYMapExtent mapExtent;

	public TYMapInfo() {
		super();
	}

	@Override
	public String getCityID() {
		return cityID;
	}

	@Override
	public String getBuildingID() {
		return buildingID;
	}

	@Override
	public String getMapID() {
		return mapID;
	}

	@Override
	public TYMapSize getMapSize() {
		return new TYMapSize(size_x, size_y);
	}

	@Override
	public TYMapExtent getMapExtent() {
		return mapExtent;
	}

	@Override
	public String getFloorName() {
		return floorName;
	}

	@Override
	public int getFloorNumber() {
		return floorNumber;
	}

	// public double getScaleX() {
	// return size_x / (xmax - xmin);
	// }
	//
	// public double getScaleY() {
	// return size_y / (ymax - ymin);
	// }

	public void setMapExtent(TYMapExtent extent) {
		this.mapExtent = extent;
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

	@Override
	public JSONObject toJson() {
		JSONObject mapInfoObject = new JSONObject();
		mapInfoObject.put(KEY_JSON_MAPINFO_CITYID, getCityID());
		mapInfoObject.put(KEY_JSON_MAPINFO_BUILDINGID, getBuildingID());
		mapInfoObject.put(KEY_JSON_MAPINFO_MAPID, getMapID());
		mapInfoObject.put(KEY_JSON_MAPINFO_FLOOR, getFloorName());
		mapInfoObject.put(KEY_JSON_MAPINFO_FLOOR_INDEX, getFloorNumber());
		mapInfoObject.put(KEY_JSON_MAPINFO_SIZEX, getMapSize().getX());
		mapInfoObject.put(KEY_JSON_MAPINFO_SIZEY, getMapSize().getY());
		mapInfoObject.put(KEY_JSON_MAPINFO_XMIN, getMapExtent().getXmin());
		mapInfoObject.put(KEY_JSON_MAPINFO_XMAX, getMapExtent().getXmax());
		mapInfoObject.put(KEY_JSON_MAPINFO_YMIN, getMapExtent().getYmin());
		mapInfoObject.put(KEY_JSON_MAPINFO_YMAX, getMapExtent().getYmax());
		return mapInfoObject;
	}

	@Override
	public String toString() {
		String str = "MapID: %s, Floor:%d ,Size:(%.2f, %.2f) Extent: (%.4f, %.4f, %.4f, %.4f)";
		return String.format(str, mapID, floorNumber, size_x, size_y, mapExtent.getXmin(), mapExtent.getYmin(),
				mapExtent.getXmax(), mapExtent.getYmax());
	}
}

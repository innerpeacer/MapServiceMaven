package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYIMapInfo;

public class TYMapInfo implements TYIMapInfo {
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

	public TYMapExtent getMapExtent() {
		return new TYMapExtent(xmin, ymin, xmax, ymax);
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
}

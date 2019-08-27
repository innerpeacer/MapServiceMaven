package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYIBuilding;
import cn.platalk.map.entity.base.TYIMapExtent;

public class TYBuilding implements TYIBuilding {
	private String cityID;
	private String buildingID;
	private String name;
	private String address;

	private double longitude;
	private double latitude;

	private double initAngle;
	private int initFloorIndex;

	private String routeURL;
	private TYMapSize offset;
	private double xmin;
	private double xmax;
	private double ymin;
	private double ymax;

	private double[] wgs84CalibrationPoint;
	private double[] wtCalibrationPoint;

	private int status;

	public TYBuilding() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof TYBuilding) {
			if (this.buildingID.equals(((TYBuilding) obj).getBuildingID())) {
				return true;
			}
		}
		return false;
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}

	public void setBuildingID(String buildingID) {
		this.buildingID = buildingID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setInitAngle(double initAngle) {
		this.initAngle = initAngle;
	}

	public void setInitFloorIndex(int initFloorIndex) {
		this.initFloorIndex = initFloorIndex;
	}

	public void setRouteURL(String routeURL) {
		this.routeURL = routeURL;
	}

	public void setOffset(TYMapSize offset) {
		this.offset = offset;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCityID() {
		return cityID;
	}

	public String getBuildingID() {
		return buildingID;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getInitAngle() {
		return initAngle;
	}

	public int getInitFloorIndex() {
		return initFloorIndex;
	}

	public String getRouteURL() {
		return routeURL;
	}

	public TYMapSize getOffset() {
		return offset;
	}

	public TYIMapExtent getBuildingExtent() {
		return new TYMapExtent(xmin, ymin, xmax, ymax);
	}

	public int getStatus() {
		return status;
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

	public double[] getWgs84CalibrationPoint() {
		return wgs84CalibrationPoint;
	}

	public void setWgs84CalibrationPoint(double[] wgs84CalibrationPoint) {
		this.wgs84CalibrationPoint = wgs84CalibrationPoint;
	}

	public double[] getWtCalibrationPoint() {
		return wtCalibrationPoint;
	}

	public void setWtCalibrationPoint(double[] wtCalibrationPoint) {
		this.wtCalibrationPoint = wtCalibrationPoint;
	}

	@Override
	public String toString() {
		return "BuildingID = " + buildingID + ", Name = " + name;
	}

}

package cn.platalk.map.entity.base.impl;

import org.json.JSONObject;

import cn.platalk.map.entity.base.TYIBuilding;
import cn.platalk.map.entity.base.TYIMapExtent;

public class TYBuilding implements TYIBuilding {
	static final String KEY_JSON_BUILDING_CITY_ID = "cityID";
	static final String KEY_JSON_BUILDING_ID = "id";
	static final String KEY_JSON_BUILDING_NAME = "name";
	static final String KEY_JSON_BUILDING_LONGITUDE = "longitude";
	static final String KEY_JSON_BUILDING_LATITUDE = "latitude";
	static final String KEY_JSON_BUILDING_ADDRESS = "address";
	static final String KEY_JSON_BUILDING_INIT_ANGLE = "initAngle";
	static final String KEY_JSON_BUILDING_INIT_FLOOR = "initFloorIndex";
	static final String KEY_JSON_BUILDING_ROUTE_URL = "routeURL";
	static final String KEY_JSON_BUILDING_OFFSET_X = "offsetX";
	static final String KEY_JSON_BUILDING_OFFSET_Y = "offsetY";
	static final String KEY_JSON_BUILDING_XMIN = "xmin";
	static final String KEY_JSON_BUILDING_XMAX = "xmax";
	static final String KEY_JSON_BUILDING_YMIN = "ymin";
	static final String KEY_JSON_BUILDING_YMAX = "ymax";
	static final String KEY_JSON_BUILDING_WGS84_CALIBRATION_POINT = "wgs84CalibrationPoint";
	static final String KEY_JSON_BUILDING_WT_CALIBRATION_POINT = "wtCalibrationPoint";
	static final String KEY_JSON_BUILDING_STATUS = "status";

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
	private TYMapExtent buildingExtent;

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

	@Override
	public String getCityID() {
		return cityID;
	}

	@Override
	public String getBuildingID() {
		return buildingID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public double getLongitude() {
		return longitude;
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

	@Override
	public double getInitAngle() {
		return initAngle;
	}

	@Override
	public int getInitFloorIndex() {
		return initFloorIndex;
	}

	@Override
	public String getRouteURL() {
		return routeURL;
	}

	@Override
	public TYMapSize getOffset() {
		return offset;
	}

	@Override
	public TYIMapExtent getBuildingExtent() {
		return buildingExtent;
	}

	@Override
	public int getStatus() {
		return status;
	}

	public void setBuildingExtent(TYMapExtent extent) {
		this.buildingExtent = extent;
	}

	@Override
	public double[] getWgs84CalibrationPoint() {
		return wgs84CalibrationPoint;
	}

	public void setWgs84CalibrationPoint(double[] wgs84CalibrationPoint) {
		this.wgs84CalibrationPoint = wgs84CalibrationPoint;
	}

	@Override
	public double[] getWtCalibrationPoint() {
		return wtCalibrationPoint;
	}

	public void setWtCalibrationPoint(double[] wtCalibrationPoint) {
		this.wtCalibrationPoint = wtCalibrationPoint;
	}

	@Override
	public JSONObject toJson() {
		JSONObject buildingObject = new JSONObject();
		buildingObject.put(KEY_JSON_BUILDING_CITY_ID, getCityID());
		buildingObject.put(KEY_JSON_BUILDING_ID, getBuildingID());
		buildingObject.put(KEY_JSON_BUILDING_NAME, getName());
		buildingObject.put(KEY_JSON_BUILDING_LONGITUDE, getLongitude());
		buildingObject.put(KEY_JSON_BUILDING_LATITUDE, getLatitude());
		buildingObject.put(KEY_JSON_BUILDING_ADDRESS, getAddress());
		buildingObject.put(KEY_JSON_BUILDING_INIT_ANGLE, getInitAngle());
		buildingObject.put(KEY_JSON_BUILDING_INIT_FLOOR, getInitFloorIndex());
		buildingObject.put(KEY_JSON_BUILDING_ROUTE_URL, getRouteURL());
		buildingObject.put(KEY_JSON_BUILDING_OFFSET_X, getOffset().getX());
		buildingObject.put(KEY_JSON_BUILDING_OFFSET_Y, getOffset().getY());
		buildingObject.put(KEY_JSON_BUILDING_XMIN, getBuildingExtent().getXmin());
		buildingObject.put(KEY_JSON_BUILDING_XMAX, getBuildingExtent().getXmax());
		buildingObject.put(KEY_JSON_BUILDING_YMIN, getBuildingExtent().getYmin());
		buildingObject.put(KEY_JSON_BUILDING_YMAX, getBuildingExtent().getYmax());
		buildingObject.put(KEY_JSON_BUILDING_WGS84_CALIBRATION_POINT, getWgs84CalibrationPoint());
		buildingObject.put(KEY_JSON_BUILDING_WT_CALIBRATION_POINT, getWtCalibrationPoint());
		buildingObject.put(KEY_JSON_BUILDING_STATUS, getStatus());
		return buildingObject;
	}

	@Override
	public String toString() {
		return "BuildingID = " + buildingID + ", Name = " + name;
	}

}

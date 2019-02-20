package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYIBuilding;

public class TYBuilding implements TYIBuilding {

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

	private String cityID;
	private String buildingID;
	private String name;
	private String address;

	private double longitude;
	private double latitude;

	private double initAngle;
	private String routeURL;
	private TYMapSize offset;

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

	// public void parseJson(JSONObject jsonObject) {
	// if (jsonObject != null) {
	// setCityID(jsonObject.optString(FIELD_BUILDING_1_CITY_ID));
	// setBuildingID(jsonObject.optString(FIELD_BUILDING_2_ID));
	// setName(jsonObject.optString(FIELD_BUILDING_3_NAME));
	// setLongitude(jsonObject.optDouble(FIELD_BUILDING_4_LONGITUDE));
	// setLatitude(jsonObject.optDouble(FIELD_BUILDING_5_LATITUDE));
	// setAddress(jsonObject.optString(FIELD_BUILDING_6_ADDRESS));
	// setInitAngle(jsonObject.optDouble(FIELD_BUILDING_7_INIT_ANGLE));
	// setRouteURL(jsonObject.optString(FIELD_BUILDING_8_ROUTE_URL));
	//
	// TYMapSize offset = new TYMapSize(0, 0);
	// double x = jsonObject.optDouble(FIELD_BUILDING_9_OFFSET_X);
	// offset.x = x;
	// double y = jsonObject.optDouble(FIELD_BUILDING_10_OFFSET_Y);
	// offset.y = y;
	// setOffset(offset);
	// setStatus(jsonObject.optInt(FIELD_BUILDING_11_STATUS));
	// }
	// }
	//
	// public JSONObject buildJson() throws JSONException {
	// JSONObject jsonObject = new JSONObject();
	// jsonObject.put(FIELD_BUILDING_1_CITY_ID, cityID);
	// jsonObject.put(FIELD_BUILDING_2_ID, buildingID);
	// jsonObject.put(FIELD_BUILDING_3_NAME, name);
	// jsonObject.put(FIELD_BUILDING_4_LONGITUDE, longitude);
	// jsonObject.put(FIELD_BUILDING_5_LATITUDE, latitude);
	// jsonObject.put(FIELD_BUILDING_6_ADDRESS, address);
	//
	// jsonObject.put(FIELD_BUILDING_7_INIT_ANGLE, initAngle);
	// jsonObject.put(FIELD_BUILDING_8_ROUTE_URL, routeURL);
	// jsonObject.put(FIELD_BUILDING_9_OFFSET_X, offset.x);
	// jsonObject.put(FIELD_BUILDING_10_OFFSET_Y, offset.y);
	// jsonObject.put(FIELD_BUILDING_11_STATUS, status);
	// return jsonObject;
	// }

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

	public String getRouteURL() {
		return routeURL;
	}

	public TYMapSize getOffset() {
		return offset;
	}

	public int getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "BuildingID = " + buildingID + ", Name = " + name;
	}

}

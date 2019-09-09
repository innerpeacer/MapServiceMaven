package cn.platalk.map.entity.base.impl;

import org.json.JSONObject;

import cn.platalk.map.entity.base.TYICity;

public class TYCity implements TYICity {
	static final String KEY_JSON_CITY_ID = "id";
	static final String KEY_JSON_CITY_NAME = "name";
	static final String KEY_JSON_CITY_SHORT_NAME = "sname";
	static final String KEY_JSON_CITY_LONGITUDE = "longitude";
	static final String KEY_JSON_CITY_LATITUDE = "latitude";
	static final String KEY_JSON_CITY_STATUS = "status";

	private String cityID;
	private String name;
	private String sname;

	private double longitude;
	private double latitude;

	private int status;

	public TYCity() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof TYCity) {
			if (this.cityID.equals(((TYCity) obj).getCityID())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getCityID() {
		return cityID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSname() {
		return sname;
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
	public int getStatus() {
		return status;
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public JSONObject toJson() {
		JSONObject cityObject = new JSONObject();
		cityObject.put(KEY_JSON_CITY_ID, getCityID());
		cityObject.put(KEY_JSON_CITY_NAME, getName());
		cityObject.put(KEY_JSON_CITY_SHORT_NAME, getSname());
		cityObject.put(KEY_JSON_CITY_LONGITUDE, getLongitude());
		cityObject.put(KEY_JSON_CITY_LATITUDE, getLatitude());
		cityObject.put(KEY_JSON_CITY_STATUS, getStatus());
		return cityObject;
	}

	@Override
	public String toString() {
		return "CityID = " + cityID + ", CityName = " + name;
	}

}

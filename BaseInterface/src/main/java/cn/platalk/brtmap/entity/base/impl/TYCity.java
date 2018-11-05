package cn.platalk.brtmap.entity.base.impl;

import cn.platalk.brtmap.entity.base.TYICity;

public class TYCity implements TYICity {

	static final String FIELD_CITY_1_ID = "CITY_ID";
	static final String FIELD_CITY_2_NAME = "NAME";
	static final String FIELD_CITY_3_SNAME = "SNAME";
	static final String FIELD_CITY_4_LONGITUDE = "LONGITUDE";
	static final String FIELD_CITY_5_LATITUDE = "LATITUDE";
	static final String FIELD_CITY_6_STATUS = "STATUS";

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

	// public void parseJson(JSONObject jsonObject) {
	// if (jsonObject != null) {
	// setCityID(jsonObject.optString(FIELD_CITY_1_ID));
	// setName(jsonObject.optString(FIELD_CITY_2_NAME));
	// setSname(jsonObject.optString(FIELD_CITY_3_SNAME));
	// setLongitude(jsonObject.optDouble(FIELD_CITY_4_LONGITUDE));
	// setLatitude(jsonObject.optDouble(FIELD_CITY_5_LATITUDE));
	// setStatus(jsonObject.optInt(FIELD_CITY_6_STATUS));
	// }
	// }
	//
	// public JSONObject buildJson() throws JSONException {
	// JSONObject jsonObject = new JSONObject();
	// jsonObject.put(FIELD_CITY_1_ID, cityID);
	// jsonObject.put(FIELD_CITY_2_NAME, name);
	// jsonObject.put(FIELD_CITY_3_SNAME, sname);
	// jsonObject.put(FIELD_CITY_4_LONGITUDE, longitude);
	// jsonObject.put(FIELD_CITY_5_LATITUDE, latitude);
	// jsonObject.put(FIELD_CITY_6_STATUS, status);
	// return jsonObject;
	// }

	public String getCityID() {
		return cityID;
	}

	public String getName() {
		return name;
	}

	public String getSname() {
		return sname;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

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
	public String toString() {
		return "CityID = " + cityID + ", CityName = " + name;
	}

}

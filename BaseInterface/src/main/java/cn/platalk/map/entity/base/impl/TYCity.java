package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYICity;

public class TYCity implements TYICity {
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

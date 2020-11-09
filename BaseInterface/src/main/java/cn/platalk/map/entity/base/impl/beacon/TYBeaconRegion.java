package cn.platalk.map.entity.base.impl.beacon;

import cn.platalk.map.entity.base.beacon.TYIBeaconRegion;

public class TYBeaconRegion implements TYIBeaconRegion {
	static final String FIELD_BEACON_REGION_1_CITY_ID = "CITY_ID";
	static final String FIELD_BEACON_REGION_2_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_BEACON_REGION_3_BUILDING_NAME = "BUILDING_NAME";
	static final String FIELD_BEACON_REGION_4_UUID = "UUID";
	static final String FIELD_BEACON_REGION_5_MAJOR = "MAJOR";

	private String cityID;
	private String buildingID;
	private String buildingName;
	private String uuid;
	private Integer major;

	public String getCityID() {
		return cityID;
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}

	public String getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(String buildingID) {
		this.buildingID = buildingID;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String name) {
		this.buildingName = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getMajor() {
		return major;
	}

	public void setMajor(Integer major) {
		this.major = major;
	}

	public String toString() {
		return buildingName + "(" + buildingID + "): " + uuid + ", " + major;
	}
}

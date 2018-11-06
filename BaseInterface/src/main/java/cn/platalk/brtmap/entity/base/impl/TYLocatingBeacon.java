package cn.platalk.brtmap.entity.base.impl;

import cn.platalk.brtmap.entity.base.TYILocatingBeacon;
import cn.platalk.brtmap.entity.base.TYLocalPoint;

public class TYLocatingBeacon extends TYBeacon implements TYILocatingBeacon {
	static final String FIELD_BEACON_1_GEOM = "GEOM";
	public static final String FIELD_BEACON_2_UUID = "UUID";
	public static final String FIELD_BEACON_3_MAJOR = "MAJOR";
	public static final String FIELD_BEACON_4_MINOR = "MINOR";
	public static final String FIELD_BEACON_5_FLOOR = "FLOOR";
	public static final String FIELD_BEACON_6_X = "X";
	public static final String FIELD_BEACON_7_Y = "Y";
	public static final String FIELD_BEACON_8_ROOM_ID = "ROOM_ID";
	public static final String FIELD_BEACON_9_TAG = "TAG";

	public static final String FIELD_BEACON_10_MAP_ID = "MAP_ID";
	public static final String FIELD_BEACON_11_BUILDING_ID = "BUILDING_ID";
	public static final String FIELD_BEACON_12_CITY_ID = "CITY_ID";

	private TYLocalPoint location;
	private String roomID;

	private String mapID;
	private String buildingID;
	private String cityID;

	public TYLocatingBeacon() {

	}

	public TYLocatingBeacon(String uuid, int major, int minor, String tag, String roomID) {
		super(uuid, major, minor, tag);
		this.roomID = roomID;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String rID) {
		this.roomID = rID;
	}

	public TYLocalPoint getLocation() {
		return location;
	}

	public void setLocation(TYLocalPoint location) {
		this.location = location;
	}

	public String getMapID() {
		return mapID;
	}

	public void setMapID(String mapID) {
		this.mapID = mapID;
	}

	public String getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(String buildingID) {
		this.buildingID = buildingID;
	}

	public String getCityID() {
		return cityID;
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}
}

package cn.platalk.map.entity.base.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import cn.platalk.foundation.TYGeojsonBuilder;
import cn.platalk.map.entity.base.TYILocatingBeacon;

public class TYLocatingBeacon extends TYBeacon implements TYILocatingBeacon {
	static GeometryFactory factory = new GeometryFactory();

	static final String KEY_GEOJSON_BEACON_ATTRIBUTE_UUID = "uuid";
	static final String KEY_GEOJSON_BEACON_ATTRIBUTE_MAJOR = "major";
	static final String KEY_GEOJSON_BEACON_ATTRIBUTE_MINOR = "minor";
	static final String KEY_GEOJSON_BEACON_ATTRIBUTE_FLOOR = "floor";

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

	@Override
	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String rID) {
		this.roomID = rID;
	}

	@Override
	public TYLocalPoint getLocation() {
		return location;
	}

	public void setLocation(TYLocalPoint location) {
		this.location = location;
	}

	@Override
	public String getMapID() {
		return mapID;
	}

	public void setMapID(String mapID) {
		this.mapID = mapID;
	}

	@Override
	public String getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(String buildingID) {
		this.buildingID = buildingID;
	}

	@Override
	public String getCityID() {
		return cityID;
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}

	@Override
	public JSONObject toGeojson() {
		Point point = factory.createPoint(new Coordinate(location.getX(), location.getY()));
		return TYGeojsonBuilder.buildGeometry(point, beaconPropertyMap());
	}

	Map<String, Object> beaconPropertyMap() {
		Map<String, Object> propMap = new HashMap<String, Object>();
		propMap.put(KEY_GEOJSON_BEACON_ATTRIBUTE_UUID, getUUID());
		propMap.put(KEY_GEOJSON_BEACON_ATTRIBUTE_MAJOR, getMajor());
		propMap.put(KEY_GEOJSON_BEACON_ATTRIBUTE_MINOR, getMinor());
		propMap.put(KEY_GEOJSON_BEACON_ATTRIBUTE_FLOOR, getLocation().getFloor());
		return propMap;
	}
}

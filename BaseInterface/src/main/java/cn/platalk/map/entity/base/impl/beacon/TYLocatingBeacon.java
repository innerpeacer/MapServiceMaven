package cn.platalk.map.entity.base.impl.beacon;

import java.util.HashMap;
import java.util.Map;

import cn.platalk.map.entity.base.impl.beacon.TYBeacon;
import cn.platalk.map.entity.base.impl.map.TYLocalPoint;
import org.json.JSONObject;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import cn.platalk.foundation.TYGeojsonBuilder;
import cn.platalk.map.entity.base.beacon.TYILocatingBeacon;

public class TYLocatingBeacon extends TYBeacon implements TYILocatingBeacon {
	static final GeometryFactory factory = new GeometryFactory();

	static final String KEY_JSON_BEACON_1_UUID = "uuid";
	static final String KEY_JSON_BEACON_2_MAJOR = "major";
	static final String KEY_JSON_BEACON_3_MINOR = "minor";
	static final String KEY_JSON_BEACON_4_X = "x";
	static final String KEY_JSON_BEACON_5_Y = "y";
	static final String KEY_JSON_BEACON_6_FLOOR = "floor";

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
	public JSONObject toJson() {
		JSONObject beaconObject = new JSONObject();
		beaconObject.put(KEY_JSON_BEACON_1_UUID, getUUID());
		beaconObject.put(KEY_JSON_BEACON_2_MAJOR, getMajor());
		beaconObject.put(KEY_JSON_BEACON_3_MINOR, getMinor());
		beaconObject.put(KEY_JSON_BEACON_4_X, getLocation().getX());
		beaconObject.put(KEY_JSON_BEACON_5_Y, getLocation().getY());
		beaconObject.put(KEY_JSON_BEACON_6_FLOOR, getLocation().getFloor());
		return beaconObject;
	}

	@Override
	public JSONObject toGeojson() {
		Point point = factory.createPoint(new Coordinate(location.getX(), location.getY()));
		return TYGeojsonBuilder.buildGeometry(point, beaconPropertyMap());
	}

	Map<String, Object> beaconPropertyMap() {
		Map<String, Object> propMap = new HashMap<>();
		propMap.put(KEY_GEOJSON_BEACON_ATTRIBUTE_UUID, getUUID());
		propMap.put(KEY_GEOJSON_BEACON_ATTRIBUTE_MAJOR, getMajor());
		propMap.put(KEY_GEOJSON_BEACON_ATTRIBUTE_MINOR, getMinor());
		propMap.put(KEY_GEOJSON_BEACON_ATTRIBUTE_FLOOR, getLocation().getFloor());
		return propMap;
	}
}

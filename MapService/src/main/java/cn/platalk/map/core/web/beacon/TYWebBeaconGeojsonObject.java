package cn.platalk.map.core.web.beacon;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import cn.platalk.foundation.TYGeojsonBuilder;
import cn.platalk.map.entity.base.impl.TYLocatingBeacon;

public class TYWebBeaconGeojsonObject {

	public static final String KEY_BEACON_DATA_ATTRIBUTE_UUID = "uuid";
	public static final String KEY_BEACON_DATA_ATTRIBUTE_MAJOR = "major";
	public static final String KEY_BEACON_DATA_ATTRIBUTE_MINOR = "minor";
	public static final String KEY_BEACON_DATA_ATTRIBUTE_FLOOR = "floor";

	static GeometryFactory factory = new GeometryFactory();

	public static JSONObject buildLocatingBeacon(TYLocatingBeacon beacon) throws JSONException {
		Point point = factory.createPoint(new Coordinate(beacon.getLocation().getX(), beacon.getLocation().getY()));

		Map<String, Object> propMap = new HashMap<String, Object>();
		propMap.put(KEY_BEACON_DATA_ATTRIBUTE_UUID, beacon.getUUID());
		propMap.put(KEY_BEACON_DATA_ATTRIBUTE_MAJOR, beacon.getMajor());
		propMap.put(KEY_BEACON_DATA_ATTRIBUTE_MINOR, beacon.getMinor());
		propMap.put(KEY_BEACON_DATA_ATTRIBUTE_FLOOR, beacon.getLocation().getFloor());

		return TYGeojsonBuilder.buildGeometry(point, propMap);
	}
}

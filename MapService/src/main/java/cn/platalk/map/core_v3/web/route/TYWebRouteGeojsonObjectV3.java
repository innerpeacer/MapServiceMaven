package cn.platalk.map.core_v3.web.route;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.map.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.utils.coordinate.TYCoordProjection;

public class TYWebRouteGeojsonObjectV3 {
	public static final String VALUE_GEOMETRY_TYPE_POINT = "Point";
	public static final String VALUE_GEOMETRY_TYPE_MULTI_POINT = "MultiPoint";

	public static final String VALUE_GEOMETRY_TYPE_LINEARRING = "LinearRing";
	public static final String VALUE_GEOMETRY_TYPE_LINESTRING = "LineString";
	public static final String VALUE_GEOMETRY_TYPE_MULTI_LINESTRING = "MultiLineString";

	public static final String VALUE_GEOMETRY_TYPE_POLYGON = "Polygon";
	public static final String VALUE_GEOMETRY_TYPE_MULTIPOLYGON = "MultiPolygon";

	public static final String GEOJSON_KEY_FEATURE_TYPE = "type";
	public static final String GEOJSON_KEY_GEOMETRY = "geometry";
	public static final String GEOJSON_KEY_PROPERTIES = "properties";

	public static final String GEOJSON_VALUE_FEATURE_TYPE__FEATURE = "Feature";

	public static final String GEOJSON_KEY_GEOMETRY_TYPE = "type";
	public static final String GEOJSON_KEY_GEOMETRY_COORDINATES = "coordinates";

	static final String GEOJSON_VALUE_GEOMETRY_TYPE__POINT = "Point";
	static final String GEOJSON_VALUE_GEOMETRY_TYPE__MULTIPOINT = "MultiPoint";
	static final String GEOJSON_VALUE_GEOMETRY_TYPE__LINESTRING = "LineString";
	static final String GEOJSON_VALUE_GEOMETRY_TYPE__MULTILINESTRING = "MultiLineString";
	static final String GEOJSON_VALUE_GEOMETRY_TYPE__POLYGON = "Polygon";
	static final String GEOJSON_VALUE_GEOMETRY_TYPE__MULTIPOLYGON = "MultiPolygon";
	static final String GEOJSON_VALUE_GEOMETRY_TYPE__GEOMETRYCOLLECTION = "GeometryCollection";

	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_LINK_ID = "linkID";
	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_HEAD_NODE = "headNode";
	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_END_NODE = "endNode";
	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_IS_ONE_WAY = "isOneWay";
	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_LINK_NAME = "linkName";
	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_FLOOR = "floor";
	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_LEVEL = "level";
	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_REVERSE = "reverse";
	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_ROOM_ID = "roomID";
	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_OPEN = "open";
	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_OPEN_TIME = "openTime";
	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_ALLOWSNAP = "allowSnap";
	public static final String KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_LINK_TYPE = "linkType";

	public static final String KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_NODE_ID = "nodeID";
	public static final String KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_NODE_NAME = "nodeName";
	public static final String KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_CATEGORY_ID = "categoryID";
	public static final String KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_FLOOR = "floor";
	public static final String KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_LEVEL = "level";
	public static final String KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_IS_SWITCHING = "isSwitching";
	public static final String KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_SWITCHING_ID = "switchingID";
	public static final String KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_DIRECTION = "direction";
	public static final String KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_NODE_TYPE = "nodeType";
	public static final String KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_OPEN = "open";
	public static final String KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_OPEN_TIME = "openTime";
	public static final String KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_ROOM_ID = "roomID";

	static WKBReader reader = new WKBReader();

	public static JSONObject buildingLinkRecord(TYIRouteLinkRecordV3 record) throws JSONException {
		JSONObject routeDataObject = new JSONObject();
		routeDataObject.put(GEOJSON_KEY_FEATURE_TYPE, GEOJSON_VALUE_FEATURE_TYPE__FEATURE);

		JSONObject geometryObject = new JSONObject();
		Geometry geometry = null;
		try {
			geometry = (LineString) reader.read(record.getGeometryData());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (geometry == null) {
			return null;
		}

		if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_LINESTRING)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE, GEOJSON_VALUE_GEOMETRY_TYPE__LINESTRING);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES, buildGeojsonLineString((LineString) geometry));
		} else if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_LINEARRING)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE, GEOJSON_VALUE_GEOMETRY_TYPE__LINESTRING);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES, buildGeojsonLineString((LinearRing) geometry));
		} else if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_MULTI_LINESTRING)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE, GEOJSON_VALUE_GEOMETRY_TYPE__MULTILINESTRING);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES,
					buildGeojsonMultiLineString((MultiLineString) geometry));
		}
		routeDataObject.put(GEOJSON_KEY_GEOMETRY, geometryObject);

		JSONObject propertiesObject = new JSONObject();
		propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_LINK_ID, record.getLinkID());
		propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_HEAD_NODE, record.getHeadNode());
		propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_END_NODE, record.getEndNode());
		propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_IS_ONE_WAY, record.isOneWay());
		if (record.getLinkName() != null) {
			propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_LINK_NAME, record.getLinkName());
		} else {
			propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_LINK_NAME, "null");
		}
		propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_FLOOR, record.getFloor());
		propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_LEVEL, record.getLevel());
		propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_REVERSE, record.isReverse());

		if (record.getRoomID() != null) {
			propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_ROOM_ID, record.getRoomID());
		} else {
			propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_ROOM_ID, "null");
		}
		propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_OPEN, record.isOpen());

		propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_OPEN_TIME, record.getOpenTime());
		propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_ALLOWSNAP, record.isAllowSnap());
		propertiesObject.put(KEY_WEB_ROUTE_LINK_DATA_ATTRIBUTE_LINK_TYPE, record.getLinkType());

		routeDataObject.put(GEOJSON_KEY_PROPERTIES, propertiesObject);
		return routeDataObject;
	}

	public static JSONObject buildingNodeRecord(TYIRouteNodeRecordV3 record) throws JSONException {
		JSONObject routeDataObject = new JSONObject();
		routeDataObject.put(GEOJSON_KEY_FEATURE_TYPE, GEOJSON_VALUE_FEATURE_TYPE__FEATURE);

		JSONObject geometryObject = new JSONObject();
		Geometry geometry = null;
		try {
			geometry = (Point) reader.read(record.getGeometryData());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (geometry == null) {
			return null;
		}

		if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_POINT)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE, GEOJSON_VALUE_GEOMETRY_TYPE__POINT);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES, buildGeojsonPointFeature((Point) geometry));
		} else if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_MULTI_POINT)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE, GEOJSON_VALUE_GEOMETRY_TYPE__MULTIPOINT);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES, buildGeojsonMultiPointFeature((MultiPoint) geometry));
		}
		routeDataObject.put(GEOJSON_KEY_GEOMETRY, geometryObject);

		JSONObject propertiesObject = new JSONObject();
		propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_NODE_ID, record.getNodeID());
		if (record.getNodeName() != null) {
			propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_NODE_NAME, record.getNodeName());
		} else {
			propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_NODE_NAME, "null");
		}
		propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_CATEGORY_ID, record.getCategoryID());
		propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_FLOOR, record.getFloor());
		propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_LEVEL, record.getLevel());
		propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_IS_SWITCHING, record.isSwitching());
		propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_SWITCHING_ID, record.getSwitchingID());
		propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_DIRECTION, record.getDirection());
		propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_NODE_TYPE, record.getNodeType());
		propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_OPEN, record.isOpen());
		propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_OPEN_TIME, record.getOpenTime());
		if (record.getRoomID() != null) {
			propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_ROOM_ID, record.getRoomID());
		} else {
			propertiesObject.put(KEY_WEB_ROUTE_NODE_DATA_ATTRIBUTE_ROOM_ID, "null");
		}
		routeDataObject.put(GEOJSON_KEY_PROPERTIES, propertiesObject);
		return routeDataObject;
	}

	public static JSONArray buildGeojsonPointFeature(Point point) throws JSONException {
		JSONArray coordArray = new JSONArray();
		double xy[] = TYCoordProjection.mercatorToLngLat(point.getX(), point.getY());
		coordArray.put(xy[0]);
		coordArray.put(xy[1]);
		return coordArray;
	}

	public static JSONArray buildGeojsonMultiPointFeature(MultiPoint mp) throws JSONException {
		JSONArray coordArray = new JSONArray();
		for (int i = 0; i < mp.getNumGeometries(); ++i) {
			Point point = (Point) mp.getGeometryN(i);
			coordArray.put(buildGeojsonPointFeature(point));
		}
		return coordArray;
	}

	public static JSONArray buildGeojsonLineString(LineString ls) throws JSONException {
		JSONArray coordArray = new JSONArray();
		int pointCount = ls.getNumPoints();
		for (int i = 0; i < pointCount; ++i) {
			Coordinate coord = ls.getCoordinateN(i);
			double xy[] = TYCoordProjection.mercatorToLngLat(coord.x, coord.y);
			JSONArray xyObject = new JSONArray();
			xyObject.put(xy[0]);
			xyObject.put(xy[1]);
			coordArray.put(xyObject);
		}
		return coordArray;
	}

	public static JSONArray buildGeojsonMultiLineString(MultiLineString mls) throws JSONException {
		JSONArray coordArray = new JSONArray();
		for (int i = 0; i < mls.getNumGeometries(); ++i) {
			LineString ls = (LineString) mls.getGeometryN(i);
			coordArray.put(buildGeojsonLineString(ls));
		}
		return coordArray;
	}

	public static JSONArray buildGeojsonPolygonFeature(Polygon polygon) throws JSONException {
		JSONArray coordArray = new JSONArray();
		coordArray.put(buildGeojsonLineString(polygon.getExteriorRing()));
		for (int k = 0; k < polygon.getNumInteriorRing(); ++k) {
			LineString interiorRing = polygon.getInteriorRingN(k);
			coordArray.put(buildGeojsonLineString(interiorRing));
		}
		return coordArray;
	}

	public static JSONArray buildGeojsonMultiPolygonFeature(MultiPolygon mp) throws JSONException {
		JSONArray coordArray = new JSONArray();
		for (int l = 0; l < mp.getNumGeometries(); ++l) {
			Polygon polygon = (Polygon) mp.getGeometryN(l);
			coordArray.put(buildGeojsonPolygonFeature(polygon));
		}
		return coordArray;
	}
}

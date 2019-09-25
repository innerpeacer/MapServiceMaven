package cn.platalk.foundation;

import java.util.List;
import java.util.Map;

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

import cn.platalk.common.TYIGeojsonFeature;
import cn.platalk.map.entity.base.impl.TYLocalPoint;

public class TYGeojsonBuilder {
	public static JSONObject emptyGeojson;
	static {
		emptyGeojson = new JSONObject();
		try {
			emptyGeojson.put("type", "FeatureCollection");
			emptyGeojson.put("features", new JSONArray());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static final String GEOJSON_KEY_FEATURE_TYPE = "type";
	public static final String GEOJSON_KEY_GEOMETRY = "geometry";
	public static final String GEOJSON_KEY_PROPERTIES = "properties";

	public static final String GEOJSON_KEY_FEATURES = "features";

	public static final String GEOJSON_KEY_GEOJSON_TYPE = "type";
	public static final String GEOJSON_VALUE_GEOJSON_TYPE__FEATURECOLLECTION = "FeatureCollection";

	public static final String GEOJSON_VALUE_FEATURE_TYPE__FEATURE = "Feature";

	public static final String GEOJSON_KEY_GEOMETRY_TYPE = "type";
	public static final String GEOJSON_KEY_GEOMETRY_COORDINATES = "coordinates";

	public static final String VALUE_GEOMETRY_TYPE_POINT = "Point";
	public static final String VALUE_GEOMETRY_TYPE_MULTI_POINT = "MultiPoint";

	public static final String VALUE_GEOMETRY_TYPE_LINEARRING = "LinearRing";
	public static final String VALUE_GEOMETRY_TYPE_LINESTRING = "LineString";
	public static final String VALUE_GEOMETRY_TYPE_MULTI_LINESTRING = "MultiLineString";

	public static final String VALUE_GEOMETRY_TYPE_POLYGON = "Polygon";
	public static final String VALUE_GEOMETRY_TYPE_MULTIPOLYGON = "MultiPolygon";

	public static final String GEOJSON_VALUE_GEOMETRY_TYPE__POINT = "Point";
	public static final String GEOJSON_VALUE_GEOMETRY_TYPE__MULTIPOINT = "MultiPoint";
	public static final String GEOJSON_VALUE_GEOMETRY_TYPE__LINESTRING = "LineString";
	public static final String GEOJSON_VALUE_GEOMETRY_TYPE__MULTILINESTRING = "MultiLineString";
	public static final String GEOJSON_VALUE_GEOMETRY_TYPE__POLYGON = "Polygon";
	public static final String GEOJSON_VALUE_GEOMETRY_TYPE__MULTIPOLYGON = "MultiPolygon";
	public static final String GEOJSON_VALUE_GEOMETRY_TYPE__GEOMETRYCOLLECTION = "GeometryCollection";

	public static JSONObject buildFeatureCollection(List<TYIGeojsonFeature> features) {
		if (features == null || features.size() == 0) {
			return emptyGeojson;
		}

		JSONObject result = new JSONObject();
		result.put(GEOJSON_KEY_GEOJSON_TYPE, GEOJSON_VALUE_GEOJSON_TYPE__FEATURECOLLECTION);
		JSONArray featureArray = new JSONArray();
		for (TYIGeojsonFeature feature : features) {
			featureArray.put(feature.toGeojson());
		}
		result.put(GEOJSON_KEY_FEATURES, featureArray);
		return result;
	}

	public static JSONObject buildGeometry(Geometry geometry, Map<String, Object> propMap) {
		JSONObject resultObject = new JSONObject();
		resultObject.put(GEOJSON_KEY_FEATURE_TYPE, GEOJSON_VALUE_FEATURE_TYPE__FEATURE);

		JSONObject geometryObject = new JSONObject();
		if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_POINT)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE, GEOJSON_VALUE_GEOMETRY_TYPE__POINT);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES, buildGeojsonPointFeature((Point) geometry));
		} else if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_MULTI_POINT)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE, GEOJSON_VALUE_GEOMETRY_TYPE__MULTIPOINT);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES, buildGeojsonMultiPointFeature((MultiPoint) geometry));
		} else if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_LINESTRING)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE, GEOJSON_VALUE_GEOMETRY_TYPE__LINESTRING);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES, buildGeojsonLineString((LineString) geometry));
		} else if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_LINEARRING)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE, GEOJSON_VALUE_GEOMETRY_TYPE__LINESTRING);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES, buildGeojsonLineString((LinearRing) geometry));
		} else if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_MULTI_LINESTRING)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE, GEOJSON_VALUE_GEOMETRY_TYPE__MULTILINESTRING);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES,
					buildGeojsonMultiLineString((MultiLineString) geometry));
		} else if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_POLYGON)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE, GEOJSON_VALUE_GEOMETRY_TYPE__POLYGON);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES, buildGeojsonPolygonFeature((Polygon) geometry));
		} else if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_MULTIPOLYGON)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE, GEOJSON_VALUE_GEOMETRY_TYPE__MULTIPOLYGON);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES,
					buildGeojsonMultiPolygonFeature((MultiPolygon) geometry));
		}
		resultObject.put(GEOJSON_KEY_GEOMETRY, geometryObject);

		JSONObject propertiesObject = new JSONObject();
		for (String key : propMap.keySet()) {
			propertiesObject.put(key, propMap.get(key));
		}
		resultObject.put(GEOJSON_KEY_PROPERTIES, propertiesObject);
		return resultObject;
	}

	static JSONArray buildGeojsonPointFeature(Point point) throws JSONException {
		TYLocalPoint lp = new TYLocalPoint(point.getX(), point.getY());
		return lp.toLngLat().toJsonArray();
	}

	static JSONArray buildGeojsonMultiPointFeature(MultiPoint mp) throws JSONException {
		JSONArray coordArray = new JSONArray();
		for (int i = 0; i < mp.getNumGeometries(); ++i) {
			Point point = (Point) mp.getGeometryN(i);
			coordArray.put(buildGeojsonPointFeature(point));
		}
		return coordArray;
	}

	static JSONArray buildGeojsonLineString(LineString ls) throws JSONException {
		JSONArray coordArray = new JSONArray();
		int pointCount = ls.getNumPoints();
		for (int i = 0; i < pointCount; ++i) {
			Coordinate coord = ls.getCoordinateN(i);
			TYLocalPoint lp = new TYLocalPoint(coord.x, coord.y);
			coordArray.put(lp.toLngLat().toJsonArray());
		}
		return coordArray;
	}

	static JSONArray buildGeojsonMultiLineString(MultiLineString mls) throws JSONException {
		JSONArray coordArray = new JSONArray();
		for (int i = 0; i < mls.getNumGeometries(); ++i) {
			LineString ls = (LineString) mls.getGeometryN(i);
			coordArray.put(buildGeojsonLineString(ls));
		}
		return coordArray;
	}

	static JSONArray buildGeojsonPolygonFeature(Polygon polygon) throws JSONException {
		JSONArray coordArray = new JSONArray();
		coordArray.put(buildGeojsonLineString(polygon.getExteriorRing()));

		for (int k = 0; k < polygon.getNumInteriorRing(); ++k) {
			LineString interiorRing = polygon.getInteriorRingN(k);
			coordArray.put(buildGeojsonLineString(interiorRing));
		}
		return coordArray;
	}

	static JSONArray buildGeojsonMultiPolygonFeature(MultiPolygon mp) throws JSONException {
		JSONArray coordArray = new JSONArray();

		for (int l = 0; l < mp.getNumGeometries(); ++l) {
			Polygon polygon = (Polygon) mp.getGeometryN(l);
			coordArray.put(buildGeojsonPolygonFeature(polygon));
		}
		return coordArray;
	}
}

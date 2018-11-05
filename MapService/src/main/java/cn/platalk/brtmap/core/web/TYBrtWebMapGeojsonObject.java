package cn.platalk.brtmap.core.web;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.brtmap.core.utils.TYBrtCoordProjection;
import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

class TYBrtWebMapGeojsonObject {

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

	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_GEO_ID = "GEO_ID";
	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_POI_ID = "POI_ID";
	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_NAME = "NAME";
	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_CATEGORY_ID = "CATEGORY_ID";
	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_SYMBOL_ID = "COLOR";
	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_LEVEL_MIN = "LEVEL_MIN";
	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_LEVEL_MAX = "LEVEL_MAX";
	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_LABEL_X = "LABEL_X";
	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_LABEL_Y = "LABEL_Y";
	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_FLOOR_NUMBER = "floor";

	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_EXTRUSION = "extrusion";
	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_EXTRUSION_HEIGHT = "extrusion-height";
	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_EXTRUSION_BASE = "extrusion-base";
	public static final String KEY_WEB_MAP_DATA_ATTRIBUTE_EXTRUSION_OPACITY = "fill-extrusion-opacity";

	public static JSONObject buildMapDataRecord(TYMapDataFeatureRecord record)
			throws JSONException {
		// System.out.println("buildMapDataRecord: " + record.poiID);
		JSONObject mapDataObject = new JSONObject();
		mapDataObject.put(GEOJSON_KEY_FEATURE_TYPE,
				GEOJSON_VALUE_FEATURE_TYPE__FEATURE);

		// ====== geometry object =====================
		JSONObject geometryObject = new JSONObject();

		Geometry geometry = record.getGeometryData();
		if (geometry == null) {
			return null;
		}

		if (geometry.getGeometryType().equals(VALUE_GEOMETRY_TYPE_POINT)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE,
					GEOJSON_VALUE_GEOMETRY_TYPE__POINT);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES,
					buildGeojsonPointFeature((Point) geometry));
		} else if (geometry.getGeometryType().equals(
				VALUE_GEOMETRY_TYPE_MULTI_POINT)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE,
					GEOJSON_VALUE_GEOMETRY_TYPE__MULTIPOINT);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES,
					buildGeojsonMultiPointFeature((MultiPoint) geometry));
		} else if (geometry.getGeometryType().equals(
				VALUE_GEOMETRY_TYPE_LINESTRING)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE,
					GEOJSON_VALUE_GEOMETRY_TYPE__LINESTRING);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES,
					buildGeojsonLineString((LineString) geometry));
		} else if (geometry.getGeometryType().equals(
				VALUE_GEOMETRY_TYPE_LINEARRING)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE,
					GEOJSON_VALUE_GEOMETRY_TYPE__LINESTRING);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES,
					buildGeojsonLineString((LinearRing) geometry));
		} else if (geometry.getGeometryType().equals(
				VALUE_GEOMETRY_TYPE_MULTI_LINESTRING)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE,
					GEOJSON_VALUE_GEOMETRY_TYPE__MULTILINESTRING);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES,
					buildGeojsonMultiLineString((MultiLineString) geometry));
		} else if (geometry.getGeometryType().equals(
				VALUE_GEOMETRY_TYPE_POLYGON)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE,
					GEOJSON_VALUE_GEOMETRY_TYPE__POLYGON);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES,
					buildGeojsonPolygonFeature((Polygon) geometry));
		} else if (geometry.getGeometryType().equals(
				VALUE_GEOMETRY_TYPE_MULTIPOLYGON)) {
			geometryObject.put(GEOJSON_KEY_GEOMETRY_TYPE,
					GEOJSON_VALUE_GEOMETRY_TYPE__MULTIPOLYGON);
			geometryObject.put(GEOJSON_KEY_GEOMETRY_COORDINATES,
					buildGeojsonMultiPolygonFeature((MultiPolygon) geometry));
		}
		mapDataObject.put(GEOJSON_KEY_GEOMETRY, geometryObject);

		// ====== properties object =====================
		JSONObject propertiesObject = new JSONObject();
		propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_GEO_ID, record.geoID);
		propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_POI_ID, record.poiID);
		if (record.name != null) {
			propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_NAME, record.name);
		} else {
			propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_NAME, "null");
		}
		propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_CATEGORY_ID,
				record.categoryID);
		propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_SYMBOL_ID,
				record.symbolID);
		propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_LEVEL_MIN,
				record.levelMin);
		propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_LEVEL_MAX,
				record.levelMax);

		propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_LABEL_X, record.labelX);
		propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_LABEL_Y, record.labelY);

		propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_FLOOR_NUMBER,
				record.floorNumber);
		propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_EXTRUSION,
				record.extrusion);
		propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_EXTRUSION_HEIGHT,
				record.extrusionHeight);
		propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_EXTRUSION_BASE,
				record.extrusionBase);
		// propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_EXTRUSION_OPACITY,
		// record.extrusionOpacity);

		mapDataObject.put(GEOJSON_KEY_PROPERTIES, propertiesObject);
		return mapDataObject;
	}

	public static JSONArray buildGeojsonPointFeature(Point point)
			throws JSONException {
		JSONArray coordArray = new JSONArray();
		double xy[] = TYBrtCoordProjection.mercatorToLonLat(point.getX(),
				point.getY());
		coordArray.put(xy[0]);
		coordArray.put(xy[1]);
		return coordArray;
	}

	public static JSONArray buildGeojsonMultiPointFeature(MultiPoint mp)
			throws JSONException {
		JSONArray coordArray = new JSONArray();
		for (int i = 0; i < mp.getNumGeometries(); ++i) {
			Point point = (Point) mp.getGeometryN(i);
			coordArray.put(buildGeojsonPointFeature(point));
		}
		return coordArray;
	}

	public static JSONArray buildGeojsonLineString(LineString ls)
			throws JSONException {
		JSONArray coordArray = new JSONArray();
		int pointCount = ls.getNumPoints();
		for (int i = 0; i < pointCount; ++i) {
			Coordinate coord = ls.getCoordinateN(i);
			double xy[] = TYBrtCoordProjection.mercatorToLonLat(coord.x,
					coord.y);
			JSONArray xyObject = new JSONArray();
			xyObject.put(xy[0]);
			xyObject.put(xy[1]);
			coordArray.put(xyObject);
		}
		return coordArray;
	}

	public static JSONArray buildGeojsonMultiLineString(MultiLineString mls)
			throws JSONException {
		JSONArray coordArray = new JSONArray();
		for (int i = 0; i < mls.getNumGeometries(); ++i) {
			LineString ls = (LineString) mls.getGeometryN(i);
			coordArray.put(buildGeojsonLineString(ls));
		}
		return coordArray;
	}

	public static JSONArray buildGeojsonPolygonFeature(Polygon polygon)
			throws JSONException {
		JSONArray coordArray = new JSONArray();
		coordArray.put(buildGeojsonLineString(polygon.getExteriorRing()));

		for (int k = 0; k < polygon.getNumInteriorRing(); ++k) {
			LineString interiorRing = polygon.getInteriorRingN(k);
			coordArray.put(buildGeojsonLineString(interiorRing));
		}
		return coordArray;
	}

	public static JSONArray buildGeojsonMultiPolygonFeature(MultiPolygon mp)
			throws JSONException {
		JSONArray coordArray = new JSONArray();

		for (int l = 0; l < mp.getNumGeometries(); ++l) {
			Polygon polygon = (Polygon) mp.getGeometryN(l);
			coordArray.put(buildGeojsonPolygonFeature(polygon));
		}
		return coordArray;
	}
}

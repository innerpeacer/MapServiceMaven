package cn.platalk.map.api.three.map;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

import cn.platalk.foundation.TYGdalFields;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;

public class TYThreeShapeBuilder {
	public static JSONObject emptyShape;

	public static final String SHAPE_KEY_GEOMETRY = "geometry";
	public static final String SHAPE_KEY_GEOMETRY_TYPE = "type";

	public static final String SHAPE_KEY_PROPERTIES = "properties";
	public static final String SHAPE_KEY_COORDINATES = "coordinates";

	public static final String SHAPE_KEY_POLYGON_RING = "ring";
	public static final String SHAPE_KEY_POLYGON_HOLES = "holes";

	public static final String SHAPE_KEY_COORDINATE_X = "x";
	public static final String SHAPE_KEY_COORDINATE_Y = "y";

	static {
		emptyShape = new JSONObject();
		emptyShape.put(SHAPE_KEY_GEOMETRY, new JSONArray());
		emptyShape.put(SHAPE_KEY_PROPERTIES, new JSONObject());
	}

	public static JSONObject buildShape(TYBuilding building, TYMapDataFeatureRecord record) {
		JSONObject json = new JSONObject();
		json.put(SHAPE_KEY_GEOMETRY, buildGeomtery(building, record.getGeometryData()));

		Map<String, Object> propMap = new HashMap<String, Object>();
		propMap.put("floor", record.getFloorNumber());
		propMap.put("layer", record.getLayer());
		propMap.put("base", record.getExtrusionBase());
		propMap.put("height", record.getExtrusionHeight());
		propMap.put("symbolID", record.getSymbolID());
		propMap.put("poiID", record.getPoiID());

		json.put(SHAPE_KEY_PROPERTIES, buildPropertyMap(propMap));
		return json;
	}

	static JSONObject buildPropertyMap(Map<String, Object> propMap) {
		JSONObject propertiesObject = new JSONObject();
		for (String key : propMap.keySet()) {
			propertiesObject.put(key, propMap.get(key));
		}
		return propertiesObject;
	}

	static JSONObject buildGeomtery(TYBuilding building, Geometry geometry) {
		JSONObject geometryObject = new JSONObject();
		if (geometry.getGeometryType().equals(TYGdalFields.VALUE_GEOMETRY_TYPE_POINT)) {
		} else if (geometry.getGeometryType().equals(TYGdalFields.VALUE_GEOMETRY_TYPE_MULTI_POINT)) {
		} else if (geometry.getGeometryType().equals(TYGdalFields.VALUE_GEOMETRY_TYPE_LINESTRING)) {
		} else if (geometry.getGeometryType().equals(TYGdalFields.VALUE_GEOMETRY_TYPE_LINEARRING)) {
		} else if (geometry.getGeometryType().equals(TYGdalFields.VALUE_GEOMETRY_TYPE_MULTI_LINESTRING)) {
		} else if (geometry.getGeometryType().equals(TYGdalFields.VALUE_GEOMETRY_TYPE_POLYGON)) {
			geometryObject.put(SHAPE_KEY_COORDINATES, buildPolygonShape(building, (Polygon) geometry));
			geometryObject.put(SHAPE_KEY_GEOMETRY_TYPE, "Polygon");
		} else if (geometry.getGeometryType().equals(TYGdalFields.VALUE_GEOMETRY_TYPE_MULTIPOLYGON)) {
			geometryObject.put(SHAPE_KEY_COORDINATES, buildMultiPolygonShape(building, (MultiPolygon) geometry));
			geometryObject.put(SHAPE_KEY_GEOMETRY_TYPE, "MultiPolygon");
		}
		return geometryObject;
	}

	static JSONObject coordinateToJson(TYBuilding building, Coordinate c) {
		JSONObject json = new JSONObject();
		json.put(SHAPE_KEY_COORDINATE_X, c.x - building.getBuildingExtent().getXmin());
		json.put(SHAPE_KEY_COORDINATE_Y, c.y - building.getBuildingExtent().getYmin());
		return json;
	}

	static JSONArray buildLineStringShape(TYBuilding building, LineString line) {
		JSONArray coordArray = new JSONArray();
		int pointCount = line.getNumPoints();
		for (int i = 0; i < pointCount; ++i) {
			coordArray.put(coordinateToJson(building, line.getCoordinateN(i)));
		}
		return coordArray;
	}

	static JSONObject buildPolygonShape(TYBuilding building, Polygon polygon) {
		JSONObject jsonObject = new JSONObject();
		{
			JSONArray exteriorArray = buildLineStringShape(building, polygon.getExteriorRing());
			jsonObject.put(SHAPE_KEY_POLYGON_RING, exteriorArray);
		}

		if (polygon.getNumInteriorRing() > 0) {
			JSONArray holesArray = new JSONArray();
			for (int k = 0; k < polygon.getNumInteriorRing(); ++k) {
				JSONArray interiorArray = buildLineStringShape(building, polygon.getInteriorRingN(k));
				holesArray.put(interiorArray);
			}
			jsonObject.put(SHAPE_KEY_POLYGON_HOLES, holesArray);
		}
		return jsonObject;
	}

	static JSONArray buildMultiPolygonShape(TYBuilding building, MultiPolygon mp) {
		JSONArray coordArray = new JSONArray();

		for (int l = 0; l < mp.getNumGeometries(); ++l) {
			Polygon polygon = (Polygon) mp.getGeometryN(l);
			JSONObject polygonObject = buildPolygonShape(building, polygon);
			coordArray.put(polygonObject);
		}
		return coordArray;
	}
}

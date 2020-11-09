package cn.platalk.map.api.three.map;

import java.util.HashMap;
import java.util.Map;

import cn.platalk.map.entity.base.impl.map.TYBuilding;
import cn.platalk.map.entity.base.impl.map.TYMapDataFeatureRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

import cn.platalk.foundation.TYGdalFields;

public class TYThreeShapeBuilder {
	public static final JSONObject emptyShape;

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

	private double centerX;
	private double centerY;

	public TYThreeShapeBuilder(TYBuilding building) {
		centerX = building.getCenterX();
		centerY = building.getCenterY();
		if (centerX == 0 && centerY == 0) {
			centerX = (building.getBuildingExtent().getXmin() + building.getBuildingExtent().getXmax()) * 0.5;
			centerY = (building.getBuildingExtent().getYmin() + building.getBuildingExtent().getYmax()) * 0.5;
		}
	}

	public JSONObject buildShape(TYMapDataFeatureRecord record) {
		JSONObject json = new JSONObject();
		json.put(SHAPE_KEY_GEOMETRY, buildGeometry(record.getGeometryData()));

		Map<String, Object> propMap = new HashMap<>();
		propMap.put("floor", record.getFloorNumber());
		propMap.put("layer", record.getLayer());
		propMap.put("base", record.getExtrusionBase());
		propMap.put("height", record.getExtrusionHeight());
		propMap.put("symbolID", record.getSymbolID());
		propMap.put("poiID", record.getPoiID());

		json.put(SHAPE_KEY_PROPERTIES, buildPropertyMap(propMap));
		return json;
	}

	JSONObject buildPropertyMap(Map<String, Object> propMap) {
		JSONObject propertiesObject = new JSONObject();
		for (String key : propMap.keySet()) {
			propertiesObject.put(key, propMap.get(key));
		}
		return propertiesObject;
	}

	JSONObject buildGeometry(Geometry geometry) {
		JSONObject geometryObject = new JSONObject();
		switch (geometry.getGeometryType()) {
			case TYGdalFields.VALUE_GEOMETRY_TYPE_POINT:
			case TYGdalFields.VALUE_GEOMETRY_TYPE_MULTI_LINESTRING:
			case TYGdalFields.VALUE_GEOMETRY_TYPE_LINEARRING:
			case TYGdalFields.VALUE_GEOMETRY_TYPE_LINESTRING:
			case TYGdalFields.VALUE_GEOMETRY_TYPE_MULTI_POINT:
				break;
			case TYGdalFields.VALUE_GEOMETRY_TYPE_POLYGON:
				geometryObject.put(SHAPE_KEY_COORDINATES, buildPolygonShape((Polygon) geometry));
				geometryObject.put(SHAPE_KEY_GEOMETRY_TYPE, "Polygon");
				break;
			case TYGdalFields.VALUE_GEOMETRY_TYPE_MULTIPOLYGON:
				geometryObject.put(SHAPE_KEY_COORDINATES, buildMultiPolygonShape((MultiPolygon) geometry));
				geometryObject.put(SHAPE_KEY_GEOMETRY_TYPE, "MultiPolygon");
				break;
		}
		return geometryObject;
	}

	JSONObject coordinateToJson(Coordinate c) {
		JSONObject json = new JSONObject();
		json.put(SHAPE_KEY_COORDINATE_X, c.x - centerX);
		json.put(SHAPE_KEY_COORDINATE_Y, c.y - centerY);
		return json;
	}

	JSONArray buildLineStringShape(LineString line) {
		JSONArray coordArray = new JSONArray();
		int pointCount = line.getNumPoints();
		for (int i = 0; i < pointCount; ++i) {
			coordArray.put(coordinateToJson(line.getCoordinateN(i)));
		}
		return coordArray;
	}

	JSONObject buildPolygonShape(Polygon polygon) {
		JSONObject jsonObject = new JSONObject();
		{
			JSONArray exteriorArray = buildLineStringShape(polygon.getExteriorRing());
			jsonObject.put(SHAPE_KEY_POLYGON_RING, exteriorArray);
		}

		if (polygon.getNumInteriorRing() > 0) {
			JSONArray holesArray = new JSONArray();
			for (int k = 0; k < polygon.getNumInteriorRing(); ++k) {
				JSONArray interiorArray = buildLineStringShape(polygon.getInteriorRingN(k));
				holesArray.put(interiorArray);
			}
			jsonObject.put(SHAPE_KEY_POLYGON_HOLES, holesArray);
		}
		return jsonObject;
	}

	JSONArray buildMultiPolygonShape(MultiPolygon mp) {
		JSONArray coordArray = new JSONArray();

		for (int l = 0; l < mp.getNumGeometries(); ++l) {
			Polygon polygon = (Polygon) mp.getGeometryN(l);
			JSONObject polygonObject = buildPolygonShape(polygon);
			coordArray.put(polygonObject);
		}
		return coordArray;
	}
}

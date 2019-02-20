package cn.platalk.brtmap.core.web;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.vividsolutions.jts.geom.Geometry;

import cn.platalk.brtmap.core.web.util.TYBrtGeojsonBuilder;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;

class TYBrtWebMapGeojsonObject {

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

	public static JSONObject buildMapDataRecord(TYMapDataFeatureRecord record) throws JSONException {
		// System.out.println("buildMapDataRecord: " + record.poiID);
		// ====== geometry object =====================
		Geometry geometry = record.getGeometryData();
		if (geometry == null) {
			return null;
		}

		// ====== properties object =====================
		Map<String, Object> propMap = new HashMap<String, Object>();
		propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_GEO_ID, record.geoID);
		propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_POI_ID, record.poiID);
		if (record.name != null) {
			propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_NAME, record.name);
		} else {
			propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_NAME, "null");
		}
		propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_CATEGORY_ID, record.categoryID);
		propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_SYMBOL_ID, record.symbolID);
		propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_LEVEL_MIN, record.levelMin);
		propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_LEVEL_MAX, record.levelMax);

		propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_LABEL_X, record.labelX);
		propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_LABEL_Y, record.labelY);

		propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_FLOOR_NUMBER, record.floorNumber);
		propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_EXTRUSION, record.extrusion);
		propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_EXTRUSION_HEIGHT, record.extrusionHeight);
		propMap.put(KEY_WEB_MAP_DATA_ATTRIBUTE_EXTRUSION_BASE, record.extrusionBase);
		// propertiesObject.put(KEY_WEB_MAP_DATA_ATTRIBUTE_EXTRUSION_OPACITY,
		// record.extrusionOpacity);

		return TYBrtGeojsonBuilder.buildGeometry(geometry, propMap);
	}
}

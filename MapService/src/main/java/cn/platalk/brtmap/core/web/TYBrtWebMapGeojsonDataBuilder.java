package cn.platalk.brtmap.core.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.brtmap.core.web.util.TYBrtGeojsonBuilder;
import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;

public class TYBrtWebMapGeojsonDataBuilder {
	public static final String KEY_BOX_WEB_MAP_DATA_FLOOR = "floor";
	public static final String KEY_BOX_WEB_MAP_DATA_ROOM = "room";
	public static final String KEY_BOX_WEB_MAP_DATA_ASSET = "asset";
	public static final String KEY_BOX_WEB_MAP_DATA_FACILITY = "facility";
	public static final String KEY_BOX_WEB_MAP_DATA_LABEL = "label";
	public static final String KEY_BOX_WEB_MAP_DATA_EXTRUSION = "extrusion";

	public static JSONObject generateMapDataObject(List<TYMapDataFeatureRecord> dataList,
			List<TYFillSymbolRecord> fillSymbolList, List<TYIconSymbolRecord> iconSymbolList) throws JSONException {
		Map<Integer, TYFillSymbolRecord> fillSymbolMap = new HashMap<Integer, TYFillSymbolRecord>();
		for (TYFillSymbolRecord symbol : fillSymbolList) {
			fillSymbolMap.put(symbol.symbolID, symbol);
		}

		Map<Integer, TYIconSymbolRecord> iconSymbolMap = new HashMap<Integer, TYIconSymbolRecord>();
		for (TYIconSymbolRecord symbol : iconSymbolList) {
			iconSymbolMap.put(symbol.symbolID, symbol);
		}

		List<TYMapDataFeatureRecord> floorList = new ArrayList<TYMapDataFeatureRecord>();
		List<TYMapDataFeatureRecord> roomList = new ArrayList<TYMapDataFeatureRecord>();
		List<TYMapDataFeatureRecord> assetList = new ArrayList<TYMapDataFeatureRecord>();
		List<TYMapDataFeatureRecord> facilityList = new ArrayList<TYMapDataFeatureRecord>();
		List<TYMapDataFeatureRecord> labelList = new ArrayList<TYMapDataFeatureRecord>();
		// List<TYMapDataFeatureRecord> extrusionList = new
		// ArrayList<TYMapDataFeatureRecord>();

		for (TYMapDataFeatureRecord record : dataList) {
			// System.out.println(record.name);
			if (record.layer == TYMapDataFeatureRecord.LAYER_FLOOR) {
				floorList.add(record);
				// if (record.extrusion) {
				// extrusionList.add(record);
				// }
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_ROOM) {
				roomList.add(record);
				// if (record.extrusion) {
				// extrusionList.add(record);
				// }
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_ASSET) {
				assetList.add(record);
				// if (record.extrusion) {
				// extrusionList.add(record);
				// }
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_FACILITY) {
				facilityList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_LABEL) {
				labelList.add(record);
			}
		}

		JSONObject jsonObject = new JSONObject();
		if (floorList.size() != 0) {
			JSONObject floorObject = new JSONObject();
			floorObject.put(TYBrtGeojsonBuilder.GEOJSON_KEY_GEOJSON_TYPE,
					TYBrtGeojsonBuilder.GEOJSON_VALUE_GEOJSON_TYPE__FEATURECOLLECTION);
			JSONArray floorFeatures = new JSONArray();
			for (TYMapDataFeatureRecord record : floorList) {
				JSONObject feature = processFillFeatureRecord(record, fillSymbolMap.get(record.symbolID));
				floorFeatures.put(feature);
			}
			floorObject.put(TYBrtGeojsonBuilder.GEOJSON_KEY_FEATURES, floorFeatures);
			jsonObject.put(KEY_BOX_WEB_MAP_DATA_FLOOR, floorObject);
		} else {
			jsonObject.put(KEY_BOX_WEB_MAP_DATA_FLOOR, TYBrtGeojsonBuilder.emptyGeojson);
		}

		if (roomList.size() != 0) {
			JSONObject roomObject = new JSONObject();
			roomObject.put(TYBrtGeojsonBuilder.GEOJSON_KEY_GEOJSON_TYPE,
					TYBrtGeojsonBuilder.GEOJSON_VALUE_GEOJSON_TYPE__FEATURECOLLECTION);
			JSONArray roomFeatures = new JSONArray();
			for (TYMapDataFeatureRecord record : roomList) {
				JSONObject feature = processFillFeatureRecord(record, fillSymbolMap.get(record.symbolID));
				roomFeatures.put(feature);
			}
			roomObject.put(TYBrtGeojsonBuilder.GEOJSON_KEY_FEATURES, roomFeatures);
			jsonObject.put(KEY_BOX_WEB_MAP_DATA_ROOM, roomObject);
		} else {
			jsonObject.put(KEY_BOX_WEB_MAP_DATA_ROOM, TYBrtGeojsonBuilder.emptyGeojson);
		}

		if (assetList.size() != 0) {
			JSONObject assetObject = new JSONObject();
			assetObject.put(TYBrtGeojsonBuilder.GEOJSON_KEY_GEOJSON_TYPE,
					TYBrtGeojsonBuilder.GEOJSON_VALUE_GEOJSON_TYPE__FEATURECOLLECTION);
			JSONArray assetFeatures = new JSONArray();
			for (TYMapDataFeatureRecord record : assetList) {
				JSONObject feature = processFillFeatureRecord(record, fillSymbolMap.get(record.symbolID));
				assetFeatures.put(feature);
			}
			assetObject.put(TYBrtGeojsonBuilder.GEOJSON_KEY_FEATURES, assetFeatures);
			jsonObject.put(KEY_BOX_WEB_MAP_DATA_ASSET, assetObject);
		} else {
			jsonObject.put(KEY_BOX_WEB_MAP_DATA_ASSET, TYBrtGeojsonBuilder.emptyGeojson);
		}

		if (facilityList.size() != 0) {
			JSONObject facilityObject = new JSONObject();
			facilityObject.put(TYBrtGeojsonBuilder.GEOJSON_KEY_GEOJSON_TYPE,
					TYBrtGeojsonBuilder.GEOJSON_VALUE_GEOJSON_TYPE__FEATURECOLLECTION);
			JSONArray facilityFeatures = new JSONArray();
			for (TYMapDataFeatureRecord record : facilityList) {
				JSONObject feature = processIconFeatureRecord(record, iconSymbolMap.get(record.symbolID));
				if (feature != null) {
					facilityFeatures.put(feature);
				}
			}
			facilityObject.put(TYBrtGeojsonBuilder.GEOJSON_KEY_FEATURES, facilityFeatures);
			jsonObject.put(KEY_BOX_WEB_MAP_DATA_FACILITY, facilityObject);
		} else {
			jsonObject.put(KEY_BOX_WEB_MAP_DATA_FACILITY, TYBrtGeojsonBuilder.emptyGeojson);
		}

		if (labelList.size() != 0) {
			JSONObject labelObject = new JSONObject();
			labelObject.put(TYBrtGeojsonBuilder.GEOJSON_KEY_GEOJSON_TYPE,
					TYBrtGeojsonBuilder.GEOJSON_VALUE_GEOJSON_TYPE__FEATURECOLLECTION);
			JSONArray labelFeatures = new JSONArray();
			for (TYMapDataFeatureRecord record : labelList) {
				JSONObject feature = TYBrtWebMapGeojsonObject.buildMapDataRecord(record);
				if (feature != null) {
					labelFeatures.put(feature);
				}
			}
			labelObject.put(TYBrtGeojsonBuilder.GEOJSON_KEY_FEATURES, labelFeatures);
			jsonObject.put(KEY_BOX_WEB_MAP_DATA_LABEL, labelObject);
		} else {
			jsonObject.put(KEY_BOX_WEB_MAP_DATA_LABEL, TYBrtGeojsonBuilder.emptyGeojson);
		}
		return jsonObject;
	}

	private static JSONObject processFillFeatureRecord(TYMapDataFeatureRecord record, TYFillSymbolRecord fillRecord)
			throws JSONException {
		JSONObject featureObject = TYBrtWebMapGeojsonObject.buildMapDataRecord(record);
		if (featureObject == null) {
			return null;
		}
		JSONObject propertiesObject = featureObject.getJSONObject("properties");
		if (fillRecord != null) {
			String _fillColor = String.format("#%s", fillRecord.fillColor.substring(3, 9));
			float _opacity = Integer.parseInt(fillRecord.fillColor.substring(1, 3), 16) / 255.0f;
			propertiesObject.put("fill-color", _fillColor);
			propertiesObject.put("fill-opacity", _opacity);

			String _outlineColor = String.format("#%s", fillRecord.outlineColor.substring(3, 9));
			float _outlineWidth = (float) fillRecord.lineWidth;
			propertiesObject.put("outline-color", _outlineColor);
			propertiesObject.put("outline-width", _outlineWidth);
		}
		return featureObject;
	}

	private static JSONObject processIconFeatureRecord(TYMapDataFeatureRecord record, TYIconSymbolRecord iconRecord)
			throws JSONException {
		JSONObject featureObject = TYBrtWebMapGeojsonObject.buildMapDataRecord(record);
		if (featureObject == null) {
			return null;
		}

		JSONObject propertiesObject = featureObject.getJSONObject("properties");
		if (iconRecord != null) {
			String iconName = iconRecord.icon;
			propertiesObject.put("image-normal", String.format("%s_normal", iconName));
			propertiesObject.put("image-highlighted", String.format("%s_highlighted", iconName));
		}
		return featureObject;
	}
}

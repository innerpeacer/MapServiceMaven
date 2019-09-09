package cn.platalk.foundation;

import java.util.List;

import org.json.JSONArray;

import cn.platalk.common.TYIJsonFeature;

public class TYJsonBuilder {

	public static JSONArray buildJsonArray(List<TYIJsonFeature> features) {
		JSONArray jsonArray = new JSONArray();
		for (TYIJsonFeature feature : features) {
			jsonArray.put(feature.toJson());
		}
		return jsonArray;
	}

	public static JSONArray buildJsonArray(TYIJsonFeature feature) {
		JSONArray jsonArray = new JSONArray();
		if (feature != null) {
			jsonArray.put(feature.toJson());
		}
		return jsonArray;
	}
}

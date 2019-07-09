package cn.platalk.map.core.web.beacon;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.platalk.map.entity.base.impl.TYLocatingBeacon;
import cn.platalk.utils.geojson.TYGeojsonBuilder;

public class TYWebBeaconGeojsonDataBuilder {

	public static JSONObject generateBeaconDataObject(List<TYLocatingBeacon> beaconList) {
		JSONObject resultObject = new JSONObject();
		resultObject.put(TYGeojsonBuilder.GEOJSON_KEY_GEOJSON_TYPE,
				TYGeojsonBuilder.GEOJSON_VALUE_GEOJSON_TYPE__FEATURECOLLECTION);
		JSONArray beaconFeatures = new JSONArray();
		for (TYLocatingBeacon beacon : beaconList) {
			JSONObject feature = TYWebBeaconGeojsonObject.buildLocatingBeacon(beacon);
			beaconFeatures.put(feature);
		}
		resultObject.put(TYGeojsonBuilder.GEOJSON_KEY_FEATURES, beaconFeatures);
		return resultObject;
	}
}

package cn.platalk.brtmap.core.web.beacon;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.platalk.brtmap.core.web.util.TYBrtGeojsonBuilder;
import cn.platalk.map.entity.base.impl.TYLocatingBeacon;

public class TYBrtWebBeaconGeojsonDataBuilder {

	public static JSONObject generateBeaconDataObject(List<TYLocatingBeacon> beaconList) {
		JSONObject resultObject = new JSONObject();
		resultObject.put(TYBrtGeojsonBuilder.GEOJSON_KEY_GEOJSON_TYPE,
				TYBrtGeojsonBuilder.GEOJSON_VALUE_GEOJSON_TYPE__FEATURECOLLECTION);
		JSONArray beaconFeatures = new JSONArray();
		for (TYLocatingBeacon beacon : beaconList) {
			JSONObject feature = TYBrtWebBeaconGeojsonObject.buildLocatingBeacon(beacon);
			beaconFeatures.put(feature);
		}
		resultObject.put(TYBrtGeojsonBuilder.GEOJSON_KEY_FEATURES, beaconFeatures);
		return resultObject;
	}
}

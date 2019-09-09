package cn.platalk.map.core.lab.blesample;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.platalk.lab.blesample.entity.WTBleSample;

public class WTBleSampleObjectBuilder {

	public static JSONObject generateSamplePointJson(WTBleSample sample) {
		JSONObject json = new JSONObject();
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_1_ID, sample.getSampleID());
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_2_BUILDING_ID, sample.getBuildingID());
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_3_X, sample.getLocation().getX());
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_4_Y, sample.getLocation().getY());
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_5_FLOOR, sample.getLocation().getFloor());
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_6_TIMESTAMP, sample.getTimestamp());
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_7_PLATFORM, sample.getPlatform().getValue());
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_8_USER, sample.getUser());
		return json;
	}

	public static JSONArray generateSamplePointList(List<WTBleSample> sampleList) {
		JSONArray array = new JSONArray();
		for (int i = 0; i < sampleList.size(); ++i) {
			array.put(generateSamplePointJson(sampleList.get(i)));
		}
		return array;
	}

	public static JSONObject generateSampleJson(WTBleSample sample) {
		JSONObject json = new JSONObject();
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_1_ID, sample.getSampleID());
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_2_BUILDING_ID, sample.getBuildingID());
		JSONObject locationObject = new JSONObject();
		locationObject.put(WTBleSampleFields.KEY_LAB_SAMPLE_3_X, sample.getLocation().getX());
		locationObject.put(WTBleSampleFields.KEY_LAB_SAMPLE_4_Y, sample.getLocation().getY());
		locationObject.put(WTBleSampleFields.KEY_LAB_SAMPLE_5_FLOOR, sample.getLocation().getFloor());
		json.put("location", locationObject);
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_6_TIMESTAMP, sample.getTimestamp());
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_7_PLATFORM, sample.getPlatform().getValue());
		json.put(WTBleSampleFields.KEY_LAB_SAMPLE_8_USER, sample.getUser());

		json.put("gps", sample.getGpsList().size());
		json.put("ble", sample.getBleList().size());
		return json;
	}
}

package cn.platalk.map.api.three.map;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;

public class TYThreeMapDataBuilder {
	private TYThreeShapeBuilder shapeBuilder;

	public static final String KEY_THREE_MAP_DATA_FLOOR = "floor";
	public static final String KEY_THREE_MAP_DATA_ROOM = "room";
	public static final String KEY_THREE_MAP_DATA_ASSET = "asset";
	// public static final String KEY_THREE_MAP_DATA_FACILITY = "facility";
	// public static final String KEY_THREE_MAP_DATA_LABEL = "label";

	public TYThreeMapDataBuilder(TYBuilding building) {
		this.shapeBuilder = new TYThreeShapeBuilder(building);
	}

	public JSONObject generateThreeMapDataObject(List<TYMapDataFeatureRecord> dataList) {
		List<TYMapDataFeatureRecord> floorList = new ArrayList<TYMapDataFeatureRecord>();
		List<TYMapDataFeatureRecord> roomList = new ArrayList<TYMapDataFeatureRecord>();
		List<TYMapDataFeatureRecord> assetList = new ArrayList<TYMapDataFeatureRecord>();
		// List<TYMapDataFeatureRecord> facilityList = new
		// ArrayList<TYMapDataFeatureRecord>();
		// List<TYMapDataFeatureRecord> labelList = new
		// ArrayList<TYMapDataFeatureRecord>();

		for (TYMapDataFeatureRecord record : dataList) {
			// System.out.println(record.name);
			if (record.layer == TYMapDataFeatureRecord.LAYER_FLOOR) {
				floorList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_ROOM) {
				roomList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_ASSET) {
				assetList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_FACILITY) {
				// facilityList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_LABEL) {
				// labelList.add(record);
			}
		}

		JSONObject jsonObject = new JSONObject();
		if (floorList.size() != 0) {
			JSONArray floorShapes = new JSONArray();
			for (TYMapDataFeatureRecord record : floorList) {
				floorShapes.put(shapeBuilder.buildShape(record));
			}
			jsonObject.put(KEY_THREE_MAP_DATA_FLOOR, floorShapes);
		} else {
			jsonObject.put(KEY_THREE_MAP_DATA_FLOOR, new JSONArray());
		}

		if (roomList.size() != 0) {
			JSONArray roomShapes = new JSONArray();
			for (TYMapDataFeatureRecord record : roomList) {
				roomShapes.put(shapeBuilder.buildShape(record));
			}
			jsonObject.put(KEY_THREE_MAP_DATA_ROOM, roomShapes);
		} else {
			jsonObject.put(KEY_THREE_MAP_DATA_ROOM, new JSONArray());
		}

		if (assetList.size() != 0) {
			JSONArray assetShapes = new JSONArray();
			for (TYMapDataFeatureRecord record : assetList) {
				assetShapes.put(shapeBuilder.buildShape(record));
			}
			jsonObject.put(KEY_THREE_MAP_DATA_ASSET, assetShapes);
		} else {
			jsonObject.put(KEY_THREE_MAP_DATA_ASSET, new JSONArray());
		}
		return jsonObject;
	}
}

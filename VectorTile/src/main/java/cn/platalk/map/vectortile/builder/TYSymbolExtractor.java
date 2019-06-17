package cn.platalk.map.vectortile.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.platalk.map.entity.base.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.TYIIconSymbolRecord;
import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;

public class TYSymbolExtractor {
	public static JSONObject extractSymbolJson(List<TYIMapDataFeatureRecord> mapDataRecords,
			List<TYIFillSymbolRecord> fillSymbols, List<TYIIconSymbolRecord> iconSymbols) {
		JSONObject json = new JSONObject();

		Set<Integer> floorSet = new HashSet<Integer>();
		Set<Integer> roomSet = new HashSet<Integer>();
		Set<Integer> assetSet = new HashSet<Integer>();
		Set<Integer> facilitySet = new HashSet<Integer>();
		Set<Integer> labelSet = new HashSet<Integer>();
		Set<Integer> extrusionSet = new HashSet<Integer>();

		for (TYIMapDataFeatureRecord record : mapDataRecords) {
			switch (record.getLayer()) {
			case TYIMapDataFeatureRecord.LAYER_FLOOR:
				floorSet.add(record.getSymbolID());
				if (record.isExtrusion()) {
					extrusionSet.add(record.getSymbolID());
				}
				break;

			case TYIMapDataFeatureRecord.LAYER_ROOM:
				roomSet.add(record.getSymbolID());
				if (record.isExtrusion()) {
					extrusionSet.add(record.getSymbolID());
				}
				break;

			case TYIMapDataFeatureRecord.LAYER_ASSET:
				assetSet.add(record.getSymbolID());
				if (record.isExtrusion()) {
					extrusionSet.add(record.getSymbolID());
				}
				break;

			case TYIMapDataFeatureRecord.LAYER_FACILITY:
				facilitySet.add(record.getSymbolID());
				break;

			case TYIMapDataFeatureRecord.LAYER_LABEL:
				labelSet.add(record.getSymbolID());
				break;
			default:
				break;
			}
		}

		List<Integer> floorList = new ArrayList<Integer>(floorSet);
		Collections.sort(floorList);
		List<Integer> roomList = new ArrayList<Integer>(roomSet);
		Collections.sort(roomList);
		List<Integer> assetList = new ArrayList<Integer>(assetSet);
		Collections.sort(assetList);
		List<Integer> facilityList = new ArrayList<Integer>(facilitySet);
		Collections.sort(facilityList);
		List<Integer> labelList = new ArrayList<Integer>(labelSet);
		Collections.sort(labelList);
		List<Integer> extrusionList = new ArrayList<Integer>(extrusionSet);
		Collections.sort(extrusionList);

		json.put("floor", listToJsonArray(floorList));
		json.put("room", listToJsonArray(roomList));
		json.put("asset", listToJsonArray(assetList));
		json.put("facility", listToJsonArray(facilityList));
		json.put("label", listToJsonArray(labelList));
		json.put("extrusions", listToJsonArray(extrusionList));
		return json;
	}

	private static JSONArray listToJsonArray(List<Integer> list) {
		JSONArray array = new JSONArray();
		for (Integer i : list) {
			array.put(i);
		}
		return array;
	}
}

package cn.platalk.map.vectortile.cbm.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.platalk.map.entity.base.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.TYIIconTextSymbolRecord;
import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;

public class TYSymbolExtractor {
	public static JSONObject extractSymbolJson(List<TYIMapDataFeatureRecord> mapDataRecords,
			List<TYIFillSymbolRecord> fillSymbols, List<TYIIconTextSymbolRecord> iconTextSymbols) {
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

		List<Integer> floorList = findUIDForFillSymbolIDSet(fillSymbols, floorSet);
		Collections.sort(floorList);
		List<Integer> roomList = findUIDForFillSymbolIDSet(fillSymbols, roomSet);
		Collections.sort(roomList);
		List<Integer> assetList = findUIDForFillSymbolIDSet(fillSymbols, assetSet);
		Collections.sort(assetList);
		List<Integer> facilityList = findUIDForIconTextSymbolIDSet(iconTextSymbols, facilitySet);
		Collections.sort(facilityList);
		List<Integer> labelList = findUIDForIconTextSymbolIDSet(iconTextSymbols, labelSet);
		Collections.sort(labelList);
		List<Integer> extrusionList = findUIDForFillSymbolIDSet(fillSymbols, extrusionSet);
		Collections.sort(extrusionList);

		json.put("floor", listToJsonArray(floorList));
		json.put("room", listToJsonArray(roomList));
		json.put("asset", listToJsonArray(assetList));
		json.put("facility", listToJsonArray(facilityList));
		json.put("label", listToJsonArray(labelList));
		json.put("extrusion", listToJsonArray(extrusionList));
		return json;
	}

	private static List<Integer> findUIDForFillSymbolIDSet(List<TYIFillSymbolRecord> fillSymbols, Set<Integer> set) {
		List<Integer> resultList = new ArrayList<Integer>();
		for (Integer symbolID : set) {
			resultList.addAll(uIDForFillSymbol(fillSymbols, symbolID));
		}
		return resultList;
	}

	private static List<Integer> uIDForFillSymbol(List<TYIFillSymbolRecord> fillSymbols, int symbolID) {
		List<Integer> resultList = new ArrayList<Integer>();
		for (TYIFillSymbolRecord symbol : fillSymbols) {
			if (symbol.getSymbolID() == symbolID) {
				resultList.add(symbol.getUID());
			}
		}
		return resultList;
	}

	private static List<Integer> findUIDForIconTextSymbolIDSet(List<TYIIconTextSymbolRecord> iconTextSymbols,
			Set<Integer> set) {
		List<Integer> resultList = new ArrayList<Integer>();
		for (Integer symbolID : set) {
			resultList.addAll(uIDForIconTextSymbol(iconTextSymbols, symbolID));
		}
		return resultList;
	}

	private static List<Integer> uIDForIconTextSymbol(List<TYIIconTextSymbolRecord> iconTextSymbols, int symbolID) {
		List<Integer> resultList = new ArrayList<Integer>();
		for (TYIIconTextSymbolRecord symbol : iconTextSymbols) {
			if (symbol.getSymbolID() == symbolID) {
				resultList.add(symbol.getUID());
			}
		}
		return resultList;
	}

	private static JSONArray listToJsonArray(List<Integer> list) {
		JSONArray array = new JSONArray();
		for (Integer i : list) {
			array.put(i);
		}
		return array;
	}
}

package cn.platalk.map.vectortile.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Geometry;

import cn.platalk.map.entity.base.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.TYIIconSymbolRecord;
import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.map.entity.base.TYIMapInfo;

class TYGeometrySet {
	String buildingID;
	private List<TYIMapInfo> mapInfoList = new ArrayList<TYIMapInfo>();
	private List<TYIMapDataFeatureRecord> allRecords = new ArrayList<TYIMapDataFeatureRecord>();
	private List<TYIFillSymbolRecord> fillSymbolList = new ArrayList<TYIFillSymbolRecord>();
	private List<TYIIconSymbolRecord> iconSymbolList = new ArrayList<TYIIconSymbolRecord>();
	private Map<Integer, TYIFillSymbolRecord> fillSymbolMap = new HashMap<Integer, TYIFillSymbolRecord>();
	private Map<Integer, TYIIconSymbolRecord> iconSymbolMap = new HashMap<Integer, TYIIconSymbolRecord>();

	private List<Geometry> floorList = new ArrayList<Geometry>();
	private List<Geometry> roomList = new ArrayList<Geometry>();
	private List<Geometry> assetList = new ArrayList<Geometry>();
	private List<Geometry> facilityList = new ArrayList<Geometry>();
	private List<Geometry> labelList = new ArrayList<Geometry>();

	private List<Geometry> fillList = new ArrayList<Geometry>();
	private Map<String, List<Geometry>> layerMap = new HashMap<String, List<Geometry>>();

	public TYGeometrySet(String buildingID) {
		this.buildingID = buildingID;
	}

	public void addData(List<TYIMapInfo> mapInfos, List<TYIMapDataFeatureRecord> mapDataRecords,
			List<TYIFillSymbolRecord> fillSymbols, List<TYIIconSymbolRecord> iconSymbols) {
		mapInfoList.addAll(mapInfos);
		allRecords.addAll(mapDataRecords);
		fillSymbolList.addAll(fillSymbols);
		iconSymbolList.addAll(iconSymbols);

		for (TYIFillSymbolRecord symbol : fillSymbolList) {
			fillSymbolMap.put(symbol.getSymbolID(), symbol);
		}

		for (TYIIconSymbolRecord symbol : iconSymbolList) {
			iconSymbolMap.put(symbol.getSymbolID(), symbol);
		}
	}

	public List<TYIMapInfo> getMapInfos() {
		return mapInfoList;
	}

	public List<Geometry> getGeomList(String layerName) {
		return layerMap.get(layerName);
	}

	private List<TYIMapDataFeatureRecord> sortFeatures(List<TYIMapDataFeatureRecord> records) {
		List<TYIMapDataFeatureRecord> resultList = new ArrayList<TYIMapDataFeatureRecord>();

		List<TYIMapDataFeatureRecord> prioritizedList = new ArrayList<TYIMapDataFeatureRecord>();
		List<TYIMapDataFeatureRecord> unPrioritizedList = new ArrayList<TYIMapDataFeatureRecord>();
		for (TYIMapDataFeatureRecord record : records) {
			if (record.getPriority() == 0) {
				unPrioritizedList.add(record);
			} else {
				prioritizedList.add(record);
			}
		}
		Collections.sort(prioritizedList, new TYMapDataFeatureComparator());
		resultList.addAll(prioritizedList);
		resultList.addAll(unPrioritizedList);
		return resultList;
	}

	public void processData() {
		List<TYIMapDataFeatureRecord> floorRecords = new ArrayList<TYIMapDataFeatureRecord>();
		List<TYIMapDataFeatureRecord> roomRecords = new ArrayList<TYIMapDataFeatureRecord>();
		List<TYIMapDataFeatureRecord> assetRecords = new ArrayList<TYIMapDataFeatureRecord>();
		List<TYIMapDataFeatureRecord> facilityRecords = new ArrayList<TYIMapDataFeatureRecord>();
		List<TYIMapDataFeatureRecord> labelRecords = new ArrayList<TYIMapDataFeatureRecord>();
		List<TYIMapDataFeatureRecord> fillRecords = new ArrayList<TYIMapDataFeatureRecord>();

		for (TYIMapDataFeatureRecord record : allRecords) {
			if (record.getLayer() == TYIMapDataFeatureRecord.LAYER_FLOOR) {
				floorRecords.add(record);
				fillRecords.add(record);
			} else if (record.getLayer() == TYIMapDataFeatureRecord.LAYER_ROOM) {
				roomRecords.add(record);
				fillRecords.add(record);
			} else if (record.getLayer() == TYIMapDataFeatureRecord.LAYER_ASSET) {
				assetRecords.add(record);
				fillRecords.add(record);
			} else if (record.getLayer() == TYIMapDataFeatureRecord.LAYER_FACILITY) {
				facilityRecords.add(record);
			} else if (record.getLayer() == TYIMapDataFeatureRecord.LAYER_LABEL) {
				labelRecords.add(record);
			}
		}

		floorRecords = sortFeatures(floorRecords);
		roomRecords = sortFeatures(roomRecords);
		assetRecords = sortFeatures(assetRecords);
		facilityRecords = sortFeatures(facilityRecords);
		labelRecords = sortFeatures(labelRecords);
		fillRecords = sortFeatures(fillRecords);

		if (floorRecords.size() != 0) {
			for (TYIMapDataFeatureRecord record : floorRecords) {
				Geometry g = TYGeometryUtils.geometryFromFillRecord(record, fillSymbolMap.get(record.getSymbolID()));
				floorList.add(g);
				fillList.add(g);
			}
		}

		if (roomRecords.size() != 0) {
			for (TYIMapDataFeatureRecord record : roomRecords) {
				Geometry g = TYGeometryUtils.geometryFromFillRecord(record, fillSymbolMap.get(record.getSymbolID()));
				roomList.add(g);
				fillList.add(g);

			}
		}

		if (assetRecords.size() != 0) {
			for (TYIMapDataFeatureRecord record : assetRecords) {
				Geometry g = TYGeometryUtils.geometryFromFillRecord(record, fillSymbolMap.get(record.getSymbolID()));
				assetList.add(g);
				fillList.add(g);
			}
		}

		if (facilityRecords.size() != 0) {
			for (TYIMapDataFeatureRecord record : facilityRecords) {
				Geometry g = TYGeometryUtils.geometryFromIconRecord(record, iconSymbolMap.get(record.getSymbolID()));
				facilityList.add(g);
			}
		}

		if (labelRecords.size() != 0) {
			for (TYIMapDataFeatureRecord record : labelRecords) {
				Geometry g = TYGeometryUtils.geometryFromMapDataRecord(record);
				labelList.add(g);
			}
		}

		layerMap.put(TYVectorTileParams.LAYER_FLOOR, floorList);
		layerMap.put(TYVectorTileParams.LAYER_ROOM, roomList);
		layerMap.put(TYVectorTileParams.LAYER_ASSET, assetList);
		layerMap.put(TYVectorTileParams.LAYER_FACILITY, facilityList);
		layerMap.put(TYVectorTileParams.LAYER_LABEL, labelList);
		layerMap.put(TYVectorTileParams.LAYER_FILL, fillList);
	}

}

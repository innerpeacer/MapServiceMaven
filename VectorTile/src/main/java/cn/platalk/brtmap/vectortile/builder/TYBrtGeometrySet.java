package cn.platalk.brtmap.vectortile.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.TYIFillSymbolRecord;
import cn.platalk.brtmap.entity.base.TYIIconSymbolRecord;
import cn.platalk.brtmap.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.TYIMapInfo;

import com.vividsolutions.jts.geom.Geometry;

class TYBrtGeometrySet {
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

	public TYBrtGeometrySet(String buildingID) {
		this.buildingID = buildingID;
	}

	public void addData(List<TYIMapInfo> mapInfos,
			List<TYIMapDataFeatureRecord> mapDataRecords,
			List<TYIFillSymbolRecord> fillSymbols,
			List<TYIIconSymbolRecord> iconSymbols) {
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

		if (floorRecords.size() != 0) {
			for (TYIMapDataFeatureRecord record : floorRecords) {
				Geometry g = TYBrtGeometryUtils.geometryFromFillRecord(record,
						fillSymbolMap.get(record.getSymbolID()));
				floorList.add(g);
				fillList.add(g);
			}
		}

		if (roomRecords.size() != 0) {
			for (TYIMapDataFeatureRecord record : roomRecords) {
				Geometry g = TYBrtGeometryUtils.geometryFromFillRecord(record,
						fillSymbolMap.get(record.getSymbolID()));
				roomList.add(g);
				fillList.add(g);

			}
		}

		if (assetRecords.size() != 0) {
			for (TYIMapDataFeatureRecord record : assetRecords) {
				Geometry g = TYBrtGeometryUtils.geometryFromFillRecord(record,
						fillSymbolMap.get(record.getSymbolID()));
				assetList.add(g);
				fillList.add(g);
			}
		}

		if (facilityRecords.size() != 0) {
			for (TYIMapDataFeatureRecord record : facilityRecords) {
				Geometry g = TYBrtGeometryUtils.geometryFromIconRecord(record,
						iconSymbolMap.get(record.getSymbolID()));
				facilityList.add(g);
			}
		}

		if (labelRecords.size() != 0) {
			for (TYIMapDataFeatureRecord record : labelRecords) {
				Geometry g = TYBrtGeometryUtils
						.geometryFromMapDataRecord(record);
				labelList.add(g);
			}
		}

		layerMap.put(TYBrtVectorTileParams.LAYER_FLOOR, floorList);
		layerMap.put(TYBrtVectorTileParams.LAYER_ROOM, roomList);
		layerMap.put(TYBrtVectorTileParams.LAYER_ASSET, assetList);
		layerMap.put(TYBrtVectorTileParams.LAYER_FACILITY, facilityList);
		layerMap.put(TYBrtVectorTileParams.LAYER_LABEL, labelList);
		layerMap.put(TYBrtVectorTileParams.LAYER_FILL, fillList);
	}

}

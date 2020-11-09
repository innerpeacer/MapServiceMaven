package cn.platalk.core.pbf.mapdata.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.map.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.map.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.map.TYMapDataFeatureRecord;
import com.vividsolutions.jts.geom.Geometry;

import cn.platalk.core.pbf.mapdata.TYMapDataPbf;
import cn.platalk.core.pbf.mapdata.TYMapDataPbf.TYIndoorDataPbf;
import cn.platalk.core.pbf.mapdata.TYMapDataPbf.TYIndoorFeaturePbf;
import cn.platalk.core.pbf.mapdata.TYMapDataPbf.TYIndoorLayerPbf;
import cn.platalk.core.pbf.mapdata.TYSymbolPbf.TYRenderingSymbolsPbf;

public class TYIndoorDataPbfBuilder {

	public static TYIndoorDataPbf generateMapDataObject(String mapID, List<TYMapDataFeatureRecord> dataList,
														List<TYFillSymbolRecord> fillSymbolList, List<TYIconSymbolRecord> iconSymbolList) {
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

		for (TYMapDataFeatureRecord record : dataList) {
			if (record.layer == TYMapDataFeatureRecord.LAYER_FLOOR) {
				floorList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_ROOM) {
				roomList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_ASSET) {
				assetList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_FACILITY) {
				facilityList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_LABEL) {
				labelList.add(record);
			}
		}

		TYMapDataPbf.TYIndoorDataPbf.Builder dataBuilder = TYIndoorDataPbf.newBuilder();
		dataBuilder.setMapID(mapID);
		dataBuilder.setFloorLayer(buildFillLayerPbf(floorList, fillSymbolMap));
		dataBuilder.setRoomLayer(buildFillLayerPbf(roomList, fillSymbolMap));
		dataBuilder.setAssetLayer(buildFillLayerPbf(assetList, fillSymbolMap));
		dataBuilder.setFacilityLayer(buildIconLayerPbf(facilityList, iconSymbolMap));
		dataBuilder.setLabelLayer(buildLayerPbf(labelList));

		TYRenderingSymbolsPbf symbols = IPSymbolPbfUtils.symbolsToPbf(mapID, fillSymbolList, iconSymbolList);
		dataBuilder.setSymbols(symbols);

		return dataBuilder.build();
	}

	static TYIndoorLayerPbf buildFillLayerPbf(List<TYMapDataFeatureRecord> features,
			Map<Integer, TYFillSymbolRecord> fillSymbolMap) {
		TYIndoorLayerPbf.Builder layerBuilder = TYIndoorLayerPbf.newBuilder();
		for (int i = 0; i < features.size(); i++) {
			TYMapDataFeatureRecord record = features.get(i);
			TYIndoorFeaturePbf fpbf = fillFeatureToPbf(record, fillSymbolMap.get(record.symbolID));
			if (fpbf != null) {
				layerBuilder.addFeatures(fpbf);
			}
		}
		return layerBuilder.build();
	}

	static TYIndoorLayerPbf buildIconLayerPbf(List<TYMapDataFeatureRecord> features,
			Map<Integer, TYIconSymbolRecord> iconSymbolMap) {
		TYIndoorLayerPbf.Builder layerBuilder = TYIndoorLayerPbf.newBuilder();
		for (int i = 0; i < features.size(); i++) {
			TYMapDataFeatureRecord record = features.get(i);
			TYIndoorFeaturePbf fpbf = iconFeatureToPbf(record, iconSymbolMap.get(record.symbolID));
			if (fpbf != null) {
				layerBuilder.addFeatures(fpbf);
			}
		}
		return layerBuilder.build();
	}

	static TYIndoorLayerPbf buildLayerPbf(List<TYMapDataFeatureRecord> features) {
		TYIndoorLayerPbf.Builder layerBuilder = TYIndoorLayerPbf.newBuilder();
		for (int i = 0; i < features.size(); i++) {
			TYIndoorFeaturePbf fpbf = featureToPbf(features.get(i));
			if (fpbf != null) {
				layerBuilder.addFeatures(fpbf);
			}
		}
		return layerBuilder.build();
	}

	static TYIndoorFeaturePbf fillFeatureToPbf(TYMapDataFeatureRecord record, TYFillSymbolRecord fillRecord) {
		TYIndoorFeaturePbf.Builder featureBuilder = TYIndoorFeaturePbf.newBuilder();

		Geometry geometry = record.getGeometryData();
		if (geometry == null) {
			return null;
		}
		featureBuilder.setGeometry(IPGeometryPbfUtils.geometryToPbf(geometry));
		featureBuilder.setProperties(IPProperityPbfUtils.fillPropertiesToPbf(record, fillRecord));
		return featureBuilder.build();
	}

	static TYIndoorFeaturePbf iconFeatureToPbf(TYMapDataFeatureRecord record, TYIconSymbolRecord iconRecord) {
		TYIndoorFeaturePbf.Builder featureBuilder = TYIndoorFeaturePbf.newBuilder();

		Geometry geometry = record.getGeometryData();
		if (geometry == null) {
			return null;
		}
		featureBuilder.setGeometry(IPGeometryPbfUtils.geometryToPbf(geometry));
		featureBuilder.setProperties(IPProperityPbfUtils.iconPropertiesToPbf(record, iconRecord));
		return featureBuilder.build();
	}

	static TYIndoorFeaturePbf featureToPbf(TYMapDataFeatureRecord record) {
		TYIndoorFeaturePbf.Builder featureBuilder = TYIndoorFeaturePbf.newBuilder();

		Geometry geometry = record.getGeometryData();
		if (geometry == null) {
			return null;
		}
		featureBuilder.setGeometry(IPGeometryPbfUtils.geometryToPbf(geometry));
		featureBuilder.setProperties(IPProperityPbfUtils.propertiesToPbf(record));
		return featureBuilder.build();
	}
}

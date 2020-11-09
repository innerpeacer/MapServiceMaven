package cn.platalk.core.pbf.poi.wrapper;

import java.util.List;

import cn.platalk.core.pbf.poi.TYPoiPbf;
import cn.platalk.core.pbf.poi.TYPoiPbf.PoiCollectionPbf;
import cn.platalk.core.pbf.poi.TYPoiPbf.PoiPbf;
import cn.platalk.map.entity.base.impl.map.TYMapDataFeatureRecord;

public class TYPoiPbfBuilder {

	public static PoiCollectionPbf generatePoiCollectionObject(List<TYMapDataFeatureRecord> dataList) {
		TYPoiPbf.PoiCollectionPbf.Builder poiBuilder = TYPoiPbf.PoiCollectionPbf.newBuilder();
		for (TYMapDataFeatureRecord record : dataList) {
			if (record.layer == TYMapDataFeatureRecord.LAYER_LABEL) {
				continue;
			}
			poiBuilder.addPois(featureToPbf(record));
		}
		return poiBuilder.build();
	}

	private static PoiPbf featureToPbf(TYMapDataFeatureRecord record) {
		PoiPbf.Builder poiBuilder = TYPoiPbf.PoiPbf.newBuilder();
		// poiBuilder.setGeoID(record.geoID);
		poiBuilder.setPoiID(record.poiID);
		// poiBuilder.setBuildingID(record.buildingID);
		// poiBuilder.setFloorID(record.floorID);
		poiBuilder.setFloor(record.floorNumber);
		// poiBuilder.setFloorName(record.floorName);
		poiBuilder.setLayer(record.layer);
		if (record.name != null) {
			poiBuilder.setName(record.name);
		}
		poiBuilder.setCategoryID(record.categoryID);
		// poiBuilder.setSymbolID(record.symbolID);
		poiBuilder.setLabelX(record.labelX);
		poiBuilder.setLabelY(record.labelY);
		return poiBuilder.build();
	}
}

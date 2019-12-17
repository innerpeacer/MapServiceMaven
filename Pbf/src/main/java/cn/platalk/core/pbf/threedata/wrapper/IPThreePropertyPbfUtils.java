package cn.platalk.core.pbf.threedata.wrapper;

import cn.platalk.core.pbf.threedata.TYThreePropertyPbf.ThreeFeaturePropertiesPbf;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;

public class IPThreePropertyPbfUtils {

	public static ThreeFeaturePropertiesPbf propertiesToPbf(TYMapDataFeatureRecord record) {
		ThreeFeaturePropertiesPbf.Builder builder = ThreeFeaturePropertiesPbf.newBuilder();
		builder.setSymbolID(record.getSymbolID());
		builder.setPoiID(record.getPoiID());
		builder.setFloor(record.getFloorNumber());
		builder.setLayer(record.getLayer());
		builder.setBase(record.getExtrusionBase());
		builder.setHeight(record.getExtrusionHeight());
		return builder.build();
	}
}

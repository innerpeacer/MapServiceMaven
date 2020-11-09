package cn.platalk.core.pbf.mapdata.wrapper;

import cn.platalk.core.pbf.mapdata.TYMapDataPbf;
import cn.platalk.core.pbf.mapdata.TYMapDataPbf.TYIndoorFeaturePbf;
import cn.platalk.map.entity.base.impl.map.TYMapDataFeatureRecord;

class IPIndoorFeaturePbfUtils {

	public static TYMapDataPbf.TYIndoorFeaturePbf buildMapDataRecord(TYMapDataFeatureRecord record) {
		TYIndoorFeaturePbf.Builder builder = TYIndoorFeaturePbf.newBuilder();
		builder.setGeometry(IPGeometryPbfUtils.geometryToPbf(record.getGeometryData()));
		builder.setProperties(IPProperityPbfUtils.propertiesToPbf(record));
		return builder.build();
	}
}

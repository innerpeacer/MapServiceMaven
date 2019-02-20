package cn.platalk.map.core.pbf;

import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import innerpeacer.mapdata.pbf.TYMapDataPbf;
import innerpeacer.mapdata.pbf.TYMapDataPbf.TYIndoorFeaturePbf;

public class TYWebMapPbfObject {

	public static TYMapDataPbf.TYIndoorFeaturePbf buildMapDataRecord(
			TYMapDataFeatureRecord record) {
		TYIndoorFeaturePbf.Builder builder = TYIndoorFeaturePbf.newBuilder();
		builder.setGeometry(TYWebMapGeom2PbfUtils.geometryToPbf(record
				.getGeometryData()));
		builder.setProperties(TYWebMapProperities2PbfUtils
				.propertiesToPbf(record));
		return builder.build();
	}
}

package cn.platalk.brtmap.core.pbf;

import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import innerpeacer.mapdata.pbf.TYMapDataPbf;
import innerpeacer.mapdata.pbf.TYMapDataPbf.TYIndoorFeaturePbf;

public class TYBrtWebMapPbfObject {

	public static TYMapDataPbf.TYIndoorFeaturePbf buildMapDataRecord(
			TYMapDataFeatureRecord record) {
		TYIndoorFeaturePbf.Builder builder = TYIndoorFeaturePbf.newBuilder();
		builder.setGeometry(TYBrtWebMapGeom2PbfUtils.geometryToPbf(record
				.getGeometryData()));
		builder.setProperties(TYBrtWebMapProperities2PbfUtils
				.propertiesToPbf(record));
		return builder.build();
	}
}

package cn.platalk.map.api.three.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;

public class TYThreeFeatureOptimizer {

	public static List<TYMapDataFeatureRecord> optimize(List<TYMapDataFeatureRecord> mapdataList) {
		Map<String, TYThreeMergeSet> mergeMap = new HashMap<String, TYThreeMergeSet>();

		List<TYMapDataFeatureRecord> resultList = new ArrayList<TYMapDataFeatureRecord>();
		for (TYMapDataFeatureRecord record : mapdataList) {
			if (record.layer == 4 || record.layer == 5) {
				resultList.add(record);
			} else {
				if (record.name == null || record.name.length() == 0) {
					String key = TYThreeMergeSet.getKey(record);
					TYThreeMergeSet merge = mergeMap.get(key);
					if (merge == null) {
						merge = new TYThreeMergeSet(record.floorNumber, record.layer, record.symbolID,
								record.extrusionHeight);
						merge.templateRecord = record;
						mergeMap.put(key, merge);
					}
					merge.addGeometry(record.getGeometryData());
				} else {
					resultList.add(record);
				}
			}
		}

		for (TYThreeMergeSet merge : mergeMap.values()) {
			resultList.add(merge.buildMapRecord());
		}
		return resultList;
	}

}

package cn.platalk.map.vectortile.builder;

import cn.platalk.map.entity.base.map.TYIMapDataFeatureRecord;

import java.util.Comparator;

public class TYMapDataFeatureComparator implements Comparator<TYIMapDataFeatureRecord> {
	@Override
	public int compare(TYIMapDataFeatureRecord f1, TYIMapDataFeatureRecord f2) {
		return f1.getPriority() - f2.getPriority();
	}
}

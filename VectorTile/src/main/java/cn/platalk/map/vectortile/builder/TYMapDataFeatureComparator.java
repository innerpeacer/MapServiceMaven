package cn.platalk.map.vectortile.builder;

import java.util.Comparator;

import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;

public class TYMapDataFeatureComparator implements Comparator<TYIMapDataFeatureRecord> {
	@Override
	public int compare(TYIMapDataFeatureRecord f1, TYIMapDataFeatureRecord f2) {
		return f1.getPriority() - f2.getPriority();
	}
}

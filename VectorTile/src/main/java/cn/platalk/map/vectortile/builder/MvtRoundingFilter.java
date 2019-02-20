package cn.platalk.map.vectortile.builder;

import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.CoordinateSequenceFilter;

final class MvtRoundingFilter implements CoordinateSequenceFilter {

	public static final MvtRoundingFilter INSTANCE = new MvtRoundingFilter();

	private MvtRoundingFilter() {
	}

	@Override
	public void filter(CoordinateSequence seq, int i) {
		seq.setOrdinate(i, 0, Math.round(seq.getOrdinate(i, 0)));
		seq.setOrdinate(i, 1, Math.round(seq.getOrdinate(i, 1)));
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public boolean isGeometryChanged() {
		return true;
	}
}

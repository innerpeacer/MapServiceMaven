package cn.platalk.brtmap.vectortile.builder;

import java.util.List;
import java.util.Objects;

import com.vividsolutions.jts.geom.Geometry;

final class MvtTileGeomResult {

	public final List<Geometry> intGeoms;
	public final List<Geometry> mvtGeoms;

	public MvtTileGeomResult(List<Geometry> intGeoms, List<Geometry> mvtGeoms) {
		Objects.requireNonNull(intGeoms);
		Objects.requireNonNull(mvtGeoms);
		this.intGeoms = intGeoms;
		this.mvtGeoms = mvtGeoms;
	}
}

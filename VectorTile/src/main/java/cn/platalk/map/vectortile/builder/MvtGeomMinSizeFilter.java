package cn.platalk.map.vectortile.builder;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

final class MvtGeomMinSizeFilter implements MvtIGeometryFilter {

	private final double minArea;
	private final double minLength;

	public MvtGeomMinSizeFilter(double minArea, double minLength) {
		if (minArea < 0.0d) {
			throw new IllegalArgumentException("minArea must be >= 0");
		}
		if (minLength < 0.0d) {
			throw new IllegalArgumentException("minLength must be >= 0");
		}

		this.minArea = minArea;
		this.minLength = minLength;
	}

	@Override
	public boolean accept(Geometry geometry) {
		boolean accept = true;

		if ((geometry instanceof Polygon || geometry instanceof MultiPolygon)
				&& geometry.getArea() < minArea) {
			accept = false;

		} else if ((geometry instanceof LineString || geometry instanceof MultiLineString)
				&& geometry.getLength() < minLength) {
			accept = false;
		}
		return accept;
	}
}

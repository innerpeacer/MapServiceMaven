package cn.platalk.map.vectortile.builder;

import com.vividsolutions.jts.geom.Geometry;

interface MvtIGeometryFilter {
	boolean accept(Geometry geometry);
}

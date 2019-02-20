package cn.platalk.core.map.shp.routedata;

import com.vividsolutions.jts.geom.Geometry;

public class TYShpRouteRecord {
	public byte[] geometryData;
	public int geometryID;
	public short oneWay;
	public Geometry geometry;

	public boolean isOneWay() {
		return oneWay != 0;
	}
}

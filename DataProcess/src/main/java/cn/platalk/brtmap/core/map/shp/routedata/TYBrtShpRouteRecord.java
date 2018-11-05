package cn.platalk.brtmap.core.map.shp.routedata;

import com.vividsolutions.jts.geom.Geometry;

public class TYBrtShpRouteRecord {
	public byte[] geometryData;
	public int geometryID;
	public short oneWay;
	public Geometry geometry;

	public boolean isOneWay() {
		return oneWay != 0;
	}
}

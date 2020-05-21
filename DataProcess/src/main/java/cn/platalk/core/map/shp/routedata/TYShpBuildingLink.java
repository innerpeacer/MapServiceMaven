package cn.platalk.core.map.shp.routedata;

import com.vividsolutions.jts.geom.LineString;

public class TYShpBuildingLink {
	final int linkID;
	byte[] geometryData;
	double length;
	int headNodeID;
	int endNodeID;
	final boolean isVirtual;
	final boolean isOneWay;

	LineString line;

	public TYShpBuildingLink(int linkID, boolean isVir, boolean isOW) {
		this.linkID = linkID;
		this.isVirtual = isVir;
		this.isOneWay = isOW;
	}

	@Override
	public String toString() {
		return String.format("Head: %d, End: %d, Length: %f", headNodeID,
				endNodeID, length);
	}

}

package cn.platalk.core.map.shp.routedata;

import com.vividsolutions.jts.geom.LineString;

public class TYShpBuildingLink {
	int linkID;
	byte[] geometryData;
	double length;
	int headNodeID;
	int endNodeID;
	boolean isVirtual;
	boolean isOneWay;

	LineString line;

	public TYShpBuildingLink(int linkID, boolean isVir, boolean isOW) {
		this.linkID = linkID;
		this.isVirtual = isVir;
		this.isOneWay = isOW;
	}

	@Override
	public String toString() {
		return String.format("Head: %d, End: %d, Length: %d", headNodeID,
				endNodeID, length);
	}

}

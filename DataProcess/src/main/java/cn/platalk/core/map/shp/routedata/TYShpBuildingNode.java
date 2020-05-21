package cn.platalk.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Point;

public class TYShpBuildingNode {
	final int nodeID;
	byte[] geometryData;
	final List<TYShpBuildingLink> adjacencies;
	final boolean isVirtual;
	Point pos;

	public TYShpBuildingNode(int nodeID, boolean isVir) {
		this.nodeID = nodeID;
		this.isVirtual = isVir;
		adjacencies = new ArrayList<>();
	}

	public void addLink(TYShpBuildingLink link) {
		adjacencies.add(link);
	}

	@Override
	public String toString() {
		return String.format("ID: %d, Virtual: %b", nodeID, isVirtual);
	}

}
package cn.platalk.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Point;

public class TYShpBuildingNode {
	int nodeID;
	byte[] geometryData;
	List<TYShpBuildingLink> adjacencies;
	boolean isVirutal;
	Point pos;

	public TYShpBuildingNode(int nodeID, boolean isVir) {
		this.nodeID = nodeID;
		this.isVirutal = isVir;
		adjacencies = new ArrayList<TYShpBuildingLink>();
	}

	public void addLink(TYShpBuildingLink link) {
		adjacencies.add(link);
	}

	@Override
	public String toString() {
		return String.format("ID: %d, Virtual: %d", nodeID, isVirutal);
	}

}
package cn.platalk.brtmap.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Point;

public class TYBrtShpBuildingNode {
	int nodeID;
	byte[] geometryData;
	List<TYBrtShpBuildingLink> adjacencies;
	boolean isVirutal;
	Point pos;

	public TYBrtShpBuildingNode(int nodeID, boolean isVir) {
		this.nodeID = nodeID;
		this.isVirutal = isVir;
		adjacencies = new ArrayList<TYBrtShpBuildingLink>();
	}

	public void addLink(TYBrtShpBuildingLink link) {
		adjacencies.add(link);
	}

	@Override
	public String toString() {
		return String.format("ID: %d, Virtual: %d", nodeID, isVirutal);
	}

}
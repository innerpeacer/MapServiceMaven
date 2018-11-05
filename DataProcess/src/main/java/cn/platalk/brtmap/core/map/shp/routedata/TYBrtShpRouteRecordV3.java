package cn.platalk.brtmap.core.map.shp.routedata;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

public class TYBrtShpRouteRecordV3 {
	public byte[] geometryData;
	// public int geometryID;
	public Geometry geometry;

	// Common
	public int floor;
	public int level;
	public boolean open;
	public String openTime;

	// For Link
	public String linkID;
	public boolean isOneWay;
	public String linkName;
	public boolean reverse;
	public String roomID;
	public boolean allowSnap;
	public String linkType;

	// For Node
	public String nodeID;
	public String nodeName;
	public String categoryID;
	public boolean isSwitching;
	public int switchingID;
	public int direction;
	public int nodeType;

	TYBrtShpBuildingLinkV3 toLink() {
		TYBrtShpBuildingLinkV3 l = new TYBrtShpBuildingLinkV3(linkID);
		l.geometryData = geometryData;
		l.line = (LineString) geometry;
		l.length = geometry.getLength();

		l.floor = floor;
		l.level = level;
		l.open = open;
		l.openTime = openTime;

		l.linkID = linkID;
		l.isOneWay = isOneWay;
		l.linkName = linkName;
		l.reverse = reverse;
		l.roomID = roomID;
		l.allowSnap = allowSnap;
		l.linkType = linkType;

		return l;
	}

	TYBrtShpBuildingNodeV3 toNode() {
		TYBrtShpBuildingNodeV3 n = new TYBrtShpBuildingNodeV3(nodeID);
		n.pos = (Point) geometry;
		n.geometryData = geometryData;

		n.floor = floor;
		n.level = level;
		n.open = open;
		n.openTime = openTime;

		n.nodeID = nodeID;
		n.nodeName = nodeName;
		n.categoryID = categoryID;
		n.isSwitching = isSwitching;
		n.switchingID = switchingID;
		n.direction = direction;
		n.nodeType = nodeType;

		return n;
	}

	@Override
	public String toString() {
		if (linkID != null) {
			return String.format("LinkID: %s", linkID);
		}

		if (nodeID != null) {
			return String.format("NodeID: %s", nodeID);
		}

		return super.toString();
	}
}

package cn.platalk.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.map.TYIRouteNodeRecordV3;
import com.vividsolutions.jts.geom.Point;

public class TYShpBuildingNodeV3 {
	String nodeID;
	byte[] geometryData;
	final List<TYShpBuildingLinkV3> adjacencies;
	// boolean isVirtual;
	Point pos;

	public String nodeName;
	public String categoryID;

	public int floor;
	public int level;
	public boolean isSwitching = false;
	public int switchingID;
	public int direction;

	public int nodeType;
	public boolean open = true;
	public String openTime;

	public TYShpBuildingNodeV3(String nodeID) {
		this.nodeID = nodeID;
		adjacencies = new ArrayList<>();
	}

	public void addLink(TYShpBuildingLinkV3 link) {
		adjacencies.add(link);
	}

	// TYRouteNodeRecordV3 toNode() {
	// TYRouteNodeRecordV3 n = new TYRouteNodeRecordV3();
	// n.nodeID = nodeID;
	// n.nodeGeometryData = geometryData;
	//
	// n.nodeName = nodeName;
	// n.categoryID = categoryID;
	//
	// n.floor = floor;
	// n.level = level;
	// n.isSwitching = isSwitching;
	// n.switchingID = switchingID;
	// n.direction = direction;
	//
	// n.nodeType = nodeType;
	// n.open = open;
	// n.openTime = openTime;
	//
	// return n;
	// }

	TYIRouteNodeRecordV3 toNode(TYIRouteNodeRecordV3 n) {
		n.setNodeID(nodeID);
		n.setGeometryData(geometryData);

		n.setNodeName(nodeName);
		n.setCategoryID(categoryID);

		n.setFloor(floor);
		n.setLevel(level);
		n.setSwitching(isSwitching);
		n.setSwitchingID(switchingID);
		n.setDirection(direction);

		n.setNodeType(nodeType);
		n.setOpen(open);
		n.setOpenTime(openTime);

		return n;
	}

	@Override
	public String toString() {
		// return String.format("ID: %d", nodeID);
		return String.format("NodeID: %s, Floor: %d", nodeID, floor);
	}
}

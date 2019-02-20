package cn.platalk.map.route.core_v3;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.map.entity.base.TYIRouteNodeRecordV3;

public class IPServerNodeV3 implements Comparable<IPServerNodeV3> {
	public static final double LARGE_DISTANCE = Double.POSITIVE_INFINITY;

	public double minDistance;
	public IPServerNodeV3 previousNode;

	public List<IPServerLinkV3> adjacencies = new ArrayList<IPServerLinkV3>();

	public String m_nodeID;
	public Point m_pos;

	public String m_nodeName;
	public String m_categoryID;
	public int m_floor;
	public int m_level;
	public boolean m_isSwitching;
	public int m_switchingID;
	public int m_direction;
	public int m_nodeType;
	public boolean m_open;
	public String m_openTime;
	public String m_roomID;

	public IPTimeWindows m_timeWindows = null;

	static WKBReader reader = new WKBReader();

	public static IPServerNodeV3 fromNodeRecord(TYIRouteNodeRecordV3 nodeRecord) {
		IPServerNodeV3 node = new IPServerNodeV3(nodeRecord.getNodeID());
		try {
			node.setPos((Point) reader.read(nodeRecord.getGeometryData()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		node.m_nodeName = nodeRecord.getNodeName();
		node.m_categoryID = nodeRecord.getCategoryID();
		node.m_floor = nodeRecord.getFloor();
		node.m_level = nodeRecord.getLevel();
		node.m_isSwitching = nodeRecord.isSwitching();
		node.m_switchingID = nodeRecord.getSwitchingID();
		node.m_direction = nodeRecord.getDirection();
		node.m_nodeType = nodeRecord.getNodeType();
		node.m_open = nodeRecord.isOpen();
		node.m_openTime = nodeRecord.getOpenTime();
		if (node.m_openTime != null && node.m_openTime.length() > 0) {
			node.m_timeWindows = new IPTimeWindows(node.m_openTime);
		}
		node.m_roomID = nodeRecord.getRoomID();
		return node;
	}

	public IPServerNodeV3(String nodeID) {
		m_nodeID = nodeID;
		m_pos = null;

		minDistance = LARGE_DISTANCE;
		previousNode = null;
	}

	public void addLink(IPServerLinkV3 link) {
		adjacencies.add(link);
	}

	public void removeLink(IPServerLinkV3 link) {
		adjacencies.remove(link);
	}

	public void reset() {
		minDistance = LARGE_DISTANCE;
		previousNode = null;
	}

	public String getNodeID() {
		return m_nodeID;
	}

	public Point getPos() {
		return m_pos;
	}

	public void setPos(Point p) {
		m_pos = p;
	}

	@Override
	public int compareTo(IPServerNodeV3 other) {
		return Double.compare(minDistance, other.minDistance);
	}

	public String toString() {
		return String.format("NodeID: %s", m_nodeID);
	}
}

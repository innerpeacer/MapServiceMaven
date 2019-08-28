package cn.platalk.map.route.core_v3;

import com.vividsolutions.jts.geom.Point;

public class IPServerRouteElementNode extends IPServerRouteElement {

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

	public static IPServerRouteElementNode fromNode(IPServerNodeV3 serverNode) {
		IPServerRouteElementNode node = new IPServerRouteElementNode();
		node.m_nodeID = serverNode.m_nodeID;
		node.m_pos = serverNode.m_pos;

		node.m_nodeName = serverNode.m_nodeName;
		node.m_categoryID = serverNode.m_categoryID;
		node.m_floor = serverNode.m_floor;
		node.m_level = serverNode.m_level;
		node.m_isSwitching = serverNode.m_isSwitching;
		node.m_switchingID = serverNode.m_switchingID;
		node.m_direction = serverNode.m_direction;
		node.m_nodeType = serverNode.m_nodeType;
		node.m_open = serverNode.m_open;
		node.m_openTime = serverNode.m_openTime;

		node.m_roomID = serverNode.m_roomID;
		return node;
	}

	public int getFloor() {
		return m_floor;
	};

	@Override
	public String toString() {
		return String.format("Element Node: %s", m_nodeID);
	}

	@Override
	public boolean isNode() {
		return true;
	}

	@Override
	public boolean isStop() {
		return false;
	}
}

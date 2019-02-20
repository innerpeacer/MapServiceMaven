package cn.platalk.map.route.core;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Point;

class IPServerNode implements Comparable<IPServerNode> {

	public static final double LARGE_DISTANCE = Double.POSITIVE_INFINITY;

	public double minDistance;
	public IPServerNode previousNode;

	public List<IPServerLink> adjacencies = new ArrayList<IPServerLink>();

	private int m_nodeID;
	private boolean m_isVirtual;
	private Point m_pos;

	public IPServerNode(int nodeID, boolean isVir) {
		m_nodeID = nodeID;
		m_isVirtual = isVir;
		m_pos = null;

		minDistance = LARGE_DISTANCE;
		previousNode = null;
	}

	public void addLink(IPServerLink link) {
		adjacencies.add(link);
	}

	public void removeLink(IPServerLink link) {
		adjacencies.remove(link);
	}

	public void reset() {
		minDistance = LARGE_DISTANCE;
		previousNode = null;
	}

	public int getNodeID() {
		return m_nodeID;
	}

	public Point getPos() {
		return m_pos;
	}

	public void setPos(Point p) {
		m_pos = p;
	}

	public boolean isVirtual() {
		return m_isVirtual;
	}

	@Override
	public int compareTo(IPServerNode other) {
		return Double.compare(minDistance, other.minDistance);
	}

	public String toString() {
		return String.format("NodeID: %d", m_nodeID);
	}
}

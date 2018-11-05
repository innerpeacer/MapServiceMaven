package com.route.debug;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Point;

class IPCheckerNode implements Comparable<IPCheckerNode> {

	public static final double LARGE_DISTANCE = Double.POSITIVE_INFINITY;

	public double minDistance;
	public IPCheckerNode previousNode;

	public TYCheckColor color = TYCheckColor.None;

	public List<IPCheckerLink> adjacencies = new ArrayList<IPCheckerLink>();

	private int m_nodeID;
	private boolean m_isVirtual;
	private Point m_pos;

	public IPCheckerNode(int nodeID, boolean isVir) {
		m_nodeID = nodeID;
		m_isVirtual = isVir;
		m_pos = null;

		minDistance = LARGE_DISTANCE;
		previousNode = null;
	}

	public void addLink(IPCheckerLink link) {
		adjacencies.add(link);
	}

	public void removeLink(IPCheckerLink link) {
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
	public int compareTo(IPCheckerNode other) {
		return Double.compare(minDistance, other.minDistance);
	}

	public String toString() {
		return String.format("NodeID: %d", m_nodeID);
	}
}

package cn.platalk.map.route.core_v3;

import com.vividsolutions.jts.geom.LineString;

public class IPServerRouteElementLink extends IPServerRouteElement {
	public String m_linkID;
	public LineString m_line;

	public double m_length;

	public String m_currentNodeID;
	public String m_nextNodeID;

	public String m_linkName;
	public int m_floor;
	public int m_level;
	public String m_roomID;
	public boolean m_open;
	public String m_openTime;
	public boolean m_allowSnap;
	public String m_linkType;

	public static IPServerRouteElementLink fromLink(IPServerLinkV3 serverLink) {
		IPServerRouteElementLink link = new IPServerRouteElementLink();
		link.m_linkID = serverLink.m_linkID;
		link.m_line = serverLink.m_line;

		link.m_length = serverLink.m_length;

		link.m_currentNodeID = serverLink.m_currentNodeID;
		link.m_nextNodeID = serverLink.m_nextNodeID;

		link.m_linkName = serverLink.m_linkName;
		link.m_floor = serverLink.m_floor;
		link.m_level = serverLink.m_level;
		link.m_roomID = serverLink.m_roomID;
		link.m_open = serverLink.m_open;
		link.m_openTime = serverLink.m_openTime;
		link.m_allowSnap = serverLink.m_allowSnap;
		link.m_linkType = serverLink.m_linkType;

		return link;
	}

	public String toString() {
		return String.format("Element Link: %s [ %s -> %s ]", m_linkID,
				m_currentNodeID, m_nextNodeID);
	};

	public boolean isVirtual() {
		return m_line == null;
	}

	@Override
	public int getFloor() {
		return m_floor;
	}

	@Override
	public boolean isNode() {
		return false;
	}

	@Override
	public boolean isStop() {
		return false;
	}
}

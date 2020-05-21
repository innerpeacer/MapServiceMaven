package cn.platalk.map.route.core_v3;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.map.entity.base.TYIRouteLinkRecordV3;

public class IPServerLinkV3 {
	static final String TAG = IPServerLinkV3.class.getSimpleName();

	public final String m_linkID;
	public LineString m_line;

	public double m_length;

	public String m_currentNodeID;
	public String m_nextNodeID;

	public IPServerNodeV3 nextNode;

	public String m_linkName;
	public int m_floor;
	public int m_level;
	public String m_roomID;
	public boolean m_open;
	public String m_openTime;
	public boolean m_allowSnap;
	public String m_linkType;

	public IPTimeWindows m_timeWindows = null;

	static final WKBReader reader = new WKBReader();

	public void copyAttribute(IPServerLinkV3 link) {
		m_linkName = link.m_linkName;
		m_floor = link.m_floor;
		m_level = link.m_level;
		m_roomID = link.m_roomID;
		m_open = link.m_open;
		m_openTime = link.m_openTime;
		m_timeWindows = link.m_timeWindows;
		m_allowSnap = link.m_allowSnap;
		m_linkType = link.m_linkType;
	}

	public static IPServerLinkV3 fromLinkRecord(TYIRouteLinkRecordV3 linkRecord, boolean isForward) {
		if (linkRecord.isOneWay() && !isForward) {
			return null;
		}

		IPServerLinkV3 link = new IPServerLinkV3(linkRecord.getLinkID());
		LineString lineGeometry = null;
		try {
			lineGeometry = (LineString) reader.read(linkRecord.getGeometryData());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (isForward) {
			if (linkRecord.isReverse()) {
				link.m_currentNodeID = linkRecord.getEndNode();
				link.m_nextNodeID = linkRecord.getHeadNode();
				link.m_line = (LineString) lineGeometry.reverse();
			} else {
				link.m_currentNodeID = linkRecord.getHeadNode();
				link.m_nextNodeID = linkRecord.getEndNode();
				link.m_line = lineGeometry;
			}
		} else {
			if (linkRecord.isReverse()) {
				link.m_currentNodeID = linkRecord.getHeadNode();
				link.m_nextNodeID = linkRecord.getEndNode();
				link.m_line = lineGeometry;
			} else {
				link.m_currentNodeID = linkRecord.getEndNode();
				link.m_nextNodeID = linkRecord.getHeadNode();
				link.m_line = (LineString) lineGeometry.reverse();
			}
		}

		link.m_linkName = linkRecord.getLinkName();
		link.m_length = linkRecord.getLength();
		link.m_floor = linkRecord.getFloor();

		link.m_level = linkRecord.getLevel();
		link.m_roomID = linkRecord.getRoomID();
		link.m_open = linkRecord.isOpen();
		link.m_openTime = linkRecord.getOpenTime();
		if (link.m_openTime != null && link.m_openTime.length() > 0) {
			link.m_timeWindows = new IPTimeWindows(link.m_openTime);
		}

		link.m_allowSnap = linkRecord.isAllowSnap();
		link.m_linkType = linkRecord.getLinkType();

		return link;
	}

	public String getLinkKey() {
		return String.format("%s-%s-%s", m_currentNodeID, m_linkID, m_nextNodeID);
	}

	public IPServerLinkV3(String linkID) {
		m_linkID = linkID;

		m_line = null;
		nextNode = null;
	}

	public String getLinkID() {
		return m_linkID;
	}

	public LineString getLine() {
		return m_line;
	}

	public void setLine(LineString line) {
		m_line = line;
	}

	public String toString() {
		return String.format("LinkID: %s, Current: %s, Next: %s Length: %f", m_linkID, m_currentNodeID, m_nextNodeID,
				m_length);
	}
}

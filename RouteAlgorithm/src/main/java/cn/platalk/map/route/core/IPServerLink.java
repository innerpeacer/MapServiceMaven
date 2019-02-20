package cn.platalk.map.route.core;

import com.vividsolutions.jts.geom.LineString;

class IPServerLink {

	static final String TAG = IPServerLink.class.getSimpleName();

	public int currentNodeID;
	public int nextNodeID;

	public IPServerNode nextNode;
	public double length;

	private int m_linkID;
	private boolean m_isVirtual;
	private LineString m_line;

	public IPServerLink(int linkID, boolean isVir) {
		m_linkID = linkID;
		m_isVirtual = isVir;

		m_line = null;
		nextNode = null;
	}

	public int getLinkID() {
		return m_linkID;
	}

	public boolean isVirtual() {
		return m_isVirtual;
	}

	public LineString getLine() {
		return m_line;
	}

	public void setLine(LineString line) {
		m_line = line;
	}

	public String toString() {
		return String.format("LinkID: %d, Current: %d, Next: %d Length: %f",
				m_linkID, currentNodeID, nextNodeID, length);
	}

}

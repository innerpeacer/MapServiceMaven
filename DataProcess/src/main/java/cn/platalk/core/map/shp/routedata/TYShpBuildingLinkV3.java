package cn.platalk.core.map.shp.routedata;

import cn.platalk.map.entity.base.map.TYIRouteLinkRecordV3;
import com.vividsolutions.jts.geom.LineString;

public class TYShpBuildingLinkV3 {
	String linkID;
	byte[] geometryData;
	double length;
	String headNodeID;
	String endNodeID;
	// boolean isVirtual;
	boolean isOneWay;

	String linkName;
	int floor;
	int level;
	boolean reverse = false;
	String roomID;
	boolean open = true;
	String openTime;
	boolean allowSnap = true;
	public String linkType;

	LineString line;

	public TYShpBuildingLinkV3(String linkID) {
		this.linkID = linkID;
	}

	// TYIRouteLinkRecordV3 toLink() {
	// TYIRouteLinkRecordV3 l = new TYIRouteLinkRecordV3();
	// l.linkID = linkID;
	// l.linkGeometryData = geometryData;
	// l.length = length;
	// l.headNode = headNodeID;
	// l.endNode = endNodeID;
	// l.isOneWay = isOneWay;
	//
	// l.linkName = linkName;
	// l.floor = floor;
	// l.level = level;
	// l.reverse = reverse;
	// l.roomID = roomID;
	// l.open = open;
	// l.openTime = openTime;
	// l.allowSnap = allowSnap;
	// l.linkType = linkType;
	// return l;
	// }

	TYIRouteLinkRecordV3 toLink(TYIRouteLinkRecordV3 l) {
		l.setLinkID(linkID);
		l.setGeometryData(geometryData);
		l.setLength(length);
		l.setHeadNode(headNodeID);
		l.setEndNode(endNodeID);
		l.setOneWay(isOneWay);

		l.setLinkName(linkName);
		l.setFloor(floor);
		l.setLevel(level);
		l.setReverse(reverse);
		l.setRoomID(roomID);
		l.setOpen(open);
		l.setOpenTime(openTime);
		l.setAllowSnap(allowSnap);
		l.setLinkType(linkType);
		return l;
	}

	@Override
	public String toString() {
		return String.format("LinkID: %s, Floor: %s, Head: %s, End: %s", linkID, floor, headNodeID, endNodeID);
	}
}

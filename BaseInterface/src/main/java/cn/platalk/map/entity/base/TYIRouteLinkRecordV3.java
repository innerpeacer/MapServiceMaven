package cn.platalk.map.entity.base;

import cn.platalk.common.TYIGeojsonFeature;

public interface TYIRouteLinkRecordV3 extends TYIGeojsonFeature {
	String getLinkID();

	void setLinkID(String linkID);

	byte[] getGeometryData();

	void setGeometryData(byte[] linkGeometryData);

	double getLength();

	void setLength(double length);

	String getHeadNode();

	void setHeadNode(String headNode);

	String getEndNode();

	void setEndNode(String endNode);

	boolean isOneWay();

	void setOneWay(boolean isOneWay);

	String getLinkName();

	void setLinkName(String linkName);

	int getFloor();

	void setFloor(int floor);

	int getLevel();

	void setLevel(int level);

	boolean isReverse();

	void setReverse(boolean reverse);

	String getRoomID();

	void setRoomID(String rid);

	boolean isOpen();

	void setOpen(boolean open);

	String getOpenTime();

	void setOpenTime(String openTime);

	boolean isAllowSnap();

	void setAllowSnap(boolean allowSnap);

	String getLinkType();

	void setLinkType(String linkType);

}

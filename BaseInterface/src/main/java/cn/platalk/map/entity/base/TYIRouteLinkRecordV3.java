package cn.platalk.map.entity.base;

public interface TYIRouteLinkRecordV3 {
	public String getLinkID();

	public void setLinkID(String linkID);

	public byte[] getGeometryData();

	public void setGeometryData(byte[] linkGeometryData);

	public double getLength();

	public void setLength(double length);

	public String getHeadNode();

	public void setHeadNode(String headNode);

	public String getEndNode();

	public void setEndNode(String endNode);

	public boolean isOneWay();

	public void setOneWay(boolean isOneWay);

	public String getLinkName();

	public void setLinkName(String linkName);

	public int getFloor();

	public void setFloor(int floor);

	public int getLevel();

	public void setLevel(int level);

	public boolean isReverse();

	public void setReverse(boolean reverse);

	public String getRoomID();

	public void setRoomID(String rid);

	public boolean isOpen();

	public void setOpen(boolean open);

	public String getOpenTime();

	public void setOpenTime(String openTime);

	public boolean isAllowSnap();

	public void setAllowSnap(boolean allowSnap);

	public String getLinkType();

	public void setLinkType(String linkType);

}

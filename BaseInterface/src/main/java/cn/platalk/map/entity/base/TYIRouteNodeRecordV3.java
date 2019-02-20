package cn.platalk.map.entity.base;

public interface TYIRouteNodeRecordV3 {
	public String getNodeID();

	public void setNodeID(String nodeID);

	public byte[] getGeometryData();

	public void setGeometryData(byte[] nodeGeometryData);

	public String getNodeName();

	public void setNodeName(String nodeName);

	public String getCategoryID();

	public void setCategoryID(String categoryID);

	public int getFloor();

	public void setFloor(int floor);

	public int getLevel();

	public void setLevel(int level);

	public boolean isSwitching();

	public void setSwitching(boolean isSwitching);

	public int getSwitchingID();

	public void setSwitchingID(int switchingID);

	public int getDirection();

	public void setDirection(int direction);

	public int getNodeType();

	public void setNodeType(int nodeType);

	public boolean isOpen();

	public void setOpen(boolean open);

	public String getOpenTime();

	public void setOpenTime(String openTime);

	public String getRoomID();

	public void setRoomID(String roomID);
}

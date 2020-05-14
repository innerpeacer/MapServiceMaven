package cn.platalk.map.entity.base;

import cn.platalk.common.TYIGeojsonFeature;

public interface TYIRouteNodeRecordV3 extends TYIGeojsonFeature {
	String getNodeID();

	void setNodeID(String nodeID);

	byte[] getGeometryData();

	void setGeometryData(byte[] nodeGeometryData);

	String getNodeName();

	void setNodeName(String nodeName);

	String getCategoryID();

	void setCategoryID(String categoryID);

	int getFloor();

	void setFloor(int floor);

	int getLevel();

	void setLevel(int level);

	boolean isSwitching();

	void setSwitching(boolean isSwitching);

	int getSwitchingID();

	void setSwitchingID(int switchingID);

	int getDirection();

	void setDirection(int direction);

	int getNodeType();

	void setNodeType(int nodeType);

	boolean isOpen();

	void setOpen(boolean open);

	String getOpenTime();

	void setOpenTime(String openTime);

	String getRoomID();

	void setRoomID(String roomID);
}

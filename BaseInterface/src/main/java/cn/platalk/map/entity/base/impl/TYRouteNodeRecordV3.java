package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYIRouteNodeRecordV3;

public class TYRouteNodeRecordV3 implements TYIRouteNodeRecordV3 {
	private String nodeID;
	private byte[] nodeGeometryData;

	private String nodeName;
	private String categoryID;

	private int floor;
	private int level;
	private boolean isSwitching = false;
	private int switchingID;
	private int direction;

	private int nodeType;
	private boolean open = true;
	private String openTime;
	private String roomID;

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public byte[] getGeometryData() {
		return nodeGeometryData;
	}

	public void setGeometryData(byte[] nodeGeometryData) {
		this.nodeGeometryData = nodeGeometryData;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isSwitching() {
		return isSwitching;
	}

	public void setSwitching(boolean isSwitching) {
		this.isSwitching = isSwitching;
	}

	public int getSwitchingID() {
		return switchingID;
	}

	public void setSwitchingID(int switchingID) {
		this.switchingID = switchingID;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getNodeType() {
		return nodeType;
	}

	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	@Override
	public String toString() {
		return String.format("NodeID: %s, Floor: %d", nodeID, floor);
	}
}

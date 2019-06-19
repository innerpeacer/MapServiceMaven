package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYIRouteLinkRecordV3;

public class TYRouteLinkRecordV3 implements TYIRouteLinkRecordV3 {
	private String linkID;
	private byte[] linkGeometryData;

	private double length;
	private String headNode;
	private String endNode;

	private boolean isOneWay = false;

	private String linkName;
	private int floor;
	private int level;
	private boolean reverse = false;
	private String roomID;
	private boolean open = true;
	private String openTime;
	private boolean allowSnap = true;
	private String linkType;

	public String getLinkID() {
		return linkID;
	}

	public void setLinkID(String linkID) {
		this.linkID = linkID;
	}

	public byte[] getGeometryData() {
		return linkGeometryData;
	}

	public void setGeometryData(byte[] linkGeometryData) {
		this.linkGeometryData = linkGeometryData;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getHeadNode() {
		return headNode;
	}

	public void setHeadNode(String headNode) {
		this.headNode = headNode;
	}

	public String getEndNode() {
		return endNode;
	}

	public void setEndNode(String endNode) {
		this.endNode = endNode;
	}

	public boolean isOneWay() {
		return isOneWay;
	}

	public void setOneWay(boolean isOneWay) {
		this.isOneWay = isOneWay;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
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

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
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

	public boolean isAllowSnap() {
		return allowSnap;
	}

	public void setAllowSnap(boolean allowSnap) {
		this.allowSnap = allowSnap;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
}

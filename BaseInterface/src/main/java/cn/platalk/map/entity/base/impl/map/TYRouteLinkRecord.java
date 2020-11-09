package cn.platalk.map.entity.base.impl.map;

import cn.platalk.map.entity.base.map.TYIRouteLinkRecord;

public class TYRouteLinkRecord implements TYIRouteLinkRecord {
	public int linkID;
	public byte[] linkGeometryData;

	public double length;
	public int headNode;
	public int endNode;
	public boolean isVirtual;
	public boolean isOneWay;

	public TYRouteLinkRecord() {
	}

	public int getLinkID() {
		return linkID;
	}

	public void setLinkID(int linkID) {
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

	public int getHeadNode() {
		return headNode;
	}

	public void setHeadNode(int headNode) {
		this.headNode = headNode;
	}

	public int getEndNode() {
		return endNode;
	}

	public void setEndNode(int endNode) {
		this.endNode = endNode;
	}

	public boolean isVirtual() {
		return isVirtual;
	}

	public void setVirtual(boolean isVirtual) {
		this.isVirtual = isVirtual;
	}

	public boolean isOneWay() {
		return isOneWay;
	}

	public void setOneWay(boolean isOneWay) {
		this.isOneWay = isOneWay;
	}

	@Override
	public String toString() {
		return String.format("Link-%d", linkID);
	}

}

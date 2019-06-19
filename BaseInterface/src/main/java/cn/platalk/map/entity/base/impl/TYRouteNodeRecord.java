package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYIRouteNodeRecord;

public class TYRouteNodeRecord implements TYIRouteNodeRecord {
	public int nodeID;
	public byte[] nodeGeometryData;
	public boolean isVirtual;

	public TYRouteNodeRecord() {
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public byte[] getGeometryData() {
		return nodeGeometryData;
	}

	public void setGeometryData(byte[] nodeGeometryData) {
		this.nodeGeometryData = nodeGeometryData;
	}

	public boolean isVirtual() {
		return isVirtual;
	}

	public void setVirtual(boolean isVirtual) {
		this.isVirtual = isVirtual;
	}

	@Override
	public String toString() {
		return String.format("Node-%d", nodeID);
	}
}
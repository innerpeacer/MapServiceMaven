package cn.platalk.brtmap.entity.base.impl;

import cn.platalk.brtmap.entity.base.TYIRouteNodeRecord;

public class TYRouteNodeRecord implements TYIRouteNodeRecord {
	static final String FIELD_ROUTE_NODE_1_NODE_ID = "NODE_ID";
	static final String FIELD_ROUTE_NODE_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_NODE_3_VIRTUAL = "VIRTUAL";

	public int nodeID;
	public byte[] nodeGeometryData;
	public boolean isVirtual;

	public TYRouteNodeRecord() {
	}

	// public void parseJson(JSONObject jsonObject) {
	// if (jsonObject != null) {
	// nodeID = jsonObject.optInt(FIELD_ROUTE_NODE_1_NODE_ID);
	// // nodeGeometryData = TYBase64Encoding.decodeString(geometryString);
	// try {
	// String geometryString = jsonObject
	// .optString(FIELD_ROUTE_NODE_2_GEOMETRY);
	// nodeGeometryData = TYGZipUtils.decompress(TYBase64Encoding
	// .decodeString(geometryString));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// isVirtual = jsonObject.optBoolean(FIELD_ROUTE_NODE_3_VIRTUAL);
	// }
	// }
	//
	// public JSONObject buildJson() {
	// JSONObject jsonObject = new JSONObject();
	// try {
	// jsonObject.put(FIELD_ROUTE_NODE_1_NODE_ID, nodeID);
	// // String geometryString = TYBase64Encoding
	// // .encodeBytes(nodeGeometryData);
	// String geometryString = TYBase64Encoding.encodeBytes(TYGZipUtils
	// .compress(nodeGeometryData));
	// jsonObject.put(FIELD_ROUTE_NODE_2_GEOMETRY, geometryString);
	// jsonObject.put(FIELD_ROUTE_NODE_3_VIRTUAL, isVirtual);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return jsonObject;
	// }

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
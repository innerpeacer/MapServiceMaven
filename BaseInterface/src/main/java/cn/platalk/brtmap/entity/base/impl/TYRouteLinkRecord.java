package cn.platalk.brtmap.entity.base.impl;

import cn.platalk.brtmap.entity.base.TYIRouteLinkRecord;

public class TYRouteLinkRecord implements TYIRouteLinkRecord {

	static final String FIELD_ROUTE_LINK_1_LINK_ID = "LINK_ID";
	static final String FIELD_ROUTE_LINK_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_LINK_3_LENGTH = "LENGTH";
	static final String FIELD_ROUTE_LINK_4_HEAD_NODE = "HEAD_NODE";
	static final String FIELD_ROUTE_LINK_5_END_NODE = "END_NODE";
	static final String FIELD_ROUTE_LINK_6_VIRTUAL = "VIRTUAL";
	static final String FIELD_ROUTE_LINK_7_ONE_WAY = "ONE_WAY";

	public int linkID;
	public byte[] linkGeometryData;

	public double length;
	public int headNode;
	public int endNode;
	public boolean isVirtual;
	public boolean isOneWay;

	public TYRouteLinkRecord() {
	}

	// public void parseJson(JSONObject jsonObject) {
	// if (jsonObject != null) {
	// linkID = jsonObject.optInt(FIELD_ROUTE_LINK_1_LINK_ID);
	// try {
	// String geometryString = jsonObject
	// .optString(FIELD_ROUTE_LINK_2_GEOMETRY);
	// linkGeometryData = TYGZipUtils.decompress(TYBase64Encoding
	// .decodeString(geometryString));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// // linkGeometryData = TYBase64Encoding.decodeString(geometryString);
	// length = jsonObject.optDouble(FIELD_ROUTE_LINK_3_LENGTH);
	// headNode = jsonObject.optInt(FIELD_ROUTE_LINK_4_HEAD_NODE);
	// endNode = jsonObject.optInt(FIELD_ROUTE_LINK_5_END_NODE);
	// isVirtual = jsonObject.optBoolean(FIELD_ROUTE_LINK_6_VIRTUAL);
	// isOneWay = jsonObject.optBoolean(FIELD_ROUTE_LINK_7_ONE_WAY);
	// }
	// }
	//
	// public JSONObject buildJson() {
	// JSONObject jsonObject = new JSONObject();
	// try {
	// jsonObject.put(FIELD_ROUTE_LINK_1_LINK_ID, linkID);
	// String geometryString = TYBase64Encoding.encodeBytes(TYGZipUtils
	// .compress(linkGeometryData));
	// jsonObject.put(FIELD_ROUTE_LINK_2_GEOMETRY, geometryString);
	// // jsonObject.put(FIELD_ROUTE_LINK_2_GEOMETRY, geometryString);
	// jsonObject.put(FIELD_ROUTE_LINK_3_LENGTH, length);
	// jsonObject.put(FIELD_ROUTE_LINK_4_HEAD_NODE, headNode);
	// jsonObject.put(FIELD_ROUTE_LINK_5_END_NODE, endNode);
	// jsonObject.put(FIELD_ROUTE_LINK_6_VIRTUAL, isVirtual);
	// jsonObject.put(FIELD_ROUTE_LINK_7_ONE_WAY, isOneWay);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return jsonObject;
	// }

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

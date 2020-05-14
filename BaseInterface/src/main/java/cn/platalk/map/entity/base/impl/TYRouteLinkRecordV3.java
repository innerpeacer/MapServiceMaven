package cn.platalk.map.entity.base.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.foundation.TYGeojsonBuilder;
import cn.platalk.map.entity.base.TYIRouteLinkRecordV3;

public class TYRouteLinkRecordV3 implements TYIRouteLinkRecordV3 {
	static final WKBReader reader = new WKBReader();

	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_LINK_ID = "linkID";
	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_HEAD_NODE = "headNode";
	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_END_NODE = "endNode";
	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_IS_ONE_WAY = "isOneWay";
	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_LINK_NAME = "linkName";
	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_FLOOR = "floor";
	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_LEVEL = "level";
	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_REVERSE = "reverse";
	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_ROOM_ID = "roomID";
	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_OPEN = "open";
	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_OPEN_TIME = "openTime";
	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_ALLOW_SNAP = "allowSnap";
	static final String KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_LINK_TYPE = "linkType";

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

	@Override
	public String getLinkID() {
		return linkID;
	}

	@Override
	public void setLinkID(String linkID) {
		this.linkID = linkID;
	}

	@Override
	public byte[] getGeometryData() {
		return linkGeometryData;
	}

	@Override
	public void setGeometryData(byte[] linkGeometryData) {
		this.linkGeometryData = linkGeometryData;
	}

	@Override
	public double getLength() {
		return length;
	}

	@Override
	public void setLength(double length) {
		this.length = length;
	}

	@Override
	public String getHeadNode() {
		return headNode;
	}

	@Override
	public void setHeadNode(String headNode) {
		this.headNode = headNode;
	}

	@Override
	public String getEndNode() {
		return endNode;
	}

	@Override
	public void setEndNode(String endNode) {
		this.endNode = endNode;
	}

	@Override
	public boolean isOneWay() {
		return isOneWay;
	}

	@Override
	public void setOneWay(boolean isOneWay) {
		this.isOneWay = isOneWay;
	}

	@Override
	public String getLinkName() {
		return linkName;
	}

	@Override
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	@Override
	public int getFloor() {
		return floor;
	}

	@Override
	public void setFloor(int floor) {
		this.floor = floor;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public boolean isReverse() {
		return reverse;
	}

	@Override
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	@Override
	public String getRoomID() {
		return roomID;
	}

	@Override
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public String getOpenTime() {
		return openTime;
	}

	@Override
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	@Override
	public boolean isAllowSnap() {
		return allowSnap;
	}

	@Override
	public void setAllowSnap(boolean allowSnap) {
		this.allowSnap = allowSnap;
	}

	@Override
	public String getLinkType() {
		return linkType;
	}

	@Override
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	@Override
	public JSONObject toGeojson() {
		Geometry geometry = null;
		try {
			geometry = reader.read(getGeometryData());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (geometry == null) {
			return null;
		}
		return TYGeojsonBuilder.buildGeometry(geometry, linkPropertyMap());
	}

	Map<String, Object> linkPropertyMap() {
		Map<String, Object> propMap = new HashMap<>();
		propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_LINK_ID, getLinkID());
		propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_HEAD_NODE, getHeadNode());
		propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_END_NODE, getEndNode());
		propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_IS_ONE_WAY, isOneWay());
		if (getLinkName() != null) {
			propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_LINK_NAME, getLinkName());
		} else {
			propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_LINK_NAME, "null");
		}
		propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_FLOOR, getFloor());
		propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_LEVEL, getLevel());
		propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_REVERSE, isReverse());

		if (getRoomID() != null) {
			propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_ROOM_ID, getRoomID());
		} else {
			propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_ROOM_ID, "null");
		}
		propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_OPEN, isOpen());

		propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_OPEN_TIME, getOpenTime());
		propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_ALLOW_SNAP, isAllowSnap());
		propMap.put(KEY_GEOJSON_ROUTE_LINK_DATA_ATTRIBUTE_LINK_TYPE, getLinkType());
		return propMap;
	}
}

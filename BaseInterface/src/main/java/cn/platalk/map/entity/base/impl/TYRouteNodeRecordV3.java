package cn.platalk.map.entity.base.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.foundation.TYGeojsonBuilder;
import cn.platalk.map.entity.base.TYIRouteNodeRecordV3;

public class TYRouteNodeRecordV3 implements TYIRouteNodeRecordV3 {
	static final WKBReader reader = new WKBReader();

	static final String KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_NODE_ID = "nodeID";
	static final String KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_NODE_NAME = "nodeName";
	static final String KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_CATEGORY_ID = "categoryID";
	static final String KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_FLOOR = "floor";
	static final String KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_LEVEL = "level";
	static final String KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_IS_SWITCHING = "isSwitching";
	static final String KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_SWITCHING_ID = "switchingID";
	static final String KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_DIRECTION = "direction";
	static final String KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_NODE_TYPE = "nodeType";
	static final String KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_OPEN = "open";
	static final String KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_OPEN_TIME = "openTime";
	static final String KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_ROOM_ID = "roomID";

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

	@Override
	public String getNodeID() {
		return nodeID;
	}

	@Override
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	@Override
	public byte[] getGeometryData() {
		return nodeGeometryData;
	}

	@Override
	public void setGeometryData(byte[] nodeGeometryData) {
		this.nodeGeometryData = nodeGeometryData;
	}

	@Override
	public String getNodeName() {
		return nodeName;
	}

	@Override
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Override
	public String getCategoryID() {
		return categoryID;
	}

	@Override
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
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
	public boolean isSwitching() {
		return isSwitching;
	}

	@Override
	public void setSwitching(boolean isSwitching) {
		this.isSwitching = isSwitching;
	}

	@Override
	public int getSwitchingID() {
		return switchingID;
	}

	@Override
	public void setSwitchingID(int switchingID) {
		this.switchingID = switchingID;
	}

	@Override
	public int getDirection() {
		return direction;
	}

	@Override
	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public int getNodeType() {
		return nodeType;
	}

	@Override
	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
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
	public String getRoomID() {
		return roomID;
	}

	@Override
	public void setRoomID(String roomID) {
		this.roomID = roomID;
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
		return TYGeojsonBuilder.buildGeometry(geometry, nodePropertyMap());
	}

	Map<String, Object> nodePropertyMap() {
		Map<String, Object> propMap = new HashMap<>();
		propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_NODE_ID, getNodeID());
		if (getNodeName() != null) {
			propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_NODE_NAME, getNodeName());
		} else {
			propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_NODE_NAME, "null");
		}
		propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_CATEGORY_ID, getCategoryID());
		propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_FLOOR, getFloor());
		propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_LEVEL, getLevel());
		propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_IS_SWITCHING, isSwitching());
		propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_SWITCHING_ID, getSwitchingID());
		propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_DIRECTION, getDirection());
		propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_NODE_TYPE, getNodeType());
		propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_OPEN, isOpen());
		propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_OPEN_TIME, getOpenTime());
		if (getRoomID() != null) {
			propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_ROOM_ID, getRoomID());
		} else {
			propMap.put(KEY_GEOJSON_ROUTE_NODE_DATA_ATTRIBUTE_ROOM_ID, "null");
		}
		return propMap;
	}

	@Override
	public String toString() {
		return String.format("NodeID: %s, Floor: %d", nodeID, floor);
	}
}

package cn.platalk.brtmap.db.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.brtmap.entity.base.impl.TYRouteNodeRecordV3;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlRouteNodeV3Params {
	static final String TABLE_ROUTE_NODE = "ROUTE_NODE_%s";

	static final String FIELD_ROUTE_NODE_1_NODE_ID = "NODE_ID";
	static final String FIELD_ROUTE_NODE_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_NODE_3_VIRTUAL = "IS_VIRTUAL";

	static final String FIELD_ROUTE_NODE_4_NODE_NAME = "NODE_NAME";
	static final String FIELD_ROUTE_NODE_5_CATEGORY_ID = "CATEGORY_ID";
	static final String FIELD_ROUTE_NODE_6_FLOOR = "FLOOR";
	static final String FIELD_ROUTE_NODE_7_LEVEL = "LEVEL";
	static final String FIELD_ROUTE_NODE_8_IS_SWITCHING = "IS_SWITCHING";
	static final String FIELD_ROUTE_NODE_9_SWITCHING_ID = "SWITCHING_ID";
	static final String FIELD_ROUTE_NODE_10_DIRECTION = "DIRECTION";
	static final String FIELD_ROUTE_NODE_11_NODE_TYPE = "NODE_TYPE";
	static final String FIELD_ROUTE_NODE_12_OPEN = "OPEN";
	static final String FIELD_ROUTE_NODE_13_OPEN_TIME = "OPEN_TIME";
	static final String FIELD_ROUTE_NODE_14_ROOM_ID = "ROOM_ID";

	private static List<IPSqlField> routeNodeFieldList = null;

	public static IPSqlTable CreateTable(String buildingID) {
		return new IPSqlTable(String.format(TABLE_ROUTE_NODE, buildingID), GetRouteNodeFieldList(), null);
	}

	public static List<IPSqlField> GetRouteNodeFieldList() {
		if (routeNodeFieldList == null) {
			routeNodeFieldList = new ArrayList<IPSqlField>();
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_1_NODE_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_2_GEOMETRY,
					new IPSqlFieldType(byte[].class.getName(), "MediumBlob"), false));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_3_VIRTUAL,
					new IPSqlFieldType(Boolean.class.getName(), "INTEGER"), false));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_4_NODE_NAME,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_5_CATEGORY_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_6_FLOOR,
					new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_7_LEVEL,
					new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_8_IS_SWITCHING,
					new IPSqlFieldType(Boolean.class.getName(), "INTEGER(1)"), false));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_9_SWITCHING_ID,
					new IPSqlFieldType(Integer.class.getName(), "INTEGER"), true));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_10_DIRECTION,
					new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_11_NODE_TYPE,
					new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_12_OPEN,
					new IPSqlFieldType(Boolean.class.getName(), "INTEGER(1)"), false));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_13_OPEN_TIME,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(255)"), true));
			routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_14_ROOM_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), true));

		}
		return routeNodeFieldList;
	}

	public static List<TYRouteNodeRecordV3> RouteNodeListFromRecords(List<IPSqlRecord> records) {
		List<TYRouteNodeRecordV3> nodeList = new ArrayList<TYRouteNodeRecordV3>();
		for (IPSqlRecord record : records) {
			TYRouteNodeRecordV3 node = new TYRouteNodeRecordV3();
			node.setNodeID(record.getString(FIELD_ROUTE_NODE_1_NODE_ID));
			node.setGeometryData(record.getBlob(FIELD_ROUTE_NODE_2_GEOMETRY));
			node.setNodeName(record.getString(FIELD_ROUTE_NODE_4_NODE_NAME));
			node.setCategoryID(record.getString(FIELD_ROUTE_NODE_5_CATEGORY_ID));
			node.setFloor(record.getInteger(FIELD_ROUTE_NODE_6_FLOOR));
			node.setLevel(record.getInteger(FIELD_ROUTE_NODE_7_LEVEL));
			node.setSwitching(record.getBoolean(FIELD_ROUTE_NODE_8_IS_SWITCHING));
			node.setSwitchingID(record.getInteger(FIELD_ROUTE_NODE_9_SWITCHING_ID));
			node.setDirection(record.getInteger(FIELD_ROUTE_NODE_10_DIRECTION));
			node.setNodeType(record.getInteger(FIELD_ROUTE_NODE_11_NODE_TYPE));
			node.setOpen(record.getBoolean(FIELD_ROUTE_NODE_12_OPEN));
			node.setOpenTime(record.getString(FIELD_ROUTE_NODE_13_OPEN_TIME));
			node.setRoomID(record.getString(FIELD_ROUTE_NODE_14_ROOM_ID));
			nodeList.add(node);
		}
		return nodeList;
	}

	public static Map<String, Object> DataMapFromRouteNodeRecord(TYIRouteNodeRecordV3 record) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_ROUTE_NODE_1_NODE_ID, record.getNodeID());
		data.put(FIELD_ROUTE_NODE_2_GEOMETRY, record.getGeometryData());
		data.put(FIELD_ROUTE_NODE_3_VIRTUAL, false);
		data.put(FIELD_ROUTE_NODE_4_NODE_NAME, record.getNodeName());
		data.put(FIELD_ROUTE_NODE_5_CATEGORY_ID, record.getCategoryID());
		data.put(FIELD_ROUTE_NODE_6_FLOOR, record.getFloor());
		data.put(FIELD_ROUTE_NODE_7_LEVEL, record.getLevel());
		data.put(FIELD_ROUTE_NODE_8_IS_SWITCHING, record.isSwitching());
		data.put(FIELD_ROUTE_NODE_9_SWITCHING_ID, record.getSwitchingID());
		data.put(FIELD_ROUTE_NODE_10_DIRECTION, record.getDirection());
		data.put(FIELD_ROUTE_NODE_11_NODE_TYPE, record.getNodeType());
		data.put(FIELD_ROUTE_NODE_12_OPEN, record.isOpen());
		data.put(FIELD_ROUTE_NODE_13_OPEN_TIME, record.getOpenTime());
		data.put(FIELD_ROUTE_NODE_14_ROOM_ID, record.getRoomID());
		return data;
	}

}

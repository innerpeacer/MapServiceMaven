package cn.platalk.mysql.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.impl.TYRouteLinkRecordV3;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlRouteLinkV3Params {
	static final String TABLE_ROUTE_LINK = "ROUTE_LINK_%s";

	static final String FIELD_ROUTE_LINK_1_LINK_ID = "LINK_ID";
	static final String FIELD_ROUTE_LINK_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_LINK_3_LENGTH = "LENGTH";
	static final String FIELD_ROUTE_LINK_4_HEAD_NODE = "HEAD_NODE";
	static final String FIELD_ROUTE_LINK_5_END_NODE = "END_NODE";
	static final String FIELD_ROUTE_LINK_6_VIRTUAL = "IS_VIRTUAL";
	static final String FIELD_ROUTE_LINK_7_ONE_WAY = "ONE_WAY";
	static final String FIELD_ROUTE_LINK_8_LINK_NAME = "LINK_NAME";
	static final String FIELD_ROUTE_LINK_9_FLOOR = "FLOOR";
	static final String FIELD_ROUTE_LINK_10_LEVEL = "LEVEL";
	static final String FIELD_ROUTE_LINK_11_REVERSE = "REVERSE";
	static final String FIELD_ROUTE_LINK_12_ROOM_ID = "ROOM_ID";
	static final String FIELD_ROUTE_LINK_13_OPEN = "OPEN";
	static final String FIELD_ROUTE_LINK_14_OPEN_TIME = "OPEN_TIME";
	static final String FIELD_ROUTE_LINK_15_ALLOW_SNAP = "ALLOW_SNAP";
	static final String FIELD_ROUTE_LINK_16_LINK_TYPE = "LINK_TYPE";

	private static final List<IPSqlField> routeLinkFieldList = new ArrayList<>();
	static {
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_1_LINK_ID,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_2_GEOMETRY,
				new IPSqlFieldType(byte[].class.getName(), "MediumBlob"), false));
		routeLinkFieldList.add(
				new IPSqlField(FIELD_ROUTE_LINK_3_LENGTH, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));

		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_4_HEAD_NODE,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_5_END_NODE,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));

		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_6_VIRTUAL,
				new IPSqlFieldType(Boolean.class.getName(), "INTEGER"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_7_ONE_WAY,
				new IPSqlFieldType(Boolean.class.getName(), "INTEGER(1)"), false));

		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_8_LINK_NAME,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_9_FLOOR,
				new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_10_LEVEL,
				new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_11_REVERSE,
				new IPSqlFieldType(Boolean.class.getName(), "INTEGER(1)"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_12_ROOM_ID,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), true));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_13_OPEN,
				new IPSqlFieldType(Boolean.class.getName(), "INTEGER(1)"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_14_OPEN_TIME,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(255)"), true));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_15_ALLOW_SNAP,
				new IPSqlFieldType(Boolean.class.getName(), "INTEGER(1)"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_16_LINK_TYPE,
				new IPSqlFieldType(String.class.getName(), "VARCHAR(45)"), false));
	}

	public static IPSqlTable CreateTable(String buildingID) {
		return new IPSqlTable(String.format(TABLE_ROUTE_LINK, buildingID), GetRouteLinkFieldList(), null);
	}

	public synchronized static List<IPSqlField> GetRouteLinkFieldList() {
		return routeLinkFieldList;
	}

	public static List<TYRouteLinkRecordV3> RouteLinkListFromRecords(List<IPSqlRecord> records) {
		List<TYRouteLinkRecordV3> linkList = new ArrayList<>();
		for (IPSqlRecord record : records) {
			TYRouteLinkRecordV3 link = new TYRouteLinkRecordV3();
			link.setLinkID(record.getString(FIELD_ROUTE_LINK_1_LINK_ID));
			link.setGeometryData(record.getBlob(FIELD_ROUTE_LINK_2_GEOMETRY));
			link.setLength(record.getDouble(FIELD_ROUTE_LINK_3_LENGTH));
			link.setHeadNode(record.getString(FIELD_ROUTE_LINK_4_HEAD_NODE));
			link.setEndNode(record.getString(FIELD_ROUTE_LINK_5_END_NODE));
			link.setOneWay(record.getBoolean(FIELD_ROUTE_LINK_7_ONE_WAY));
			link.setLinkName(record.getString(FIELD_ROUTE_LINK_8_LINK_NAME));
			link.setFloor(record.getInteger(FIELD_ROUTE_LINK_9_FLOOR));
			link.setLevel(record.getInteger(FIELD_ROUTE_LINK_10_LEVEL));
			link.setReverse(record.getBoolean(FIELD_ROUTE_LINK_11_REVERSE));
			link.setRoomID(record.getString(FIELD_ROUTE_LINK_12_ROOM_ID));
			link.setOpen(record.getBoolean(FIELD_ROUTE_LINK_13_OPEN));
			link.setOpenTime(record.getString(FIELD_ROUTE_LINK_14_OPEN_TIME));
			link.setAllowSnap(record.getBoolean(FIELD_ROUTE_LINK_15_ALLOW_SNAP));
			link.setLinkType(record.getString(FIELD_ROUTE_LINK_16_LINK_TYPE));
			linkList.add(link);
		}
		return linkList;
	}

	public static Map<String, Object> DataMapFromRouteLinkRecord(TYIRouteLinkRecordV3 record) {
		Map<String, Object> data = new HashMap<>();
		data.put(FIELD_ROUTE_LINK_1_LINK_ID, record.getLinkID());
		data.put(FIELD_ROUTE_LINK_2_GEOMETRY, record.getGeometryData());
		data.put(FIELD_ROUTE_LINK_3_LENGTH, record.getLength());
		data.put(FIELD_ROUTE_LINK_4_HEAD_NODE, record.getHeadNode());
		data.put(FIELD_ROUTE_LINK_5_END_NODE, record.getEndNode());
		data.put(FIELD_ROUTE_LINK_6_VIRTUAL, false);
		data.put(FIELD_ROUTE_LINK_7_ONE_WAY, record.isOneWay());
		data.put(FIELD_ROUTE_LINK_8_LINK_NAME, record.getLinkName());
		data.put(FIELD_ROUTE_LINK_9_FLOOR, record.getFloor());
		data.put(FIELD_ROUTE_LINK_10_LEVEL, record.getLevel());
		data.put(FIELD_ROUTE_LINK_11_REVERSE, record.isReverse());
		data.put(FIELD_ROUTE_LINK_12_ROOM_ID, record.getRoomID());
		data.put(FIELD_ROUTE_LINK_13_OPEN, record.isOpen());
		data.put(FIELD_ROUTE_LINK_14_OPEN_TIME, record.getOpenTime());
		data.put(FIELD_ROUTE_LINK_15_ALLOW_SNAP, record.isAllowSnap());
		data.put(FIELD_ROUTE_LINK_16_LINK_TYPE, record.getLinkType());
		return data;
	}
}

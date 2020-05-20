package cn.platalk.mysql.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.TYRouteLinkRecord;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlRouteLinkParams {
	static final String TABLE_ROUTE_LINK = "ROUTE_LINK_%s";

	static final String FIELD_ROUTE_LINK_1_LINK_ID = "LINK_ID";
	static final String FIELD_ROUTE_LINK_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_LINK_3_LENGTH = "LENGTH";
	static final String FIELD_ROUTE_LINK_4_HEAD_NODE = "HEAD_NODE";
	static final String FIELD_ROUTE_LINK_5_END_NODE = "END_NODE";
	static final String FIELD_ROUTE_LINK_6_VIRTUAL = "IS_VIRTUAL";
	static final String FIELD_ROUTE_LINK_7_ONE_WAY = "ONE_WAY";

	private static final List<IPSqlField> routeLinkFieldList = new ArrayList<>();
	static {
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_1_LINK_ID,
				new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_2_GEOMETRY,
				new IPSqlFieldType(byte[].class.getName(), "MediumBlob"), false));
		routeLinkFieldList.add(
				new IPSqlField(FIELD_ROUTE_LINK_3_LENGTH, new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_4_HEAD_NODE,
				new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_5_END_NODE,
				new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_6_VIRTUAL,
				new IPSqlFieldType(Boolean.class.getName(), "INTEGER"), false));
		routeLinkFieldList.add(new IPSqlField(FIELD_ROUTE_LINK_7_ONE_WAY,
				new IPSqlFieldType(Boolean.class.getName(), "INTEGER"), false));
	}

	public static IPSqlTable CreateTable(String buildingID) {
		return new IPSqlTable(String.format(TABLE_ROUTE_LINK, buildingID), GetRouteLinkFieldList(), null);
	}

	public static List<IPSqlField> GetRouteLinkFieldList() {
		return routeLinkFieldList;
	}

	public static List<TYRouteLinkRecord> RouteLinkListFromRecords(List<IPSqlRecord> records) {
		List<TYRouteLinkRecord> linkList = new ArrayList<>();
		for (IPSqlRecord record : records) {
			TYRouteLinkRecord link = new TYRouteLinkRecord();
			link.setLinkID(record.getInteger(FIELD_ROUTE_LINK_1_LINK_ID));
			link.setGeometryData(record.getBlob(FIELD_ROUTE_LINK_2_GEOMETRY));
			link.setLength(record.getDouble(FIELD_ROUTE_LINK_3_LENGTH));
			link.setHeadNode(record.getInteger(FIELD_ROUTE_LINK_4_HEAD_NODE));
			link.setEndNode(record.getInteger(FIELD_ROUTE_LINK_5_END_NODE));
			link.setVirtual(record.getBoolean(FIELD_ROUTE_LINK_6_VIRTUAL));
			link.setOneWay(record.getBoolean(FIELD_ROUTE_LINK_7_ONE_WAY));
			linkList.add(link);
		}
		return linkList;
	}

	public static Map<String, Object> DataMapFromRouteLinkRecord(TYRouteLinkRecord record) {
		Map<String, Object> data = new HashMap<>();
		data.put(FIELD_ROUTE_LINK_1_LINK_ID, record.getLinkID());
		data.put(FIELD_ROUTE_LINK_2_GEOMETRY, record.getGeometryData());
		data.put(FIELD_ROUTE_LINK_3_LENGTH, record.getLength());
		data.put(FIELD_ROUTE_LINK_4_HEAD_NODE, record.getHeadNode());
		data.put(FIELD_ROUTE_LINK_5_END_NODE, record.getEndNode());
		data.put(FIELD_ROUTE_LINK_6_VIRTUAL, record.isVirtual());
		data.put(FIELD_ROUTE_LINK_7_ONE_WAY, record.isOneWay());
		return data;
	}
}

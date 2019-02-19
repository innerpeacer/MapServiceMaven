package cn.platalk.brtmap.db.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.impl.TYRouteLinkRecord;
import cn.platalk.sqlhelper.sql.SqlField;
import cn.platalk.sqlhelper.sql.SqlFieldType;
import cn.platalk.sqlhelper.sql.SqlRecord;
import cn.platalk.sqlhelper.sql.SqlTable;

public class MysqlRouteLinkParams {
	static final String TABLE_ROUTE_LINK = "ROUTE_LINK_%s";

	static final String FIELD_ROUTE_LINK_1_LINK_ID = "LINK_ID";
	static final String FIELD_ROUTE_LINK_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_LINK_3_LENGTH = "LENGTH";
	static final String FIELD_ROUTE_LINK_4_HEAD_NODE = "HEAD_NODE";
	static final String FIELD_ROUTE_LINK_5_END_NODE = "END_NODE";
	static final String FIELD_ROUTE_LINK_6_VIRTUAL = "IS_VIRTUAL";
	static final String FIELD_ROUTE_LINK_7_ONE_WAY = "ONE_WAY";

	private static List<SqlField> routeLinkFieldList = null;

	public static SqlTable CreateTable(String buildingID) {
		return new SqlTable(String.format(TABLE_ROUTE_LINK, buildingID), GetRouteLinkFieldList(), null);
	}

	public static List<SqlField> GetRouteLinkFieldList() {
		if (routeLinkFieldList == null) {
			routeLinkFieldList = new ArrayList<SqlField>();
			routeLinkFieldList.add(new SqlField(FIELD_ROUTE_LINK_1_LINK_ID,
					new SqlFieldType(Integer.class.getName(), "INTEGER"), false));
			routeLinkFieldList.add(new SqlField(FIELD_ROUTE_LINK_2_GEOMETRY,
					new SqlFieldType(byte[].class.getName(), "MediumBlob"), false));
			routeLinkFieldList.add(
					new SqlField(FIELD_ROUTE_LINK_3_LENGTH, new SqlFieldType(Double.class.getName(), "DOUBLE"), false));
			routeLinkFieldList.add(new SqlField(FIELD_ROUTE_LINK_4_HEAD_NODE,
					new SqlFieldType(Integer.class.getName(), "INTEGER"), false));
			routeLinkFieldList.add(new SqlField(FIELD_ROUTE_LINK_5_END_NODE,
					new SqlFieldType(Integer.class.getName(), "INTEGER"), false));
			routeLinkFieldList.add(new SqlField(FIELD_ROUTE_LINK_6_VIRTUAL,
					new SqlFieldType(Boolean.class.getName(), "INTEGER"), false));
			routeLinkFieldList.add(new SqlField(FIELD_ROUTE_LINK_7_ONE_WAY,
					new SqlFieldType(Boolean.class.getName(), "INTEGER"), false));
		}
		return routeLinkFieldList;
	}

	public static List<TYRouteLinkRecord> RouteLinkListFromRecords(List<SqlRecord> records) {
		List<TYRouteLinkRecord> linkList = new ArrayList<TYRouteLinkRecord>();
		for (SqlRecord record : records) {
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
		Map<String, Object> data = new HashMap<String, Object>();
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

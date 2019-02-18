package cn.platalk.brtmap.db.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.impl.TYRouteLinkRecord;
import cn.platalk.sqlhelper.mysql.MysqlField;
import cn.platalk.sqlhelper.mysql.MysqlFieldType;
import cn.platalk.sqlhelper.mysql.MysqlRecord;
import cn.platalk.sqlhelper.mysql.MysqlTable;

public class MysqlRouteLinkParams {
	static final String TABLE_ROUTE_LINK = "ROUTE_LINK_%s";

	static final String FIELD_ROUTE_LINK_1_LINK_ID = "LINK_ID";
	static final String FIELD_ROUTE_LINK_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_LINK_3_LENGTH = "LENGTH";
	static final String FIELD_ROUTE_LINK_4_HEAD_NODE = "HEAD_NODE";
	static final String FIELD_ROUTE_LINK_5_END_NODE = "END_NODE";
	static final String FIELD_ROUTE_LINK_6_VIRTUAL = "IS_VIRTUAL";
	static final String FIELD_ROUTE_LINK_7_ONE_WAY = "ONE_WAY";

	private static List<MysqlField> routeLinkFieldList = null;

	public static MysqlTable CreateTable(String buildingID) {
		return new MysqlTable(String.format(TABLE_ROUTE_LINK, buildingID), GetRouteLinkFieldList(), null);
	}

	public static List<MysqlField> GetRouteLinkFieldList() {
		if (routeLinkFieldList == null) {
			routeLinkFieldList = new ArrayList<MysqlField>();
			routeLinkFieldList.add(new MysqlField(FIELD_ROUTE_LINK_1_LINK_ID,
					new MysqlFieldType(Integer.class.getName(), "INTEGER"), false));
			routeLinkFieldList.add(new MysqlField(FIELD_ROUTE_LINK_2_GEOMETRY,
					new MysqlFieldType(byte[].class.getName(), "MediumBlob"), false));
			routeLinkFieldList.add(new MysqlField(FIELD_ROUTE_LINK_3_LENGTH,
					new MysqlFieldType(Double.class.getName(), "DOUBLE"), false));
			routeLinkFieldList.add(new MysqlField(FIELD_ROUTE_LINK_4_HEAD_NODE,
					new MysqlFieldType(Integer.class.getName(), "INTEGER"), false));
			routeLinkFieldList.add(new MysqlField(FIELD_ROUTE_LINK_5_END_NODE,
					new MysqlFieldType(Integer.class.getName(), "INTEGER"), false));
			routeLinkFieldList.add(new MysqlField(FIELD_ROUTE_LINK_6_VIRTUAL,
					new MysqlFieldType(Boolean.class.getName(), "INTEGER"), false));
			routeLinkFieldList.add(new MysqlField(FIELD_ROUTE_LINK_7_ONE_WAY,
					new MysqlFieldType(Boolean.class.getName(), "INTEGER"), false));
		}
		return routeLinkFieldList;
	}

	public static List<TYRouteLinkRecord> RouteLinkListFromRecords(List<MysqlRecord> records) {
		List<TYRouteLinkRecord> linkList = new ArrayList<TYRouteLinkRecord>();
		for (MysqlRecord record : records) {
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

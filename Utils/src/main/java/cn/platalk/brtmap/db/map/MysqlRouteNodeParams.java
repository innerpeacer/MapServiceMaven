package cn.platalk.brtmap.db.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.impl.TYRouteNodeRecord;
import cn.platalk.sqlhelper.mysql.MysqlField;
import cn.platalk.sqlhelper.mysql.MysqlFieldType;
import cn.platalk.sqlhelper.mysql.MysqlRecord;
import cn.platalk.sqlhelper.mysql.MysqlTable;

public class MysqlRouteNodeParams {
	static final String TABLE_ROUTE_NODE = "ROUTE_NODE_%s";

	static final String FIELD_ROUTE_NODE_1_NODE_ID = "NODE_ID";
	static final String FIELD_ROUTE_NODE_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_NODE_3_VIRTUAL = "IS_VIRTUAL";

	private static List<MysqlField> routeNodeFieldList = null;

	public static MysqlTable CreateTable(String buildingID) {
		return new MysqlTable(String.format(TABLE_ROUTE_NODE, buildingID), GetRouteNodeFieldList(), null);
	}

	public static List<MysqlField> GetRouteNodeFieldList() {
		if (routeNodeFieldList == null) {
			routeNodeFieldList = new ArrayList<MysqlField>();
			routeNodeFieldList.add(new MysqlField(FIELD_ROUTE_NODE_1_NODE_ID,
					new MysqlFieldType(Integer.class.getName(), "INTEGER"), false));
			routeNodeFieldList.add(new MysqlField(FIELD_ROUTE_NODE_2_GEOMETRY,
					new MysqlFieldType(byte[].class.getName(), "MediumBlob"), false));
			routeNodeFieldList.add(new MysqlField(FIELD_ROUTE_NODE_3_VIRTUAL,
					new MysqlFieldType(Boolean.class.getName(), "INTEGER"), false));
		}
		return routeNodeFieldList;
	}

	public static List<TYRouteNodeRecord> RouteNodeListFromRecords(List<MysqlRecord> records) {
		List<TYRouteNodeRecord> nodeList = new ArrayList<TYRouteNodeRecord>();
		for (MysqlRecord record : records) {
			TYRouteNodeRecord node = new TYRouteNodeRecord();
			node.setNodeID(record.getInteger(FIELD_ROUTE_NODE_1_NODE_ID));
			node.setGeometryData(record.getBlob(FIELD_ROUTE_NODE_2_GEOMETRY));
			node.setVirtual(record.getBoolean(FIELD_ROUTE_NODE_3_VIRTUAL));
			nodeList.add(node);
		}
		return nodeList;
	}

	public static Map<String, Object> DataMapFromRouteNodeRecord(TYRouteNodeRecord record) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_ROUTE_NODE_1_NODE_ID, record.getNodeID());
		data.put(FIELD_ROUTE_NODE_2_GEOMETRY, record.getGeometryData());
		data.put(FIELD_ROUTE_NODE_3_VIRTUAL, record.isVirtual());
		return data;
	}

}

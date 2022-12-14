package cn.platalk.mysql.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.map.TYRouteNodeRecord;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlRouteNodeParams {
	static final String TABLE_ROUTE_NODE = "ROUTE_NODE_%s";

	static final String FIELD_ROUTE_NODE_1_NODE_ID = "NODE_ID";
	static final String FIELD_ROUTE_NODE_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_NODE_3_VIRTUAL = "IS_VIRTUAL";

	private static final List<IPSqlField> routeNodeFieldList = new ArrayList<>();
	static {
		routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_1_NODE_ID,
				new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
		routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_2_GEOMETRY,
				new IPSqlFieldType(byte[].class.getName(), "MediumBlob"), false));
		routeNodeFieldList.add(new IPSqlField(FIELD_ROUTE_NODE_3_VIRTUAL,
				new IPSqlFieldType(Boolean.class.getName(), "INTEGER"), false));
	}

	public static IPSqlTable CreateTable(String buildingID) {
		return new IPSqlTable(String.format(TABLE_ROUTE_NODE, buildingID), GetRouteNodeFieldList(), null);
	}

	public static List<IPSqlField> GetRouteNodeFieldList() {
		return routeNodeFieldList;
	}

	public static List<TYRouteNodeRecord> RouteNodeListFromRecords(List<IPSqlRecord> records) {
		List<TYRouteNodeRecord> nodeList = new ArrayList<>();
		for (IPSqlRecord record : records) {
			TYRouteNodeRecord node = new TYRouteNodeRecord();
			node.setNodeID(record.getInteger(FIELD_ROUTE_NODE_1_NODE_ID));
			node.setGeometryData(record.getBlob(FIELD_ROUTE_NODE_2_GEOMETRY));
			node.setVirtual(record.getBoolean(FIELD_ROUTE_NODE_3_VIRTUAL));
			nodeList.add(node);
		}
		return nodeList;
	}

	public static Map<String, Object> DataMapFromRouteNodeRecord(TYRouteNodeRecord record) {
		Map<String, Object> data = new HashMap<>();
		data.put(FIELD_ROUTE_NODE_1_NODE_ID, record.getNodeID());
		data.put(FIELD_ROUTE_NODE_2_GEOMETRY, record.getGeometryData());
		data.put(FIELD_ROUTE_NODE_3_VIRTUAL, record.isVirtual());
		return data;
	}

}

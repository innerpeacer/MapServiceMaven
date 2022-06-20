package cn.platalk.mysql.map;

import cn.platalk.map.entity.base.map.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.map.TYIRouteNodeRecordV3;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TYRouteDBAdapterV3 {
	final String buildingID;
	final IPMysqlDB db;
	final IPSqlTable routeLinkTable;
	final IPSqlTable routeNodeTable;

	public TYRouteDBAdapterV3(String buildingID) {
		this.buildingID = buildingID;
		db = new IPMysqlDB(TYDatabaseManager.GetRouteDBUrl(), TYDatabaseManager.GetUserName(), TYDatabaseManager.GetPassword());
		routeLinkTable = IPMysqlRouteLinkV3Params.CreateTable(buildingID);
		routeNodeTable = IPMysqlRouteNodeV3Params.CreateTable(buildingID);
	}

	public void connectDB() {
		db.connectDB();
	}

	public void disconnectDB() {
		db.disconnectDB();
	}

	public void createTableIfNotExist() {
		db.createTable(routeLinkTable);
		db.createTable(routeNodeTable);
	}

	public void eraseRouteTable() {
		db.eraseTable(routeLinkTable);
		db.eraseTable(routeNodeTable);
	}

	public int insertRouteLinkRecordsInBatch(List<TYIRouteLinkRecordV3> recordList) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		for (TYIRouteLinkRecordV3 record : recordList) {
			dataList.add(IPMysqlRouteLinkV3Params.DataMapFromRouteLinkRecord(record));
		}
		return db.insertDataListInBatch(routeLinkTable, dataList);
	}

	public int insertRouteNodeRecordsInBatch(List<TYIRouteNodeRecordV3> recordList) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		for (TYIRouteNodeRecordV3 record : recordList) {
			dataList.add(IPMysqlRouteNodeV3Params.DataMapFromRouteNodeRecord(record));
		}
		return db.insertDataListInBatch(routeNodeTable, dataList);
	}

	int insertLinkRecord(TYIRouteLinkRecordV3 record) {
		return db.insertData(routeLinkTable, IPMysqlRouteLinkV3Params.DataMapFromRouteLinkRecord(record));
	}

	int insertNodeRecord(TYIRouteNodeRecordV3 record) {
		return db.insertData(routeNodeTable, IPMysqlRouteNodeV3Params.DataMapFromRouteNodeRecord(record));
	}

	public List<TYIRouteLinkRecordV3> getAllLinkRecords() {
		List<TYIRouteLinkRecordV3> result = new ArrayList<>();
		result.addAll(IPMysqlRouteLinkV3Params.RouteLinkListFromRecords(db.readData(routeLinkTable)));
		return result;
	}

	public List<TYIRouteNodeRecordV3> getAllNodeRecords() {
		List<TYIRouteNodeRecordV3> result = new ArrayList<>();
		result.addAll(IPMysqlRouteNodeV3Params.RouteNodeListFromRecords(db.readData(routeNodeTable)));
		return result;
	}
}

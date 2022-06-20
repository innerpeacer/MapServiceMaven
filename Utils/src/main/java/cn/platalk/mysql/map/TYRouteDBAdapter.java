package cn.platalk.mysql.map;

import cn.platalk.map.entity.base.impl.map.TYRouteLinkRecord;
import cn.platalk.map.entity.base.impl.map.TYRouteNodeRecord;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TYRouteDBAdapter {
	final String buildingID;
	final IPMysqlDB db;
	final IPSqlTable routeLinkTable;
	final IPSqlTable routeNodeTable;

	public TYRouteDBAdapter(String buildingID) {
		this.buildingID = buildingID;
		db = new IPMysqlDB(TYDatabaseManager.GetRouteDBUrl(), TYDatabaseManager.GetUserName(), TYDatabaseManager.GetPassword());
		routeLinkTable = IPMysqlRouteLinkParams.CreateTable(buildingID);
		routeNodeTable = IPMysqlRouteNodeParams.CreateTable(buildingID);
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

	public int insertRouteLinkRecordsInBatch(List<TYRouteLinkRecord> recordList) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		for (TYRouteLinkRecord record : recordList) {
			dataList.add(IPMysqlRouteLinkParams.DataMapFromRouteLinkRecord(record));
		}
		return db.insertDataListInBatch(routeLinkTable, dataList);
	}

	public int insertRouteNodeRecordsInBatch(List<TYRouteNodeRecord> recordList) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		for (TYRouteNodeRecord record : recordList) {
			dataList.add(IPMysqlRouteNodeParams.DataMapFromRouteNodeRecord(record));
		}
		return db.insertDataListInBatch(routeNodeTable, dataList);
	}

	int insertLinkRecord(TYRouteLinkRecord record) {
		return db.insertData(routeLinkTable, IPMysqlRouteLinkParams.DataMapFromRouteLinkRecord(record));
	}

	int insertNodeRecord(TYRouteNodeRecord record) {
		return db.insertData(routeNodeTable, IPMysqlRouteNodeParams.DataMapFromRouteNodeRecord(record));
	}

	public List<TYRouteLinkRecord> getAllLinkRecords() {
		return IPMysqlRouteLinkParams.RouteLinkListFromRecords(db.readData(routeLinkTable));
	}

	public List<TYRouteNodeRecord> getAllNodeRecords() {
		return IPMysqlRouteNodeParams.RouteNodeListFromRecords(db.readData(routeNodeTable));
	}
}

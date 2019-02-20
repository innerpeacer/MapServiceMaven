package cn.platalk.brtmap.db.map;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.impl.TYRouteLinkRecord;
import cn.platalk.brtmap.entity.base.impl.TYRouteNodeRecord;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class TYRouteDBAdapter {
	String buildingID;
	IPMysqlDB db;
	IPSqlTable routeLinkTable;
	IPSqlTable routeNodeTable;

	public TYRouteDBAdapter(String buildingID) {
		this.buildingID = buildingID;
		db = new IPMysqlDB(TYDatabaseManager.GetRouteDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		routeLinkTable = IPMysqlRouteLinkParams.CreateTable(buildingID);
		routeNodeTable = IPMysqlRouteNodeParams.CreateTable(buildingID);
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection connectDB() {
		return db.connectDB();
	}

	public void disconnectDB() {
		db.disconnectDB();
	}

	public void createTableIfNotExist() {
		db.createTable(routeLinkTable);
		db.createTable(routeNodeTable);
	}

	// private void createRouteLinkTableIfNotExist() {
	// String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_LINK + "(_id INT NOT
	// NULL AUTO_INCREMENT,"
	// + "LINK_ID INTEGER NOT NULL,"
	// // + "GEOMETRY BLOB NOT NULL,"
	// + "GEOMETRY MediumBlob NOT NULL," + "LENGTH DOUBLE NOT NULL," +
	// "HEAD_NODE INTEGER NOT NULL, "
	// + "END_NODE INTEGER NOT NULL, " + "IS_VIRTUAL INTEGER NOT NULL, " +
	// "ONE_WAY INTEGER NOT NULL, "
	// + " PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));";
	//
	// try {
	// Statement stmt = connection.createStatement();
	// stmt.executeUpdate(sql);
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }

	// private void createRouteNodeTableIfNotExist() {
	// String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NODE + "(_id INT NOT
	// NULL AUTO_INCREMENT,"
	// + "NODE_ID INTEGER NOT NULL,"
	// // + "GEOMETRY BLOB NOT NULL,"
	// + "GEOMETRY MediumBlob NOT NULL," + "IS_VIRTUAL INTEGER NOT NULL, " + "
	// PRIMARY KEY (_id),"
	// + " UNIQUE INDEX _id_UNIQUE (_id ASC));";
	// try {
	// Statement stmt = connection.createStatement();
	// stmt.executeUpdate(sql);
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }

	public void eraseRouteTable() {
		db.eraseTable(routeLinkTable);
		db.eraseTable(routeNodeTable);
	}

	public int insertRouteLinkRecordsInBatch(List<TYRouteLinkRecord> recordList) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (TYRouteLinkRecord record : recordList) {
			dataList.add(IPMysqlRouteLinkParams.DataMapFromRouteLinkRecord(record));
		}
		return db.insertDataListInBatch(routeLinkTable, dataList);
	}

	public int insertRouteNodeRecordsInBatch(List<TYRouteNodeRecord> recordList) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
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

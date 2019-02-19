package cn.platalk.brtmap.db.map;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.platalk.brtmap.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.sqlhelper.mysql.MysqlDB;
import cn.platalk.sqlhelper.sql.SqlTable;

public class TYRouteDBAdapterV3 {
	String buildingID;
	MysqlDB db;
	SqlTable routeLinkTable;
	SqlTable routeNodeTable;

	public TYRouteDBAdapterV3(String buildingID) {
		this.buildingID = buildingID;
		db = new MysqlDB(TYDatabaseManager.GetRouteDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		routeLinkTable = MysqlRouteLinkV3Params.CreateTable(buildingID);
		routeNodeTable = MysqlRouteNodeV3Params.CreateTable(buildingID);
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
	// + "LINK_ID VARCHAR(45) NOT NULL," + "GEOMETRY MediumBlob NOT NULL," +
	// "LENGTH DOUBLE NOT NULL,"
	// + "HEAD_NODE VARCHAR(45) NOT NULL, " + "END_NODE VARCHAR(45) NOT NULL, "
	// + "IS_VIRTUAL INTEGER NOT NULL, " + "ONE_WAY INTEGER(1) NOT NULL DEFAULT
	// 0, "
	// + "LINK_NAME VARCHAR(45), " + "FLOOR INTEGER NOT NULL, " + "LEVEL INTEGER
	// NOT NULL DEFAULT 0, "
	// + "REVERSE INTEGER(1) NOT NULL DEFAULT 0, " + "ROOM_ID VARCHAR(45), "
	// + "OPEN INTEGER(1) NOT NULL DEFAULT 1, " + "OPEN_TIME VARCHAR(255), "
	// + "ALLOW_SNAP INTEGER(1) NOT NULL DEFAULT 1, " + "LINK_TYPE VARCHAR(45)
	// NOT NULL, "
	// + " PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));";
	//
	// try {
	// Statement stmt = connection.createStatement();
	// stmt.executeUpdate(sql);
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// private void createRouteNodeTableIfNotExist() {
	// String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NODE + "(_id INT NOT
	// NULL AUTO_INCREMENT,"
	// + "NODE_ID VARCHAR(45) NOT NULL," + "GEOMETRY MediumBlob NOT NULL," +
	// "IS_VIRTUAL INTEGER NOT NULL, "
	// + "NODE_NAME VARCHAR(45), " + "CATEGORY_ID VARCHAR(45), " + "FLOOR
	// INTEGER NOT NULL, "
	// + "LEVEL INTEGER NOT NULL, " + "IS_SWITCHING INTEGER(1) NOT NULL, " +
	// "SWITCHING_ID INTEGER, "
	// + "DIRECTION INTEGER NOT NULL, " + "NODE_TYPE INTEGER NOT NULL, " + "OPEN
	// INTEGER(1) NOT NULL, "
	// + "OPEN_TIME VARCHAR(255), " + "ROOM_ID VARCHAR(45), " + " PRIMARY KEY
	// (_id),"
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

	public int insertRouteLinkRecordsInBatch(List<TYIRouteLinkRecordV3> recordList) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (TYIRouteLinkRecordV3 record : recordList) {
			dataList.add(MysqlRouteLinkV3Params.DataMapFromRouteLinkRecord(record));
		}
		return db.insertDataListInBatch(routeLinkTable, dataList);
	}

	public int insertRouteNodeRecordsInBatch(List<TYIRouteNodeRecordV3> recordList) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (TYIRouteNodeRecordV3 record : recordList) {
			dataList.add(MysqlRouteNodeV3Params.DataMapFromRouteNodeRecord(record));
		}
		return db.insertDataListInBatch(routeNodeTable, dataList);
	}

	int insertLinkRecord(TYIRouteLinkRecordV3 record) {
		return db.insertData(routeLinkTable, MysqlRouteLinkV3Params.DataMapFromRouteLinkRecord(record));
	}

	int insertNodeRecord(TYIRouteNodeRecordV3 record) {
		return db.insertData(routeNodeTable, MysqlRouteNodeV3Params.DataMapFromRouteNodeRecord(record));
	}

	public List<TYIRouteLinkRecordV3> getAllLinkRecords() {
		List<TYIRouteLinkRecordV3> result = new ArrayList<TYIRouteLinkRecordV3>();
		result.addAll(MysqlRouteLinkV3Params.RouteLinkListFromRecords(db.readData(routeLinkTable)));
		return result;
	}

	public List<TYIRouteNodeRecordV3> getAllNodeRecords() {
		List<TYIRouteNodeRecordV3> result = new ArrayList<TYIRouteNodeRecordV3>();
		result.addAll(MysqlRouteNodeV3Params.RouteNodeListFromRecords(db.readData(routeNodeTable)));
		return result;
	}
}

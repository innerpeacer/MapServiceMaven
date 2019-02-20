package cn.platalk.brtmap.db.map;

import java.sql.Connection;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class TYSymbolDBAdapter {
	IPMysqlDB db;
	IPSqlTable fillTable;
	IPSqlTable iconTable;

	public TYSymbolDBAdapter() {
		db = new IPMysqlDB(TYDatabaseManager.GetMapDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		fillTable = IPMysqlFillSymbolParams.CreateTable();
		iconTable = IPMysqlIconSymbolParams.CreateTable();

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
		// String sql = null;
		//
		// sql = "CREATE TABLE IF NOT EXISTS " + TABLE_FILL_SYMBOL + " (_id INT
		// NOT NULL AUTO_INCREMENT, "
		// + "SYMBOL_ID INT NOT NULL, " + "FILL VARCHAR(45) NOT NULL, " +
		// "OUTLINE VARCHAR(45) NOT NULL, "
		// + "LINE_WIDTH DOUBLE NOT NULL," + "BUILDING_ID VARCHAR(45) NOT NULL,"
		// + " PRIMARY KEY (_id),"
		// + " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		// try {
		// Statement stmt = connection.createStatement();
		// stmt.executeUpdate(sql);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		//
		// sql = "CREATE TABLE IF NOT EXISTS " + TABLE_ICON_SYMBOL + " (_id INT
		// NOT NULL AUTO_INCREMENT, "
		// + "SYMBOL_ID INT NOT NULL, " + "ICON VARCHAR(45) NOT NULL, " +
		// "BUILDING_ID VARCHAR(45) NOT NULL,"
		// + " PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		//
		// try {
		// Statement stmt = connection.createStatement();
		// stmt.executeUpdate(sql);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		db.createTable(fillTable);
		db.createTable(iconTable);
	}

	public void eraseSymbolTable() {
		db.eraseTable(fillTable);
		db.eraseTable(iconTable);
	}

	public void deleteSymbols(String buildingID) {
		db.deleteRecord(fillTable, fillTable.getField(IPMysqlFillSymbolParams.FIELD_MAP_SYMBOL_FILL_5_BUILDING_ID),
				buildingID);
		db.deleteRecord(iconTable, iconTable.getField(IPMysqlIconSymbolParams.FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID),
				buildingID);
	}

	public int insertFillSymbolRecords(List<TYFillSymbolRecord> recordList, String buildingID) {
		int count = 0;
		for (TYFillSymbolRecord record : recordList) {
			count += insertFillSymbolRecord(record, buildingID);
		}
		return count;
	}

	public int insertIconSymbolRecords(List<TYIconSymbolRecord> recordList, String buildingID) {
		int count = 0;
		for (TYIconSymbolRecord record : recordList) {
			count += insertIconSymbolRecord(record, buildingID);
		}
		return count;
	}

	int insertFillSymbolRecord(TYFillSymbolRecord record, String buildingID) {
		return db.insertData(fillTable, IPMysqlFillSymbolParams.DataMapFromFillSymbol(record, buildingID));
	}

	int insertIconSymbolRecord(TYIconSymbolRecord record, String buildingID) {
		return db.insertData(iconTable, IPMysqlIconSymbolParams.DataMapFromIconSymbol(record, buildingID));
	}

	public List<TYFillSymbolRecord> getFillSymbolRecords(String buildingID) {
		return IPMysqlFillSymbolParams.FillSymbolListFromRecords(db.readData(fillTable,
				fillTable.getField(IPMysqlFillSymbolParams.FIELD_MAP_SYMBOL_FILL_5_BUILDING_ID), buildingID));
	}

	public List<TYIconSymbolRecord> getIconSymbolRecords(String buildingID) {
		return IPMysqlIconSymbolParams.IconSymbolListFromRecords(db.readData(iconTable,
				iconTable.getField(IPMysqlIconSymbolParams.FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID), buildingID));
	}
}

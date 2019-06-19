package cn.platalk.mysql.map;

import java.sql.Connection;
import java.util.List;

import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconTextSymbolRecord;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class TYSymbolDBAdapter {
	IPMysqlDB db;
	IPSqlTable fillTable;
	IPSqlTable iconTable;
	IPSqlTable iconTextTable;

	public TYSymbolDBAdapter() {
		db = new IPMysqlDB(TYDatabaseManager.GetMapDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		fillTable = IPMysqlFillSymbolParams.CreateTable();
		iconTable = IPMysqlIconSymbolParams.CreateTable();
		iconTextTable = IPMysqlIconTextSymbolParams.CreateTable();
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
		db.createTable(fillTable);
		db.createTable(iconTable);
		db.createTable(iconTextTable);
	}

	public void eraseSymbolTable() {
		db.eraseTable(fillTable);
		db.eraseTable(iconTable);
		db.eraseTable(iconTextTable);
	}

	public void deleteSymbols(String buildingID) {
		db.deleteRecord(fillTable, fillTable.getField(IPMysqlFillSymbolParams.FIELD_MAP_SYMBOL_FILL_5_BUILDING_ID),
				buildingID);
		db.deleteRecord(iconTable, iconTable.getField(IPMysqlIconSymbolParams.FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID),
				buildingID);
		db.deleteRecord(iconTextTable,
				iconTextTable.getField(IPMysqlIconTextSymbolParams.FIELD_MAP_SYMBOL_ICON_TEXT_100_BUILDING_ID),
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

	public int insertIconTextSymbolRecords(List<TYIconTextSymbolRecord> recordList, String buildingID) {
		int count = 0;
		for (TYIconTextSymbolRecord record : recordList) {
			count += insertIconTextSymbolRecord(record, buildingID);
		}
		return count;
	}

	int insertFillSymbolRecord(TYFillSymbolRecord record, String buildingID) {
		return db.insertData(fillTable, IPMysqlFillSymbolParams.DataMapFromFillSymbol(record, buildingID));
	}

	int insertIconSymbolRecord(TYIconSymbolRecord record, String buildingID) {
		return db.insertData(iconTable, IPMysqlIconSymbolParams.DataMapFromIconSymbol(record, buildingID));
	}

	int insertIconTextSymbolRecord(TYIconTextSymbolRecord record, String buildingID) {
		return db.insertData(iconTextTable, IPMysqlIconTextSymbolParams.DataMapFromIconTextSymbol(record, buildingID));
	}

	public List<TYFillSymbolRecord> getFillSymbolRecords(String buildingID) {
		return IPMysqlFillSymbolParams.FillSymbolListFromRecords(db.readData(fillTable,
				fillTable.getField(IPMysqlFillSymbolParams.FIELD_MAP_SYMBOL_FILL_5_BUILDING_ID), buildingID));
	}

	public List<TYIconSymbolRecord> getIconSymbolRecords(String buildingID) {
		return IPMysqlIconSymbolParams.IconSymbolListFromRecords(db.readData(iconTable,
				iconTable.getField(IPMysqlIconSymbolParams.FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID), buildingID));
	}

	public List<TYIconTextSymbolRecord> getIconTextSymbolRecords(String buildingID) {
		return IPMysqlIconTextSymbolParams.IconTextSymbolListFromRecords(db.readData(iconTextTable,
				iconTextTable.getField(IPMysqlIconTextSymbolParams.FIELD_MAP_SYMBOL_ICON_TEXT_100_BUILDING_ID),
				buildingID));
	}
}

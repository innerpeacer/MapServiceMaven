package cn.platalk.mysql.map;

import cn.platalk.map.entity.base.impl.map.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.map.TYIconTextSymbolRecord;
import cn.platalk.map.entity.base.impl.map.TYTheme;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlTable;
import cn.platalk.sqlite.map.IPSqliteThemeParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TYThemeDBAdapter {
	final IPMysqlDB db;
	final IPSqlTable themeTable;
	final IPSqlTable fillTable;
	final IPSqlTable iconTextTable;

	public TYThemeDBAdapter() {
		db = new IPMysqlDB(TYDatabaseManager.GetMapDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		themeTable = IPMysqlThemeParams.CreateTable();
		fillTable = IPMysqlThemeFillSymbolParams.CreateTable();
		iconTextTable = IPMysqlThemeIconTextSymbolParams.CreateTable();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void connectDB() {
		db.connectDB();
	}

	public void disconnectDB() {
		db.disconnectDB();
	}

	public void createTableIfNotExist() {
		db.createTable(themeTable);
		db.createTable(fillTable);
		db.createTable(iconTextTable);
	}

	public void eraseThemeTable() {
		db.eraseTable(themeTable);
		db.eraseTable(fillTable);
		db.eraseTable(iconTextTable);
	}

	public int insertOrUpdateTheme(TYTheme theme) {
		if (!existTheme(theme.getThemeID())) {
			return insertTheme(theme);
		} else {
			return updateTheme(theme);
		}
	}

	public void deleteTheme(String themeID) {
		db.deleteRecord(themeTable, themeTable.getField(IPMysqlThemeParams.FIELD_THEME_1_ID), themeID);
		db.deleteRecord(fillTable, fillTable.getField(IPMysqlThemeFillSymbolParams.FIELD_MAP_SYMBOL_FILL_101_THEME_ID), themeID);
		db.deleteRecord(iconTextTable,
				iconTextTable.getField(IPMysqlThemeIconTextSymbolParams.FIELD_MAP_SYMBOL_ICON_TEXT_101_THEME_ID), themeID);
	}

	public int insertFillSymbolRecords(List<TYFillSymbolRecord> recordList, String themeID) {
		int count = 0;
		for (TYFillSymbolRecord record : recordList) {
			count += insertFillSymbolRecord(record, themeID);
		}
		return count;
	}

	public int insertIconTextSymbolRecords(List<TYIconTextSymbolRecord> recordList, String themeID) {
		int count = 0;
		for (TYIconTextSymbolRecord record : recordList) {
			count += insertIconTextSymbolRecord(record, themeID);
		}
		return count;
	}

	int insertTheme(TYTheme theme) {
		return db.insertData(themeTable, IPMysqlThemeParams.DataMapFromTheme(theme));
	}

	int updateTheme(TYTheme theme) {
		return db.updateData(themeTable, IPMysqlThemeParams.DataMapFromTheme(theme), themeTable.getField(IPMysqlThemeParams.FIELD_THEME_1_ID), theme.getThemeID());
	}

	int insertFillSymbolRecord(TYFillSymbolRecord record, String themeID) {
		return db.insertData(fillTable, IPMysqlThemeFillSymbolParams.DataMapFromFillSymbol(record, themeID));
	}

	int insertIconTextSymbolRecord(TYIconTextSymbolRecord record, String themeID) {
		return db.insertData(iconTextTable, IPMysqlThemeIconTextSymbolParams.DataMapFromIconTextSymbol(record, themeID));
	}

	public List<TYFillSymbolRecord> getFillSymbolRecords(String themeID) {
		return IPMysqlThemeFillSymbolParams.FillSymbolListFromRecords(db.readData(fillTable,
				fillTable.getField(IPMysqlThemeFillSymbolParams.FIELD_MAP_SYMBOL_FILL_101_THEME_ID), themeID));
	}

	public List<TYIconTextSymbolRecord> getIconTextSymbolRecords(String themeID) {
		return IPMysqlThemeIconTextSymbolParams.IconTextSymbolListFromRecords(db.readData(iconTextTable,
				iconTextTable.getField(IPMysqlThemeIconTextSymbolParams.FIELD_MAP_SYMBOL_ICON_TEXT_101_THEME_ID),
				themeID));
	}

	public void deleteFillSymbolRecords(String themeID) {
		db.deleteRecord(fillTable, fillTable.getField(IPMysqlThemeFillSymbolParams.FIELD_MAP_SYMBOL_FILL_101_THEME_ID), themeID);
	}

	public void deleteIconTextSymbolRecords(String themeID) {
		db.deleteRecord(iconTextTable, iconTextTable.getField(IPMysqlThemeIconTextSymbolParams.FIELD_MAP_SYMBOL_ICON_TEXT_101_THEME_ID), themeID);
	}

	public boolean existTheme(String themeID) {
		Map<IPSqlField, Object> clause = new HashMap<>();
		clause.put(themeTable.getField(IPMysqlThemeParams.FIELD_THEME_1_ID), themeID);
		return db.existRecord(themeTable, clause);
	}
}

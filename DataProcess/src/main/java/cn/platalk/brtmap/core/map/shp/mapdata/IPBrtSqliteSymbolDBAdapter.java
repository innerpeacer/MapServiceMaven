package cn.platalk.brtmap.core.map.shp.mapdata;

import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.sqlhelper.sqlite.SqliteDB;
import cn.platalk.sqlhelper.sqlite.SqliteTable;

public class IPBrtSqliteSymbolDBAdapter {
	SqliteDB db;
	SqliteTable fillTable;
	SqliteTable iconTable;

	public IPBrtSqliteSymbolDBAdapter(String path) {
		db = new SqliteDB(path);
		fillTable = new SqliteTable(SqliteFillSymbolParams.TABLE_FILL_SYMBOL,
				SqliteFillSymbolParams.GetFillSymbolFieldList(), null);
		iconTable = new SqliteTable(SqliteIconSymbolParams.TABLE_ICON_SYMBOL,
				SqliteIconSymbolParams.GetIconSymbolFieldList(), null);
	}

	public boolean open() {
		return db.open();
	}

	public void close() {
		db.close();
	}

	public List<TYFillSymbolRecord> getFillSymbolRecords() {
		return SqliteFillSymbolParams.FillSymbolListFromRecords(db.readData(fillTable));
	}

	public List<TYIconSymbolRecord> getIconSymbolRecords() {
		return SqliteIconSymbolParams.IconSymbolListFromRecords(db.readData(iconTable));
	}

	boolean existTable(String table) {
		return db.existTable(table);
	}
}

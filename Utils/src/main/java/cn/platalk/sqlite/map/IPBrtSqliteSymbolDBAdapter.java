package cn.platalk.sqlite.map;

import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.sqlhelper.sql.SqlTable;
import cn.platalk.sqlhelper.sqlite.SqliteDB;

public class IPBrtSqliteSymbolDBAdapter {
	SqliteDB db;
	SqlTable fillTable;
	SqlTable iconTable;

	public IPBrtSqliteSymbolDBAdapter(String path) {
		db = new SqliteDB(path);
		fillTable = new SqlTable(IPSqliteFillSymbolParams.TABLE_FILL_SYMBOL,
				IPSqliteFillSymbolParams.GetFillSymbolFieldList(), null);
		iconTable = new SqlTable(IPSqliteIconSymbolParams.TABLE_ICON_SYMBOL,
				IPSqliteIconSymbolParams.GetIconSymbolFieldList(), null);
	}

	public boolean open() {
		return db.open();
	}

	public void close() {
		db.close();
	}

	public List<TYFillSymbolRecord> getFillSymbolRecords() {
		return IPSqliteFillSymbolParams.FillSymbolListFromRecords(db.readData(fillTable));
	}

	public List<TYIconSymbolRecord> getIconSymbolRecords() {
		return IPSqliteIconSymbolParams.IconSymbolListFromRecords(db.readData(iconTable));
	}
}

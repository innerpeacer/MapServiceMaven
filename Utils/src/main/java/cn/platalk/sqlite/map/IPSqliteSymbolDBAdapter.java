package cn.platalk.sqlite.map;

import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;
import cn.platalk.sqlhelper.sqlite.IPSqliteDB;

public class IPSqliteSymbolDBAdapter {
	IPSqliteDB db;
	IPSqlTable fillTable;
	IPSqlTable iconTable;

	public IPSqliteSymbolDBAdapter(String path) {
		db = new IPSqliteDB(path);
		fillTable = new IPSqlTable(IPSqliteFillSymbolParams.TABLE_FILL_SYMBOL,
				IPSqliteFillSymbolParams.GetFillSymbolFieldList(), null);
		iconTable = new IPSqlTable(IPSqliteIconSymbolParams.TABLE_ICON_SYMBOL,
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
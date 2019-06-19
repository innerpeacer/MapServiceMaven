package cn.platalk.sqlite.map;

import java.util.List;

import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconTextSymbolRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;
import cn.platalk.sqlhelper.sqlite.IPSqliteDB;

public class IPSqliteSymbolDBAdapter {
	IPSqliteDB db;
	IPSqlTable fillTable;
	IPSqlTable iconTable;
	IPSqlTable iconTextTable;

	public IPSqliteSymbolDBAdapter(String path) {
		db = new IPSqliteDB(path);
		fillTable = new IPSqlTable(IPSqliteFillSymbolParams.TABLE_FILL_SYMBOL,
				IPSqliteFillSymbolParams.GetFillSymbolFieldList(), null);
		iconTable = new IPSqlTable(IPSqliteIconSymbolParams.TABLE_ICON_SYMBOL,
				IPSqliteIconSymbolParams.GetIconSymbolFieldList(), null);
		iconTextTable = new IPSqlTable(IPSqliteIconTextSymbolParams.TABLE_ICON_TEXT_SYMBOL,
				IPSqliteIconTextSymbolParams.GetIconTextSymbolFieldList(), null);
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

	public List<TYIconTextSymbolRecord> getIconTextSymbolRecords() {
		return IPSqliteIconTextSymbolParams.IconTextSymbolListFromRecords(db.readData(iconTextTable));
	}
}

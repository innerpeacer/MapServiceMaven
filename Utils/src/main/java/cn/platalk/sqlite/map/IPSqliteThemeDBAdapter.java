package cn.platalk.sqlite.map;

import cn.platalk.map.entity.base.impl.map.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.map.TYIconTextSymbolRecord;
import cn.platalk.map.entity.base.impl.map.TYTheme;
import cn.platalk.sqlhelper.sql.IPSqlTable;
import cn.platalk.sqlhelper.sqlite.IPSqliteDB;

import java.util.List;

public class IPSqliteThemeDBAdapter {
	final IPSqliteDB db;
	final IPSqlTable themeTable;
	final IPSqlTable fillTable;
	final IPSqlTable iconTextTable;

	public IPSqliteThemeDBAdapter(String path) {
		db = new IPSqliteDB(path);
		themeTable = new IPSqlTable(IPSqliteThemeParams.TABLE_THEME,
				IPSqliteThemeParams.GetThemeFieldList(), null);
		fillTable = new IPSqlTable(IPSqliteFillSymbolParams.TABLE_FILL_SYMBOL,
				IPSqliteFillSymbolParams.GetFillSymbolFieldList(), null);
		iconTextTable = new IPSqlTable(IPSqliteIconTextSymbolParams.TABLE_ICON_TEXT_SYMBOL,
				IPSqliteIconTextSymbolParams.GetIconTextSymbolFieldList(), null);
	}

	public boolean open() {
		return db.open();
	}

	public void close() {
		db.close();
	}

	public List<TYTheme> getThemeRecords() {
		return IPSqliteThemeParams.ThemeListFromRecords(db.readData(themeTable));
	}

	public List<TYFillSymbolRecord> getFillSymbolRecords() {
		return IPSqliteFillSymbolParams.FillSymbolListFromRecords(db.readData(fillTable));
	}

	public List<TYIconTextSymbolRecord> getIconTextSymbolRecords() {
		return IPSqliteIconTextSymbolParams.IconTextSymbolListFromRecords(db.readData(iconTextTable));
	}
}

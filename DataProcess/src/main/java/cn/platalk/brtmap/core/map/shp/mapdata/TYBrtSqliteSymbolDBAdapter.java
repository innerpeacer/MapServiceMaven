package cn.platalk.brtmap.core.map.shp.mapdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;

public class TYBrtSqliteSymbolDBAdapter {
	static final String TABLE_FILL_SYMBOL = "FILL_SYMBOL";
	static final String TABLE_ICON_SYMBOL = "ICON_SYMBOL";

	static final String FIELD_MAP_SYMBOL_FILL_0_PRIMARY_KEY = "_id";
	static final String FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID = "SYMBOL_ID";
	static final String FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR = "FILL";
	static final String FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR = "OUTLINE";
	static final String FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH = "LINE_WIDTH";

	static final String FIELD_MAP_SYMBOL_ICON_0_PRIMARY_KEY = "_id";
	static final String FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID = "SYMBOL_ID";
	static final String FIELD_MAP_SYMBOL_ICON_2_ICON = "ICON";

	private Connection connection;
	private String dbPath;

	public TYBrtSqliteSymbolDBAdapter(String path) {
		try {
			Class.forName("org.sqlite.JDBC");
			dbPath = path;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean open() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
		} catch (SQLException e) {
			e.printStackTrace();
			connection = null;
		}
		return (connection != null);
	}

	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		connection = null;
	}

	public List<TYFillSymbolRecord> getFillSymbolRecords() {
		String sql = String.format("select * from %s", TABLE_FILL_SYMBOL);
		return queryFillSymbolRecords(sql);
	}

	public List<TYIconSymbolRecord> getIconSymbolRecords() {
		String sql = String.format("select * from %s", TABLE_ICON_SYMBOL);
		return queryIconSymbolsRecords(sql);
	}

	List<TYFillSymbolRecord> queryFillSymbolRecords(String sql) {
		List<TYFillSymbolRecord> resultRecordList = new ArrayList<TYFillSymbolRecord>();
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				TYFillSymbolRecord fillSymbol = new TYFillSymbolRecord();
				fillSymbol.symbolID = rs
						.getInt(FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID);
				fillSymbol.fillColor = rs
						.getString(FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR);
				fillSymbol.outlineColor = rs
						.getString(FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR);
				fillSymbol.lineWidth = rs
						.getDouble(FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH);
				resultRecordList.add(fillSymbol);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultRecordList;
	}

	List<TYIconSymbolRecord> queryIconSymbolsRecords(String sql) {
		List<TYIconSymbolRecord> resultRecordList = new ArrayList<TYIconSymbolRecord>();
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				TYIconSymbolRecord iconSymbol = new TYIconSymbolRecord();
				iconSymbol.symbolID = rs
						.getInt(FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID);
				iconSymbol.icon = rs.getString(FIELD_MAP_SYMBOL_ICON_2_ICON);
				resultRecordList.add(iconSymbol);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultRecordList;
	}

	boolean existTable(String table) {
		try {
			ResultSet rs = connection.getMetaData().getTables(null, null,
					table, null);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

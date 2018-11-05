package cn.platalk.brtmap.db.map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;

public class TYSymbolDBAdapter {
	static final String TABLE_FILL_SYMBOL = "FILL_SYMBOL";
	static final String TABLE_ICON_SYMBOL = "ICON_SYMBOL";

	static final String FIELD_FILL_SYMBOL_0_PRIMARY_KEY = "_id";
	static final String FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID = "SYMBOL_ID";
	static final String FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR = "FILL";
	static final String FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR = "OUTLINE";
	static final String FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH = "LINE_WIDTH";
	static final String FIELD_MAP_SYMBOL_FILL_5_BUILDING_ID = "BUILDING_ID";

	static final String FIELD_MAP_SYMBOL_ICON_0_PRIMARY_KEY = "_id";
	static final String FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID = "SYMBOL_ID";
	static final String FIELD_MAP_SYMBOL_ICON_2_ICON = "ICON";
	static final String FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID = "BUILDING_ID";

	private Connection connection;

	public TYSymbolDBAdapter() {
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
		try {
			connection = DriverManager.getConnection(TYDatabaseManager.GetMapDBUrl(), TYDatabaseManager.GetUserName(),
					TYDatabaseManager.GetPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void disconnectDB() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection = null;
	}

	public void createTableIfNotExist() {
		String sql = null;

		sql = "CREATE TABLE IF NOT EXISTS " + TABLE_FILL_SYMBOL + " (_id INT NOT NULL AUTO_INCREMENT, "
				+ "SYMBOL_ID INT NOT NULL, " + "FILL VARCHAR(45) NOT NULL, " + "OUTLINE VARCHAR(45) NOT NULL, "
				+ "LINE_WIDTH DOUBLE NOT NULL," + "BUILDING_ID VARCHAR(45) NOT NULL," + " PRIMARY KEY (_id),"
				+ " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sql = "CREATE TABLE IF NOT EXISTS " + TABLE_ICON_SYMBOL + " (_id INT NOT NULL AUTO_INCREMENT, "
				+ "SYMBOL_ID INT NOT NULL, " + "ICON VARCHAR(45) NOT NULL, " + "BUILDING_ID VARCHAR(45) NOT NULL,"
				+ " PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));";

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eraseSymbolTable() {
		String sql = null;
		sql = String.format("delete from %s", TABLE_FILL_SYMBOL);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sql = String.format("delete from %s", TABLE_ICON_SYMBOL);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteSymbols(String buildingID) {
		String sql = null;
		sql = String.format("delete from %s where %s='%s'", TABLE_FILL_SYMBOL, FIELD_MAP_SYMBOL_FILL_5_BUILDING_ID,
				buildingID);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sql = String.format("delete from %s where %s='%s'", TABLE_ICON_SYMBOL, FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID,
				buildingID);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		int result = 0;
		String sql = String.format("insert into %s (%s, %s, %s, %s, %s) values (?,?,?,?,?)", TABLE_FILL_SYMBOL,
				FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID, FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR,
				FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR, FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH,
				FIELD_MAP_SYMBOL_FILL_5_BUILDING_ID);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setInt(1, record.symbolID);
			stmt.setString(2, record.fillColor);
			stmt.setString(3, record.outlineColor);
			stmt.setDouble(4, record.lineWidth);
			stmt.setString(5, buildingID);
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	int insertIconSymbolRecord(TYIconSymbolRecord record, String buildingID) {
		int result = 0;
		String sql = String.format("insert into %s (%s, %s, %s) values (?,?,?)", TABLE_ICON_SYMBOL,
				FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID, FIELD_MAP_SYMBOL_ICON_2_ICON, FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setInt(1, record.symbolID);
			stmt.setString(2, record.icon);
			stmt.setString(3, buildingID);

			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<TYFillSymbolRecord> getFillSymbolRecords(String buildingID) {
		String sql = String.format("select * from %s where %s='%s'", TABLE_FILL_SYMBOL,
				FIELD_MAP_SYMBOL_FILL_5_BUILDING_ID, buildingID);
		return queryFillSymbolRecords(sql);
	}

	public List<TYIconSymbolRecord> getIconSymbolRecords(String buildingID) {
		String sql = String.format("select * from %s where %s='%s'", TABLE_ICON_SYMBOL,
				FIELD_MAP_SYMBOL_ICON_3_BUILDING_ID, buildingID);
		return queryIconSymbolsRecords(sql);
	}

	private List<TYFillSymbolRecord> queryFillSymbolRecords(String sql) {
		List<TYFillSymbolRecord> resultRecordList = new ArrayList<TYFillSymbolRecord>();
		if (existTable(TABLE_FILL_SYMBOL)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					TYFillSymbolRecord fillSymbol = new TYFillSymbolRecord();
					fillSymbol.symbolID = rs.getInt(FIELD_MAP_SYMBOL_FILL_1_SYMBOL_ID);
					fillSymbol.fillColor = rs.getString(FIELD_MAP_SYMBOL_FILL_2_FILL_COLOR);
					fillSymbol.outlineColor = rs.getString(FIELD_MAP_SYMBOL_FILL_3_OUTLINE_COLOR);
					fillSymbol.lineWidth = rs.getDouble(FIELD_MAP_SYMBOL_FILL_4_LINE_WIDTH);
					resultRecordList.add(fillSymbol);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultRecordList;
	}

	private List<TYIconSymbolRecord> queryIconSymbolsRecords(String sql) {
		List<TYIconSymbolRecord> resultRecordList = new ArrayList<TYIconSymbolRecord>();
		if (existTable(TABLE_ICON_SYMBOL)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					TYIconSymbolRecord iconSymbol = new TYIconSymbolRecord();
					iconSymbol.symbolID = rs.getInt(FIELD_MAP_SYMBOL_ICON_1_SYMBOL_ID);
					iconSymbol.icon = rs.getString(FIELD_MAP_SYMBOL_ICON_2_ICON);
					resultRecordList.add(iconSymbol);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultRecordList;
	}

	boolean existTable(String table) {
		try {
			ResultSet rs = connection.getMetaData().getTables(null, null, table, null);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

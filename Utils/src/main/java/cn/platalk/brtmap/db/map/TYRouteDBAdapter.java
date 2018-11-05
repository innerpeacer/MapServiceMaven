package cn.platalk.brtmap.db.map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYRouteLinkRecord;
import cn.platalk.brtmap.entity.base.impl.TYRouteNodeRecord;

public class TYRouteDBAdapter {
	static final String TABLE_ROUTE_LINK = "ROUTE_LINK_%s";
	static final String TABLE_ROUTE_NODE = "ROUTE_NODE_%s";

	static final String FIELD_ROUTE_LINK_1_LINK_ID = "LINK_ID";
	static final String FIELD_ROUTE_LINK_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_LINK_3_LENGTH = "LENGTH";
	static final String FIELD_ROUTE_LINK_4_HEAD_NODE = "HEAD_NODE";
	static final String FIELD_ROUTE_LINK_5_END_NODE = "END_NODE";
	static final String FIELD_ROUTE_LINK_6_VIRTUAL = "IS_VIRTUAL";
	static final String FIELD_ROUTE_LINK_7_ONE_WAY = "ONE_WAY";

	static final String FIELD_ROUTE_NODE_1_NODE_ID = "NODE_ID";
	static final String FIELD_ROUTE_NODE_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_NODE_3_VIRTUAL = "IS_VIRTUAL";

	private Connection connection;
	private String buildingID;

	private String TABLE_LINK;
	private String TABLE_NODE;

	public TYRouteDBAdapter(String building) {
		buildingID = building;
		TABLE_LINK = String.format(TABLE_ROUTE_LINK, buildingID);
		TABLE_NODE = String.format(TABLE_ROUTE_NODE, buildingID);
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
			connection = DriverManager.getConnection(TYDatabaseManager.GetRouteDBUrl(), TYDatabaseManager.GetUserName(),
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
		createRouteLinkTableIfNotExist();
		createRouteNodeTableIfNotExist();
	}

	private void createRouteLinkTableIfNotExist() {
		String sql = "CREATE TABLE  IF NOT EXISTS " + TABLE_LINK + "(_id INT NOT NULL AUTO_INCREMENT,"
				+ "LINK_ID INTEGER NOT NULL,"
				// + "GEOMETRY BLOB NOT NULL,"
				+ "GEOMETRY MediumBlob NOT NULL," + "LENGTH DOUBLE NOT NULL," + "HEAD_NODE INTEGER NOT NULL, "
				+ "END_NODE INTEGER NOT NULL, " + "IS_VIRTUAL INTEGER NOT NULL, " + "ONE_WAY INTEGER NOT NULL, "
				+ " PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));";

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createRouteNodeTableIfNotExist() {
		String sql = "CREATE TABLE  IF NOT EXISTS " + TABLE_NODE + "(_id INT NOT NULL AUTO_INCREMENT,"
				+ "NODE_ID INTEGER NOT NULL,"
				// + "GEOMETRY BLOB NOT NULL,"
				+ "GEOMETRY MediumBlob NOT NULL," + "IS_VIRTUAL INTEGER NOT NULL, " + " PRIMARY KEY (_id),"
				+ " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eraseRouteTable() {
		String linkSql = String.format("delete from %s", TABLE_LINK);
		String nodeSql = String.format("delete from %s", TABLE_NODE);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(linkSql);
			stmt.execute(nodeSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertRouteLinkRecordsInBatch(List<TYRouteLinkRecord> recordList) {
		int result = 0;
		String sql = String.format("insert into %s (%s, %s, %s, %s, %s, %s, %s) values (?, ?, ?, ?, ?, ?, ?)",
				TABLE_LINK, FIELD_ROUTE_LINK_1_LINK_ID, FIELD_ROUTE_LINK_2_GEOMETRY, FIELD_ROUTE_LINK_3_LENGTH,
				FIELD_ROUTE_LINK_4_HEAD_NODE, FIELD_ROUTE_LINK_5_END_NODE, FIELD_ROUTE_LINK_6_VIRTUAL,
				FIELD_ROUTE_LINK_7_ONE_WAY);
		try {
			connection.setAutoCommit(false);
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			for (TYRouteLinkRecord record : recordList) {
				stmt.setInt(1, record.linkID);
				stmt.setBytes(2, record.linkGeometryData);
				stmt.setDouble(3, record.length);
				stmt.setInt(4, record.headNode);
				stmt.setInt(5, record.endNode);
				stmt.setBoolean(6, record.isVirtual);
				stmt.setBoolean(7, record.isOneWay);
				stmt.addBatch();
			}
			int rows[] = stmt.executeBatch();
			result = rows.length;
			connection.commit();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int insertRouteNodeRecordsInBatch(List<TYRouteNodeRecord> recordList) {
		int result = 0;
		String sql = String.format("insert into %s (%s, %s, %s) values (?, ?, ?)", TABLE_NODE,
				FIELD_ROUTE_NODE_1_NODE_ID, FIELD_ROUTE_NODE_2_GEOMETRY, FIELD_ROUTE_NODE_3_VIRTUAL);
		try {
			connection.setAutoCommit(false);
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			for (TYRouteNodeRecord record : recordList) {
				stmt.setInt(1, record.nodeID);
				stmt.setBytes(2, record.nodeGeometryData);
				stmt.setBoolean(3, record.isVirtual);
				stmt.addBatch();
			}
			int rows[] = stmt.executeBatch();
			result = rows.length;
			connection.commit();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	int insertLinkRecord(TYRouteLinkRecord record) {
		int result = 0;
		String sql = String.format("insert into %s (%s, %s, %s, %s, %s, %s, %s) values (?, ?, ?, ?, ?, ?, ?)",
				TABLE_LINK, FIELD_ROUTE_LINK_1_LINK_ID, FIELD_ROUTE_LINK_2_GEOMETRY, FIELD_ROUTE_LINK_3_LENGTH,
				FIELD_ROUTE_LINK_4_HEAD_NODE, FIELD_ROUTE_LINK_5_END_NODE, FIELD_ROUTE_LINK_6_VIRTUAL,
				FIELD_ROUTE_LINK_7_ONE_WAY);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setInt(1, record.linkID);
			stmt.setBytes(2, record.linkGeometryData);
			stmt.setDouble(3, record.length);
			stmt.setInt(4, record.headNode);
			stmt.setInt(5, record.endNode);
			stmt.setBoolean(6, record.isVirtual);
			stmt.setBoolean(7, record.isOneWay);

			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	int insertNodeRecord(TYRouteNodeRecord record) {
		int result = 0;
		String sql = String.format("insert into %s (%s, %s, %s) values (?, ?, ?)", TABLE_NODE,
				FIELD_ROUTE_NODE_1_NODE_ID, FIELD_ROUTE_NODE_2_GEOMETRY, FIELD_ROUTE_NODE_3_VIRTUAL);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setInt(1, record.nodeID);
			stmt.setBytes(2, record.nodeGeometryData);
			stmt.setBoolean(3, record.isVirtual);
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<TYRouteLinkRecord> getAllLinkRecords() {
		String sql = String.format("select * from %s", TABLE_LINK);
		return queryLinkRecords(sql);
	}

	public List<TYRouteNodeRecord> getAllNodeRecords() {
		String sql = String.format("select * from %s", TABLE_NODE);
		return queryNodeRecords(sql);
	}

	private List<TYRouteLinkRecord> queryLinkRecords(String sql) {
		List<TYRouteLinkRecord> linkRecordList = new ArrayList<TYRouteLinkRecord>();
		if (existTable(TABLE_LINK)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					TYRouteLinkRecord record = new TYRouteLinkRecord();
					record.linkID = rs.getInt(FIELD_ROUTE_LINK_1_LINK_ID);
					record.linkGeometryData = rs.getBytes(FIELD_ROUTE_LINK_2_GEOMETRY);
					record.length = rs.getDouble(FIELD_ROUTE_LINK_3_LENGTH);
					record.headNode = rs.getInt(FIELD_ROUTE_LINK_4_HEAD_NODE);
					record.endNode = rs.getInt(FIELD_ROUTE_LINK_5_END_NODE);
					record.isVirtual = rs.getBoolean(FIELD_ROUTE_LINK_6_VIRTUAL);
					record.isOneWay = rs.getBoolean(FIELD_ROUTE_LINK_7_ONE_WAY);
					linkRecordList.add(record);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return linkRecordList;
	}

	private List<TYRouteNodeRecord> queryNodeRecords(String sql) {
		List<TYRouteNodeRecord> nodeRecordList = new ArrayList<TYRouteNodeRecord>();
		if (existTable(TABLE_NODE)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					TYRouteNodeRecord record = new TYRouteNodeRecord();
					record.nodeID = rs.getInt(FIELD_ROUTE_NODE_1_NODE_ID);
					record.nodeGeometryData = rs.getBytes(FIELD_ROUTE_NODE_2_GEOMETRY);
					record.isVirtual = rs.getBoolean(FIELD_ROUTE_NODE_3_VIRTUAL);
					nodeRecordList.add(record);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return nodeRecordList;
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

	public static void main(String[] args) {
		System.out.println("main");
		TYRouteDBAdapter db = new TYRouteDBAdapter("00000000");
		db.connectDB();
		db.createTableIfNotExist();
		db.disconnectDB();
	}
}

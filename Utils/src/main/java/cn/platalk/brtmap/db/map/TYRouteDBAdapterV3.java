package cn.platalk.brtmap.db.map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.brtmap.entity.base.impl.TYRouteLinkRecordV3;
import cn.platalk.brtmap.entity.base.impl.TYRouteNodeRecordV3;

public class TYRouteDBAdapterV3 {
	static final String TABLE_ROUTE_LINK = "ROUTE_LINK_%s";
	static final String TABLE_ROUTE_NODE = "ROUTE_NODE_%s";

	static final String FIELD_ROUTE_LINK_1_LINK_ID = "LINK_ID";
	static final String FIELD_ROUTE_LINK_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_LINK_3_LENGTH = "LENGTH";
	static final String FIELD_ROUTE_LINK_4_HEAD_NODE = "HEAD_NODE";
	static final String FIELD_ROUTE_LINK_5_END_NODE = "END_NODE";
	static final String FIELD_ROUTE_LINK_6_VIRTUAL = "IS_VIRTUAL";
	static final String FIELD_ROUTE_LINK_7_ONE_WAY = "ONE_WAY";

	static final String FIELD_ROUTE_LINK_8_LINK_NAME = "LINK_NAME";
	static final String FIELD_ROUTE_LINK_9_FLOOR = "FLOOR";
	static final String FIELD_ROUTE_LINK_10_LEVEL = "LEVEL";
	static final String FIELD_ROUTE_LINK_11_REVERSE = "REVERSE";
	static final String FIELD_ROUTE_LINK_12_ROOM_ID = "ROOM_ID";
	static final String FIELD_ROUTE_LINK_13_OPEN = "OPEN";
	static final String FIELD_ROUTE_LINK_14_OPEN_TIME = "OPEN_TIME";
	static final String FIELD_ROUTE_LINK_15_ALLOW_SNAP = "ALLOW_SNAP";
	static final String FIELD_ROUTE_LINK_16_LINK_TYPE = "LINK_TYPE";

	static final String FIELD_ROUTE_NODE_1_NODE_ID = "NODE_ID";
	static final String FIELD_ROUTE_NODE_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_ROUTE_NODE_3_VIRTUAL = "IS_VIRTUAL";

	static final String FIELD_ROUTE_NODE_4_NODE_NAME = "NODE_NAME";
	static final String FIELD_ROUTE_NODE_5_CATEGORY_ID = "CATEGORY_ID";
	static final String FIELD_ROUTE_NODE_6_FLOOR = "FLOOR";
	static final String FIELD_ROUTE_NODE_7_LEVEL = "LEVEL";
	static final String FIELD_ROUTE_NODE_8_IS_SWITCHING = "IS_SWITCHING";
	static final String FIELD_ROUTE_NODE_9_SWITCHING_ID = "SWITCHING_ID";
	static final String FIELD_ROUTE_NODE_10_DIRECTION = "DIRECTION";
	static final String FIELD_ROUTE_NODE_11_NODE_TYPE = "NODE_TYPE";
	static final String FIELD_ROUTE_NODE_12_OPEN = "OPEN";
	static final String FIELD_ROUTE_NODE_13_OPEN_TIME = "OPEN_TIME";
	static final String FIELD_ROUTE_NODE_14_ROOM_ID = "ROOM_ID";

	private Connection connection;
	private String buildingID;

	private String TABLE_LINK;
	private String TABLE_NODE;

	public TYRouteDBAdapterV3(String building) {
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
				+ "LINK_ID VARCHAR(45) NOT NULL," + "GEOMETRY MediumBlob NOT NULL," + "LENGTH DOUBLE NOT NULL,"
				+ "HEAD_NODE VARCHAR(45) NOT NULL, " + "END_NODE VARCHAR(45) NOT NULL, "
				+ "IS_VIRTUAL INTEGER NOT NULL, " + "ONE_WAY INTEGER(1) NOT NULL DEFAULT 0, "
				+ "LINK_NAME VARCHAR(45), " + "FLOOR INTEGER NOT NULL, " + "LEVEL INTEGER NOT NULL DEFAULT 0, "
				+ "REVERSE INTEGER(1) NOT NULL DEFAULT 0, " + "ROOM_ID VARCHAR(45), "
				+ "OPEN INTEGER(1) NOT NULL DEFAULT 1, " + "OPEN_TIME VARCHAR(255), "
				+ "ALLOW_SNAP INTEGER(1) NOT NULL DEFAULT 1, " + "LINK_TYPE VARCHAR(45) NOT NULL, "
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
				+ "NODE_ID VARCHAR(45) NOT NULL," + "GEOMETRY MediumBlob NOT NULL," + "IS_VIRTUAL INTEGER NOT NULL, "
				+ "NODE_NAME VARCHAR(45), " + "CATEGORY_ID VARCHAR(45), " + "FLOOR INTEGER NOT NULL, "
				+ "LEVEL INTEGER NOT NULL, " + "IS_SWITCHING INTEGER(1) NOT NULL, " + "SWITCHING_ID INTEGER, "
				+ "DIRECTION INTEGER NOT NULL, " + "NODE_TYPE INTEGER NOT NULL, " + "OPEN INTEGER(1) NOT NULL, "
				+ "OPEN_TIME VARCHAR(255), " + "ROOM_ID VARCHAR(45), " + " PRIMARY KEY (_id),"
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

	public int insertRouteLinkRecordsInBatch(List<TYIRouteLinkRecordV3> recordList) {
		int result = 0;

		String sql = String.format(
				"insert into %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				TABLE_LINK, FIELD_ROUTE_LINK_1_LINK_ID, FIELD_ROUTE_LINK_2_GEOMETRY, FIELD_ROUTE_LINK_3_LENGTH,
				FIELD_ROUTE_LINK_4_HEAD_NODE, FIELD_ROUTE_LINK_5_END_NODE, FIELD_ROUTE_LINK_6_VIRTUAL,
				FIELD_ROUTE_LINK_7_ONE_WAY, FIELD_ROUTE_LINK_8_LINK_NAME, FIELD_ROUTE_LINK_9_FLOOR,
				FIELD_ROUTE_LINK_10_LEVEL, FIELD_ROUTE_LINK_11_REVERSE, FIELD_ROUTE_LINK_12_ROOM_ID,
				FIELD_ROUTE_LINK_13_OPEN, FIELD_ROUTE_LINK_14_OPEN_TIME, FIELD_ROUTE_LINK_15_ALLOW_SNAP,
				FIELD_ROUTE_LINK_16_LINK_TYPE);
		try {
			connection.setAutoCommit(false);
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			for (TYIRouteLinkRecordV3 record : recordList) {
				stmt.setString(1, record.getLinkID());
				stmt.setBytes(2, record.getGeometryData());
				stmt.setDouble(3, record.getLength());
				stmt.setString(4, record.getHeadNode());
				stmt.setString(5, record.getEndNode());
				stmt.setBoolean(6, false);
				stmt.setBoolean(7, record.isOneWay());

				if (record.getLinkName() != null) {
					stmt.setString(8, record.getLinkName());
				} else {
					stmt.setNull(8, Types.VARCHAR);
				}
				stmt.setInt(9, record.getFloor());
				stmt.setInt(10, record.getLevel());
				stmt.setBoolean(11, record.isReverse());

				if (record.getRoomID() != null) {
					stmt.setString(12, record.getRoomID());
				} else {
					stmt.setNull(12, Types.VARCHAR);
				}
				stmt.setBoolean(13, record.isOpen());

				if (record.getOpenTime() != null) {
					stmt.setString(14, record.getOpenTime());
				} else {
					stmt.setNull(14, Types.VARCHAR);
				}
				stmt.setBoolean(15, record.isAllowSnap());
				stmt.setString(16, record.getLinkType());
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

	public int insertRouteNodeRecordsInBatch(List<TYIRouteNodeRecordV3> recordList) {
		int result = 0;
		String sql = String.format(
				"insert into %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				TABLE_NODE, FIELD_ROUTE_NODE_1_NODE_ID, FIELD_ROUTE_NODE_2_GEOMETRY, FIELD_ROUTE_NODE_3_VIRTUAL,
				FIELD_ROUTE_NODE_4_NODE_NAME, FIELD_ROUTE_NODE_5_CATEGORY_ID, FIELD_ROUTE_NODE_6_FLOOR,
				FIELD_ROUTE_NODE_7_LEVEL, FIELD_ROUTE_NODE_8_IS_SWITCHING, FIELD_ROUTE_NODE_9_SWITCHING_ID,
				FIELD_ROUTE_NODE_10_DIRECTION, FIELD_ROUTE_NODE_11_NODE_TYPE, FIELD_ROUTE_NODE_12_OPEN,
				FIELD_ROUTE_NODE_13_OPEN_TIME, FIELD_ROUTE_NODE_14_ROOM_ID);

		try {
			connection.setAutoCommit(false);
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			for (TYIRouteNodeRecordV3 record : recordList) {
				stmt.setString(1, record.getNodeID());
				stmt.setBytes(2, record.getGeometryData());
				stmt.setBoolean(3, false);
				if (record.getNodeName() != null) {
					stmt.setString(4, record.getNodeName());
				} else {
					stmt.setNull(4, Types.VARCHAR);
				}

				if (record.getCategoryID() != null) {
					stmt.setString(5, record.getCategoryID());
				} else {
					stmt.setNull(5, Types.VARCHAR);
				}

				stmt.setInt(6, record.getFloor());
				stmt.setInt(7, record.getLevel());
				stmt.setBoolean(8, record.isSwitching());
				stmt.setInt(9, record.getSwitchingID());
				stmt.setInt(10, record.getDirection());
				stmt.setInt(11, record.getNodeType());
				stmt.setBoolean(12, record.isOpen());

				if (record.getOpenTime() != null) {
					stmt.setString(13, record.getOpenTime());
				} else {
					stmt.setNull(13, Types.VARCHAR);
				}

				if (record.getRoomID() != null) {
					stmt.setString(14, record.getRoomID());
				} else {
					stmt.setNull(14, Types.VARCHAR);
				}
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

	int insertLinkRecord(TYIRouteLinkRecordV3 record) {
		int result = 0;
		String sql = String.format(
				"insert into %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				TABLE_LINK, FIELD_ROUTE_LINK_1_LINK_ID, FIELD_ROUTE_LINK_2_GEOMETRY, FIELD_ROUTE_LINK_3_LENGTH,
				FIELD_ROUTE_LINK_4_HEAD_NODE, FIELD_ROUTE_LINK_5_END_NODE, FIELD_ROUTE_LINK_6_VIRTUAL,
				FIELD_ROUTE_LINK_7_ONE_WAY, FIELD_ROUTE_LINK_8_LINK_NAME, FIELD_ROUTE_LINK_9_FLOOR,
				FIELD_ROUTE_LINK_10_LEVEL, FIELD_ROUTE_LINK_11_REVERSE, FIELD_ROUTE_LINK_12_ROOM_ID,
				FIELD_ROUTE_LINK_13_OPEN, FIELD_ROUTE_LINK_14_OPEN_TIME, FIELD_ROUTE_LINK_15_ALLOW_SNAP,
				FIELD_ROUTE_LINK_16_LINK_TYPE);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setString(1, record.getLinkID());
			stmt.setBytes(2, record.getGeometryData());
			stmt.setDouble(3, record.getLength());
			stmt.setString(4, record.getHeadNode());
			stmt.setString(5, record.getEndNode());
			stmt.setBoolean(6, false);
			stmt.setBoolean(7, record.isOneWay());

			if (record.getLinkName() != null) {
				stmt.setString(8, record.getLinkName());
			} else {
				stmt.setNull(8, Types.VARCHAR);
			}
			stmt.setInt(9, record.getFloor());
			stmt.setInt(10, record.getLevel());
			stmt.setBoolean(11, record.isReverse());

			if (record.getRoomID() != null) {
				stmt.setString(12, record.getRoomID());
			} else {
				stmt.setNull(12, Types.VARCHAR);
			}
			stmt.setBoolean(13, record.isOpen());

			if (record.getOpenTime() != null) {
				stmt.setString(14, record.getOpenTime());
			} else {
				stmt.setNull(14, Types.VARCHAR);
			}
			stmt.setBoolean(15, record.isAllowSnap());
			stmt.setString(16, record.getLinkType());

			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	int insertNodeRecord(TYIRouteNodeRecordV3 record) {
		int result = 0;
		String sql = String.format(
				"insert into %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				TABLE_NODE, FIELD_ROUTE_NODE_1_NODE_ID, FIELD_ROUTE_NODE_2_GEOMETRY, FIELD_ROUTE_NODE_3_VIRTUAL,
				FIELD_ROUTE_NODE_4_NODE_NAME, FIELD_ROUTE_NODE_5_CATEGORY_ID, FIELD_ROUTE_NODE_6_FLOOR,
				FIELD_ROUTE_NODE_7_LEVEL, FIELD_ROUTE_NODE_8_IS_SWITCHING, FIELD_ROUTE_NODE_9_SWITCHING_ID,
				FIELD_ROUTE_NODE_10_DIRECTION, FIELD_ROUTE_NODE_11_NODE_TYPE, FIELD_ROUTE_NODE_12_OPEN,
				FIELD_ROUTE_NODE_13_OPEN_TIME, FIELD_ROUTE_NODE_14_ROOM_ID);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setString(1, record.getNodeID());
			stmt.setBytes(2, record.getGeometryData());
			stmt.setBoolean(3, false);
			if (record.getNodeName() != null) {
				stmt.setString(4, record.getNodeName());
			} else {
				stmt.setNull(4, Types.VARCHAR);
			}

			if (record.getCategoryID() != null) {
				stmt.setString(5, record.getCategoryID());
			} else {
				stmt.setNull(5, Types.VARCHAR);
			}

			stmt.setInt(6, record.getFloor());
			stmt.setInt(7, record.getLevel());
			stmt.setBoolean(8, record.isSwitching());
			stmt.setInt(9, record.getSwitchingID());
			stmt.setInt(10, record.getDirection());
			stmt.setInt(11, record.getNodeType());
			stmt.setBoolean(12, record.isOpen());

			if (record.getOpenTime() != null) {
				stmt.setString(13, record.getOpenTime());
			} else {
				stmt.setNull(13, Types.VARCHAR);
			}

			if (record.getRoomID() != null) {
				stmt.setString(14, record.getRoomID());
			} else {
				stmt.setNull(14, Types.VARCHAR);
			}

			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<TYIRouteLinkRecordV3> getAllLinkRecords() {
		String sql = String.format("select * from %s", TABLE_LINK);
		return queryLinkRecords(sql);
	}

	public List<TYIRouteNodeRecordV3> getAllNodeRecords() {
		String sql = String.format("select * from %s", TABLE_NODE);
		return queryNodeRecords(sql);
	}

	private List<TYIRouteLinkRecordV3> queryLinkRecords(String sql) {
		List<TYIRouteLinkRecordV3> linkRecordList = new ArrayList<TYIRouteLinkRecordV3>();
		if (existTable(TABLE_LINK)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					TYRouteLinkRecordV3 record = new TYRouteLinkRecordV3();
					record.setLinkID(rs.getString(FIELD_ROUTE_LINK_1_LINK_ID));
					record.setGeometryData(rs.getBytes(FIELD_ROUTE_LINK_2_GEOMETRY));
					record.setLength(rs.getDouble(FIELD_ROUTE_LINK_3_LENGTH));
					record.setHeadNode(rs.getString(FIELD_ROUTE_LINK_4_HEAD_NODE));
					record.setEndNode(rs.getString(FIELD_ROUTE_LINK_5_END_NODE));
					// record.isVirtual = rs
					// .getBoolean(FIELD_ROUTE_LINK_6_VIRTUAL);
					record.setOneWay(rs.getBoolean(FIELD_ROUTE_LINK_7_ONE_WAY));
					record.setLinkName(rs.getString(FIELD_ROUTE_LINK_8_LINK_NAME));
					record.setFloor(rs.getInt(FIELD_ROUTE_LINK_9_FLOOR));
					record.setLevel(rs.getInt(FIELD_ROUTE_LINK_10_LEVEL));
					record.setReverse(rs.getBoolean(FIELD_ROUTE_LINK_11_REVERSE));
					record.setRoomID(rs.getString(FIELD_ROUTE_LINK_12_ROOM_ID));
					record.setOpen(rs.getBoolean(FIELD_ROUTE_LINK_13_OPEN));
					record.setOpenTime(rs.getString(FIELD_ROUTE_LINK_14_OPEN_TIME));
					record.setAllowSnap(rs.getBoolean(FIELD_ROUTE_LINK_15_ALLOW_SNAP));
					record.setLinkType(rs.getString(FIELD_ROUTE_LINK_16_LINK_TYPE));
					linkRecordList.add(record);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return linkRecordList;
	}

	private List<TYIRouteNodeRecordV3> queryNodeRecords(String sql) {
		List<TYIRouteNodeRecordV3> nodeRecordList = new ArrayList<TYIRouteNodeRecordV3>();
		if (existTable(TABLE_NODE)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					TYRouteNodeRecordV3 record = new TYRouteNodeRecordV3();
					record.setNodeID(rs.getString(FIELD_ROUTE_NODE_1_NODE_ID));
					record.setGeometryData(rs.getBytes(FIELD_ROUTE_NODE_2_GEOMETRY));
					// record.isVirtual = rs
					// .getBoolean(FIELD_ROUTE_NODE_3_VIRTUAL);
					record.setNodeName(rs.getString(FIELD_ROUTE_NODE_4_NODE_NAME));
					record.setCategoryID(rs.getString(FIELD_ROUTE_NODE_5_CATEGORY_ID));
					record.setFloor(rs.getInt(FIELD_ROUTE_NODE_6_FLOOR));
					record.setLevel(rs.getInt(FIELD_ROUTE_NODE_7_LEVEL));
					record.setSwitching(rs.getBoolean(FIELD_ROUTE_NODE_8_IS_SWITCHING));
					record.setSwitchingID(rs.getInt(FIELD_ROUTE_NODE_9_SWITCHING_ID));
					record.setDirection(rs.getInt(FIELD_ROUTE_NODE_10_DIRECTION));
					record.setNodeType(rs.getInt(FIELD_ROUTE_NODE_11_NODE_TYPE));
					record.setOpen(rs.getBoolean(FIELD_ROUTE_NODE_12_OPEN));
					record.setOpenTime(rs.getString(FIELD_ROUTE_NODE_13_OPEN_TIME));
					record.setRoomID(rs.getString(FIELD_ROUTE_NODE_14_ROOM_ID));
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
}

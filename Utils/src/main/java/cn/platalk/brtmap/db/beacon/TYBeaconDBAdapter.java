package cn.platalk.brtmap.db.beacon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.db.map.TYDatabaseManager;
import cn.platalk.brtmap.entity.base.TYIBeacon;
import cn.platalk.brtmap.entity.base.TYILocatingBeacon;
import cn.platalk.brtmap.entity.base.TYLocalPoint;
import cn.platalk.brtmap.entity.base.impl.TYLocatingBeacon;

public class TYBeaconDBAdapter {
	static final String TABLE_BEACON = "BEACON_%s";

	static final String FIELD_BEACON_0_PRIMARY_KEY = "_id";

	static final String FIELD_BEACON_1_GEOM = "GEOM";
	static final String FIELD_BEACON_2_UUID = "UUID";
	static final String FIELD_BEACON_3_MAJOR = "MAJOR";
	static final String FIELD_BEACON_4_MINOR = "MINOR";
	static final String FIELD_BEACON_5_FLOOR = "FLOOR";
	static final String FIELD_BEACON_6_X = "X";
	static final String FIELD_BEACON_7_Y = "Y";
	static final String FIELD_BEACON_8_ROOM_ID = "ROOM_ID";
	static final String FIELD_BEACON_9_TAG = "TAG";

	static final String FIELD_BEACON_10_MAP_ID = "MAP_ID";
	static final String FIELD_BEACON_11_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_BEACON_12_CITY_ID = "CITY_ID";

	private Connection connection;
	private String buildingID;

	private String TABLE;

	public TYBeaconDBAdapter(String building) {
		buildingID = building;
		TABLE = String.format(TABLE_BEACON, buildingID);
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
			connection = DriverManager.getConnection(TYDatabaseManager.GetBeaconDBUrl(),
					TYDatabaseManager.GetUserName(), TYDatabaseManager.GetPassword());
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
		String sql = "CREATE TABLE  IF NOT EXISTS " + TABLE + "(_id INT NOT NULL AUTO_INCREMENT,"
				+ "GEOM BLOB NOT NULL," + "UUID VARCHAR(45) NOT NULL," + "MAJOR INTEGER NOT NULL, "
				+ "MINOR INTEGER NOT NULL," + "FLOOR INTEGER NOT NULL," + "X DOUBLE NOT NULL," + "Y DOUBLE NOT NULL,"
				+ "ROOM_ID VARCHAR(45)," + "TAG VARCHAR(45), " + "MAP_ID VARCHAR(45)," + "BUILDING_ID VARCHAR(45),"
				+ "CITY_ID VARCHAR(45)," + " PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));";

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteBeacon(String uuid, int major, int minor) {
		String sql = String.format("delete from %s where %s='%s' and %s=%d and %s=%d", TABLE, FIELD_BEACON_2_UUID, uuid,
				FIELD_BEACON_3_MAJOR, major, FIELD_BEACON_4_MINOR, minor);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteBeacons(List<TYIBeacon> beaconList) {
		for (TYIBeacon beacon : beaconList) {
			deleteBeacon(beacon.getUUID(), beacon.getMajor(), beacon.getMinor());
		}
	}

	public void eraseBeaconTable() {
		String sql = String.format("delete from %s", TABLE);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertOrUpdateBeacon(TYILocatingBeacon beacon) {
		if (!existBeacon(beacon.getUUID(), beacon.getMajor(), beacon.getMinor())) {
			return insertBeacon(beacon);
		} else {
			return updateBeacon(beacon);
		}
	}

	public int insertOrUpdateBeacons(List<TYILocatingBeacon> beacons) {
		int count = 0;
		for (TYILocatingBeacon beacon : beacons) {
			count += insertOrUpdateBeacon(beacon);
		}
		return count;
	}

	int updateBeacon(TYILocatingBeacon beacon) {
		int result = 0;
		StringBuffer buffer = new StringBuffer(String.format(
				"update %s set %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?", TABLE, FIELD_BEACON_1_GEOM,
				FIELD_BEACON_5_FLOOR, FIELD_BEACON_6_X, FIELD_BEACON_7_Y, FIELD_BEACON_8_ROOM_ID, FIELD_BEACON_9_TAG,
				FIELD_BEACON_10_MAP_ID, FIELD_BEACON_11_BUILDING_ID, FIELD_BEACON_12_CITY_ID));
		buffer.append(String.format(" where %s='%s' and %s=%d and %s=%d", FIELD_BEACON_2_UUID, beacon.getUUID(),
				FIELD_BEACON_3_MAJOR, beacon.getMajor(), FIELD_BEACON_4_MINOR, beacon.getMinor()));
		PreparedStatement stmt;

		try {
			stmt = (PreparedStatement) connection.prepareStatement(buffer.toString());
			stmt.setBytes(1, beacon.getLocation().getGeometryBytes());

			stmt.setInt(2, beacon.getLocation().getFloor());
			stmt.setDouble(3, beacon.getLocation().getX());
			stmt.setDouble(4, beacon.getLocation().getY());
			if (beacon.getRoomID() != null) {
				stmt.setString(5, beacon.getRoomID());
			} else {
				stmt.setNull(5, Types.VARCHAR);
			}

			if (beacon.getTag() != null) {
				stmt.setString(6, beacon.getTag());
			} else {
				stmt.setNull(6, Types.VARCHAR);
			}

			stmt.setString(7, beacon.getMapID());
			stmt.setString(8, beacon.getBuildingID());
			stmt.setString(9, beacon.getCityID());
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	int insertBeacon(TYILocatingBeacon beacon) {
		int result = 0;
		String sql = String.format(
				"insert into %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				TABLE, FIELD_BEACON_1_GEOM, FIELD_BEACON_2_UUID, FIELD_BEACON_3_MAJOR, FIELD_BEACON_4_MINOR,
				FIELD_BEACON_5_FLOOR, FIELD_BEACON_6_X, FIELD_BEACON_7_Y, FIELD_BEACON_8_ROOM_ID, FIELD_BEACON_9_TAG,
				FIELD_BEACON_10_MAP_ID, FIELD_BEACON_11_BUILDING_ID, FIELD_BEACON_12_CITY_ID);

		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setBytes(1, beacon.getLocation().getGeometryBytes());
			stmt.setString(2, beacon.getUUID());
			stmt.setInt(3, beacon.getMajor());
			stmt.setInt(4, beacon.getMinor());
			stmt.setInt(5, beacon.getLocation().getFloor());
			stmt.setDouble(6, beacon.getLocation().getX());
			stmt.setDouble(7, beacon.getLocation().getY());
			if (beacon.getRoomID() != null) {
				stmt.setString(8, beacon.getRoomID());
			} else {
				stmt.setNull(8, Types.VARCHAR);
			}

			if (beacon.getTag() != null) {
				stmt.setString(9, beacon.getTag());
			} else {
				stmt.setNull(9, Types.VARCHAR);
			}

			stmt.setString(10, beacon.getMapID());
			stmt.setString(11, beacon.getBuildingID());
			stmt.setString(12, beacon.getCityID());
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<TYLocatingBeacon> getAllBeacons() {
		String sql = String.format("select * from %s", TABLE);
		return queryBeacons(sql);
	}

	public List<TYLocatingBeacon> getAllBeaconsOnFloor(int floor) {
		String sql = String.format("select * from %s where %s=%d", TABLE, FIELD_BEACON_5_FLOOR, floor);
		return queryBeacons(sql);
	}

	public TYLocatingBeacon getBeacon(String uuid, int major, int minor) {
		String sql = String.format("select * from %s where %s='%s' and %s=%d and %s=%d", TABLE, FIELD_BEACON_2_UUID,
				uuid, FIELD_BEACON_3_MAJOR, major, FIELD_BEACON_4_MINOR, minor);
		return queryBeacon(sql);
	}

	private List<TYLocatingBeacon> queryBeacons(String sql) {
		List<TYLocatingBeacon> resultBeaconList = new ArrayList<TYLocatingBeacon>();
		if (existTable(TABLE)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					TYLocatingBeacon pb = new TYLocatingBeacon(rs.getString(FIELD_BEACON_2_UUID),
							rs.getInt(FIELD_BEACON_3_MAJOR), rs.getInt(FIELD_BEACON_4_MINOR),
							rs.getString(FIELD_BEACON_9_TAG), rs.getString(FIELD_BEACON_8_ROOM_ID));
					TYLocalPoint lp = new TYLocalPoint(rs.getDouble(FIELD_BEACON_6_X), rs.getDouble(FIELD_BEACON_7_Y),
							rs.getInt(FIELD_BEACON_5_FLOOR));
					pb.setLocation(lp);
					resultBeaconList.add(pb);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultBeaconList;
	}

	private TYLocatingBeacon queryBeacon(String sql) {
		TYLocatingBeacon result = null;
		if (existTable(TABLE)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					result = new TYLocatingBeacon(rs.getString(FIELD_BEACON_2_UUID), rs.getInt(FIELD_BEACON_3_MAJOR),
							rs.getInt(FIELD_BEACON_4_MINOR), rs.getString(FIELD_BEACON_9_TAG),
							rs.getString(FIELD_BEACON_8_ROOM_ID));
					TYLocalPoint lp = new TYLocalPoint(rs.getDouble(FIELD_BEACON_6_X), rs.getDouble(FIELD_BEACON_7_Y),
							rs.getInt(FIELD_BEACON_5_FLOOR));
					result.setLocation(lp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean existBeacon(String uuid, int major, int minor) {
		int result = 0;
		String sql = String.format("select count(*)  from %s where %s='%s' and %s=%d and %s=%d", TABLE,
				FIELD_BEACON_2_UUID, uuid, FIELD_BEACON_3_MAJOR, major, FIELD_BEACON_4_MINOR, minor);
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return (result != 0);
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

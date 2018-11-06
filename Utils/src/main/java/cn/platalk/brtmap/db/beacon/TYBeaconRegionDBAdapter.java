package cn.platalk.brtmap.db.beacon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import cn.platalk.brtmap.db.map.TYDatabaseManager;
import cn.platalk.brtmap.entity.base.TYIBeaconRegion;
import cn.platalk.brtmap.entity.base.impl.TYBeaconRegion;

public class TYBeaconRegionDBAdapter {
	static final String TABLE_BEACON_REGION = "BEACON_REGION";

	static final String FIELD_BEACON_REGION_0_PRIMARY_KEY = "_id";
	static final String FIELD_BEACON_REGION_1_CITY_ID = "CITY_ID";
	static final String FIELD_BEACON_REGION_2_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_BEACON_REGION_3_BUILDING_NAME = "BUILDING_NAME";
	static final String FIELD_BEACON_REGION_4_UUID = "UUID";
	static final String FIELD_BEACON_REGION_5_MAJOR = "MAJOR";

	private Connection connection;

	public TYBeaconRegionDBAdapter() {
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
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_BEACON_REGION + " (_id INT NOT NULL AUTO_INCREMENT, "
				+ "CITY_ID VARCHAR(45) NOT NULL, " + "BUILDING_ID VARCHAR(45) NOT NULL, "
				+ "BUILDING_NAME VARCHAR(45) NOT NULL, " + "UUID VARCHAR(45) NOT NULL, " + "MAJOR INT, "
				+ " PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteBeaconRegion(String buildingID) {
		String sql = String.format("delete from %s where %s='%s'", TABLE_BEACON_REGION,
				FIELD_BEACON_REGION_2_BUILDING_ID, buildingID);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// public void eraseBeaconRegionTable() {
	// String sql = String.format("delete from %s", TABLE_BEACON_REGION);
	// try {
	// Statement stmt = connection.createStatement();
	// stmt.execute(sql);
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }

	public int insertOrUpdateBeaconRegion(TYIBeaconRegion region) {
		if (!existBeaconRegion(region.getBuildingID())) {
			return insertBeaconRegion(region);
		} else {
			return updateBeaconRegion(region);
		}
	}

	public int insertOrUpdateBeaconRegions(List<TYIBeaconRegion> regions) {
		int count = 0;
		for (TYIBeaconRegion region : regions) {
			count += insertOrUpdateBeaconRegion(region);
		}
		return count;
	}

	int insertBeaconRegion(TYIBeaconRegion region) {
		int result = 0;
		String sql = String.format("insert into %s (%s, %s, %s, %s, %s) values (?,?,?,?,?)", TABLE_BEACON_REGION,
				FIELD_BEACON_REGION_1_CITY_ID, FIELD_BEACON_REGION_2_BUILDING_ID, FIELD_BEACON_REGION_3_BUILDING_NAME,
				FIELD_BEACON_REGION_4_UUID, FIELD_BEACON_REGION_5_MAJOR);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setString(1, region.getCityID());
			stmt.setString(2, region.getBuildingID());
			stmt.setString(3, region.getBuildingName());
			stmt.setString(4, region.getUuid());
			if (region.getMajor() != null) {
				stmt.setInt(5, region.getMajor());
			} else {
				stmt.setNull(5, Types.INTEGER);
			}
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	int updateBeaconRegion(TYIBeaconRegion region) {
		int result = 0;
		StringBuffer buffer = new StringBuffer(String.format("update %s set %s=?, %s=?, %s=?, %s=?, %s=?",
				TABLE_BEACON_REGION, FIELD_BEACON_REGION_1_CITY_ID, FIELD_BEACON_REGION_2_BUILDING_ID,
				FIELD_BEACON_REGION_3_BUILDING_NAME, FIELD_BEACON_REGION_4_UUID, FIELD_BEACON_REGION_5_MAJOR));
		buffer.append(String.format(" where %s='%s'", FIELD_BEACON_REGION_2_BUILDING_ID, region.getBuildingID()));
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(buffer.toString());
			stmt.setString(1, region.getCityID());
			stmt.setString(2, region.getBuildingID());
			stmt.setString(3, region.getBuildingName());
			stmt.setString(4, region.getUuid());
			if (region.getMajor() != null) {
				stmt.setInt(5, region.getMajor());
			} else {
				stmt.setNull(5, Types.INTEGER);
			}
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<TYBeaconRegion> getAllBeaconRegions() {
		String sql = String.format("select * from %s", TABLE_BEACON_REGION);
		return queryBeaconRegions(sql);
	}

	public TYBeaconRegion getBeaconRegion(String buildingID) {
		String sql = String.format("select * from %s where %s='%s'", TABLE_BEACON_REGION,
				FIELD_BEACON_REGION_2_BUILDING_ID, buildingID);
		return queryBeaconRegion(sql);
	}

	private List<TYBeaconRegion> queryBeaconRegions(String sql) {
		List<TYBeaconRegion> resultRegionList = new ArrayList<TYBeaconRegion>();
		if (existTable(TABLE_BEACON_REGION)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					TYBeaconRegion br = new TYBeaconRegion();
					br.setCityID(rs.getString(FIELD_BEACON_REGION_1_CITY_ID));
					br.setBuildingID(rs.getString(FIELD_BEACON_REGION_2_BUILDING_ID));
					br.setBuildingName(rs.getString(FIELD_BEACON_REGION_3_BUILDING_NAME));
					br.setUuid(rs.getString(FIELD_BEACON_REGION_4_UUID));

					String majorString = rs.getString(FIELD_BEACON_REGION_5_MAJOR);
					if (majorString != null) {
						br.setMajor(Integer.parseInt(majorString));
					}
					resultRegionList.add(br);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultRegionList;
	}

	private TYBeaconRegion queryBeaconRegion(String sql) {
		TYBeaconRegion br = null;
		if (existTable(TABLE_BEACON_REGION)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					br = new TYBeaconRegion();
					br.setCityID(rs.getString(FIELD_BEACON_REGION_1_CITY_ID));
					br.setBuildingID(rs.getString(FIELD_BEACON_REGION_2_BUILDING_ID));
					br.setBuildingName(rs.getString(FIELD_BEACON_REGION_3_BUILDING_NAME));
					br.setUuid(rs.getString(FIELD_BEACON_REGION_4_UUID));
					String majorString = rs.getString(FIELD_BEACON_REGION_5_MAJOR);
					if (majorString != null) {
						br.setMajor(Integer.parseInt(majorString));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return br;
	}

	public boolean existBeaconRegion(String buildingID) {
		int result = 0;
		String sql = String.format("select count(*)  from %s where %s='%s'", TABLE_BEACON_REGION,
				FIELD_BEACON_REGION_2_BUILDING_ID, buildingID);
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
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

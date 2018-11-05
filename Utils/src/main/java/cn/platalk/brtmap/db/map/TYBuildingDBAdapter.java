package cn.platalk.brtmap.db.map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYMapSize;

public class TYBuildingDBAdapter {
	static final String TABLE_BUILDING = "BUILDING";

	static final String FIELD_BUILDING_0_PRIMARY_KEY = "_id";
	static final String FIELD_BUILDING_1_CITY_ID = "CITY_ID";
	static final String FIELD_BUILDING_2_ID = "BUILDING_ID";

	static final String FIELD_BUILDING_3_NAME = "NAME";
	static final String FIELD_BUILDING_4_LONGITUDE = "LONGITUDE";
	static final String FIELD_BUILDING_5_LATITUDE = "LATITUDE";

	static final String FIELD_BUILDING_6_ADDRESS = "ADDRESS";
	static final String FIELD_BUILDING_7_INIT_ANGLE = "INIT_ANGLE";
	static final String FIELD_BUILDING_8_ROUTE_URL = "ROUTE_URL";
	static final String FIELD_BUILDING_9_OFFSET_X = "OFFSET_X";
	static final String FIELD_BUILDING_10_OFFSET_Y = "OFFSET_Y";

	static final String FIELD_BUILDING_11_STATUS = "STATUS";

	private Connection connection;

	public TYBuildingDBAdapter() {
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
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_BUILDING + " (_id INT NOT NULL AUTO_INCREMENT, "
				+ "CITY_ID VARCHAR(45) NOT NULL," + "BUILDING_ID VARCHAR(45) NOT NULL," + "NAME VARCHAR(255) NOT NULL,"
				+ "LONGITUDE DOUBLE NOT NULL," + "LATITUDE DOUBLE NOT NULL," + "ADDRESS VARCHAR(255) NOT NULL,"
				+ "INIT_ANGLE DOUBLE NOT NULL," + "ROUTE_URL VARCHAR(255) NOT NULL," + "OFFSET_X DOUBLE NOT NULL,"
				+ "OFFSET_Y DOUBLE NOT NULL," + "STATUS INT NOT NULL," + " PRIMARY KEY (_id),"
				+ " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eraseBuildingTable() {
		String sql = String.format("delete from %s", TABLE_BUILDING);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteBuilding(String buildingID) {
		String sql = String.format("delete from %s where %s='%s'", TABLE_BUILDING, FIELD_BUILDING_2_ID, buildingID);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertOrUpdateBuildings(List<TYBuilding> buildingList) {
		int count = 0;
		for (TYBuilding building : buildingList) {
			count += insertOrUpdateBuilding(building);
		}
		return count;
	}

	public int insertOrUpdateBuilding(TYBuilding building) {
		if (!existBuilding(building.getBuildingID())) {
			return insertBuilding(building);
		} else {
			return updateBuilding(building);
		}
	}

	int insertBuilding(TYBuilding building) {
		int result = 0;
		String sql = String.format(
				"insert into %s (%s, %s, %s, %s, %s, %s, %s, %s, %s,%s, %s) values (?,?,?,?,?,?,?,?,?,?,?)",
				TABLE_BUILDING, FIELD_BUILDING_1_CITY_ID, FIELD_BUILDING_2_ID, FIELD_BUILDING_3_NAME,
				FIELD_BUILDING_4_LONGITUDE, FIELD_BUILDING_5_LATITUDE, FIELD_BUILDING_6_ADDRESS,
				FIELD_BUILDING_7_INIT_ANGLE, FIELD_BUILDING_8_ROUTE_URL, FIELD_BUILDING_9_OFFSET_X,
				FIELD_BUILDING_10_OFFSET_Y, FIELD_BUILDING_11_STATUS);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setString(1, building.getCityID());
			stmt.setString(2, building.getBuildingID());
			stmt.setString(3, building.getName());
			stmt.setDouble(4, building.getLongitude());
			stmt.setDouble(5, building.getLatitude());
			stmt.setString(6, building.getAddress());
			stmt.setDouble(7, building.getInitAngle());
			stmt.setString(8, building.getRouteURL());
			stmt.setDouble(9, building.getOffset().getX());
			stmt.setDouble(10, building.getOffset().getY());
			stmt.setInt(11, building.getStatus());
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	int updateBuilding(TYBuilding building) {
		int result = 0;
		StringBuffer buffer = new StringBuffer(String.format(
				"update %s set  %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?", TABLE_BUILDING,
				FIELD_BUILDING_1_CITY_ID, FIELD_BUILDING_3_NAME, FIELD_BUILDING_4_LONGITUDE, FIELD_BUILDING_5_LATITUDE,
				FIELD_BUILDING_6_ADDRESS, FIELD_BUILDING_7_INIT_ANGLE, FIELD_BUILDING_8_ROUTE_URL,
				FIELD_BUILDING_9_OFFSET_X, FIELD_BUILDING_10_OFFSET_Y, FIELD_BUILDING_11_STATUS));
		buffer.append(String.format(" where %s='%s'", FIELD_BUILDING_2_ID, building.getBuildingID()));
		PreparedStatement stmt;

		try {
			stmt = (PreparedStatement) connection.prepareStatement(buffer.toString());
			stmt.setString(1, building.getCityID());
			stmt.setString(2, building.getName());
			stmt.setDouble(3, building.getLongitude());
			stmt.setDouble(4, building.getLatitude());
			stmt.setString(5, building.getAddress());
			stmt.setDouble(6, building.getInitAngle());
			stmt.setString(7, building.getRouteURL());
			stmt.setDouble(8, building.getOffset().getX());
			stmt.setDouble(9, building.getOffset().getY());
			stmt.setInt(10, building.getStatus());

			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<TYBuilding> getBuildings() {
		String sql = String.format("select * from %s where %s=%d", TABLE_BUILDING, FIELD_BUILDING_11_STATUS, 0);
		return queryBuildings(sql);
	}

	public List<TYBuilding> getBuildingsInCity(String cityID) {
		String sql = String.format("select * from %s where %s='%s' and %s=%d", TABLE_BUILDING, FIELD_BUILDING_1_CITY_ID,
				cityID, FIELD_BUILDING_11_STATUS, 0);
		return queryBuildings(sql);
	}

	public TYBuilding getBuilding(String buildingID) {
		String sql = String.format("select * from %s where %s='%s' and %s=%d", TABLE_BUILDING, FIELD_BUILDING_2_ID,
				buildingID, FIELD_BUILDING_11_STATUS, 0);
		System.out.println("getBuilding");
		System.out.println(sql);
		return queryBuilding(sql);
	}

	private List<TYBuilding> queryBuildings(String sql) {
		List<TYBuilding> resultBuildingList = new ArrayList<TYBuilding>();
		if (existTable(TABLE_BUILDING)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					TYBuilding building = new TYBuilding();
					building.setCityID(rs.getString(FIELD_BUILDING_1_CITY_ID));
					building.setBuildingID(rs.getString(FIELD_BUILDING_2_ID));
					building.setName(rs.getString(FIELD_BUILDING_3_NAME));

					building.setLongitude(rs.getDouble(FIELD_BUILDING_4_LONGITUDE));
					building.setLatitude(rs.getDouble(FIELD_BUILDING_5_LATITUDE));

					building.setAddress(rs.getString(FIELD_BUILDING_6_ADDRESS));
					building.setInitAngle(rs.getDouble(FIELD_BUILDING_7_INIT_ANGLE));
					building.setRouteURL(rs.getString(FIELD_BUILDING_8_ROUTE_URL));
					building.setOffset(new TYMapSize(rs.getDouble(FIELD_BUILDING_9_OFFSET_X),
							rs.getDouble(FIELD_BUILDING_10_OFFSET_Y)));
					building.setStatus(rs.getInt(FIELD_BUILDING_11_STATUS));
					resultBuildingList.add(building);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultBuildingList;
	}

	private TYBuilding queryBuilding(String sql) {
		TYBuilding building = null;
		if (existTable(TABLE_BUILDING)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					building = new TYBuilding();
					building.setCityID(rs.getString(FIELD_BUILDING_1_CITY_ID));
					building.setBuildingID(rs.getString(FIELD_BUILDING_2_ID));
					building.setName(rs.getString(FIELD_BUILDING_3_NAME));

					building.setLongitude(rs.getDouble(FIELD_BUILDING_4_LONGITUDE));
					building.setLatitude(rs.getDouble(FIELD_BUILDING_5_LATITUDE));

					building.setAddress(rs.getString(FIELD_BUILDING_6_ADDRESS));
					building.setInitAngle(rs.getDouble(FIELD_BUILDING_7_INIT_ANGLE));
					building.setRouteURL(rs.getString(FIELD_BUILDING_8_ROUTE_URL));
					building.setOffset(new TYMapSize(rs.getDouble(FIELD_BUILDING_9_OFFSET_X),
							rs.getDouble(FIELD_BUILDING_10_OFFSET_Y)));
					building.setStatus(rs.getInt(FIELD_BUILDING_11_STATUS));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return building;
	}

	public boolean existBuilding(String buildingID) {
		int result = 0;
		String sql = String.format("select count(*)  from %s where %s='%s'", TABLE_BUILDING, FIELD_BUILDING_2_ID,
				buildingID);
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

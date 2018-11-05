package cn.platalk.brtmap.db.map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYMapInfo;

public class TYMapInfoDBAdapter {
	static final String TABLE_MAPINFO = "MAPINFO";

	static final String FIELD_MAPINFO_1_CITY_ID = "CITY_ID";
	static final String FIELD_MAPINFO_2_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_MAPINFO_3_MAP_ID = "MAP_ID";
	static final String FIELD_MAPINFO_4_FLOOR_NAME = "FLOOR_NAME";
	static final String FIELD_MAPINFO_5_FLOOR_NUMBER = "FLOOR_NUMBER";
	static final String FIELD_MAPINFO_6_SIZE_X = "SIZE_X";
	static final String FIELD_MAPINFO_7_SIZE_Y = "SIZE_Y";
	static final String FIELD_MAPINFO_8_XMIN = "XMIN";
	static final String FIELD_MAPINFO_9_YMIN = "YMIN";
	static final String FIELD_MAPINFO_10_XMAX = "XMAX";
	static final String FIELD_MAPINFO_11_YMAX = "YMAX";

	private Connection connection;

	public TYMapInfoDBAdapter() {
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
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_MAPINFO + " (_id INT NOT NULL AUTO_INCREMENT, "
				+ "CITY_ID VARCHAR(45) NOT NULL," + "BUILDING_ID VARCHAR(45) NOT NULL," + "MAP_ID VARCHAR(45) NOT NULL,"
				+ "FLOOR_NAME VARCHAR(45) NOT NULL," + "FLOOR_NUMBER INT NOT NULL," + "SIZE_X DOUBLE NOT NULL,"
				+ "SIZE_Y DOUBLE NOT NULL," + "XMIN DOUBLE NOT NULL," + "YMIN DOUBLE NOT NULL,"
				+ "XMAX DOUBLE NOT NULL," + "YMAX DOUBLE NOT NULL," + " PRIMARY KEY (_id),"
				+ " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eraseMapInfoTable() {
		String sql = String.format("delete from %s", TABLE_MAPINFO);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteMapInfoByID(String mapID) {
		String sql = String.format("delete from %s where %s='%s'", TABLE_MAPINFO, FIELD_MAPINFO_3_MAP_ID, mapID);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteMapInfosByBuildingID(String buildingID) {
		String sql = String.format("delete from %s where %s='%s'", TABLE_MAPINFO, FIELD_MAPINFO_2_BUILDING_ID,
				buildingID);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertOrUpdateMapInfos(List<TYMapInfo> mapInfoList) {
		int count = 0;
		for (TYMapInfo mapInfo : mapInfoList) {
			count += insertOrUpdateMapInfo(mapInfo);
		}
		return count;
	}

	public int insertOrUpdateMapInfo(TYMapInfo mapInfo) {
		if (!existMapInfo(mapInfo.getMapID())) {
			return insertMapInfo(mapInfo);
		} else {
			return updateMapInfo(mapInfo);
		}
	}

	int insertMapInfo(TYMapInfo mapInfo) {
		int result = 0;
		String sql = String.format(
				"insert into %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)  values (?,?,?,?,?,?,?,?,?,?,?)",
				TABLE_MAPINFO, FIELD_MAPINFO_1_CITY_ID, FIELD_MAPINFO_2_BUILDING_ID, FIELD_MAPINFO_3_MAP_ID,
				FIELD_MAPINFO_4_FLOOR_NAME, FIELD_MAPINFO_5_FLOOR_NUMBER, FIELD_MAPINFO_6_SIZE_X,
				FIELD_MAPINFO_7_SIZE_Y, FIELD_MAPINFO_8_XMIN, FIELD_MAPINFO_9_YMIN, FIELD_MAPINFO_10_XMAX,
				FIELD_MAPINFO_11_YMAX);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setString(1, mapInfo.getCityID());
			stmt.setString(2, mapInfo.getBuildingID());
			stmt.setString(3, mapInfo.getMapID());
			stmt.setString(4, mapInfo.getFloorName());
			stmt.setInt(5, mapInfo.getFloorNumber());
			stmt.setDouble(6, mapInfo.getMapSize().getX());
			stmt.setDouble(7, mapInfo.getMapSize().getY());
			stmt.setDouble(8, mapInfo.getMapExtent().getXmin());
			stmt.setDouble(9, mapInfo.getMapExtent().getYmin());
			stmt.setDouble(10, mapInfo.getMapExtent().getXmax());
			stmt.setDouble(11, mapInfo.getMapExtent().getYmax());
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	int updateMapInfo(TYMapInfo mapInfo) {
		int result = 0;
		StringBuffer buffer = new StringBuffer(
				String.format("update %s set  %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?",
						TABLE_MAPINFO, FIELD_MAPINFO_1_CITY_ID, FIELD_MAPINFO_2_BUILDING_ID, FIELD_MAPINFO_4_FLOOR_NAME,
						FIELD_MAPINFO_5_FLOOR_NUMBER, FIELD_MAPINFO_6_SIZE_X, FIELD_MAPINFO_7_SIZE_Y,
						FIELD_MAPINFO_8_XMIN, FIELD_MAPINFO_9_YMIN, FIELD_MAPINFO_10_XMAX, FIELD_MAPINFO_11_YMAX));
		buffer.append(String.format(" where %s='%s'", FIELD_MAPINFO_3_MAP_ID, mapInfo.getMapID()));
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(buffer.toString());
			stmt.setString(1, mapInfo.getCityID());
			stmt.setString(2, mapInfo.getBuildingID());
			stmt.setString(3, mapInfo.getFloorName());
			stmt.setInt(4, mapInfo.getFloorNumber());
			stmt.setDouble(5, mapInfo.getMapSize().getX());
			stmt.setDouble(6, mapInfo.getMapSize().getY());
			stmt.setDouble(7, mapInfo.getMapExtent().getXmin());
			stmt.setDouble(8, mapInfo.getMapExtent().getYmin());
			stmt.setDouble(9, mapInfo.getMapExtent().getXmax());
			stmt.setDouble(10, mapInfo.getMapExtent().getYmax());

			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<TYMapInfo> getMapInfos() {
		String sql = String.format("select * from %s", TABLE_MAPINFO);
		return queryMapInfos(sql);
	}

	public List<TYMapInfo> getMapInfos(String buildingID) {
		String sql = String.format("select * from %s where %s='%s'", TABLE_MAPINFO, FIELD_MAPINFO_2_BUILDING_ID,
				buildingID);
		return queryMapInfos(sql);
	}

	public TYMapInfo getMapInfo(String mapID) {
		String sql = String.format("select * from %s where %s='%s'", TABLE_MAPINFO, FIELD_MAPINFO_3_MAP_ID, mapID);
		return queryMapInfo(sql);
	}

	private List<TYMapInfo> queryMapInfos(String sql) {
		List<TYMapInfo> resultMapInfoList = new ArrayList<TYMapInfo>();
		if (existTable(TABLE_MAPINFO)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					TYMapInfo info = new TYMapInfo();
					info.setCityID(rs.getString(FIELD_MAPINFO_1_CITY_ID));
					info.setBuildingID(rs.getString(FIELD_MAPINFO_2_BUILDING_ID));
					info.setMapID(rs.getString(FIELD_MAPINFO_3_MAP_ID));
					info.setFloorName(rs.getString(FIELD_MAPINFO_4_FLOOR_NAME));
					info.setFloorNumber(rs.getInt(FIELD_MAPINFO_5_FLOOR_NUMBER));
					info.setSize_x(rs.getDouble(FIELD_MAPINFO_6_SIZE_X));
					info.setSize_y(rs.getDouble(FIELD_MAPINFO_7_SIZE_Y));
					info.setXmin(rs.getDouble(FIELD_MAPINFO_8_XMIN));
					info.setYmin(rs.getDouble(FIELD_MAPINFO_9_YMIN));
					info.setXmax(rs.getDouble(FIELD_MAPINFO_10_XMAX));
					info.setYmax(rs.getDouble(FIELD_MAPINFO_11_YMAX));
					resultMapInfoList.add(info);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultMapInfoList;
	}

	private TYMapInfo queryMapInfo(String sql) {
		TYMapInfo info = null;
		if (existTable(TABLE_MAPINFO)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					info = new TYMapInfo();
					info.setCityID(rs.getString(FIELD_MAPINFO_1_CITY_ID));
					info.setBuildingID(rs.getString(FIELD_MAPINFO_2_BUILDING_ID));
					info.setMapID(rs.getString(FIELD_MAPINFO_3_MAP_ID));
					info.setFloorName(rs.getString(FIELD_MAPINFO_4_FLOOR_NAME));
					info.setFloorNumber(rs.getInt(FIELD_MAPINFO_5_FLOOR_NUMBER));
					info.setSize_x(rs.getDouble(FIELD_MAPINFO_6_SIZE_X));
					info.setSize_y(rs.getDouble(FIELD_MAPINFO_7_SIZE_Y));
					info.setXmin(rs.getDouble(FIELD_MAPINFO_8_XMIN));
					info.setYmin(rs.getDouble(FIELD_MAPINFO_9_YMIN));
					info.setXmax(rs.getDouble(FIELD_MAPINFO_10_XMAX));
					info.setYmax(rs.getDouble(FIELD_MAPINFO_11_YMAX));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	public boolean existMapInfo(String mapID) {
		int result = 0;
		String sql = String.format("select count(*)  from %s where %s='%s'", TABLE_MAPINFO, FIELD_MAPINFO_3_MAP_ID,
				mapID);
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

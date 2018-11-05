package cn.platalk.brtmap.db.map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYCity;

public class TYCityDBAdapter {
	static final String TABLE_CITY = "CITY";

	static final String FIELD_CITY_0_PRIMARY_KEY = "_id";
	static final String FIELD_CITY_1_ID = "CITY_ID";
	static final String FIELD_CITY_2_NAME = "NAME";
	static final String FIELD_CITY_3_SNAME = "SNAME";
	static final String FIELD_CITY_4_LONGITUDE = "LONGITUDE";
	static final String FIELD_CITY_5_LATITUDE = "LATITUDE";
	static final String FIELD_CITY_6_STATUS = "STATUS";
	private Connection connection;

	public TYCityDBAdapter() {
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
		String sql = "CREATE TABLE  IF NOT EXISTS " + TABLE_CITY + "(_id INT NOT NULL AUTO_INCREMENT,"
				+ "CITY_ID VARCHAR(45) NOT NULL," + "NAME VARCHAR(45) NOT NULL," + "SNAME VARCHAR(45) NOT NULL,"
				+ "LONGITUDE DOUBLE NOT NULL," + "LATITUDE DOUBLE NOT NULL," + "STATUS INT NOT NULL,"
				+ " PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eraseCityTable() {
		String sql = String.format("delete from %s", TABLE_CITY);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCity(String cityID) {
		String sql = String.format("delete from %s where %s='%s'", TABLE_CITY, FIELD_CITY_1_ID, cityID);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertOrUpdateCities(List<TYCity> cityList) {
		int count = 0;
		for (TYCity city : cityList) {
			count += insertOrUpdateCity(city);
		}
		return count;
	}

	public int insertOrUpdateCity(TYCity city) {
		if (!existCity(city.getCityID())) {
			return insertCity(city);
		} else {
			return updateCity(city);
		}
	}

	int insertCity(TYCity city) {
		int result = 0;
		String sql = String.format("insert into %s (%s, %s, %s, %s,%s, %s) values (?,?,?,?,?,?)", TABLE_CITY,
				FIELD_CITY_1_ID, FIELD_CITY_2_NAME, FIELD_CITY_3_SNAME, FIELD_CITY_4_LONGITUDE, FIELD_CITY_5_LATITUDE,
				FIELD_CITY_6_STATUS);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setString(1, city.getCityID());
			stmt.setString(2, city.getName());
			stmt.setString(3, city.getSname());
			stmt.setDouble(4, city.getLongitude());
			stmt.setDouble(5, city.getLatitude());
			stmt.setInt(6, city.getStatus());
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	int updateCity(TYCity city) {
		int result = 0;
		StringBuffer buffer = new StringBuffer(
				String.format("update %s set %s=?, %s=?, %s=?, %s=?, %s=?", TABLE_CITY, FIELD_CITY_2_NAME,
						FIELD_CITY_3_SNAME, FIELD_CITY_4_LONGITUDE, FIELD_CITY_5_LATITUDE, FIELD_CITY_6_STATUS));
		buffer.append(String.format(" where %s='%s'", FIELD_CITY_1_ID, city.getCityID()));
		PreparedStatement stmt;

		try {
			stmt = (PreparedStatement) connection.prepareStatement(buffer.toString());
			stmt.setString(1, city.getName());
			stmt.setString(2, city.getSname());
			stmt.setDouble(3, city.getLongitude());
			stmt.setDouble(4, city.getLatitude());
			stmt.setInt(5, city.getStatus());

			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<TYCity> getCities() {
		String sql = String.format("select * from %s where %s=%d", TABLE_CITY, FIELD_CITY_6_STATUS, 0);
		return queryCities(sql);
	}

	public TYCity getCity(String cityID) {
		String sql = String.format("select * from %s where %s='%s' and %s=%d", TABLE_CITY, FIELD_CITY_1_ID, cityID,
				FIELD_CITY_6_STATUS, 0);
		return queryCity(sql);
	}

	private List<TYCity> queryCities(String sql) {
		List<TYCity> resultCityList = new ArrayList<TYCity>();
		if (existTable(TABLE_CITY)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					TYCity city = new TYCity();
					city.setCityID(rs.getString(FIELD_CITY_1_ID));
					city.setName(rs.getString(FIELD_CITY_2_NAME));
					city.setSname(rs.getString(FIELD_CITY_3_SNAME));
					city.setLongitude(rs.getDouble(FIELD_CITY_4_LONGITUDE));
					city.setLatitude(rs.getDouble(FIELD_CITY_5_LATITUDE));
					city.setStatus(rs.getInt(FIELD_CITY_6_STATUS));
					resultCityList.add(city);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultCityList;
	}

	private TYCity queryCity(String sql) {
		TYCity city = null;
		if (existTable(TABLE_CITY)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					city = new TYCity();
					city.setCityID(rs.getString(FIELD_CITY_1_ID));
					city.setName(rs.getString(FIELD_CITY_2_NAME));
					city.setSname(rs.getString(FIELD_CITY_3_SNAME));
					city.setLongitude(rs.getDouble(FIELD_CITY_4_LONGITUDE));
					city.setLatitude(rs.getDouble(FIELD_CITY_5_LATITUDE));
					city.setStatus(rs.getInt(FIELD_CITY_6_STATUS));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return city;
	}

	public boolean existCity(String cityID) {
		int result = 0;
		String sql = String.format("select count(*)  from %s where %s='%s'", TABLE_CITY, FIELD_CITY_1_ID, cityID);
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

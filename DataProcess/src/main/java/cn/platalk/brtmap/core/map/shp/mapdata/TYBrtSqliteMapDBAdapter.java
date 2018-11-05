package cn.platalk.brtmap.core.map.shp.mapdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYCity;
import cn.platalk.brtmap.entity.base.impl.TYMapSize;

public class TYBrtSqliteMapDBAdapter {
	static final String TABLE_CITY = "CITY";
	static final String TABLE_BUILDING = "BUILDING";

	static final String FIELD_CITY_0_PRIMARY_KEY = "_id";
	static final String FIELD_CITY_1_ID = "CITY_ID";
	static final String FIELD_CITY_2_NAME = "NAME";
	static final String FIELD_CITY_3_SNAME = "SNAME";
	static final String FIELD_CITY_4_LONGITUDE = "LONGITUDE";
	static final String FIELD_CITY_5_LATITUDE = "LATITUDE";
	static final String FIELD_CITY_6_STATUS = "STATUS";

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
	private String dbPath;

	public TYBrtSqliteMapDBAdapter(String path) {
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

	public List<TYBuilding> queryBuildings() {
		String sql = String.format("select * from %s", TABLE_BUILDING);
		return queryBuildings(sql);
	}

	public List<TYCity> queryCities() {
		String sql = String.format("select * from %s", TABLE_CITY);
		return queryCities(sql);
	}

	List<TYBuilding> queryBuildings(String sql) {
		List<TYBuilding> buildingList = new ArrayList<TYBuilding>();
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
				TYMapSize size = new TYMapSize(
						rs.getDouble(FIELD_BUILDING_9_OFFSET_X),
						rs.getDouble(FIELD_BUILDING_10_OFFSET_Y));
				building.setOffset(size);
				building.setStatus(rs.getInt(FIELD_BUILDING_11_STATUS));

				buildingList.add(building);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buildingList;
	}

	List<TYCity> queryCities(String sql) {
		List<TYCity> cityList = new ArrayList<TYCity>();
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
				cityList.add(city);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityList;
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

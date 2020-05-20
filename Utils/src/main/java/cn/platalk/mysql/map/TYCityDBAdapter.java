package cn.platalk.mysql.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.TYCity;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class TYCityDBAdapter {
	final IPMysqlDB db;
	final IPSqlTable cityTable;

	public TYCityDBAdapter() {
		db = new IPMysqlDB(TYDatabaseManager.GetMapDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		cityTable = IPMysqlCityParams.CreateTable();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void connectDB() {
		db.connectDB();
	}

	public void disconnectDB() {
		db.disconnectDB();
	}

	public void createTableIfNotExist() {
		// String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_CITY + "(_id INT
		// NOT NULL AUTO_INCREMENT,"
		// + "CITY_ID VARCHAR(45) NOT NULL," + "NAME VARCHAR(45) NOT NULL," +
		// "SNAME VARCHAR(45) NOT NULL,"
		// + "LONGITUDE DOUBLE NOT NULL," + "LATITUDE DOUBLE NOT NULL," +
		// "STATUS INT NOT NULL,"
		// + " PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		// try {
		// Statement stmt = connection.createStatement();
		// stmt.executeUpdate(sql);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// try {
		// Statement stmt = connection.createStatement();
		// stmt.executeUpdate(MysqlBuilder.tableCreateSql(table));
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		db.createTable(cityTable);
	}

	public void dropCityTable() {
		db.dropTable(cityTable);
	}

	public void eraseCityTable() {
		db.eraseTable(cityTable);
	}

	public void deleteCity(String cityID) {
		db.deleteRecord(cityTable, cityTable.getField(IPMysqlCityParams.FIELD_CITY_1_ID), cityID);
	}

	public int insertOrUpdateCities(List<TYCity> cityList) {
		int count = 0;
		for (TYCity city : cityList) {
			count += insertOrUpdateCity(city);
		}
		return count;
	}

	public int insertCitiesInBatch(List<TYCity> cityList) {
		List<Map<String, Object>> dataList = new ArrayList<>();

		for (TYCity city : cityList) {
			dataList.add(IPMysqlCityParams.DataMapFromCity(city));
		}
		return db.insertDataListInBatch(cityTable, dataList);
	}

	public int insertOrUpdateCity(TYCity city) {
		if (!existCity(city.getCityID())) {
			return insertCity(city);
		} else {
			return updateCity(city);
		}
	}

	int insertCity(TYCity city) {
		return db.insertData(cityTable, IPMysqlCityParams.DataMapFromCity(city));
	}

	int updateCity(TYCity city) {
		return db.updateData(cityTable, IPMysqlCityParams.DataMapFromCity(city),
				cityTable.getField(IPMysqlCityParams.FIELD_CITY_1_ID), city.getCityID());
	}

	public List<TYCity> getCities() {
		return IPMysqlCityParams.CityListFromRecords(db.readData(cityTable));
	}

	public TYCity getCity(String cityID) {
		List<TYCity> cities = IPMysqlCityParams.CityListFromRecords(
				db.readData(cityTable, cityTable.getField(IPMysqlCityParams.FIELD_CITY_1_ID), cityID));
		if (cities.size() > 0) {
			return cities.get(0);
		}
		return null;
	}

	public boolean existCity(String cityID) {
		return db.existRecord(cityTable, cityTable.getField(IPMysqlCityParams.FIELD_CITY_1_ID), cityID);
	}

	boolean existTable() {
		return db.existTable(cityTable);
	}

}

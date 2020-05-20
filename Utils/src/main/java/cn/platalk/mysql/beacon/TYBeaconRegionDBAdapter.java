package cn.platalk.mysql.beacon;

import java.sql.Connection;
import java.util.List;

import cn.platalk.map.entity.base.TYIBeaconRegion;
import cn.platalk.map.entity.base.impl.TYBeaconRegion;
import cn.platalk.mysql.map.TYDatabaseManager;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class TYBeaconRegionDBAdapter {
	final IPMysqlDB db;
	final IPSqlTable beaconRegionTable;

	public TYBeaconRegionDBAdapter() {
		db = new IPMysqlDB(TYDatabaseManager.GetBeaconDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		beaconRegionTable = IPMysqlBeaconRegionParams.CreateTable();
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
		return db.connectDB();
	}

	public void disconnectDB() {
		db.disconnectDB();
	}

	public void createTableIfNotExist() {
		// String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_BEACON_REGION + "
		// (_id INT NOT NULL AUTO_INCREMENT, "
		// + "CITY_ID VARCHAR(45) NOT NULL, " + "BUILDING_ID VARCHAR(45) NOT
		// NULL, "
		// + "BUILDING_NAME VARCHAR(45) NOT NULL, " + "UUID VARCHAR(45) NOT
		// NULL, " + "MAJOR INT, "
		// + " PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		// try {
		// Statement stmt = connection.createStatement();
		// stmt.executeUpdate(sql);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		db.createTable(beaconRegionTable);
	}

	public void deleteBeaconRegion(String buildingID) {
		db.deleteRecord(beaconRegionTable,
				beaconRegionTable.getField(IPMysqlBeaconRegionParams.FIELD_BEACON_REGION_2_BUILDING_ID), buildingID);
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
		return db.insertData(beaconRegionTable, IPMysqlBeaconRegionParams.DataMapFromBeaconRegion(region));
	}

	int updateBeaconRegion(TYIBeaconRegion region) {
		return db.updateData(beaconRegionTable, IPMysqlBeaconRegionParams.DataMapFromBeaconRegion(region),
				beaconRegionTable.getField(IPMysqlBeaconRegionParams.FIELD_BEACON_REGION_2_BUILDING_ID),
				region.getBuildingID());
	}

	public List<TYBeaconRegion> getAllBeaconRegions() {
		return IPMysqlBeaconRegionParams.BeaconRegionListFromRecords(db.readData(beaconRegionTable));
	}

	public TYBeaconRegion getBeaconRegion(String buildingID) {
		List<TYBeaconRegion> regionList = IPMysqlBeaconRegionParams.BeaconRegionListFromRecords(db.readData(
				beaconRegionTable,
				beaconRegionTable.getField(IPMysqlBeaconRegionParams.FIELD_BEACON_REGION_2_BUILDING_ID), buildingID));
		if (regionList != null && regionList.size() > 0) {
			return regionList.get(0);
		}
		return null;
	}

	public boolean existBeaconRegion(String buildingID) {
		return db.existRecord(beaconRegionTable,
				beaconRegionTable.getField(IPMysqlBeaconRegionParams.FIELD_BEACON_REGION_2_BUILDING_ID), buildingID);
	}

	boolean existTable() {
		return db.existTable(beaconRegionTable);
	}
}

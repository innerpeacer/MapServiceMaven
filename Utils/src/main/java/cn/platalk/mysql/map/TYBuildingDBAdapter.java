package cn.platalk.mysql.map;

import java.util.List;

import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class TYBuildingDBAdapter {
	final IPMysqlDB db;
	final IPSqlTable buildingTable;

	public TYBuildingDBAdapter() {
		db = new IPMysqlDB(TYDatabaseManager.GetMapDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		buildingTable = IPMysqlBuildingParams.CreateTable();
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

	public void connectDB() {
		db.connectDB();
	}

	public void disconnectDB() {
		db.disconnectDB();
	}

	public void createTableIfNotExist() {
		// String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_BUILDING + " (_id
		// INT NOT NULL AUTO_INCREMENT, "
		// + "CITY_ID VARCHAR(45) NOT NULL," + "BUILDING_ID VARCHAR(45) NOT
		// NULL," + "NAME VARCHAR(255) NOT NULL,"
		// + "LONGITUDE DOUBLE NOT NULL," + "LATITUDE DOUBLE NOT NULL," +
		// "ADDRESS VARCHAR(255) NOT NULL,"
		// + "INIT_ANGLE DOUBLE NOT NULL," + "ROUTE_URL VARCHAR(255) NOT NULL,"
		// + "OFFSET_X DOUBLE NOT NULL,"
		// + "OFFSET_Y DOUBLE NOT NULL," + "STATUS INT NOT NULL," + " PRIMARY
		// KEY (_id),"
		// + " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		// try {
		// Statement stmt = connection.createStatement();
		// stmt.executeUpdate(sql);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		db.createTable(buildingTable);
	}

	public void eraseBuildingTable() {
		db.eraseTable(buildingTable);
	}

	public void deleteBuilding(String buildingID) {
		db.deleteRecord(buildingTable, buildingTable.getField(IPMysqlBuildingParams.FIELD_BUILDING_2_ID), buildingID);
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
		return db.insertData(buildingTable, IPMysqlBuildingParams.DataMapFromBuilding(building));
	}

	int updateBuilding(TYBuilding building) {
		return db.updateData(buildingTable, IPMysqlBuildingParams.DataMapFromBuilding(building),
				buildingTable.getField(IPMysqlBuildingParams.FIELD_BUILDING_2_ID), building.getBuildingID());
	}

	public List<TYBuilding> getBuildings() {
		return IPMysqlBuildingParams.BuildingListFromRecords(db.readData(buildingTable));
	}

	public List<TYBuilding> getBuildingsInCity(String cityID) {
		return IPMysqlBuildingParams.BuildingListFromRecords(db.readData(buildingTable,
				buildingTable.getField(IPMysqlBuildingParams.FIELD_BUILDING_1_CITY_ID), cityID));
	}

	public TYBuilding getBuilding(String buildingID) {
		List<TYBuilding> buildings = IPMysqlBuildingParams.BuildingListFromRecords(db.readData(buildingTable,
				buildingTable.getField(IPMysqlBuildingParams.FIELD_BUILDING_2_ID), buildingID));
		if (buildings != null && buildings.size() > 0) {
			return buildings.get(0);
		}
		return null;
	}

	public boolean existBuilding(String buildingID) {
		return db.existRecord(buildingTable, buildingTable.getField(IPMysqlBuildingParams.FIELD_BUILDING_2_ID),
				buildingID);
	}

	boolean existTable() {
		return db.existTable(buildingTable);
	}
}

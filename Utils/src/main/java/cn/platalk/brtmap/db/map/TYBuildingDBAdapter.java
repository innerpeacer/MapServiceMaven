package cn.platalk.brtmap.db.map;

import java.sql.Connection;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.sqlhelper.mysql.MysqlDB;
import cn.platalk.sqlhelper.mysql.MysqlTable;

public class TYBuildingDBAdapter {
	MysqlDB db;
	MysqlTable buildingTable;

	public TYBuildingDBAdapter() {
		db = new MysqlDB(TYDatabaseManager.GetMapDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		buildingTable = MysqlBuildingParams.CreateTable();
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
		db.deleteRecord(buildingTable, buildingTable.getField(MysqlBuildingParams.FIELD_BUILDING_2_ID), buildingID);
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
		return db.insertData(buildingTable, MysqlBuildingParams.DataMapFromBuilding(building));
	}

	int updateBuilding(TYBuilding building) {
		return db.updateData(buildingTable, MysqlBuildingParams.DataMapFromBuilding(building),
				buildingTable.getField(MysqlBuildingParams.FIELD_BUILDING_2_ID), building.getBuildingID());
	}

	public List<TYBuilding> getBuildings() {
		return MysqlBuildingParams.BuildingListFromRecords(db.readData(buildingTable));
	}

	public List<TYBuilding> getBuildingsInCity(String cityID) {
		return MysqlBuildingParams.BuildingListFromRecords(db.readData(buildingTable,
				buildingTable.getField(MysqlBuildingParams.FIELD_BUILDING_1_CITY_ID), cityID));
	}

	public TYBuilding getBuilding(String buildingID) {
		List<TYBuilding> buildings = MysqlBuildingParams.BuildingListFromRecords(db.readData(buildingTable,
				buildingTable.getField(MysqlBuildingParams.FIELD_BUILDING_2_ID), buildingID));
		if (buildings != null && buildings.size() > 0) {
			return buildings.get(0);
		}
		return null;
	}

	public boolean existBuilding(String buildingID) {
		return db.existRecord(buildingTable, buildingTable.getField(MysqlBuildingParams.FIELD_BUILDING_2_ID),
				buildingID);
	}

	boolean existTable() {
		return db.existTable(buildingTable);
	}
}

package cn.platalk.mysql.map;

import cn.platalk.map.entity.base.impl.map.TYBuilding;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

import java.util.List;

public class TYBuildingDBAdapter {
	final IPMysqlDB db;
	final IPSqlTable buildingTable;

	public TYBuildingDBAdapter() {
		db = new IPMysqlDB(TYDatabaseManager.GetMapDBUrl(), TYDatabaseManager.GetUserName(), TYDatabaseManager.GetPassword());
		buildingTable = IPMysqlBuildingParams.CreateTable();
	}

	public void connectDB() {
		db.connectDB();
	}

	public void disconnectDB() {
		db.disconnectDB();
	}

	public void createTableIfNotExist() {
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
		return db.updateData(buildingTable, IPMysqlBuildingParams.DataMapFromBuilding(building), buildingTable.getField(IPMysqlBuildingParams.FIELD_BUILDING_2_ID), building.getBuildingID());
	}

	public List<TYBuilding> getBuildings() {
		return IPMysqlBuildingParams.BuildingListFromRecords(db.readData(buildingTable));
	}

	public List<TYBuilding> getBuildingsInCity(String cityID) {
		return IPMysqlBuildingParams.BuildingListFromRecords(db.readData(buildingTable, buildingTable.getField(IPMysqlBuildingParams.FIELD_BUILDING_1_CITY_ID), cityID));
	}

	public TYBuilding getBuilding(String buildingID) {
		List<TYBuilding> buildings = IPMysqlBuildingParams.BuildingListFromRecords(db.readData(buildingTable, buildingTable.getField(IPMysqlBuildingParams.FIELD_BUILDING_2_ID), buildingID));
		if (buildings.size() > 0) {
			return buildings.get(0);
		}
		return null;
	}

	public boolean existBuilding(String buildingID) {
		return db.existRecord(buildingTable, buildingTable.getField(IPMysqlBuildingParams.FIELD_BUILDING_2_ID), buildingID);
	}

	boolean existTable() {
		return db.existTable(buildingTable);
	}
}

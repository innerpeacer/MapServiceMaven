package cn.platalk.sqlite.map;

import java.util.List;

import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYCity;
import cn.platalk.sqlhelper.sql.IPSqlTable;
import cn.platalk.sqlhelper.sqlite.IPSqliteDB;

public class IPSqliteMapDBAdapter {
	IPSqliteDB db;
	IPSqlTable cityTable;
	IPSqlTable buildingTable;

	public IPSqliteMapDBAdapter(String path) {
		db = new IPSqliteDB(path);
		cityTable = new IPSqlTable(IPSqliteCityParams.TABLE_CITY, IPSqliteCityParams.GetCityFieldList(), "_id");
		buildingTable = new IPSqlTable(IPSqliteBuildingParams.TABLE_BUILDING,
				IPSqliteBuildingParams.GetBuildingFieldList(), null);
	}

	public boolean open() {
		return db.open();
	}

	public void close() {
		db.close();
	}

	public void createCityTableIfNotExist() {
		db.createSqliteTable(cityTable);
	}

	public void dropCityTable() {
		db.dropTable(cityTable);
	}

	public void eraseCityTable() {
		db.eraseTable(cityTable);
	}

	public void deleteCity(String cityID) {
		db.deleteRecord(cityTable, cityTable.getField(IPSqliteCityParams.FIELD_CITY_1_ID), cityID);
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
		return db.insertData(cityTable, IPSqliteCityParams.DataMapFromCity(city));
	}

	int updateCity(TYCity city) {
		return db.updateData(cityTable, IPSqliteCityParams.DataMapFromCity(city),
				cityTable.getField(IPSqliteCityParams.FIELD_CITY_1_ID), city.getCityID());
	}

	public List<TYCity> getCities() {
		return IPSqliteCityParams.CityListFromRecords(db.readData(cityTable));
	}

	public TYCity getCity(String cityID) {
		List<TYCity> cities = IPSqliteCityParams.CityListFromRecords(
				db.readData(cityTable, cityTable.getField(IPSqliteCityParams.FIELD_CITY_1_ID), cityID));
		if (cities != null && cities.size() > 0) {
			return cities.get(0);
		}
		return null;
	}

	public boolean existCity(String cityID) {
		return db.existRecord(cityTable, cityTable.getField(IPSqliteCityParams.FIELD_CITY_1_ID), cityID);
	}

	boolean existCityTable() {
		return db.existTable(cityTable);
	}

	public List<TYCity> queryCities() {
		return IPSqliteCityParams.CityListFromRecords(db.readData(cityTable));
	}

	public void createBuildingTableIfNotExist() {
		db.createSqliteTable(buildingTable);
	}

	public void eraseBuildingTable() {
		db.eraseTable(buildingTable);
	}

	public void deleteBuilding(String buildingID) {
		db.deleteRecord(buildingTable, buildingTable.getField(IPSqliteBuildingParams.FIELD_BUILDING_2_ID), buildingID);
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
		return db.insertData(buildingTable, IPSqliteBuildingParams.DataMapFromBuilding(building));
	}

	int updateBuilding(TYBuilding building) {
		return db.updateData(buildingTable, IPSqliteBuildingParams.DataMapFromBuilding(building),
				buildingTable.getField(IPSqliteBuildingParams.FIELD_BUILDING_2_ID), building.getBuildingID());
	}

	public List<TYBuilding> getBuildings() {
		return IPSqliteBuildingParams.BuildingListFromRecords(db.readData(buildingTable));
	}

	public List<TYBuilding> getBuildingsInCity(String cityID) {
		return IPSqliteBuildingParams.BuildingListFromRecords(db.readData(buildingTable,
				buildingTable.getField(IPSqliteBuildingParams.FIELD_BUILDING_1_CITY_ID), cityID));
	}

	public TYBuilding getBuilding(String buildingID) {
		List<TYBuilding> buildings = IPSqliteBuildingParams.BuildingListFromRecords(db.readData(buildingTable,
				buildingTable.getField(IPSqliteBuildingParams.FIELD_BUILDING_2_ID), buildingID));
		if (buildings != null && buildings.size() > 0) {
			return buildings.get(0);
		}
		return null;
	}

	public boolean existBuilding(String buildingID) {
		return db.existRecord(buildingTable, buildingTable.getField(IPSqliteBuildingParams.FIELD_BUILDING_2_ID),
				buildingID);
	}

	boolean existTable() {
		return db.existTable(buildingTable);
	}

	public List<TYBuilding> queryBuildings() {
		return IPSqliteBuildingParams.BuildingListFromRecords(db.readData(buildingTable));
	}
}

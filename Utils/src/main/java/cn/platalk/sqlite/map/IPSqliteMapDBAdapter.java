package cn.platalk.sqlite.map;

import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYCity;
import cn.platalk.sqlhelper.sqlite.SqliteDB;
import cn.platalk.sqlhelper.sqlite.SqliteTable;

public class IPSqliteMapDBAdapter {
	SqliteDB db;
	SqliteTable cityTable;
	SqliteTable buildingTable;

	public IPSqliteMapDBAdapter(String path) {
		db = new SqliteDB(path);
		cityTable = new SqliteTable(SqliteCityParams.TABLE_CITY, SqliteCityParams.GetCityFieldList(), null);
		buildingTable = new SqliteTable(SqliteBuildingParams.TABLE_BUILDING,
				SqliteBuildingParams.GetBuildingFieldList(), null);
	}

	public boolean open() {
		return db.open();
	}

	public void close() {
		db.close();
	}

	public List<TYBuilding> queryBuildings() {
		return SqliteBuildingParams.BuildingListFromRecords(db.readData(buildingTable));
	}

	public List<TYCity> queryCities() {
		return SqliteCityParams.CityListFromRecords(db.readData(cityTable));
	}

	boolean existTable(String table) {
		return db.existTable(table);
	}
}

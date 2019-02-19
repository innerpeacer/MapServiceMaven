package cn.platalk.sqlite.map;

import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYCity;
import cn.platalk.sqlhelper.sql.SqlTable;
import cn.platalk.sqlhelper.sqlite.SqliteDB;

public class IPSqliteMapDBAdapter {
	SqliteDB db;
	SqlTable cityTable;
	SqlTable buildingTable;

	public IPSqliteMapDBAdapter(String path) {
		db = new SqliteDB(path);
		cityTable = new SqlTable(IPSqliteCityParams.TABLE_CITY, IPSqliteCityParams.GetCityFieldList(), null);
		buildingTable = new SqlTable(IPSqliteBuildingParams.TABLE_BUILDING, IPSqliteBuildingParams.GetBuildingFieldList(),
				null);
	}

	public boolean open() {
		return db.open();
	}

	public void close() {
		db.close();
	}

	public List<TYBuilding> queryBuildings() {
		return IPSqliteBuildingParams.BuildingListFromRecords(db.readData(buildingTable));
	}

	public List<TYCity> queryCities() {
		return IPSqliteCityParams.CityListFromRecords(db.readData(cityTable));
	}
}

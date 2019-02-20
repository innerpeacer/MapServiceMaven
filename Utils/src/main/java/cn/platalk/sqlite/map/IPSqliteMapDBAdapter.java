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
		cityTable = new IPSqlTable(IPSqliteCityParams.TABLE_CITY, IPSqliteCityParams.GetCityFieldList(), null);
		buildingTable = new IPSqlTable(IPSqliteBuildingParams.TABLE_BUILDING, IPSqliteBuildingParams.GetBuildingFieldList(),
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

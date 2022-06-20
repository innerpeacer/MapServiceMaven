package cn.platalk.mysql.map;

import cn.platalk.map.entity.base.impl.map.TYMapInfo;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

import java.util.List;

public class TYMapInfoDBAdapter {
	final IPMysqlDB db;
	final IPSqlTable mapInfoTable;

	public TYMapInfoDBAdapter() {
		db = new IPMysqlDB(TYDatabaseManager.GetMapDBUrl(), TYDatabaseManager.GetUserName(), TYDatabaseManager.GetPassword());
		mapInfoTable = IPMysqlMapInfoParams.CreateTable();
	}

	public void connectDB() {
		db.connectDB();
	}

	public void disconnectDB() {
		db.disconnectDB();
	}

	public void createTableIfNotExist() {
		db.createTable(mapInfoTable);
	}

	public void eraseMapInfoTable() {
		db.eraseTable(mapInfoTable);
	}

	public void deleteMapInfoByID(String mapID) {
		db.deleteRecord(mapInfoTable, mapInfoTable.getField(IPMysqlMapInfoParams.FIELD_MAPINFO_3_MAP_ID), mapID);
	}

	public void deleteMapInfosByBuildingID(String buildingID) {
		db.deleteRecord(mapInfoTable, mapInfoTable.getField(IPMysqlMapInfoParams.FIELD_MAPINFO_2_BUILDING_ID), buildingID);
	}

	public int insertOrUpdateMapInfos(List<TYMapInfo> mapInfoList) {
		int count = 0;
		for (TYMapInfo mapInfo : mapInfoList) {
			count += insertOrUpdateMapInfo(mapInfo);
		}
		return count;
	}

	public int insertOrUpdateMapInfo(TYMapInfo mapInfo) {
		if (!existMapInfo(mapInfo.getMapID())) {
			return insertMapInfo(mapInfo);
		} else {
			return updateMapInfo(mapInfo);
		}
	}

	int insertMapInfo(TYMapInfo mapInfo) {
		return db.insertData(mapInfoTable, IPMysqlMapInfoParams.DataMapFromMapInfo(mapInfo));
	}

	int updateMapInfo(TYMapInfo mapInfo) {
		return db.updateData(mapInfoTable, IPMysqlMapInfoParams.DataMapFromMapInfo(mapInfo), mapInfoTable.getField(IPMysqlMapInfoParams.FIELD_MAPINFO_3_MAP_ID), mapInfo.getMapID());
	}

	public List<TYMapInfo> getMapInfos() {
		return IPMysqlMapInfoParams.MapInfoListFromRecord(db.readData(mapInfoTable));
	}

	public List<TYMapInfo> getMapInfos(String buildingID) {
		return IPMysqlMapInfoParams.MapInfoListFromRecord(db.readData(mapInfoTable, mapInfoTable.getField(IPMysqlMapInfoParams.FIELD_MAPINFO_2_BUILDING_ID), buildingID));
	}

	public TYMapInfo getMapInfo(String mapID) {
		List<TYMapInfo> mapInfos = IPMysqlMapInfoParams.MapInfoListFromRecord(db.readData(mapInfoTable, mapInfoTable.getField(IPMysqlMapInfoParams.FIELD_MAPINFO_3_MAP_ID), mapID));
		if (mapInfos.size() > 0) {
			return mapInfos.get(0);
		}
		return null;
	}

	public boolean existMapInfo(String mapID) {
		return db.existRecord(mapInfoTable, mapInfoTable.getField(IPMysqlMapInfoParams.FIELD_MAPINFO_3_MAP_ID), mapID);
	}

	boolean existTable() {
		return db.existTable(mapInfoTable);
	}
}

package cn.platalk.mysql.map;

import java.sql.Connection;
import java.util.List;

import cn.platalk.brtmap.entity.base.impl.TYMapInfo;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class TYMapInfoDBAdapter {
	IPMysqlDB db;
	IPSqlTable mapInfoTable;

	public TYMapInfoDBAdapter() {
		db = new IPMysqlDB(TYDatabaseManager.GetMapDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		mapInfoTable = IPMysqlMapInfoParams.CreateTable();
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
		// String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_MAPINFO + " (_id
		// INT NOT NULL AUTO_INCREMENT, "
		// + "CITY_ID VARCHAR(45) NOT NULL," + "BUILDING_ID VARCHAR(45) NOT
		// NULL," + "MAP_ID VARCHAR(45) NOT NULL,"
		// + "FLOOR_NAME VARCHAR(45) NOT NULL," + "FLOOR_NUMBER INT NOT NULL," +
		// "SIZE_X DOUBLE NOT NULL,"
		// + "SIZE_Y DOUBLE NOT NULL," + "XMIN DOUBLE NOT NULL," + "YMIN DOUBLE
		// NOT NULL,"
		// + "XMAX DOUBLE NOT NULL," + "YMAX DOUBLE NOT NULL," + " PRIMARY KEY
		// (_id),"
		// + " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		// try {
		// Statement stmt = connection.createStatement();
		// stmt.executeUpdate(sql);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		db.createTable(mapInfoTable);
	}

	public void eraseMapInfoTable() {
		db.eraseTable(mapInfoTable);
	}

	public void deleteMapInfoByID(String mapID) {
		db.deleteRecord(mapInfoTable, mapInfoTable.getField(IPMysqlMapInfoParams.FIELD_MAPINFO_3_MAP_ID), mapID);
	}

	public void deleteMapInfosByBuildingID(String buildingID) {
		db.deleteRecord(mapInfoTable, mapInfoTable.getField(IPMysqlMapInfoParams.FIELD_MAPINFO_2_BUILDING_ID),
				buildingID);
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
		return db.updateData(mapInfoTable, IPMysqlMapInfoParams.DataMapFromMapInfo(mapInfo),
				mapInfoTable.getField(IPMysqlMapInfoParams.FIELD_MAPINFO_3_MAP_ID), mapInfo.getMapID());
	}

	public List<TYMapInfo> getMapInfos() {
		return IPMysqlMapInfoParams.MapInfoListFromRecord(db.readData(mapInfoTable));
	}

	public List<TYMapInfo> getMapInfos(String buildingID) {
		return IPMysqlMapInfoParams.MapInfoListFromRecord(db.readData(mapInfoTable,
				mapInfoTable.getField(IPMysqlMapInfoParams.FIELD_MAPINFO_2_BUILDING_ID), buildingID));
	}

	public TYMapInfo getMapInfo(String mapID) {
		List<TYMapInfo> mapInfos = IPMysqlMapInfoParams.MapInfoListFromRecord(
				db.readData(mapInfoTable, mapInfoTable.getField(IPMysqlMapInfoParams.FIELD_MAPINFO_3_MAP_ID), mapID));
		if (mapInfos != null && mapInfos.size() > 0) {
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

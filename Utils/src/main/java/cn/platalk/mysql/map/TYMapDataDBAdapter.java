package cn.platalk.mysql.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class TYMapDataDBAdapter {
	final String buildingID;
	final IPMysqlDB db;
	final IPSqlTable mapdataTable;

	public TYMapDataDBAdapter(String buildingID) {
		this.buildingID = buildingID;
		db = new IPMysqlDB(TYDatabaseManager.GetMapDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		mapdataTable = IPMysqlMapDataParams.CreateTable(buildingID);
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

	public void createTableIfNotExist() {
		// String sql = "CREATE TABLE IF NOT EXISTS " + TABLE + " (_id INT NOT
		// NULL AUTO_INCREMENT, "
		// + "OBJECT_ID VARCHAR(45) NOT NULL,"
		// // + "GEOMETRY BLOB NOT NULL, " + "GEO_ID VARCHAR(45) NOT NULL,"
		// + "GEOMETRY MediumBlob NOT NULL, " + "GEO_ID VARCHAR(45) NOT NULL," +
		// "POI_ID VARCHAR(45) NOT NULL,"
		// + "FLOOR_ID VARCHAR(45) NOT NULL," + "BUILDING_ID VARCHAR(45) NOT
		// NULL,"
		// + "CATEGORY_ID VARCHAR(45) NOT NULL," + "NAME VARCHAR(45)," +
		// "SYMBOL_ID INT NOT NULL,"
		// + "FLOOR_NUMBER INT NOT NULL," + "FLOOR_NAME VARCHAR(45) NOT NULL," +
		// "SHAPE_LENGTH DOUBLE NOT NULL,"
		// + "SHAPE_AREA DOUBLE NOT NULL," + "LABEL_X DOUBLE NOT NULL," +
		// "LABEL_Y DOUBLE NOT NULL,"
		// + "LAYER INT NOT NULL," + "LEVEL_MAX INT NOT NULL, " + "LEVEL_MIN INT
		// NOT NULL,"
		// + "EXTRUSION INT DEFAULT 0, " + "EXTRUSION_HEIGHT DOUBLE DEFAULT 0, "
		// + "EXTRUSION_BASE DOUBLE DEFAULT 0, " + " PRIMARY KEY (_id)," + "
		// UNIQUE INDEX _id_UNIQUE (_id ASC));";
		// try {
		// Statement stmt = connection.createStatement();
		// stmt.executeUpdate(sql);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		db.createTable(mapdataTable);
	}

	public void connectDB() {
		db.connectDB();
	}

	public void disconnectDB() {
		db.disconnectDB();
	}

	public void eraseMapDataTable() {
		db.eraseTable(mapdataTable);
	}

	public void deleteMapDataByGeoID(String geoID) {
		db.deleteRecord(mapdataTable, mapdataTable.getField(IPMysqlMapDataParams.FIELD_MAP_DATA_3_GEO_ID), geoID);
	}

	public void deleteMapDataByPoiID(String poiID) {
		db.deleteRecord(mapdataTable, mapdataTable.getField(IPMysqlMapDataParams.FIELD_MAP_DATA_4_POI_ID), poiID);
	}

	public int insertMapDataRecordsInBatch(List<TYMapDataFeatureRecord> recordList) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (TYMapDataFeatureRecord record : recordList) {
			dataList.add(IPMysqlMapDataParams.DataMapFromMapDataFeatureRecord(record));
		}
		return db.insertDataListInBatch(mapdataTable, dataList);
	}

	public int insertMapDataRecords(List<TYMapDataFeatureRecord> recordList) {
		int count = 0;
		for (TYMapDataFeatureRecord record : recordList) {
			count += insertMapDataRecord(record);
		}
		return count;
	}

	public int insertOrUpdateMapDataRecords(List<TYMapDataFeatureRecord> recordList) {
		int count = 0;
		for (TYMapDataFeatureRecord record : recordList) {
			count += insertOrUpdateMapDataRecord(record);
		}
		return count;
	}

	public int insertOrUpdateMapDataRecord(TYMapDataFeatureRecord record) {
		if (!existMapData(record.geoID, record.layer)) {
			return insertMapDataRecord(record);
		} else {
			return updateMapDataRecord(record);
		}
	}

	int insertMapDataRecord(TYMapDataFeatureRecord record) {
		return db.insertData(mapdataTable, IPMysqlMapDataParams.DataMapFromMapDataFeatureRecord(record));
	}

	int updateMapDataRecord(TYMapDataFeatureRecord record) {
		return db.updateData(mapdataTable, IPMysqlMapDataParams.DataMapFromMapDataFeatureRecord(record),
				mapdataTable.getField(IPMysqlMapDataParams.FIELD_MAP_DATA_3_GEO_ID), record.getGeoID());
	}

	public List<TYMapDataFeatureRecord> getAllMapDataRecords() {
		return IPMysqlMapDataParams.MapDataListFromRecords(db.readData(mapdataTable));
	}

	public List<TYMapDataFeatureRecord> getAllMapDataRecords(int floor) {
		return IPMysqlMapDataParams.MapDataListFromRecords(db.readData(mapdataTable,
				mapdataTable.getField(IPMysqlMapDataParams.FIELD_MAP_DATA_10_FLOOR_NUMBER), floor));
	}

	public List<TYMapDataFeatureRecord> getAllMapDataRecords(String mapID) {
		return IPMysqlMapDataParams.MapDataListFromRecords(
				db.readData(mapdataTable, mapdataTable.getField(IPMysqlMapDataParams.FIELD_MAP_DATA_5_FLOOR_ID), mapID));
	}

	public List<TYMapDataFeatureRecord> getAllMapDataRecords(int floor, int layer) {
		Map<IPSqlField, Object> clause = new HashMap<IPSqlField, Object>();
		clause.put(mapdataTable.getField(IPMysqlMapDataParams.FIELD_MAP_DATA_10_FLOOR_NUMBER), floor);
		clause.put(mapdataTable.getField(IPMysqlMapDataParams.FIELD_MAP_DATA_16_LAYER), layer);
		return IPMysqlMapDataParams.MapDataListFromRecords(db.readData(mapdataTable, clause));
	}

	public TYMapDataFeatureRecord getMapDataRecord(String geoID) {
		List<TYMapDataFeatureRecord> records = IPMysqlMapDataParams.MapDataListFromRecords(
				db.readData(mapdataTable, mapdataTable.getField(IPMysqlMapDataParams.FIELD_MAP_DATA_3_GEO_ID), geoID));
		if (records != null && records.size() > 0) {
			return records.get(0);
		}
		return null;
	}

	// private boolean isExistColumn(ResultSet rs, String columnName) {
	// try {
	// if (rs.findColumn(columnName) > 0) {
	// return true;
	// }
	// } catch (SQLException e) {
	// return false;
	// }
	// return false;
	// }

	public boolean existMapData(String geoID, int layer) {
		Map<IPSqlField, Object> clause = new HashMap<IPSqlField, Object>();
		clause.put(mapdataTable.getField(IPMysqlMapDataParams.FIELD_MAP_DATA_3_GEO_ID), geoID);
		clause.put(mapdataTable.getField(IPMysqlMapDataParams.FIELD_MAP_DATA_16_LAYER), layer);
		return db.existRecord(mapdataTable, clause);
	}

	boolean existTable() {
		return db.existTable(mapdataTable);
	}
}

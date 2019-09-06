package cn.platalk.lab.universaldata.mysql;

import java.sql.Connection;
import java.util.List;

import cn.platalk.lab.universaldata.entity.IPUniversalData;
import cn.platalk.mysql.map.TYDatabaseManager;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlUniversalDataDBAdapter {
	IPMysqlDB db;
	IPSqlTable udTable;

	public IPMysqlUniversalDataDBAdapter(String tableName) {
		db = new IPMysqlDB(TYDatabaseManager.GetLabDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		udTable = IPMysqlUniversalDataParams.CreateTable(tableName);
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
		db.createTable(udTable);
	}

	public void deleteUniversalData(String dataID) {
		db.deleteRecord(udTable, udTable.getField(IPMysqlUniversalDataParams.FIELD_UNIVERSAL_DATA_1_ID), dataID);
	}

	public int insertOrUpdateUniversalData(IPUniversalData ud) {
		if (!existUniversalData(ud.getDataID())) {
			return insertUniversalData(ud);
		} else {
			return updateUniversalData(ud);
		}
	}

	public int insertOrUpdateUniversalData(List<IPUniversalData> uds) {
		int count = 0;
		for (IPUniversalData ud : uds) {
			count += insertOrUpdateUniversalData(ud);
		}
		return count;
	}

	int insertUniversalData(IPUniversalData ud) {
		return db.insertData(udTable, IPMysqlUniversalDataParams.DataMapFromUniversalData(ud));
	}

	int updateUniversalData(IPUniversalData ud) {
		return db.updateData(udTable, IPMysqlUniversalDataParams.DataMapFromUniversalData(ud),
				udTable.getField(IPMysqlUniversalDataParams.FIELD_UNIVERSAL_DATA_1_ID), ud.getDataID());
	}

	public List<IPUniversalData> getAllUniversalData() {
		return IPMysqlUniversalDataParams.UniversalDataListFromRecords(db.readData(udTable));
	}

	public IPUniversalData getUniversalData(String dataID) {
		List<IPUniversalData> udList = IPMysqlUniversalDataParams.UniversalDataListFromRecords(
				db.readData(udTable, udTable.getField(IPMysqlUniversalDataParams.FIELD_UNIVERSAL_DATA_1_ID), dataID));
		if (udList != null && udList.size() > 0) {
			return udList.get(0);
		}
		return null;
	}

	public boolean existUniversalData(String dataID) {
		return db.existRecord(udTable, udTable.getField(IPMysqlUniversalDataParams.FIELD_UNIVERSAL_DATA_1_ID), dataID);
	}

	boolean existTable() {
		return db.existTable(udTable);
	}
}

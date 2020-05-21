package cn.platalk.lab.universaldata.sqlite;

import java.util.List;

import cn.platalk.lab.universaldata.entity.IPUniversalData;
import cn.platalk.lab.universaldata.mysql.IPMysqlUniversalDataParams;
import cn.platalk.sqlhelper.sql.IPSqlTable;
import cn.platalk.sqlhelper.sqlite.IPSqliteDB;

public class IPSqliteUniversalDataDBAdapter {
	final IPSqliteDB db;
	final IPSqlTable udTable;

	public IPSqliteUniversalDataDBAdapter(String path, String tableName) {
		db = new IPSqliteDB(path);
		udTable = new IPSqlTable(tableName, IPSqliteUniversalDataParams.GetUniversalDataFieldList(), "_id");
	}

	public boolean open() {
		return db.open();
	}

	public void close() {
		db.close();
	}

	public void createUniversalTableIfNotExist() {
		db.createSqliteTable(udTable);
	}

	public void dropUniversalTable() {
		db.dropTable(udTable);
	}

	public void eraseUniversalTable() {
		db.eraseTable(udTable);
	}

	public void deleteUniversalData(String dataID) {
		db.deleteRecord(udTable, udTable.getField(IPSqliteUniversalDataParams.FIELD_UNIVERSAL_DATA_1_ID), dataID);
	}

	public int insertOrUpdateUniversalData(List<IPUniversalData> uds) {
		int count = 0;
		for (IPUniversalData ud : uds) {
			count += insertOrUpdateUniversalData(ud);
		}
		return count;
	}

	public int insertOrUpdateUniversalData(IPUniversalData ud) {
		if (!existUniversalData(ud.getDataID())) {
			return insertUniversalData(ud);
		} else {
			return updateUniversalData(ud);
		}
	}

	int insertUniversalData(IPUniversalData ud) {
		return db.insertData(udTable, IPSqliteUniversalDataParams.DataMapFromUniversalData(ud));
	}

	int updateUniversalData(IPUniversalData ud) {
		return db.updateData(udTable, IPMysqlUniversalDataParams.DataMapFromUniversalData(ud),
				udTable.getField(IPSqliteUniversalDataParams.FIELD_UNIVERSAL_DATA_1_ID), ud.getDataID());
	}

	public List<IPUniversalData> getAllUniversalData() {
		return IPSqliteUniversalDataParams.UniversalDataListFromRecords(db.readData(udTable));
	}

	public IPUniversalData getUniversalData(String dataID) {
		List<IPUniversalData> udList = IPSqliteUniversalDataParams.UniversalDataListFromRecords(
				db.readData(udTable, udTable.getField(IPSqliteUniversalDataParams.FIELD_UNIVERSAL_DATA_1_ID), dataID));
		if (udList.size() > 0) {
			return udList.get(0);
		}
		return null;
	}

	public boolean existUniversalData(String dataID) {
		return db.existRecord(udTable, udTable.getField(IPSqliteUniversalDataParams.FIELD_UNIVERSAL_DATA_1_ID), dataID);
	}

	boolean existTable() {
		return db.existTable(udTable);
	}
}

package cn.platalk.lab.blesample.mysql;

import java.sql.Connection;
import java.util.List;

import cn.platalk.lab.blesample.entity.WTBleSample;
import cn.platalk.mysql.map.TYDatabaseManager;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class WTMysqlBleSampleDBAdapter {
	final IPMysqlDB db;
	final IPSqlTable sampleTable;

	public WTMysqlBleSampleDBAdapter() {
		db = new IPMysqlDB(TYDatabaseManager.GetLabDBUrl(), TYDatabaseManager.GetUserName(),
				TYDatabaseManager.GetPassword());
		sampleTable = WTMysqlBleSampleParams.CreateTable();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
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
		db.createTable(sampleTable);
	}

	public void deleteSample(String sampleID) {
		db.deleteRecord(sampleTable, sampleTable.getField(WTMysqlBleSampleParams.FIELD_BLE_SAMPLE_1_ID), sampleID);
	}

	public void deleteSampleByBuildingID(String buildingID) {
		db.deleteRecord(sampleTable, sampleTable.getField(WTMysqlBleSampleParams.FIELD_BLE_SAMPLE_2_BUILDING_ID),
				buildingID);
	}

	public void eraseSampleTable() {
		db.eraseTable(sampleTable);
	}

	public int insertOrUpdateSample(WTBleSample sample) {
		if (!existSample(sample.getSampleID())) {
			return insertSample(sample);
		} else {
			return updateSample(sample);
		}
	}

	public int insertOrUpdateSample(List<WTBleSample> sampleList) {
		int count = 0;
		for (WTBleSample sample : sampleList) {
			count += insertOrUpdateSample(sample);
		}
		return count;
	}

	int insertSample(WTBleSample sample) {
		return db.insertData(sampleTable, WTMysqlBleSampleParams.DataMapFromBleSample(sample));
	}

	int updateSample(WTBleSample sample) {
		return db.updateData(sampleTable, WTMysqlBleSampleParams.DataMapFromBleSample(sample),
				sampleTable.getField(WTMysqlBleSampleParams.FIELD_BLE_SAMPLE_1_ID), sample.getSampleID());
	}

	public List<WTBleSample> getAllSample() {
		return WTMysqlBleSampleParams.BleSampleListFromRecords(db.readData(sampleTable));
	}

	public List<WTBleSample> getAllSample(String buildingID) {
		return WTMysqlBleSampleParams.BleSampleListFromRecords(db.readData(sampleTable,
				sampleTable.getField(WTMysqlBleSampleParams.FIELD_BLE_SAMPLE_2_BUILDING_ID), buildingID));
	}

	public WTBleSample getSample(String sampleID) {
		List<WTBleSample> sampleList = WTMysqlBleSampleParams.BleSampleListFromRecords(
				db.readData(sampleTable, sampleTable.getField(WTMysqlBleSampleParams.FIELD_BLE_SAMPLE_1_ID), sampleID));
		if (sampleList.size() > 0) {
			return sampleList.get(0);
		}
		return null;
	}

	public boolean existSample(String sampleID) {
		return db.existRecord(sampleTable, sampleTable.getField(WTMysqlBleSampleParams.FIELD_BLE_SAMPLE_1_ID),
				sampleID);
	}

	boolean existTable() {
		return db.existTable(sampleTable);
	}
}

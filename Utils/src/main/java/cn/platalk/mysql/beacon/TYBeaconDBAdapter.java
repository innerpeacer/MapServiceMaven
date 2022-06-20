package cn.platalk.mysql.beacon;

import cn.platalk.map.entity.base.beacon.TYIBeacon;
import cn.platalk.map.entity.base.beacon.TYILocatingBeacon;
import cn.platalk.map.entity.base.impl.beacon.TYLocatingBeacon;
import cn.platalk.mysql.map.TYDatabaseManager;
import cn.platalk.sqlhelper.mysql.IPMysqlDB;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TYBeaconDBAdapter {
	final String buildingID;
	final IPMysqlDB db;
	final IPSqlTable beaconTable;

	public TYBeaconDBAdapter(String buildingID) {
		this.buildingID = buildingID;
		db = new IPMysqlDB(TYDatabaseManager.GetBeaconDBUrl(), TYDatabaseManager.GetUserName(), TYDatabaseManager.GetPassword());
		beaconTable = IPMysqlBeaconParams.CreateTable(buildingID);
	}

	public void connectDB() {
		db.connectDB();
	}

	public void disconnectDB() {
		db.disconnectDB();
	}

	public void createTableIfNotExist() {
		db.createTable(beaconTable);
	}

	public void deleteBeacon(String uuid, int major, int minor) {
		Map<IPSqlField, Object> clause = new HashMap<>();
		clause.put(beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_2_UUID), uuid);
		clause.put(beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_3_MAJOR), major);
		clause.put(beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_4_MINOR), minor);
		db.deleteRecord(beaconTable, clause);
	}

	public void deleteBeacons(List<TYIBeacon> beaconList) {
		for (TYIBeacon beacon : beaconList) {
			deleteBeacon(beacon.getUUID(), beacon.getMajor(), beacon.getMinor());
		}
	}

	public void eraseBeaconTable() {
		db.eraseTable(beaconTable);
	}

	public int insertOrUpdateBeacon(TYILocatingBeacon beacon) {
		if (!existBeacon(beacon.getUUID(), beacon.getMajor(), beacon.getMinor())) {
			return insertBeacon(beacon);
		} else {
			return updateBeacon(beacon);
		}
	}

	public int insertOrUpdateBeacons(List<TYILocatingBeacon> beacons) {
		int count = 0;
		for (TYILocatingBeacon beacon : beacons) {
			count += insertOrUpdateBeacon(beacon);
		}
		return count;
	}

	int updateBeacon(TYILocatingBeacon beacon) {
		Map<IPSqlField, Object> clause = new HashMap<>();
		clause.put(beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_2_UUID), beacon.getUUID());
		clause.put(beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_3_MAJOR), beacon.getMajor());
		clause.put(beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_4_MINOR), beacon.getMinor());
		return db.updateData(beaconTable, IPMysqlBeaconParams.DataMapFromBeacon(beacon), clause);
	}

	int insertBeacon(TYILocatingBeacon beacon) {
		return db.insertData(beaconTable, IPMysqlBeaconParams.DataMapFromBeacon(beacon));
	}

	public List<TYLocatingBeacon> getAllBeacons() {
		return IPMysqlBeaconParams.BeaconListFromRecords(db.readData(beaconTable));
	}

	public List<TYLocatingBeacon> getAllBeaconsOnFloor(int floor) {
		return IPMysqlBeaconParams.BeaconListFromRecords(db.readData(beaconTable, beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_5_FLOOR), floor));
	}

	public TYLocatingBeacon getBeacon(String uuid, int major, int minor) {
		Map<IPSqlField, Object> clause = new HashMap<>();
		clause.put(beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_2_UUID), uuid);
		clause.put(beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_3_MAJOR), major);
		clause.put(beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_4_MINOR), minor);
		List<TYLocatingBeacon> beaconList = IPMysqlBeaconParams.BeaconListFromRecords(db.readData(beaconTable, clause));
		if (beaconList.size() > 0) {
			return beaconList.get(0);
		}
		return null;
	}

	public boolean existBeacon(String uuid, int major, int minor) {
		Map<IPSqlField, Object> clause = new HashMap<>();
		clause.put(beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_2_UUID), uuid);
		clause.put(beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_3_MAJOR), major);
		clause.put(beaconTable.getField(IPMysqlBeaconParams.FIELD_BEACON_4_MINOR), minor);
		return db.existRecord(beaconTable, clause);
	}

	boolean existTable() {
		return db.existTable(beaconTable);
	}
}

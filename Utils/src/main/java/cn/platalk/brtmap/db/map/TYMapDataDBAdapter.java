package cn.platalk.brtmap.db.map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;

public class TYMapDataDBAdapter {
	static final String TABLE_MAP_DATA = "MAPDATA_%s";

	static final String FIELD_MAP_DATA_0_PRIMARY_KEY = "_id";
	static final String FIELD_MAP_DATA_1_OBJECT_ID = "OBJECT_ID";
	static final String FIELD_MAP_DATA_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_MAP_DATA_3_GEO_ID = "GEO_ID";
	static final String FIELD_MAP_DATA_4_POI_ID = "POI_ID";
	static final String FIELD_MAP_DATA_5_FLOOR_ID = "FLOOR_ID";
	static final String FIELD_MAP_DATA_6_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_MAP_DATA_7_CATEGORY_ID = "CATEGORY_ID";
	static final String FIELD_MAP_DATA_8_NAME = "NAME";
	static final String FIELD_MAP_DATA_9_SYMBOL_ID = "SYMBOL_ID";
	static final String FIELD_MAP_DATA_10_FLOOR_NUMBER = "FLOOR_NUMBER";
	static final String FIELD_MAP_DATA_11_FLOOR_NAME = "FLOOR_NAME";
	static final String FIELD_MAP_DATA_12_SHAPE_LENGTH = "SHAPE_LENGTH";
	static final String FIELD_MAP_DATA_13_SHAPE_AREA = "SHAPE_AREA";
	static final String FIELD_MAP_DATA_14_LABEL_X = "LABEL_X";
	static final String FIELD_MAP_DATA_15_LABEL_Y = "LABEL_Y";
	static final String FIELD_MAP_DATA_16_LAYER = "LAYER";
	static final String FIELD_MAP_DATA_17_LEVEL_MAX = "LEVEL_MAX";
	static final String FIELD_MAP_DATA_18_LEVEL_MIN = "LEVEL_MIN";

	static final String FIELD_MAP_DATA_19_EXTRUSION = "EXTRUSION";
	static final String FIELD_MAP_DATA_20_EXTRUSION_HEIGHT = "EXTRUSION_HEIGHT";
	static final String FIELD_MAP_DATA_21_EXTRUSION_BASE = "EXTRUSION_BASE";

	private Connection connection;
	private String buildingID;
	private String TABLE;

	public TYMapDataDBAdapter(String building) {
		buildingID = building;
		TABLE = String.format(TABLE_MAP_DATA, buildingID);
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
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE + " (_id INT NOT NULL AUTO_INCREMENT, "
				+ "OBJECT_ID VARCHAR(45) NOT NULL,"
				// + "GEOMETRY BLOB NOT NULL, " + "GEO_ID VARCHAR(45) NOT NULL,"
				+ "GEOMETRY	MediumBlob NOT NULL, " + "GEO_ID VARCHAR(45) NOT NULL," + "POI_ID VARCHAR(45) NOT NULL,"
				+ "FLOOR_ID VARCHAR(45) NOT NULL," + "BUILDING_ID VARCHAR(45) NOT NULL,"
				+ "CATEGORY_ID VARCHAR(45) NOT NULL," + "NAME VARCHAR(45)," + "SYMBOL_ID INT NOT NULL,"
				+ "FLOOR_NUMBER INT NOT NULL," + "FLOOR_NAME VARCHAR(45) NOT NULL," + "SHAPE_LENGTH DOUBLE NOT NULL,"
				+ "SHAPE_AREA DOUBLE NOT NULL," + "LABEL_X DOUBLE NOT NULL," + "LABEL_Y DOUBLE NOT NULL,"
				+ "LAYER INT NOT NULL," + "LEVEL_MAX INT NOT NULL, " + "LEVEL_MIN INT NOT NULL,"
				+ "EXTRUSION INT DEFAULT 0, " + "EXTRUSION_HEIGHT DOUBLE DEFAULT 0, "
				+ "EXTRUSION_BASE DOUBLE DEFAULT 0, " + " PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection connectDB() {
		try {
			connection = DriverManager.getConnection(TYDatabaseManager.GetMapDBUrl(), TYDatabaseManager.GetUserName(),
					TYDatabaseManager.GetPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void disconnectDB() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection = null;
	}

	public void eraseMapDataTable() {
		String sql = String.format("delete from %s", TABLE);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteMapDataByGeoID(String geoID) {
		String sql = String.format("delete from %s where %s='%s'", TABLE, FIELD_MAP_DATA_3_GEO_ID, geoID);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteMapDataByPoiID(String poiID) {
		String sql = String.format("delete from %s where %s='%s'", TABLE, FIELD_MAP_DATA_4_POI_ID, poiID);
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertMapDataRecordsInBatch(List<TYMapDataFeatureRecord> recordList) {
		int result = 0;
		String sql = String.format(
				"insert into %s (%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				TABLE, FIELD_MAP_DATA_1_OBJECT_ID, FIELD_MAP_DATA_2_GEOMETRY, FIELD_MAP_DATA_3_GEO_ID,
				FIELD_MAP_DATA_4_POI_ID, FIELD_MAP_DATA_5_FLOOR_ID, FIELD_MAP_DATA_6_BUILDING_ID,
				FIELD_MAP_DATA_7_CATEGORY_ID, FIELD_MAP_DATA_8_NAME, FIELD_MAP_DATA_9_SYMBOL_ID,
				FIELD_MAP_DATA_10_FLOOR_NUMBER, FIELD_MAP_DATA_11_FLOOR_NAME, FIELD_MAP_DATA_12_SHAPE_LENGTH,
				FIELD_MAP_DATA_13_SHAPE_AREA, FIELD_MAP_DATA_14_LABEL_X, FIELD_MAP_DATA_15_LABEL_Y,
				FIELD_MAP_DATA_16_LAYER, FIELD_MAP_DATA_17_LEVEL_MAX, FIELD_MAP_DATA_18_LEVEL_MIN,
				FIELD_MAP_DATA_19_EXTRUSION, FIELD_MAP_DATA_20_EXTRUSION_HEIGHT, FIELD_MAP_DATA_21_EXTRUSION_BASE);

		try {
			connection.setAutoCommit(false);
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			for (TYMapDataFeatureRecord record : recordList) {
				stmt.setString(1, record.objectID);
				stmt.setBytes(2, record.geometry);
				stmt.setString(3, record.geoID);
				stmt.setString(4, record.poiID);
				stmt.setString(5, record.floorID);
				stmt.setString(6, record.buildingID);
				stmt.setString(7, record.categoryID);
				if (record.name != null) {
					stmt.setString(8, record.name);
				} else {
					stmt.setNull(8, Types.VARCHAR);
				}
				stmt.setInt(9, record.symbolID);
				stmt.setInt(10, record.floorNumber);
				stmt.setString(11, record.floorName);
				stmt.setDouble(12, record.shapeLength);
				stmt.setDouble(13, record.shapeArea);
				stmt.setDouble(14, record.labelX);
				stmt.setDouble(15, record.labelY);
				stmt.setInt(16, record.layer);
				stmt.setInt(17, record.levelMax);
				stmt.setInt(18, record.levelMin);
				stmt.setBoolean(19, record.extrusion);
				stmt.setDouble(20, record.extrusionHeight);
				stmt.setDouble(21, record.extrusionBase);
				stmt.addBatch();
			}
			int rows[] = stmt.executeBatch();
			result = rows.length;
			connection.commit();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
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
		int result = 0;
		String sql = String.format(
				"insert into %s (%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				TABLE, FIELD_MAP_DATA_1_OBJECT_ID, FIELD_MAP_DATA_2_GEOMETRY, FIELD_MAP_DATA_3_GEO_ID,
				FIELD_MAP_DATA_4_POI_ID, FIELD_MAP_DATA_5_FLOOR_ID, FIELD_MAP_DATA_6_BUILDING_ID,
				FIELD_MAP_DATA_7_CATEGORY_ID, FIELD_MAP_DATA_8_NAME, FIELD_MAP_DATA_9_SYMBOL_ID,
				FIELD_MAP_DATA_10_FLOOR_NUMBER, FIELD_MAP_DATA_11_FLOOR_NAME, FIELD_MAP_DATA_12_SHAPE_LENGTH,
				FIELD_MAP_DATA_13_SHAPE_AREA, FIELD_MAP_DATA_14_LABEL_X, FIELD_MAP_DATA_15_LABEL_Y,
				FIELD_MAP_DATA_16_LAYER, FIELD_MAP_DATA_17_LEVEL_MAX, FIELD_MAP_DATA_18_LEVEL_MIN,
				FIELD_MAP_DATA_19_EXTRUSION, FIELD_MAP_DATA_20_EXTRUSION_HEIGHT, FIELD_MAP_DATA_21_EXTRUSION_BASE);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			stmt.setString(1, record.objectID);
			stmt.setBytes(2, record.geometry);
			stmt.setString(3, record.geoID);
			stmt.setString(4, record.poiID);
			stmt.setString(5, record.floorID);
			stmt.setString(6, record.buildingID);
			stmt.setString(7, record.categoryID);
			if (record.name != null) {
				stmt.setString(8, record.name);
			} else {
				stmt.setNull(8, Types.VARCHAR);
			}
			stmt.setInt(9, record.symbolID);
			stmt.setInt(10, record.floorNumber);
			stmt.setString(11, record.floorName);
			stmt.setDouble(12, record.shapeLength);
			stmt.setDouble(13, record.shapeArea);
			stmt.setDouble(14, record.labelX);
			stmt.setDouble(15, record.labelY);
			stmt.setInt(16, record.layer);
			stmt.setInt(17, record.levelMax);
			stmt.setInt(18, record.levelMin);
			stmt.setBoolean(19, record.extrusion);
			stmt.setDouble(20, record.extrusionHeight);
			stmt.setDouble(21, record.extrusionBase);
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	int updateMapDataRecord(TYMapDataFeatureRecord record) {
		int result = 0;
		StringBuffer buffer = new StringBuffer(String.format(
				"update %s set  %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?",
				TABLE, FIELD_MAP_DATA_1_OBJECT_ID, FIELD_MAP_DATA_2_GEOMETRY, FIELD_MAP_DATA_4_POI_ID,
				FIELD_MAP_DATA_5_FLOOR_ID, FIELD_MAP_DATA_6_BUILDING_ID, FIELD_MAP_DATA_7_CATEGORY_ID,
				FIELD_MAP_DATA_8_NAME, FIELD_MAP_DATA_9_SYMBOL_ID, FIELD_MAP_DATA_10_FLOOR_NUMBER,
				FIELD_MAP_DATA_11_FLOOR_NAME, FIELD_MAP_DATA_12_SHAPE_LENGTH, FIELD_MAP_DATA_13_SHAPE_AREA,
				FIELD_MAP_DATA_14_LABEL_X, FIELD_MAP_DATA_15_LABEL_Y, FIELD_MAP_DATA_16_LAYER,
				FIELD_MAP_DATA_17_LEVEL_MAX, FIELD_MAP_DATA_18_LEVEL_MIN, FIELD_MAP_DATA_19_EXTRUSION,
				FIELD_MAP_DATA_20_EXTRUSION_HEIGHT, FIELD_MAP_DATA_21_EXTRUSION_BASE));
		buffer.append(String.format(" where %s='%s'", FIELD_MAP_DATA_3_GEO_ID, record.geoID));
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(buffer.toString());
			stmt.setString(1, record.objectID);
			stmt.setBytes(2, record.geometry);
			stmt.setString(3, record.poiID);
			stmt.setString(4, record.floorID);
			stmt.setString(5, record.buildingID);
			stmt.setString(6, record.categoryID);
			if (record.name != null) {
				stmt.setString(7, record.name);
			} else {
				stmt.setNull(7, Types.VARCHAR);
			}
			stmt.setInt(8, record.symbolID);
			stmt.setInt(9, record.floorNumber);
			stmt.setString(10, record.floorName);
			stmt.setDouble(11, record.shapeLength);
			stmt.setDouble(12, record.shapeArea);
			stmt.setDouble(13, record.labelX);
			stmt.setDouble(14, record.labelY);
			stmt.setInt(15, record.layer);
			stmt.setInt(16, record.levelMax);
			stmt.setInt(17, record.levelMin);
			stmt.setBoolean(18, record.extrusion);
			stmt.setDouble(19, record.extrusionHeight);
			stmt.setDouble(20, record.extrusionBase);
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<TYMapDataFeatureRecord> getAllMapDataRecords() {
		String sql = String.format("select * from %s", TABLE);
		return queryMapDataRecords(sql);
	}

	public List<TYMapDataFeatureRecord> getAllMapDataRecords(int floor) {
		String sql = String.format("select * from %s where %s=%d", TABLE, FIELD_MAP_DATA_10_FLOOR_NUMBER, floor);
		return queryMapDataRecords(sql);
	}

	public List<TYMapDataFeatureRecord> getAllMapDataRecords(String mapID) {
		String sql = String.format("select * from %s where %s='%s'", TABLE, FIELD_MAP_DATA_5_FLOOR_ID, mapID);
		return queryMapDataRecords(sql);
	}

	public List<TYMapDataFeatureRecord> getAllMapDataRecords(int floor, int layer) {
		String sql = String.format("select * from %s where %s=%d and %s=%d", TABLE, FIELD_MAP_DATA_10_FLOOR_NUMBER,
				floor, FIELD_MAP_DATA_16_LAYER, layer);
		return queryMapDataRecords(sql);
	}

	public TYMapDataFeatureRecord getMapDataRecord(String geoID) {
		String sql = String.format("select * from %s where %s='%s'", TABLE, FIELD_MAP_DATA_3_GEO_ID, geoID);
		return queryMapDataRecord(sql);
	}

	private List<TYMapDataFeatureRecord> queryMapDataRecords(String sql) {
		System.out.println(sql);
		List<TYMapDataFeatureRecord> resultMapDataRecordList = new ArrayList<TYMapDataFeatureRecord>();
		if (existTable(TABLE)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					TYMapDataFeatureRecord record = new TYMapDataFeatureRecord();
					record.objectID = rs.getString(FIELD_MAP_DATA_1_OBJECT_ID);
					record.geometry = rs.getBytes(FIELD_MAP_DATA_2_GEOMETRY);
					record.geoID = rs.getString(FIELD_MAP_DATA_3_GEO_ID);
					record.poiID = rs.getString(FIELD_MAP_DATA_4_POI_ID);
					record.floorID = rs.getString(FIELD_MAP_DATA_5_FLOOR_ID);
					record.buildingID = rs.getString(FIELD_MAP_DATA_6_BUILDING_ID);
					record.categoryID = rs.getString(FIELD_MAP_DATA_7_CATEGORY_ID);
					record.name = rs.getString(FIELD_MAP_DATA_8_NAME);
					record.symbolID = rs.getInt(FIELD_MAP_DATA_9_SYMBOL_ID);
					record.floorNumber = rs.getInt(FIELD_MAP_DATA_10_FLOOR_NUMBER);
					record.floorName = rs.getString(FIELD_MAP_DATA_11_FLOOR_NAME);
					record.shapeLength = rs.getDouble(FIELD_MAP_DATA_12_SHAPE_LENGTH);
					record.shapeArea = rs.getDouble(FIELD_MAP_DATA_13_SHAPE_AREA);
					record.labelX = rs.getDouble(FIELD_MAP_DATA_14_LABEL_X);
					record.labelY = rs.getDouble(FIELD_MAP_DATA_15_LABEL_Y);
					record.layer = rs.getInt(FIELD_MAP_DATA_16_LAYER);
					record.levelMax = rs.getInt(FIELD_MAP_DATA_17_LEVEL_MAX);
					record.levelMin = rs.getInt(FIELD_MAP_DATA_18_LEVEL_MIN);

					if (isExistColumn(rs, FIELD_MAP_DATA_19_EXTRUSION)) {
						record.extrusion = rs.getBoolean(FIELD_MAP_DATA_19_EXTRUSION);
					}

					if (isExistColumn(rs, FIELD_MAP_DATA_20_EXTRUSION_HEIGHT)) {
						record.extrusionHeight = rs.getDouble(FIELD_MAP_DATA_20_EXTRUSION_HEIGHT);
					}
					if (isExistColumn(rs, FIELD_MAP_DATA_21_EXTRUSION_BASE)) {
						record.extrusionBase = rs.getDouble(FIELD_MAP_DATA_21_EXTRUSION_BASE);
					}
					resultMapDataRecordList.add(record);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultMapDataRecordList;
	}

	private boolean isExistColumn(ResultSet rs, String columnName) {
		try {
			if (rs.findColumn(columnName) > 0) {
				return true;
			}
		} catch (SQLException e) {
			return false;
		}
		return false;
	}

	private TYMapDataFeatureRecord queryMapDataRecord(String sql) {
		TYMapDataFeatureRecord record = null;
		if (existTable(TABLE)) {
			ResultSet rs = null;
			try {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					record = new TYMapDataFeatureRecord();
					record.objectID = rs.getString(FIELD_MAP_DATA_1_OBJECT_ID);
					record.geometry = rs.getBytes(FIELD_MAP_DATA_2_GEOMETRY);
					record.geoID = rs.getString(FIELD_MAP_DATA_3_GEO_ID);
					record.poiID = rs.getString(FIELD_MAP_DATA_4_POI_ID);
					record.floorID = rs.getString(FIELD_MAP_DATA_5_FLOOR_ID);
					record.buildingID = rs.getString(FIELD_MAP_DATA_6_BUILDING_ID);
					record.categoryID = rs.getString(FIELD_MAP_DATA_7_CATEGORY_ID);
					record.name = rs.getString(FIELD_MAP_DATA_8_NAME);
					record.symbolID = rs.getInt(FIELD_MAP_DATA_9_SYMBOL_ID);
					record.floorNumber = rs.getInt(FIELD_MAP_DATA_10_FLOOR_NUMBER);
					record.floorName = rs.getString(FIELD_MAP_DATA_11_FLOOR_NAME);
					record.shapeLength = rs.getDouble(FIELD_MAP_DATA_12_SHAPE_LENGTH);
					record.shapeArea = rs.getDouble(FIELD_MAP_DATA_13_SHAPE_AREA);
					record.labelX = rs.getDouble(FIELD_MAP_DATA_14_LABEL_X);
					record.labelY = rs.getDouble(FIELD_MAP_DATA_15_LABEL_Y);
					record.layer = rs.getInt(FIELD_MAP_DATA_16_LAYER);
					record.levelMax = rs.getInt(FIELD_MAP_DATA_17_LEVEL_MAX);
					record.levelMin = rs.getInt(FIELD_MAP_DATA_18_LEVEL_MIN);

					if (isExistColumn(rs, FIELD_MAP_DATA_19_EXTRUSION)) {
						record.extrusion = rs.getBoolean(FIELD_MAP_DATA_19_EXTRUSION);
					}

					if (isExistColumn(rs, FIELD_MAP_DATA_20_EXTRUSION_HEIGHT)) {
						record.extrusionHeight = rs.getDouble(FIELD_MAP_DATA_20_EXTRUSION_HEIGHT);
					}
					if (isExistColumn(rs, FIELD_MAP_DATA_21_EXTRUSION_BASE)) {
						record.extrusionBase = rs.getDouble(FIELD_MAP_DATA_21_EXTRUSION_BASE);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return record;
	}

	public boolean existMapData(String geoID, int layer) {
		int result = 0;
		String sql = String.format("select count(*)  from %s where %s='%s' and %s=%d", TABLE, FIELD_MAP_DATA_3_GEO_ID,
				geoID, FIELD_MAP_DATA_16_LAYER, layer);
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
		}
		return (result != 0);
	}

	boolean existTable(String table) {
		try {
			ResultSet rs = connection.getMetaData().getTables(null, null, table, null);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}

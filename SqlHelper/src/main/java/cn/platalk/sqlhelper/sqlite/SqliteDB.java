package cn.platalk.sqlhelper.sqlite;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.platalk.sqlhelper.sqlite.builder.IPSqliteBuilder;
import cn.platalk.sqlhelper.sqlite.helper.IPSqliteHelper;

public class SqliteDB {
	private Connection connection;
	private String dbPath;
	private JDBCVersion jdbcVersion = JDBCVersion.JAVA;

	public SqliteDB(String path) {
		this(path, JDBCVersion.JAVA);
	}

	public SqliteDB(String path, JDBCVersion versionn) {
		jdbcVersion = versionn;
		try {
			Class.forName(IPSqliteConfig.getDriverName(jdbcVersion));
			this.dbPath = path;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean open() {
		File dbFile = new File(dbPath);
		if (!dbFile.exists()) {
			dbFile.getParentFile().mkdirs();
			try {
				dbFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			connection = DriverManager.getConnection(IPSqliteConfig.getDBPrefix(jdbcVersion) + dbPath);
		} catch (SQLException e) {
			e.printStackTrace();
			connection = null;
		}
		return (connection != null);
	}

	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		connection = null;
	}

	public void createTable(SqliteTable table) {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(IPSqliteBuilder.tableCreateSql(table));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean existTable(SqliteTable table) {
		try {
			ResultSet rs = connection.getMetaData().getTables(null, null, table.getTableName(), null);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void dropTable(SqliteTable table) {
		String sql = String.format("drop table %s", table.getTableName());
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eraseTable(SqliteTable table) {
		String sql = String.format("delete from %s", table.getTableName());
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertData(SqliteTable table, Map<String, Object> data) {
		int result = 0;
		String sql = IPSqliteBuilder.insertSql(table);
		// System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			List<SqliteField> fields = table.getFields();
			for (int i = 0; i < fields.size(); ++i) {
				SqliteField field = fields.get(i);
				String fieldName = field.fieldName;
				IPSqliteHelper.setStmtObject(stmt, i + 1, field, data.get(fieldName));
			}
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int updateData(SqliteTable table, Map<String, Object> data, Map<String, Object> clause) {
		int result = 0;
		List<SqliteField> updateFields = new ArrayList<SqliteField>();
		for (String fieldName : data.keySet()) {
			updateFields.add(table.getField(fieldName));
		}

		String sql = IPSqliteBuilder.updateSql(table, updateFields, clause);
		// System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			for (int i = 0; i < updateFields.size(); ++i) {
				SqliteField field = updateFields.get(i);
				String fieldName = field.fieldName;
				IPSqliteHelper.setStmtObject(stmt, i + 1, field, data.get(fieldName));
			}
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<SqliteRecord> readData(SqliteTable table, Map<String, Object> clause) {
		List<SqliteRecord> records = new ArrayList<SqliteRecord>();
		String sql = IPSqliteBuilder.selectSql(table, clause);
		// System.out.println(sql);
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<SqliteEntity> entities = new ArrayList<SqliteEntity>();
				List<SqliteField> fields = table.getFields();
				for (SqliteField f : fields) {
					SqliteEntity entity = IPSqliteHelper.getObject(rs, f);
					entities.add(entity);
				}
				SqliteRecord record = new SqliteRecord(entities);
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

	public List<SqliteRecord> readData(SqliteTable table) {
		List<SqliteRecord> records = new ArrayList<SqliteRecord>();
		String sql = String.format("select * from %s", table.getTableName());
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<SqliteEntity> entities = new ArrayList<SqliteEntity>();
				List<SqliteField> fields = table.getFields();
				for (SqliteField f : fields) {
					SqliteEntity entity = IPSqliteHelper.getObject(rs, f);
					entities.add(entity);
				}
				SqliteRecord record = new SqliteRecord(entities);
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

}

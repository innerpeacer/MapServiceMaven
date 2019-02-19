package cn.platalk.sqlhelper.sqlite;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import cn.platalk.sqlhelper.sql.SqlDB;

public class SqliteDB extends SqlDB {
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
}

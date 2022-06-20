package cn.platalk.sqlhelper.mysql;

import cn.platalk.sqlhelper.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class IPMysqlDB extends IPSqlDB {
	private final String url;
	private final String name;
	private final String password;

	public IPMysqlDB(String url, String name, String pwd) {
		this.url = url;
		this.name = name;
		this.password = pwd;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection connectDB() {
		try {
			connection = DriverManager.getConnection(url, name, password);
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

	public int insertDataListInBatch(IPSqlTable table, List<Map<String, Object>> dataList) {
		int result = 0;
		String sql = IPSqlBuilder.insertSql(table);
		PreparedStatement stmt;
		try {
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(sql);
			List<IPSqlField> fields = table.getFields();

			for (Map<String, Object> data : dataList) {
				for (int i = 0; i < fields.size(); ++i) {
					IPSqlField field = fields.get(i);
					String fieldName = field.fieldName;
					IPSqlHelper.setStmtObject(stmt, i + 1, field, data.get(fieldName));
				}
				stmt.addBatch();
			}
			int[] rows = stmt.executeBatch();
			result = rows.length;
			connection.commit();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}

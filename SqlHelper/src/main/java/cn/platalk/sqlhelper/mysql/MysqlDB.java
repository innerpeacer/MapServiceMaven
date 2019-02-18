package cn.platalk.sqlhelper.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.platalk.sqlhelper.mysql.builder.MysqlBuilder;
import cn.platalk.sqlhelper.mysql.helper.MysqlHelper;

public class MysqlDB {
	private Connection connection;
	private String url;
	private String name;
	private String password;

	public MysqlDB(String url, String name, String pwd) {
		this.url = url;
		this.name = name;
		this.password = pwd;
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

	public void createTable(MysqlTable table) {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(MysqlBuilder.tableCreateSql(table));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean existTable(MysqlTable table) {
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

	public void dropTable(MysqlTable table) {
		String sql = String.format("drop table %s", table.getTableName());
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eraseTable(MysqlTable table) {
		String sql = String.format("delete from %s", table.getTableName());
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteRecord(MysqlTable table, Map<MysqlField, Object> clause) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("delete from ").append(table.getTableName());
		if (clause != null && clause.size() > 0) {
			buffer.append(MysqlBuilder.whereClause(clause));
		}
		String sql = buffer.toString();
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteRecord(MysqlTable table, MysqlField field, Object value) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("delete from ").append(table.getTableName());
		if (field != null) {
			buffer.append(MysqlBuilder.whereClause(field, value));
		}
		String sql = buffer.toString();
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean existRecord(MysqlTable table, Map<MysqlField, Object> clause) {
		int result = 0;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(*) from ").append(table.getTableName());
		if (clause != null && clause.size() > 0) {
			buffer.append(MysqlBuilder.whereClause(clause));
		}
		String sql = buffer.toString();
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

	public boolean existRecord(MysqlTable table, MysqlField field, Object value) {
		int result = 0;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(*) from ").append(table.getTableName());
		if (field != null) {
			buffer.append(MysqlBuilder.whereClause(field, value));
		}
		String sql = buffer.toString();
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

	public int insertData(MysqlTable table, Map<String, Object> data) {
		int result = 0;
		String sql = MysqlBuilder.insertSql(table);
		// System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			List<MysqlField> fields = table.getFields();
			for (int i = 0; i < fields.size(); ++i) {
				MysqlField field = fields.get(i);
				String fieldName = field.fieldName;
				MysqlHelper.setStmtObject(stmt, i + 1, field, data.get(fieldName));
			}
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int insertDataListInBatch(MysqlTable table, List<Map<String, Object>> dataList) {
		int result = 0;
		String sql = MysqlBuilder.insertSql(table);
		PreparedStatement stmt;
		try {
			connection.setAutoCommit(false);
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			List<MysqlField> fields = table.getFields();

			for (int k = 0; k < dataList.size(); ++k) {
				Map<String, Object> data = dataList.get(k);
				for (int i = 0; i < fields.size(); ++i) {
					MysqlField field = fields.get(i);
					String fieldName = field.fieldName;
					MysqlHelper.setStmtObject(stmt, i + 1, field, data.get(fieldName));
				}
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

	public int updateData(MysqlTable table, Map<String, Object> data, Map<MysqlField, Object> clause) {
		int result = 0;
		List<MysqlField> updateFields = new ArrayList<MysqlField>();
		for (String fieldName : data.keySet()) {
			updateFields.add(table.getField(fieldName));
		}

		String sql = MysqlBuilder.updateSql(table, updateFields, clause);
		// System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			for (int i = 0; i < updateFields.size(); ++i) {
				MysqlField field = updateFields.get(i);
				String fieldName = field.fieldName;
				MysqlHelper.setStmtObject(stmt, i + 1, field, data.get(fieldName));
			}
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int updateData(MysqlTable table, Map<String, Object> data, MysqlField targetField, Object value) {
		int result = 0;
		List<MysqlField> updateFields = new ArrayList<MysqlField>();
		for (String fieldName : data.keySet()) {
			updateFields.add(table.getField(fieldName));
		}

		String sql = MysqlBuilder.updateSql(table, updateFields, targetField, value);
		// System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			for (int i = 0; i < updateFields.size(); ++i) {
				MysqlField field = updateFields.get(i);
				String fieldName = field.fieldName;
				MysqlHelper.setStmtObject(stmt, i + 1, field, data.get(fieldName));
			}
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<MysqlRecord> readData(MysqlTable table, Map<MysqlField, Object> clause) {
		List<MysqlRecord> records = new ArrayList<MysqlRecord>();
		String sql = MysqlBuilder.selectSql(table, clause);
		// System.out.println(sql);
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<MysqlEntity> entities = new ArrayList<MysqlEntity>();
				List<MysqlField> fields = table.getFields();
				for (MysqlField f : fields) {
					MysqlEntity entity = MysqlHelper.getObject(rs, f);
					entities.add(entity);
				}
				MysqlRecord record = new MysqlRecord(entities);
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

	public List<MysqlRecord> readData(MysqlTable table, MysqlField targetField, Object value) {
		List<MysqlRecord> records = new ArrayList<MysqlRecord>();
		String sql = MysqlBuilder.selectSql(table, targetField, value);
		// System.out.println(sql);
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<MysqlEntity> entities = new ArrayList<MysqlEntity>();
				List<MysqlField> fields = table.getFields();
				for (MysqlField f : fields) {
					MysqlEntity entity = MysqlHelper.getObject(rs, f);
					entities.add(entity);
				}
				MysqlRecord record = new MysqlRecord(entities);
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

	public List<MysqlRecord> readData(MysqlTable table) {
		List<MysqlRecord> records = new ArrayList<MysqlRecord>();
		String sql = String.format("select * from %s", table.getTableName());
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<MysqlEntity> entities = new ArrayList<MysqlEntity>();
				List<MysqlField> fields = table.getFields();
				for (MysqlField f : fields) {
					MysqlEntity entity = MysqlHelper.getObject(rs, f);
					entities.add(entity);
				}
				MysqlRecord record = new MysqlRecord(entities);
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

}

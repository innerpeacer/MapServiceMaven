package cn.platalk.sqlhelper.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class SqlDB {
	protected Connection connection;

	public boolean existTable(SqlTable table) {
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

	public void createTable(SqlTable table) {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(SqlBuilder.mysqlTableCreateSql(table));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void dropTable(SqlTable table) {
		String sql = String.format("drop table %s", table.getTableName());
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eraseTable(SqlTable table) {
		String sql = String.format("delete from %s", table.getTableName());
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteRecord(SqlTable table, Map<SqlField, Object> clause) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("delete from ").append(table.getTableName());
		if (clause != null && clause.size() > 0) {
			buffer.append(SqlBuilder.whereClause(clause));
		}
		String sql = buffer.toString();
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteRecord(SqlTable table, SqlField field, Object value) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("delete from ").append(table.getTableName());
		if (field != null) {
			buffer.append(SqlBuilder.whereClause(field, value));
		}
		String sql = buffer.toString();
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean existRecord(SqlTable table, Map<SqlField, Object> clause) {
		int result = 0;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(*) from ").append(table.getTableName());
		if (clause != null && clause.size() > 0) {
			buffer.append(SqlBuilder.whereClause(clause));
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

	public boolean existRecord(SqlTable table, SqlField field, Object value) {
		int result = 0;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(*) from ").append(table.getTableName());
		if (field != null) {
			buffer.append(SqlBuilder.whereClause(field, value));
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

	public int insertData(SqlTable table, Map<String, Object> data) {
		int result = 0;
		String sql = SqlBuilder.insertSql(table);
		// System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			List<SqlField> fields = table.getFields();
			for (int i = 0; i < fields.size(); ++i) {
				SqlField field = fields.get(i);
				String fieldName = field.fieldName;
				SqlHelper.setStmtObject(stmt, i + 1, field, data.get(fieldName));
			}
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int updateData(SqlTable table, Map<String, Object> data, Map<SqlField, Object> clause) {
		int result = 0;
		List<SqlField> updateFields = new ArrayList<SqlField>();
		for (String fieldName : data.keySet()) {
			updateFields.add(table.getField(fieldName));
		}

		String sql = SqlBuilder.updateSql(table, updateFields, clause);
		// System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			for (int i = 0; i < updateFields.size(); ++i) {
				SqlField field = updateFields.get(i);
				String fieldName = field.fieldName;
				SqlHelper.setStmtObject(stmt, i + 1, field, data.get(fieldName));
			}
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int updateData(SqlTable table, Map<String, Object> data, SqlField targetField, Object value) {
		int result = 0;
		List<SqlField> updateFields = new ArrayList<SqlField>();
		for (String fieldName : data.keySet()) {
			updateFields.add(table.getField(fieldName));
		}

		String sql = SqlBuilder.updateSql(table, updateFields, targetField, value);
		// System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
			for (int i = 0; i < updateFields.size(); ++i) {
				SqlField field = updateFields.get(i);
				String fieldName = field.fieldName;
				SqlHelper.setStmtObject(stmt, i + 1, field, data.get(fieldName));
			}
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<SqlRecord> readData(SqlTable table, Map<SqlField, Object> clause) {
		List<SqlRecord> records = new ArrayList<SqlRecord>();
		String sql = SqlBuilder.selectSql(table, clause);
		// System.out.println(sql);
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<SqlEntity> entities = new ArrayList<SqlEntity>();
				List<SqlField> fields = table.getFields();
				for (SqlField f : fields) {
					SqlEntity entity = SqlHelper.getObject(rs, f);
					entities.add(entity);
				}
				SqlRecord record = new SqlRecord(entities);
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

	public List<SqlRecord> readData(SqlTable table, SqlField targetField, Object value) {
		List<SqlRecord> records = new ArrayList<SqlRecord>();
		String sql = SqlBuilder.selectSql(table, targetField, value);
		// System.out.println(sql);
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<SqlEntity> entities = new ArrayList<SqlEntity>();
				List<SqlField> fields = table.getFields();
				for (SqlField f : fields) {
					SqlEntity entity = SqlHelper.getObject(rs, f);
					entities.add(entity);
				}
				SqlRecord record = new SqlRecord(entities);
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

	public List<SqlRecord> readData(SqlTable table) {
		List<SqlRecord> records = new ArrayList<SqlRecord>();
		String sql = String.format("select * from %s", table.getTableName());
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<SqlEntity> entities = new ArrayList<SqlEntity>();
				List<SqlField> fields = table.getFields();
				for (SqlField f : fields) {
					SqlEntity entity = SqlHelper.getObject(rs, f);
					entities.add(entity);
				}
				SqlRecord record = new SqlRecord(entities);
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}
}

package cn.platalk.sqlhelper.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class IPSqlDB {
	protected Connection connection;

	public boolean existTable(IPSqlTable table) {
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

	public void createTable(IPSqlTable table) {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(IPSqlBuilder.mysqlTableCreateSql(table));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createSqliteTable(IPSqlTable table) {
		existSql(IPSqlBuilder.sqliteTableCreateSql(table));
	}

	public void dropTable(IPSqlTable table) {
		executeSql(String.format("drop table %s", table.getTableName()));
	}

	public void eraseTable(IPSqlTable table) {
		executeSql(String.format("delete from %s", table.getTableName()));
	}

	public void deleteRecord(IPSqlTable table, Map<IPSqlField, Object> clause) {
		StringBuilder builder = new StringBuilder();
		builder.append("delete from ").append(table.getTableName());
		if (clause != null && clause.size() > 0) {
			builder.append(IPSqlBuilder.whereClause(clause));
		}
		executeSql(builder.toString());
	}

	public void deleteRecord(IPSqlTable table, IPSqlField field, Object value) {
		StringBuilder builder = new StringBuilder();
		builder.append("delete from ").append(table.getTableName());
		if (field != null) {
			builder.append(IPSqlBuilder.whereClause(field, value));
		}
		executeSql(builder.toString());
	}

	public boolean existRecord(IPSqlTable table, Map<IPSqlField, Object> clause) {
		StringBuilder builder = new StringBuilder();
		builder.append("select count(*) from ").append(table.getTableName());
		if (clause != null && clause.size() > 0) {
			builder.append(IPSqlBuilder.whereClause(clause));
		}
		return existSql(builder.toString());
	}

	public boolean existRecord(IPSqlTable table, IPSqlField field, Object value) {
		StringBuilder builder = new StringBuilder();
		builder.append("select count(*) from ").append(table.getTableName());
		if (field != null) {
			builder.append(IPSqlBuilder.whereClause(field, value));
		}
		return existSql(builder.toString());
	}

	public int insertData(IPSqlTable table, Map<String, Object> data) {
		int result = 0;
		String sql = IPSqlBuilder.insertSql(table);
		// System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			List<IPSqlField> fields = table.getFields();
			for (int i = 0; i < fields.size(); ++i) {
				IPSqlField field = fields.get(i);
				String fieldName = field.fieldName;
				IPSqlHelper.setStmtObject(stmt, i + 1, field, data.get(fieldName));
			}
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int updateData(IPSqlTable table, Map<String, Object> data, Map<IPSqlField, Object> clause) {
		List<IPSqlField> updateFields = new ArrayList<>();
		for (String fieldName : data.keySet()) {
			updateFields.add(table.getField(fieldName));
		}

		String sql = IPSqlBuilder.updateSql(table, updateFields, clause);
		return updateSql(updateFields, data, sql);
	}

	public int updateData(IPSqlTable table, Map<String, Object> data, IPSqlField targetField, Object value) {
		List<IPSqlField> updateFields = new ArrayList<>();
		for (String fieldName : data.keySet()) {
			updateFields.add(table.getField(fieldName));
		}

		String sql = IPSqlBuilder.updateSql(table, updateFields, targetField, value);
		return updateSql(updateFields, data, sql);
	}

	public List<IPSqlRecord> readData(IPSqlTable table, Map<IPSqlField, Object> clause, List<IPSqlOrder> orderList, IPSqlLimit limit) {
		String sql = IPSqlBuilder.selectSql(table, clause) +
				IPSqlBuilder.orderByClause(orderList) +
				IPSqlBuilder.limitClause(limit);
		return readSql(table, sql);
	}

	public List<IPSqlRecord> readData(IPSqlTable table, Map<IPSqlField, Object> clause) {
		String sql = IPSqlBuilder.selectSql(table, clause);
		return readSql(table, sql);
	}

	public List<IPSqlRecord> readData(IPSqlTable table, IPSqlField targetField, Object value) {
		String sql = IPSqlBuilder.selectSql(table, targetField, value);
		return readSql(table, sql);
	}

	public List<IPSqlRecord> readData(IPSqlTable table) {
		String sql = String.format("select * from %s", table.getTableName());
		return readSql(table, sql);
	}

	public List<IPSqlRecord> readSql(IPSqlTable table, String sql) {
		List<IPSqlRecord> records = new ArrayList<>();
		ResultSet rs;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<IPSqlEntity> entities = new ArrayList<>();
				List<IPSqlField> fields = table.getFields();
				for (IPSqlField f : fields) {
					IPSqlEntity entity = IPSqlHelper.getObject(rs, f);
					entities.add(entity);
				}
				IPSqlRecord record = new IPSqlRecord(entities);
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

	private int updateSql(List<IPSqlField> updateFields, Map<String, Object> data, String sql) {
		int result = 0;
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			for (int i = 0; i < updateFields.size(); ++i) {
				IPSqlField field = updateFields.get(i);
				String fieldName = field.fieldName;
				IPSqlHelper.setStmtObject(stmt, i + 1, field, data.get(fieldName));
			}
			result = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private boolean existSql(String sql) {
		int result = 0;
		ResultSet rs;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return (result != 0);
	}

	public void executeSql(String sql) {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

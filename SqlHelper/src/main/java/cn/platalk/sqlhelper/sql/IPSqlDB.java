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

	public void dropTable(IPSqlTable table) {
		String sql = String.format("drop table %s", table.getTableName());
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eraseTable(IPSqlTable table) {
		String sql = String.format("delete from %s", table.getTableName());
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteRecord(IPSqlTable table, Map<IPSqlField, Object> clause) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("delete from ").append(table.getTableName());
		if (clause != null && clause.size() > 0) {
			buffer.append(IPSqlBuilder.whereClause(clause));
		}
		String sql = buffer.toString();
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteRecord(IPSqlTable table, IPSqlField field, Object value) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("delete from ").append(table.getTableName());
		if (field != null) {
			buffer.append(IPSqlBuilder.whereClause(field, value));
		}
		String sql = buffer.toString();
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean existRecord(IPSqlTable table, Map<IPSqlField, Object> clause) {
		int result = 0;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(*) from ").append(table.getTableName());
		if (clause != null && clause.size() > 0) {
			buffer.append(IPSqlBuilder.whereClause(clause));
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

	public boolean existRecord(IPSqlTable table, IPSqlField field, Object value) {
		int result = 0;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(*) from ").append(table.getTableName());
		if (field != null) {
			buffer.append(IPSqlBuilder.whereClause(field, value));
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

	public int insertData(IPSqlTable table, Map<String, Object> data) {
		int result = 0;
		String sql = IPSqlBuilder.insertSql(table);
		// System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
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
		int result = 0;
		List<IPSqlField> updateFields = new ArrayList<IPSqlField>();
		for (String fieldName : data.keySet()) {
			updateFields.add(table.getField(fieldName));
		}

		String sql = IPSqlBuilder.updateSql(table, updateFields, clause);
		// System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
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

	public int updateData(IPSqlTable table, Map<String, Object> data, IPSqlField targetField, Object value) {
		int result = 0;
		List<IPSqlField> updateFields = new ArrayList<IPSqlField>();
		for (String fieldName : data.keySet()) {
			updateFields.add(table.getField(fieldName));
		}

		String sql = IPSqlBuilder.updateSql(table, updateFields, targetField, value);
		// System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = (PreparedStatement) connection.prepareStatement(sql);
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

	public List<IPSqlRecord> readData(IPSqlTable table, Map<IPSqlField, Object> clause) {
		List<IPSqlRecord> records = new ArrayList<IPSqlRecord>();
		String sql = IPSqlBuilder.selectSql(table, clause);
		// System.out.println(sql);
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<IPSqlEntity> entities = new ArrayList<IPSqlEntity>();
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

	public List<IPSqlRecord> readData(IPSqlTable table, IPSqlField targetField, Object value) {
		List<IPSqlRecord> records = new ArrayList<IPSqlRecord>();
		String sql = IPSqlBuilder.selectSql(table, targetField, value);
		// System.out.println(sql);
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<IPSqlEntity> entities = new ArrayList<IPSqlEntity>();
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

	public List<IPSqlRecord> readData(IPSqlTable table) {
		List<IPSqlRecord> records = new ArrayList<IPSqlRecord>();
		String sql = String.format("select * from %s", table.getTableName());
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				List<IPSqlEntity> entities = new ArrayList<IPSqlEntity>();
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
}

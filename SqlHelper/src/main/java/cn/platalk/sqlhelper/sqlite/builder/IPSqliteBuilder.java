package cn.platalk.sqlhelper.sqlite.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.sqlhelper.sqlite.SqliteField;
import cn.platalk.sqlhelper.sqlite.SqliteTable;

public class IPSqliteBuilder {

	public static String selectSql(SqliteTable table, Map<String, Object> clause) {
		StringBuffer buffer = new StringBuffer();
		String tableName = table.getTableName();
		buffer.append("select * from ").append(tableName);
		if (clause != null && clause.size() > 0) {
			Map<SqliteField, Object> clauseMap = new HashMap<SqliteField, Object>();
			for (String fieldName : clause.keySet()) {
				clauseMap.put(table.getField(fieldName), clause.get(fieldName));
			}
			buffer.append(whereClause(clauseMap));
		}
		return buffer.toString();
	}

	public static String insertSql(SqliteTable table) {
		StringBuffer buffer = new StringBuffer();
		String tableName = table.getTableName();
		buffer.append("insert into ").append(tableName);

		buffer.append(" (");
		boolean needComma = false;
		List<SqliteField> fields = table.getFields();
		for (SqliteField entity : fields) {
			if (needComma) {
				buffer.append(", ");
			} else {
				needComma = true;
			}
			buffer.append(entity.fieldName);
		}
		buffer.append(")");

		buffer.append(" values ");

		buffer.append("(");
		needComma = false;
		for (int i = 0; i < fields.size(); ++i) {
			if (needComma) {
				buffer.append(", ");
			} else {
				needComma = true;
			}
			buffer.append("?");
		}
		buffer.append(")");

		return buffer.toString();
	}

	public static String updateSql(SqliteTable table, List<SqliteField> updateFields, Map<String, Object> clause) {
		StringBuffer buffer = new StringBuffer();
		String tableName = table.getTableName();
		buffer.append("update ").append(tableName).append(" set ");

		boolean needComma = false;
		for (int i = 0; i < updateFields.size(); ++i) {
			if (needComma) {
				buffer.append(", ");
			} else {
				needComma = true;
			}
			buffer.append(updateFields.get(i).fieldName).append("=?");
		}

		if (clause != null && clause.size() > 0) {
			Map<SqliteField, Object> clauseMap = new HashMap<SqliteField, Object>();
			for (String fieldName : clause.keySet()) {
				clauseMap.put(table.getField(fieldName), clause.get(fieldName));
			}
			buffer.append(whereClause(clauseMap));
		}
		return buffer.toString();
	}

	public static String whereClause(Map<SqliteField, Object> clause) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" where ");

		boolean needComma = false;
		for (SqliteField field : clause.keySet()) {
			if (needComma) {
				buffer.append(" and ");
			} else {
				needComma = true;
			}

			Object value = clause.get(field);
			buffer.append(field.fieldName).append("=");
			if (field.fieldType.clazz.equals(String.class.getName())) {
				buffer.append(String.format("'%s'", value));
			} else {
				buffer.append(value);
			}
		}
		return buffer.toString();
	}

	public static String tableCreateSql(SqliteTable table) {
		StringBuffer buffer = new StringBuffer();
		String tableName = table.getTableName();
		buffer.append("create table if not exists ").append(tableName);
		buffer.append("(");

		boolean needComma = false;
		String primaryKey = table.getPrimaryKey();
		if (primaryKey != null) {
			buffer.append(primaryKey + " integer primary key autoincrement ");
			needComma = true;
		}

		List<SqliteField> fields = table.getFields();
		for (SqliteField field : fields) {
			if (needComma) {
				buffer.append(", ");
			} else {
				needComma = true;
			}
			buffer.append(field.fieldName + " " + field.fieldType.type);
			if (!field.allowNull) {
				buffer.append(" not null ");
			}
		}

		buffer.append(")");
		return buffer.toString();
	}

}

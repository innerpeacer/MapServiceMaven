package cn.platalk.sqlhelper.sql;

import java.util.List;
import java.util.Map;

public class IPSqlBuilder {

	public static String selectSql(IPSqlTable table, Map<IPSqlField, Object> clause) {
		StringBuffer buffer = new StringBuffer();
		String tableName = table.getTableName();
		buffer.append("select * from ").append(tableName);
		if (clause != null && clause.size() > 0) {
			buffer.append(whereClause(clause));
		}
		return buffer.toString();
	}

	public static String selectSql(IPSqlTable table, IPSqlField targetField, Object value) {
		StringBuffer buffer = new StringBuffer();
		String tableName = table.getTableName();
		buffer.append("select * from ").append(tableName);
		if (targetField != null) {
			buffer.append(whereClause(targetField, value));
		}
		return buffer.toString();
	}

	public static String insertSql(IPSqlTable table) {
		StringBuffer buffer = new StringBuffer();
		String tableName = table.getTableName();
		buffer.append("insert into ").append(tableName);

		buffer.append(" (");
		boolean needComma = false;
		List<IPSqlField> fields = table.getFields();
		for (IPSqlField field : fields) {
			if (needComma) {
				buffer.append(", ");
			} else {
				needComma = true;
			}
			buffer.append(field.fieldName);
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

	public static String updateSql(IPSqlTable table, List<IPSqlField> updateFields, Map<IPSqlField, Object> clause) {
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
			buffer.append(whereClause(clause));
		}
		return buffer.toString();
	}

	public static String updateSql(IPSqlTable table, List<IPSqlField> updateFields, IPSqlField targetField, Object value) {
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

		if (targetField != null) {
			buffer.append(whereClause(targetField, value));
		}
		return buffer.toString();
	}

	public static String whereClause(IPSqlField field, Object value) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" where ");
		buffer.append(field.fieldName).append("=");
		if (field.fieldType.clazz.equals(String.class.getName())) {
			buffer.append(String.format("'%s'", value));
		} else {
			buffer.append(value);
		}

		return buffer.toString();
	}

	public static String whereClause(Map<IPSqlField, Object> clause) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" where ");

		boolean needComma = false;
		for (IPSqlField field : clause.keySet()) {
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

	public static String mysqlTableCreateSql(IPSqlTable table) {
		StringBuffer buffer = new StringBuffer();
		String tableName = table.getTableName();
		buffer.append("CREATE TABLE IF NOT EXISTS ").append(tableName);
		buffer.append("(_id INT NOT NULL AUTO_INCREMENT, ");

		boolean needComma = false;

		List<IPSqlField> fields = table.getFields();
		for (IPSqlField field : fields) {
			if (needComma) {
				buffer.append(", ");
			} else {
				needComma = true;
			}
			buffer.append(field.fieldName + " " + field.fieldType.type);
			if (!field.allowNull) {
				buffer.append(" NOT NULL ");
			}
		}

		buffer.append(", PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));");
		return buffer.toString();
	}

	public static String sqliteTableCreateSql(IPSqlTable table) {
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

		List<IPSqlField> fields = table.getFields();
		for (IPSqlField field : fields) {
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

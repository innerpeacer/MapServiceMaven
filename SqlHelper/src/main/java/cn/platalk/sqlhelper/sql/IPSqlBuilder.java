package cn.platalk.sqlhelper.sql;

import java.util.List;
import java.util.Map;

public class IPSqlBuilder {

	public static String selectSql(IPSqlTable table, Map<IPSqlField, Object> clause) {
		StringBuilder builder = new StringBuilder();
		String tableName = table.getTableName();
		builder.append("select * from ").append(tableName);
		if (clause != null && clause.size() > 0) {
			builder.append(whereClause(clause));
		}
		return builder.toString();
	}

	public static String selectSql(IPSqlTable table, IPSqlField targetField, Object value) {
		StringBuilder builder = new StringBuilder();
		String tableName = table.getTableName();
		builder.append("select * from ").append(tableName);
		if (targetField != null) {
			builder.append(whereClause(targetField, value));
		}
		return builder.toString();
	}

	public static String insertSql(IPSqlTable table) {
		StringBuilder builder = new StringBuilder();
		String tableName = table.getTableName();
		builder.append("insert into ").append(tableName);

		builder.append(" (");
		boolean needComma = false;
		List<IPSqlField> fields = table.getFields();
		for (IPSqlField field : fields) {
			if (needComma) {
				builder.append(", ");
			} else {
				needComma = true;
			}
			builder.append(field.fieldName);
		}
		builder.append(")");

		builder.append(" values ");

		builder.append("(");
		needComma = false;
		for (int i = 0; i < fields.size(); ++i) {
			if (needComma) {
				builder.append(", ");
			} else {
				needComma = true;
			}
			builder.append("?");
		}
		builder.append(")");

		return builder.toString();
	}

	public static String updateSql(IPSqlTable table, List<IPSqlField> updateFields, Map<IPSqlField, Object> clause) {
		StringBuilder builder = new StringBuilder();
		String tableName = table.getTableName();
		builder.append("update ").append(tableName).append(" set ");

		boolean needComma = false;
		for (IPSqlField updateField : updateFields) {
			if (needComma) {
				builder.append(", ");
			} else {
				needComma = true;
			}
			builder.append(updateField.fieldName).append("=?");
		}

		if (clause != null && clause.size() > 0) {
			builder.append(whereClause(clause));
		}
		return builder.toString();
	}

	public static String updateSql(IPSqlTable table, List<IPSqlField> updateFields, IPSqlField targetField, Object value) {
		StringBuilder builder = new StringBuilder();
		String tableName = table.getTableName();
		builder.append("update ").append(tableName).append(" set ");

		boolean needComma = false;
		for (IPSqlField updateField : updateFields) {
			if (needComma) {
				builder.append(", ");
			} else {
				needComma = true;
			}
			builder.append(updateField.fieldName).append("=?");
		}

		if (targetField != null) {
			builder.append(whereClause(targetField, value));
		}
		return builder.toString();
	}

	public static String whereClause(IPSqlField field, Object value) {
		StringBuilder builder = new StringBuilder();
		builder.append(" where ");
		builder.append(field.fieldName).append("=");
		if (field.fieldType.clazz.equals(String.class.getName())) {
			builder.append(String.format("'%s'", value));
		} else {
			builder.append(value);
		}

		return builder.toString();
	}

	public static String whereClause(Map<IPSqlField, Object> clause) {
		StringBuilder builder = new StringBuilder();
		builder.append(" where ");

		boolean needComma = false;
		for (IPSqlField field : clause.keySet()) {
			if (needComma) {
				builder.append(" and ");
			} else {
				needComma = true;
			}

			Object value = clause.get(field);
			builder.append(field.fieldName).append("=");
			if (field.fieldType.clazz.equals(String.class.getName())) {
				builder.append(String.format("'%s'", value));
			} else {
				builder.append(value);
			}
		}
		return builder.toString();
	}

	public static String orderByClause(List<IPSqlOrder> orderList) {
		if (orderList == null || orderList.size() == 0) return "";

		StringBuilder builder = new StringBuilder();
		builder.append(" order by ");

		boolean needComma = false;
		for (IPSqlOrder order : orderList) {
			if (needComma) {
				builder.append(", ");
			} else {
				needComma = true;
			}

			builder.append(order.toSql());
		}
		return builder.toString();
	}

	public static String limitClause(IPSqlLimit limit){
		return limit == null ? "" : limit.toSql();
	}

	public static String mysqlTableCreateSql(IPSqlTable table) {
		StringBuilder builder = new StringBuilder();
		String tableName = table.getTableName();
		builder.append("CREATE TABLE IF NOT EXISTS ").append(tableName);
		builder.append("(_id INT NOT NULL AUTO_INCREMENT, ");

		boolean needComma = false;

		List<IPSqlField> fields = table.getFields();
		for (IPSqlField field : fields) {
			if (needComma) {
				builder.append(", ");
			} else {
				needComma = true;
			}
			builder.append(field.fieldName).append(" ").append(field.fieldType.type);
			if (!field.allowNull) {
				builder.append(" NOT NULL ");
			}
		}

		builder.append(", PRIMARY KEY (_id)," + " UNIQUE INDEX _id_UNIQUE (_id ASC));");
		return builder.toString();
	}

	public static String sqliteTableCreateSql(IPSqlTable table) {
		StringBuilder builder = new StringBuilder();
		String tableName = table.getTableName();
		builder.append("create table if not exists ").append(tableName);
		builder.append("(");

		boolean needComma = false;
		String primaryKey = table.getPrimaryKey();
		if (primaryKey != null) {
			builder.append(primaryKey).append(" integer primary key autoincrement ");
			needComma = true;
		}

		List<IPSqlField> fields = table.getFields();
		for (IPSqlField field : fields) {
			if (needComma) {
				builder.append(", ");
			} else {
				needComma = true;
			}
			builder.append(field.fieldName).append(" ").append(field.fieldType.type);
			if (!field.allowNull) {
				builder.append(" not null ");
			}
		}

		builder.append(")");
		return builder.toString();
	}

}

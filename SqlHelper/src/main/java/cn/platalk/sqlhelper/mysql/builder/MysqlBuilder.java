package cn.platalk.sqlhelper.mysql.builder;

import java.util.List;
import java.util.Map;

import cn.platalk.sqlhelper.mysql.MysqlField;
import cn.platalk.sqlhelper.mysql.MysqlTable;

public class MysqlBuilder {

	public static String selectSql(MysqlTable table, Map<MysqlField, Object> clause) {
		StringBuffer buffer = new StringBuffer();
		String tableName = table.getTableName();
		buffer.append("select * from ").append(tableName);
		if (clause != null && clause.size() > 0) {
			buffer.append(whereClause(clause));
		}
		return buffer.toString();
	}

	public static String selectSql(MysqlTable table, MysqlField targetField, Object value) {
		StringBuffer buffer = new StringBuffer();
		String tableName = table.getTableName();
		buffer.append("select * from ").append(tableName);
		if (targetField != null) {
			buffer.append(whereClause(targetField, value));
		}
		return buffer.toString();
	}

	public static String insertSql(MysqlTable table) {
		StringBuffer buffer = new StringBuffer();
		String tableName = table.getTableName();
		buffer.append("insert into ").append(tableName);

		buffer.append(" (");
		boolean needComma = false;
		List<MysqlField> fields = table.getFields();
		for (MysqlField entity : fields) {
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

	public static String updateSql(MysqlTable table, List<MysqlField> updateFields, Map<MysqlField, Object> clause) {
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

	public static String updateSql(MysqlTable table, List<MysqlField> updateFields, MysqlField targetField,
			Object value) {
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

	public static String whereClause(MysqlField field, Object value) {
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

	public static String whereClause(Map<MysqlField, Object> clause) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" where ");

		boolean needComma = false;
		for (MysqlField field : clause.keySet()) {
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

	public static String tableCreateSql(MysqlTable table) {
		StringBuffer buffer = new StringBuffer();
		String tableName = table.getTableName();
		buffer.append("CREATE TABLE IF NOT EXISTS ").append(tableName);
		buffer.append("(_id INT NOT NULL AUTO_INCREMENT, ");

		boolean needComma = false;

		List<MysqlField> fields = table.getFields();
		for (MysqlField field : fields) {
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
}

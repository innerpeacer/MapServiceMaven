package cn.platalk.sqlhelper.sqlite.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import cn.platalk.sqlhelper.sqlite.SqliteEntity;
import cn.platalk.sqlhelper.sqlite.SqliteField;

public class IPSqliteHelper {
	public static SqliteEntity getObject(ResultSet rs, SqliteField field) throws SQLException {
		return new SqliteEntity(field.fieldName, field.fieldType.clazz, rs.getObject(field.fieldName));
	}

	public static void setStmtObject(PreparedStatement stmt, int index, SqliteField field, Object value)
			throws SQLException {
		String type = field.fieldType.clazz;
		if (type.equals(String.class.getName())) {
			if (value != null) {
				stmt.setString(index, (String) value);
			} else if (field.allowNull) {
				stmt.setNull(index, Types.VARCHAR);
			}
		}

		if (type.equals(Double.class.getName())) {
			if (value != null) {
				stmt.setDouble(index, (Double) value);
			} else if (field.allowNull) {
				stmt.setNull(index, Types.DOUBLE);
			}
		}

		if (type.equals(Float.class.getName())) {
			if (value != null) {
				stmt.setFloat(index, (Float) value);
			} else if (field.allowNull) {
				stmt.setNull(index, Types.FLOAT);
			}
		}

		if (type.equals(Boolean.class.getName())) {
			if (value != null) {
				stmt.setBoolean(index, (Boolean) value);
			} else if (field.allowNull) {
				stmt.setNull(index, Types.BOOLEAN);
			}
		}

		if (type.equals(Integer.class.getName())) {
			if (value != null) {
				stmt.setInt(index, (Integer) value);
			} else if (field.allowNull) {
				stmt.setNull(index, Types.INTEGER);
			}
		}

		if (type.equals(byte[].class.getName())) {
			if (value != null) {
				stmt.setBytes(index, (byte[]) value);
			} else if (field.allowNull) {
				stmt.setNull(index, Types.BLOB);
			}
		}
	}
}

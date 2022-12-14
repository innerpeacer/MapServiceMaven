package cn.platalk.sqlhelper.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class IPSqlHelper {
	public static IPSqlEntity getObject(ResultSet rs, IPSqlField field) throws SQLException {
		Exception error = null;
		try {
			rs.findColumn(field.fieldName);
		} catch (Exception e) {
			error = e;
		}
		if (error != null) {
			return new IPSqlEntity(field.fieldName, field.fieldType.clazz, null);
		}
		return new IPSqlEntity(field.fieldName, field.fieldType.clazz, rs.getObject(field.fieldName));
	}

	public static void setStmtObject(PreparedStatement stmt, int index, IPSqlField field, Object value)
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
				assert value instanceof Double;
				stmt.setDouble(index, (Double) value);
			} else if (field.allowNull) {
				stmt.setNull(index, Types.DOUBLE);
			}
		}

		if (type.equals(Float.class.getName())) {
			if (value != null) {
				assert value instanceof Float;
				stmt.setFloat(index, (Float) value);
			} else if (field.allowNull) {
				stmt.setNull(index, Types.FLOAT);
			}
		}

		if (type.equals(Boolean.class.getName())) {
			if (value != null) {
				assert value instanceof Boolean;
				stmt.setBoolean(index, (Boolean) value);
			} else if (field.allowNull) {
				stmt.setNull(index, Types.BOOLEAN);
			}
		}

		if (type.equals(Integer.class.getName())) {
			if (value != null) {
				assert value instanceof Integer;
				stmt.setInt(index, (Integer) value);
			} else if (field.allowNull) {
				stmt.setNull(index, Types.INTEGER);
			}
		}

		if (type.equals(Long.class.getName())) {
			if (value != null) {
				assert value instanceof Long;
				stmt.setLong(index, (Long) value);
			} else if (field.allowNull) {
				stmt.setNull(index, Types.BIGINT);
			}
		}

		if (type.equals(byte[].class.getName())) {
			if (value != null) {
				assert value instanceof byte[];
				stmt.setBytes(index, (byte[]) value);
			} else if (field.allowNull) {
				stmt.setNull(index, Types.BLOB);
			}
		}
	}
}

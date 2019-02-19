package cn.platalk.sqlhelper.sql;

import java.util.HashMap;
import java.util.Map;

public class SqlFieldType {
	public String clazz;
	public String type;

	public SqlFieldType(String c, String t) {
		this.clazz = c;
		this.type = t;
	}

	private static final Map<String, SqlFieldType> SqliteFieldTypeMap = new HashMap<String, SqlFieldType>();

	static {
		SqliteFieldTypeMap.put(String.class.getName(), new SqlFieldType(String.class.getName(), "text"));
		SqliteFieldTypeMap.put(Double.class.getName(), new SqlFieldType(Double.class.getName(), "real"));
		SqliteFieldTypeMap.put(Float.class.getName(), new SqlFieldType(Float.class.getName(), "real"));
		SqliteFieldTypeMap.put(Boolean.class.getName(), new SqlFieldType(Boolean.class.getName(), "integer"));
		SqliteFieldTypeMap.put(Integer.class.getName(), new SqlFieldType(Integer.class.getName(), "integer"));
		SqliteFieldTypeMap.put(Long.class.getName(), new SqlFieldType(Long.class.getName(), "integer"));
		SqliteFieldTypeMap.put(byte[].class.getName(), new SqlFieldType(byte[].class.getName(), "blob"));
	}

	public static SqlFieldType FieldTypeFromClass(String clazz) {
		return SqliteFieldTypeMap.get(clazz);
	}
}

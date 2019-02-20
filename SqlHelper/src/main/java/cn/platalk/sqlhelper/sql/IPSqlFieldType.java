package cn.platalk.sqlhelper.sql;

import java.util.HashMap;
import java.util.Map;

public class IPSqlFieldType {
	public String clazz;
	public String type;

	public IPSqlFieldType(String c, String t) {
		this.clazz = c;
		this.type = t;
	}

	private static final Map<String, IPSqlFieldType> SqliteFieldTypeMap = new HashMap<String, IPSqlFieldType>();

	static {
		SqliteFieldTypeMap.put(String.class.getName(), new IPSqlFieldType(String.class.getName(), "text"));
		SqliteFieldTypeMap.put(Double.class.getName(), new IPSqlFieldType(Double.class.getName(), "real"));
		SqliteFieldTypeMap.put(Float.class.getName(), new IPSqlFieldType(Float.class.getName(), "real"));
		SqliteFieldTypeMap.put(Boolean.class.getName(), new IPSqlFieldType(Boolean.class.getName(), "integer"));
		SqliteFieldTypeMap.put(Integer.class.getName(), new IPSqlFieldType(Integer.class.getName(), "integer"));
		SqliteFieldTypeMap.put(Long.class.getName(), new IPSqlFieldType(Long.class.getName(), "integer"));
		SqliteFieldTypeMap.put(byte[].class.getName(), new IPSqlFieldType(byte[].class.getName(), "blob"));
	}

	public static IPSqlFieldType FieldTypeFromClass(String clazz) {
		return SqliteFieldTypeMap.get(clazz);
	}
}

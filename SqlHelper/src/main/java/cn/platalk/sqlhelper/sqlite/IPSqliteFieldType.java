package cn.platalk.sqlhelper.sqlite;

import java.util.HashMap;
import java.util.Map;

public class IPSqliteFieldType {
	public String clazz;
	public String type;

	public IPSqliteFieldType(String c, String t) {
		this.clazz = c;
		this.type = t;
	}

	private static final Map<String, IPSqliteFieldType> FieldTypeMap = new HashMap<String, IPSqliteFieldType>();

	static {
		FieldTypeMap.put(String.class.getName(), new IPSqliteFieldType(String.class.getName(), "text"));
		FieldTypeMap.put(Double.class.getName(), new IPSqliteFieldType(Double.class.getName(), "real"));
		FieldTypeMap.put(Float.class.getName(), new IPSqliteFieldType(Float.class.getName(), "real"));
		FieldTypeMap.put(Boolean.class.getName(), new IPSqliteFieldType(Boolean.class.getName(), "integer"));
		FieldTypeMap.put(Integer.class.getName(), new IPSqliteFieldType(Integer.class.getName(), "integer"));
		FieldTypeMap.put(Long.class.getName(), new IPSqliteFieldType(Long.class.getName(), "integer"));
		FieldTypeMap.put(byte[].class.getName(), new IPSqliteFieldType(byte[].class.getName(), "blob"));
	}

	static IPSqliteFieldType FiledTypeFromClass(String clazz) {
		return FieldTypeMap.get(clazz);
	}
}

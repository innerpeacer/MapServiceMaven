package cn.platalk.sqlhelper.sqlite;

/*
 * field object, defining name/type/default value etc...
 */
public class SqliteField {
	public String fieldName;
	public IPSqliteFieldType fieldType;
	public boolean allowNull;
	public Object defaultValue;

	public SqliteField(String field, String clazz, boolean allowNull) {
		this(field, clazz, allowNull, null);
	}

	public SqliteField(String field, String clazz, boolean allowNull, Object defValue) {
		this.fieldName = field;
		this.fieldType = IPSqliteFieldType.FiledTypeFromClass(clazz);
		this.allowNull = allowNull;
		this.defaultValue = defValue;
	}

	@Override
	public String toString() {
		return String.format("Field: %s, Type: %s", fieldName, fieldType.clazz);
	}
}

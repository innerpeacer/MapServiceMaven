package cn.platalk.sqlhelper.sql;

public class SqlField {
	public String fieldName;
	public SqlFieldType fieldType;
	public boolean allowNull;
	public Object defaultValue;

	public SqlField(String field, SqlFieldType type, boolean allowNull) {
		this(field, type, allowNull, null);
	}

	public SqlField(String field, SqlFieldType type, boolean allowNull, Object defValue) {
		this.fieldName = field;
		this.fieldType = type;
		this.allowNull = allowNull;
		this.defaultValue = defValue;
	}

	@Override
	public String toString() {
		return String.format("Field: %s, Type: %s", fieldName, fieldType.clazz);
	}
}

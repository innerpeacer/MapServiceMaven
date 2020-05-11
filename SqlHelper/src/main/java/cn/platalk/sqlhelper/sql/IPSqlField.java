package cn.platalk.sqlhelper.sql;

public class IPSqlField {
	public final String fieldName;
	public final IPSqlFieldType fieldType;
	public final boolean allowNull;
	public final Object defaultValue;

	public IPSqlField(String field, IPSqlFieldType type, boolean allowNull) {
		this(field, type, allowNull, null);
	}

	public IPSqlField(String field, IPSqlFieldType type, boolean allowNull, Object defValue) {
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

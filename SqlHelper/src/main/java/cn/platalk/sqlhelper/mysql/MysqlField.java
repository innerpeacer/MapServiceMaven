package cn.platalk.sqlhelper.mysql;

public class MysqlField {
	public String fieldName;
	public MysqlFieldType fieldType;
	public boolean allowNull;
	public Object defaultValue;

	public MysqlField(String field, MysqlFieldType type, boolean allowNull) {
		this(field, type, allowNull, null);
	}

	public MysqlField(String field, MysqlFieldType type, boolean allowNull, Object defValue) {
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

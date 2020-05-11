package cn.platalk.sqlhelper.sql;

public class IPSqlEntity {
	public final String field;
	public final String clazz;
	public final Object value;

	public IPSqlEntity(String field, String clazz, Object value) {
		this.field = field;
		this.clazz = clazz;
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format("%s: %s", field, value.toString());
	}
}

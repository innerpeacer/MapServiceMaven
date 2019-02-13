package cn.platalk.sqlhelper.sqlite;

/*
 * entity object, data corresponding to sqlite field
 */
public class SqliteEntity {
	public String field;
	public String clazz;
	public Object value;

	public SqliteEntity(String field, String clazz, Object value) {
		this.field = field;
		this.clazz = clazz;
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format("%s: %s", field, value.toString());
	}
}

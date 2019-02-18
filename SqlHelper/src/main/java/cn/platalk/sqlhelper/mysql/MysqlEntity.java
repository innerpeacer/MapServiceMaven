package cn.platalk.sqlhelper.mysql;

public class MysqlEntity {
	public String field;
	public String clazz;
	public Object value;

	public MysqlEntity(String field, String clazz, Object value) {
		this.field = field;
		this.clazz = clazz;
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format("%s: %s", field, value.toString());
	}
}

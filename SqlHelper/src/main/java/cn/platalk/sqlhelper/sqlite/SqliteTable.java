package cn.platalk.sqlhelper.sqlite;

import java.util.ArrayList;
import java.util.List;

public class SqliteTable {
	private String tableName;
	private List<SqliteField> fields;
	private String primaryKey;

	public SqliteTable(String name, List<SqliteField> fields, String primaryKey) {
		this.tableName = name;
		this.fields = new ArrayList<SqliteField>(fields);
		this.primaryKey = primaryKey;
	}

	public String getTableName() {
		return tableName;
	}

	public List<SqliteField> getFields() {
		return fields;
	}

	public SqliteField getField(String name) {
		for (SqliteField field : fields) {
			if (field.fieldName.equals(name)) {
				return field;
			}
		}
		return null;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

}

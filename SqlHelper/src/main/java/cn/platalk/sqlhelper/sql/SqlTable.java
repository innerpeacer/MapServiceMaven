package cn.platalk.sqlhelper.sql;

import java.util.ArrayList;
import java.util.List;

public class SqlTable {
	private String tableName;
	private List<SqlField> fields;
	private String primaryKey;

	public SqlTable(String name, List<SqlField> fields, String primaryKey) {
		this.tableName = name;
		this.fields = new ArrayList<SqlField>(fields);
		this.primaryKey = primaryKey;
	}

	public String getTableName() {
		return tableName;
	}

	public List<SqlField> getFields() {
		return fields;
	}

	public SqlField getField(String name) {
		for (SqlField field : fields) {
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

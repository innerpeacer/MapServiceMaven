package cn.platalk.sqlhelper.sql;

import java.util.ArrayList;
import java.util.List;

public class IPSqlTable {
	private final String tableName;
	private final List<IPSqlField> fields;
	private final String primaryKey;

	public IPSqlTable(String name, List<IPSqlField> fields, String primaryKey) {
		this.tableName = name;
		this.fields = new ArrayList<>(fields);
		this.primaryKey = primaryKey;
	}

	public String getTableName() {
		return tableName;
	}

	public List<IPSqlField> getFields() {
		return fields;
	}

	public IPSqlField getField(String name) {
		for (IPSqlField field : fields) {
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

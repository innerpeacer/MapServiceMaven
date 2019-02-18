package cn.platalk.sqlhelper.mysql;

import java.util.ArrayList;
import java.util.List;

public class MysqlTable {
	private String tableName;
	private List<MysqlField> fields;
	private String primaryKey;

	public MysqlTable(String name, List<MysqlField> fields, String primaryKey) {
		this.tableName = name;
		this.fields = new ArrayList<MysqlField>(fields);
		this.primaryKey = primaryKey;
	}

	public String getTableName() {
		return tableName;
	}

	public List<MysqlField> getFields() {
		return fields;
	}

	public MysqlField getField(String name) {
		for (MysqlField field : fields) {
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

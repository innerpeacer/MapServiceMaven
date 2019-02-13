package cn.platalk.sqlhelper.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqliteRecord {
	List<SqliteEntity> entityList;
	Map<String, Object> entityMap;

	public SqliteRecord(List<SqliteEntity> entities) {
		this.entityList = new ArrayList<SqliteEntity>(entities);
		this.entityMap = new HashMap<String, Object>();
		for (SqliteEntity entity : this.entityList) {
			entityMap.put(entity.field, entity.value);
		}
	}

	public Object getValue(String fieldName) {
		return entityMap.get(fieldName);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (SqliteEntity entity : entityList) {
			buffer.append("(").append(entity.field).append(": ").append(entity.value).append("), ");
		}
		buffer.append("]");
		return buffer.toString();
	}
}

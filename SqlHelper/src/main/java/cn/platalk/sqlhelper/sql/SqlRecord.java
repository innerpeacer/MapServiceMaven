package cn.platalk.sqlhelper.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlRecord {
	List<SqlEntity> entityList;
	Map<String, Object> entityMap;

	public SqlRecord(List<SqlEntity> entities) {
		this.entityList = new ArrayList<SqlEntity>(entities);
		this.entityMap = new HashMap<String, Object>();
		for (SqlEntity entity : this.entityList) {
			entityMap.put(entity.field, entity.value);
		}
	}

	// public Object getValue(String fieldName) {
	// return entityMap.get(fieldName);
	// }

	public String getString(String fieldName) {
		return (String) entityMap.get(fieldName);
	}

	public Integer getInteger(String fieldName) {
		return (Integer) entityMap.get(fieldName);
	}

	public Double getDouble(String fieldName) {
		return (Double) entityMap.get(fieldName);
	}

	public byte[] getBlob(String fieldName) {
		return (byte[]) entityMap.get(fieldName);
	}

	public boolean getBoolean(String fieldName) {
		Object value = entityMap.get(fieldName);
		return (value != null && ((Integer) value) != 0) ? true : false;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (SqlEntity entity : entityList) {
			buffer.append("(").append(entity.field).append(": ").append(entity.value).append("), ");
		}
		buffer.append("]");
		return buffer.toString();
	}
}

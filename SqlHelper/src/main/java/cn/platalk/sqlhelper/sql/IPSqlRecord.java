package cn.platalk.sqlhelper.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IPSqlRecord {
	List<IPSqlEntity> entityList;
	Map<String, Object> entityMap;

	public IPSqlRecord(List<IPSqlEntity> entities) {
		this.entityList = new ArrayList<IPSqlEntity>(entities);
		this.entityMap = new HashMap<String, Object>();
		for (IPSqlEntity entity : this.entityList) {
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
		for (IPSqlEntity entity : entityList) {
			buffer.append("(").append(entity.field).append(": ").append(entity.value).append("), ");
		}
		buffer.append("]");
		return buffer.toString();
	}
}

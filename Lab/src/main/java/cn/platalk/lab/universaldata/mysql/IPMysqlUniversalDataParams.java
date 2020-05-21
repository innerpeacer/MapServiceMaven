package cn.platalk.lab.universaldata.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.lab.universaldata.entity.IPUniversalData;
import cn.platalk.lab.universaldata.entity.IPUniversalDataType;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class IPMysqlUniversalDataParams {
	static final String FIELD_UNIVERSAL_DATA_1_ID = "DATA_ID";
	static final String FIELD_UNIVERSAL_DATA_2_NAME = "NAME";
	static final String FIELD_UNIVERSAL_DATA_3_CONTENT = "CONTENT";
	static final String FIELD_UNIVERSAL_DATA_4_TYPE = "TYPE";
	static final String FIELD_UNIVERSAL_DATA_5_DESCRIPTION = "DESCRIPTION";

	private static List<IPSqlField> universalDataFieldList = null;

	public static IPSqlTable CreateTable(String tableName) {
		return new IPSqlTable(tableName, GetUniversalDataFieldList(), null);
	}

	public static List<IPSqlField> GetUniversalDataFieldList() {
		if (universalDataFieldList == null) {
			universalDataFieldList = new ArrayList<>();
			universalDataFieldList.add(new IPSqlField(FIELD_UNIVERSAL_DATA_1_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(200)"), false));
			universalDataFieldList.add(new IPSqlField(FIELD_UNIVERSAL_DATA_2_NAME,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(200)"), false));
			universalDataFieldList.add(new IPSqlField(FIELD_UNIVERSAL_DATA_3_CONTENT,
					new IPSqlFieldType(byte[].class.getName(), "MediumBlob"), false));
			universalDataFieldList.add(new IPSqlField(FIELD_UNIVERSAL_DATA_4_TYPE,
					new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
			universalDataFieldList.add(new IPSqlField(FIELD_UNIVERSAL_DATA_5_DESCRIPTION,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(200)"), true));
		}
		return universalDataFieldList;
	}

	public static List<IPUniversalData> UniversalDataListFromRecords(List<IPSqlRecord> records) {
		List<IPUniversalData> udList = new ArrayList<>();
		for (IPSqlRecord record : records) {
			IPUniversalData ud = new IPUniversalData();
			ud.setDataID(record.getString(FIELD_UNIVERSAL_DATA_1_ID));
			ud.setDataName(record.getString(FIELD_UNIVERSAL_DATA_2_NAME));
			ud.setDataContent(record.getBlob(FIELD_UNIVERSAL_DATA_3_CONTENT));
			ud.setDataType(IPUniversalDataType.fromValue(record.getInteger(FIELD_UNIVERSAL_DATA_4_TYPE)));
			ud.setDataDescription(record.getString(FIELD_UNIVERSAL_DATA_5_DESCRIPTION));
			udList.add(ud);
		}
		return udList;
	}

	public static Map<String, Object> DataMapFromUniversalData(IPUniversalData ud) {
		Map<String, Object> data = new HashMap<>();
		data.put(FIELD_UNIVERSAL_DATA_1_ID, ud.getDataID());
		data.put(FIELD_UNIVERSAL_DATA_2_NAME, ud.getDataName());
		data.put(FIELD_UNIVERSAL_DATA_3_CONTENT, ud.getDataContent());
		data.put(FIELD_UNIVERSAL_DATA_4_TYPE, ud.getDataType().getValue());
		data.put(FIELD_UNIVERSAL_DATA_5_DESCRIPTION, ud.getDataDescription());
		return data;
	}
}

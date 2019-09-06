
package cn.platalk.lab.blesample.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.platalk.lab.blesample.entity.WTBleSample;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.BleSamplePbf;
import cn.platalk.lab.blesample.pbf.wrapper.WTBleSample2PbfUtils;
import cn.platalk.lab.blesample.pbf.wrapper.WTPbf2BleSampleUtils;
import cn.platalk.sqlhelper.sql.IPSqlField;
import cn.platalk.sqlhelper.sql.IPSqlFieldType;
import cn.platalk.sqlhelper.sql.IPSqlRecord;
import cn.platalk.sqlhelper.sql.IPSqlTable;

public class WTMysqlBleSampleParams {
	static final String FIELD_TABLE_BLE_SAMPLE = "BLE_SAMPLE";

	static final String FIELD_BLE_SAMPLE_1_ID = "SAMPLE_ID";
	static final String FIELD_BLE_SAMPLE_2_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_BLE_SAMPLE_3_LOCATION_X = "X";
	static final String FIELD_BLE_SAMPLE_4_LOCATION_Y = "Y";
	static final String FIELD_BLE_SAMPLE_5_LOCATION_FLOOR = "FLOOR";
	static final String FIELD_BLE_SAMPLE_6_TIMESTAMP = "TIMESTAMP";
	static final String FIELD_BLE_SAMPLE_7_CONTENT = "CONTENT";
	static final String FIELD_BLE_SAMPLE_8_PLATFORM = "PLATFORM";
	static final String FIELD_BLE_SAMPLE_9_USER = "USER";

	private static List<IPSqlField> bleSampleFieldList = null;

	public static IPSqlTable CreateTable() {
		return new IPSqlTable(FIELD_TABLE_BLE_SAMPLE, GetBleSampleFieldList(), null);
	}

	public static List<IPSqlField> GetBleSampleFieldList() {
		if (bleSampleFieldList == null) {
			bleSampleFieldList = new ArrayList<IPSqlField>();
			bleSampleFieldList.add(new IPSqlField(FIELD_BLE_SAMPLE_1_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(200)"), false));
			bleSampleFieldList.add(new IPSqlField(FIELD_BLE_SAMPLE_2_BUILDING_ID,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(200)"), false));
			bleSampleFieldList.add(new IPSqlField(FIELD_BLE_SAMPLE_3_LOCATION_X,
					new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
			bleSampleFieldList.add(new IPSqlField(FIELD_BLE_SAMPLE_4_LOCATION_Y,
					new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
			bleSampleFieldList.add(new IPSqlField(FIELD_BLE_SAMPLE_5_LOCATION_FLOOR,
					new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
			bleSampleFieldList.add(new IPSqlField(FIELD_BLE_SAMPLE_6_TIMESTAMP,
					new IPSqlFieldType(Double.class.getName(), "DOUBLE"), false));
			bleSampleFieldList.add(new IPSqlField(FIELD_BLE_SAMPLE_7_CONTENT,
					new IPSqlFieldType(byte[].class.getName(), "MediumBlob"), false));
			bleSampleFieldList.add(new IPSqlField(FIELD_BLE_SAMPLE_8_PLATFORM,
					new IPSqlFieldType(Integer.class.getName(), "INTEGER"), false));
			bleSampleFieldList.add(new IPSqlField(FIELD_BLE_SAMPLE_9_USER,
					new IPSqlFieldType(String.class.getName(), "VARCHAR(200)"), false));
		}
		return bleSampleFieldList;
	}

	public static List<WTBleSample> BleSampleListFromRecords(List<IPSqlRecord> records) {
		List<WTBleSample> sampleList = new ArrayList<WTBleSample>();
		for (IPSqlRecord record : records) {
			byte[] data = record.getBlob(FIELD_BLE_SAMPLE_7_CONTENT);
			try {
				BleSamplePbf pbf = BleSamplePbf.parseFrom(data);
				WTBleSample s = WTPbf2BleSampleUtils.fromBleSamplePbf(pbf);
				sampleList.add(s);
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}
		}
		return sampleList;
	}

	public static Map<String, Object> DataMapFromBleSample(WTBleSample sample) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(FIELD_BLE_SAMPLE_1_ID, sample.getSampleID());
		data.put(FIELD_BLE_SAMPLE_2_BUILDING_ID, sample.getBuildingID());
		data.put(FIELD_BLE_SAMPLE_3_LOCATION_X, sample.getLocation().getX());
		data.put(FIELD_BLE_SAMPLE_4_LOCATION_Y, sample.getLocation().getY());
		data.put(FIELD_BLE_SAMPLE_5_LOCATION_FLOOR, sample.getLocation().getFloor());
		data.put(FIELD_BLE_SAMPLE_6_TIMESTAMP, sample.getTimestamp());
		data.put(FIELD_BLE_SAMPLE_7_CONTENT, WTBleSample2PbfUtils.toBleSamplePbf(sample).toByteArray());
		data.put(FIELD_BLE_SAMPLE_8_PLATFORM, sample.getPlatform().getValue());
		data.put(FIELD_BLE_SAMPLE_9_USER, sample.getUser());
		return data;
	}
}

package cn.platalk.lab.blesample.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.platalk.common.TYIJsonFeature;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.BleSamplePbf;
import cn.platalk.lab.blesample.pbf.wrapper.WTPbf2BleSampleUtils;
import cn.platalk.lab.universaldata.entity.IPUniversalData;
import cn.platalk.map.entity.base.impl.TYLocalPoint;

public class WTBleSample implements TYIJsonFeature {
	public static final String KEY_LAB_SAMPLE_POINTS = "SamplePoints";

	static final String KEY_JSON_SAMPLE_1_ID = "sampleID";
	static final String KEY_JSON_SAMPLE_2_BUILDING_ID = "buildingID";
	static final String KEY_JSON_SAMPLE_3_X = "x";
	static final String KEY_JSON_SAMPLE_4_Y = "y";
	static final String KEY_JSON_SAMPLE_5_FLOOR = "floor";
	static final String KEY_JSON_SAMPLE_6_TIMESTAMP = "timestamp";
	static final String KEY_JSON_SAMPLE_7_PLATFORM = "platform";
	static final String KEY_JSON_SAMPLE_8_USER = "user";

	static final String KEY_JSON_SAMPLE_GPS = "gps";
	static final String KEY_JSON_SAMPLE_BLE = "ble";

	private String sampleID;
	private String buildingID;
	private double timestamp;

	private TYLocalPoint location;

	private List<WTBleSignal> bleList = new ArrayList<WTBleSignal>();
	private List<WTGpsSignal> gpsList = new ArrayList<WTGpsSignal>();

	private WTSamplePlatform platform;
	private String user;

	public WTBleSample(String sampleID, String buildingID, double timestamp) {
		this.sampleID = sampleID;
		this.buildingID = buildingID;
		this.timestamp = timestamp;
	}

	public void addBleSignal(WTBleSignal ble) {
		bleList.add(ble);
	}

	public void addGpsSignal(WTGpsSignal gps) {
		gpsList.add(gps);
	}

	public void setLocation(TYLocalPoint location) {
		this.location = location;
	}

	public void setPlatform(WTSamplePlatform platform) {
		this.platform = platform;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSampleID() {
		return sampleID;
	}

	public String getBuildingID() {
		return buildingID;
	}

	public double getTimestamp() {
		return timestamp;
	}

	public TYLocalPoint getLocation() {
		return location;
	}

	public List<WTBleSignal> getBleList() {
		return bleList;
	}

	public List<WTGpsSignal> getGpsList() {
		return gpsList;
	}

	public WTSamplePlatform getPlatform() {
		return platform;
	}

	public String getUser() {
		return user;
	}

	public static WTBleSample fromUniversalData(IPUniversalData ud) {
		WTBleSample sample = null;
		try {
			BleSamplePbf pbf = WTBleSamplePbf.BleSamplePbf.parseFrom(ud.getDataContent());
			sample = WTPbf2BleSampleUtils.fromBleSamplePbf(pbf);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return sample;
	}

	@Override
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put(KEY_JSON_SAMPLE_1_ID, getSampleID());
		json.put(KEY_JSON_SAMPLE_2_BUILDING_ID, getBuildingID());
		json.put(KEY_JSON_SAMPLE_3_X, getLocation().getX());
		json.put(KEY_JSON_SAMPLE_4_Y, getLocation().getY());
		json.put(KEY_JSON_SAMPLE_5_FLOOR, getLocation().getFloor());
		json.put(KEY_JSON_SAMPLE_6_TIMESTAMP, getTimestamp());
		json.put(KEY_JSON_SAMPLE_7_PLATFORM, getPlatform().getValue());
		json.put(KEY_JSON_SAMPLE_8_USER, getUser());
		json.put(KEY_JSON_SAMPLE_GPS, getGpsList().size());
		json.put(KEY_JSON_SAMPLE_BLE, getBleList().size());
		return json;
	}

	@Override
	public String toString() {
		return String.format("SampleID: %s\nLocation: %s\nBle: %d\nGps: %d", sampleID, location, bleList.size(),
				gpsList.size());
	}

}

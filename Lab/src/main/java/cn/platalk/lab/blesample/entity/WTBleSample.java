package cn.platalk.lab.blesample.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;

import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.BleSamplePbf;
import cn.platalk.lab.blesample.pbf.wrapper.WTPbf2BleSampleUtils;
import cn.platalk.lab.universaldata.entity.IPUniversalData;
import cn.platalk.map.entity.base.impl.TYLocalPoint;

public class WTBleSample {
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
	public String toString() {
		return String.format("SampleID: %s\nLocation: %s\nBle: %d\nGps: %d", sampleID, location, bleList.size(),
				gpsList.size());
	}

}

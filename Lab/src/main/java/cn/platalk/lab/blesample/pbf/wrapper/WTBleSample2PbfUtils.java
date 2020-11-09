package cn.platalk.lab.blesample.pbf.wrapper;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.lab.blesample.entity.WTBeacon;
import cn.platalk.lab.blesample.entity.WTBleSample;
import cn.platalk.lab.blesample.entity.WTBleSignal;
import cn.platalk.lab.blesample.entity.WTGpsSignal;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.BleBeaconPbf;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.BleSamplePbf;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.BleSignalPbf;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.GpsSignalPbf;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.LocationPbf;
import cn.platalk.map.entity.base.impl.map.TYLocalPoint;

public class WTBleSample2PbfUtils {

	static LocationPbf toLocationPbf(TYLocalPoint location) {
		LocationPbf.Builder builder = LocationPbf.newBuilder();
		builder.setX(location.getX());
		builder.setY(location.getY());
		builder.setFloor(location.getFloor());
		return builder.build();
	}

	static BleBeaconPbf toBeaconPbf(WTBeacon beacon) {
		BleBeaconPbf.Builder builder = BleBeaconPbf.newBuilder();
		builder.setUuid(beacon.getUUID());
		builder.setMajor(beacon.getMajor());
		builder.setMinor(beacon.getMinor());
		builder.setRssi(beacon.getRssi());
		builder.setAccuracy(beacon.getAccuracy());
		return builder.build();
	}

	static GpsSignalPbf toGpsSignalPbf(WTGpsSignal gps) {
		GpsSignalPbf.Builder builder = GpsSignalPbf.newBuilder();
		builder.setLng(gps.getLng());
		builder.setLat(gps.getLat());
		builder.setAccuracy(gps.getAccuracy());
		builder.setTimestamp(gps.getTimestamp());
		return builder.build();
	}

	static BleSignalPbf toBleSignalPbf(WTBleSignal ble) {
		BleSignalPbf.Builder builder = BleSignalPbf.newBuilder();

		List<BleBeaconPbf> beaconList = new ArrayList<>();
		for (int i = 0; i < ble.getBeacons().size(); ++i) {
			WTBeacon beacon = ble.getBeacons().get(i);
			beaconList.add(toBeaconPbf(beacon));
		}

		builder.addAllBeacons(beaconList);
		builder.setTimestamp(ble.getTimestamp());
		return builder.build();
	}

	public static BleSamplePbf toBleSamplePbf(WTBleSample sample) {
		BleSamplePbf.Builder builder = BleSamplePbf.newBuilder();
		builder.setSampleID(sample.getSampleID());
		builder.setBuildingID(sample.getBuildingID());
		builder.setTimestamp(sample.getTimestamp());
		builder.setLocation(toLocationPbf(sample.getLocation()));

		List<BleSignalPbf> bleList = new ArrayList<>();
		for (int i = 0; i < sample.getBleList().size(); ++i) {
			WTBleSignal ble = sample.getBleList().get(i);
			bleList.add(toBleSignalPbf(ble));
		}
		builder.addAllBleList(bleList);

		List<GpsSignalPbf> gpsList = new ArrayList<>();
		for (int i = 0; i < sample.getGpsList().size(); ++i) {
			WTGpsSignal gps = sample.getGpsList().get(i);
			gpsList.add(toGpsSignalPbf(gps));
		}
		builder.addAllGpsList(gpsList);

		builder.setPlatform(sample.getPlatform().getValue());
		builder.setUser(sample.getUser());
		return builder.build();
	}
}

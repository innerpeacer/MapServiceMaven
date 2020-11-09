package cn.platalk.lab.blesample.pbf.wrapper;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.lab.blesample.entity.WTBeacon;
import cn.platalk.lab.blesample.entity.WTBleSample;
import cn.platalk.lab.blesample.entity.WTBleSignal;
import cn.platalk.lab.blesample.entity.WTGpsSignal;
import cn.platalk.lab.blesample.entity.WTSamplePlatform;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.BleBeaconPbf;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.BleSamplePbf;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.BleSignalPbf;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.GpsSignalPbf;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.LocationPbf;
import cn.platalk.map.entity.base.impl.map.TYLocalPoint;

public class WTPbf2BleSampleUtils {

	static TYLocalPoint fromLocationPbf(LocationPbf pbf) {
		return new TYLocalPoint(pbf.getX(), pbf.getY(), pbf.getFloor());
	}

	static WTBeacon fromBeaconPbf(BleBeaconPbf pbf) {
		WTBeacon beacon = new WTBeacon(pbf.getUuid(), pbf.getMajor(), pbf.getMinor());
		beacon.setRssi(pbf.getRssi());
		beacon.setAccuracy(pbf.getAccuracy());
		return beacon;
	}

	static WTGpsSignal fromGpsSignalPbf(GpsSignalPbf pbf) {
		return new WTGpsSignal(pbf.getLng(), pbf.getLat(), pbf.getAccuracy(), pbf.getTimestamp());
	}

	static WTBleSignal fromBleSignalPbf(BleSignalPbf pbf) {
		List<WTBeacon> beacons = new ArrayList<>();
		for (int i = 0; i < pbf.getBeaconsCount(); i++) {
			beacons.add(fromBeaconPbf(pbf.getBeacons(i)));
		}
		return new WTBleSignal(beacons, pbf.getTimestamp());
	}

	public static WTBleSample fromBleSamplePbf(BleSamplePbf pbf) {
		WTBleSample sample = new WTBleSample(pbf.getSampleID(), pbf.getBuildingID(), pbf.getTimestamp());
		sample.setLocation(fromLocationPbf(pbf.getLocation()));

		for (int i = 0; i < pbf.getBleListCount(); ++i) {
			sample.addBleSignal(fromBleSignalPbf(pbf.getBleList(i)));
		}

		for (int i = 0; i < pbf.getGpsListCount(); ++i) {
			sample.addGpsSignal(fromGpsSignalPbf(pbf.getGpsList(i)));
		}

		sample.setPlatform(WTSamplePlatform.fromValue(pbf.getPlatform()));
		sample.setUser(pbf.getUser());
		return sample;
	}

}

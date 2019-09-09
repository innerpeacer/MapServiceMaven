package cn.platalk.core.pbf.beacon.wrapper;

import java.util.List;

import cn.platalk.core.pbf.beacon.TYBeaconPbf.TYLocatingBeaconListPbf;
import cn.platalk.core.pbf.beacon.TYBeaconPbf.TYLocatingBeaconPbf;
import cn.platalk.map.entity.base.impl.TYLocatingBeacon;

public class TYBeaconPbfBuilder {

	static TYLocatingBeaconPbf beaconToPbf(TYLocatingBeacon beacon) {
		TYLocatingBeaconPbf.Builder builder = TYLocatingBeaconPbf.newBuilder();
		builder.setMajor(beacon.getMajor());
		builder.setMinor(beacon.getMinor());
		builder.setX(beacon.getLocation().getX());
		builder.setY(beacon.getLocation().getY());
		builder.setFloor(beacon.getLocation().getFloor());
		return builder.build();
	}

	public static TYLocatingBeaconListPbf beaconListToPbf(List<TYLocatingBeacon> beaconList) {
		TYLocatingBeaconListPbf.Builder builder = TYLocatingBeaconListPbf.newBuilder();
		String uuid = "";
		for (TYLocatingBeacon beacon : beaconList) {
			if (uuid.length() == 0) {
				uuid = beacon.getUUID();
			}
			builder.addBeacons(beaconToPbf(beacon));
		}
		builder.setUuid(uuid);
		return builder.build();
	}
}

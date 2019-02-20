package cn.platalk.brtmap.core.pbf.beacon;

import java.util.List;

import cn.platalk.map.entity.base.impl.TYLocatingBeacon;
import innerpeacer.beacon.pbf.TYBeaconPbf.TYLocatingBeaconListPbf;
import innerpeacer.beacon.pbf.TYBeaconPbf.TYLocatingBeaconPbf;

public class TYBrtWebBeacon2PbfUtils {

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

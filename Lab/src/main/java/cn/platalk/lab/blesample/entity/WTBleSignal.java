package cn.platalk.lab.blesample.entity;

import java.util.ArrayList;
import java.util.List;

public class WTBleSignal {
	private final List<WTBeacon> beacons;
	private final double timestamp;

	public WTBleSignal(List<WTBeacon> beacons, double timestamp) {
		this.beacons = new ArrayList<>(beacons);
		this.timestamp = timestamp;
	}

	public List<WTBeacon> getBeacons() {
		return beacons;
	}

	public double getTimestamp() {
		return timestamp;
	}

}

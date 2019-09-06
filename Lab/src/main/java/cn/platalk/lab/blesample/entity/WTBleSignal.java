package cn.platalk.lab.blesample.entity;

import java.util.ArrayList;
import java.util.List;

public class WTBleSignal {
	private List<WTBeacon> beacons;
	private double timestamp;

	public WTBleSignal(List<WTBeacon> beacons, double timestamp) {
		this.beacons = new ArrayList<WTBeacon>(beacons);
		this.timestamp = timestamp;
	}

	public List<WTBeacon> getBeacons() {
		return beacons;
	}

	public double getTimestamp() {
		return timestamp;
	}

}

package cn.platalk.lab.blesample.entity;

import cn.platalk.map.entity.base.impl.beacon.TYBeacon;

public class WTBeacon extends TYBeacon {
	private int rssi;
	private double accuracy;

	public WTBeacon(String uuid, int major, int minor) {
		super(uuid, major, minor, null);
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
}

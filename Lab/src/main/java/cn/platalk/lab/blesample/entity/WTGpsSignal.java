package cn.platalk.lab.blesample.entity;

import cn.platalk.map.entity.base.impl.TYLngLat;
import cn.platalk.map.entity.base.impl.TYLocalPoint;

public class WTGpsSignal {
	private TYLngLat lngLat;
	private double accuracy;
	private double timestamp;

	public WTGpsSignal(double lng, double lat, double accuracy, double timestamp) {
		this.lngLat = new TYLngLat(lng, lat);
		this.accuracy = accuracy;
		this.timestamp = timestamp;
	}

	public double getLng() {
		return lngLat.getLng();
	}

	public double getLat() {
		return lngLat.getLat();
	}

	public TYLngLat getLngLat() {
		return lngLat;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public double getTimestamp() {
		return timestamp;
	}

	public TYLocalPoint getLocalPoint() {
		return lngLat.toLocalPoint();
	}

}

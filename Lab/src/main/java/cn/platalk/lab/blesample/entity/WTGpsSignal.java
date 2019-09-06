package cn.platalk.lab.blesample.entity;

public class WTGpsSignal {
	private double lng;
	private double lat;
	private double accuracy;
	private double timestamp;

	public WTGpsSignal(double lng, double lat, double accuracy, double timestamp) {
		this.lng = lng;
		this.lat = lat;
		this.accuracy = accuracy;
		this.timestamp = timestamp;
	}

	public double getLng() {
		return lng;
	}

	public double getLat() {
		return lat;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public double getTimestamp() {
		return timestamp;
	}

}

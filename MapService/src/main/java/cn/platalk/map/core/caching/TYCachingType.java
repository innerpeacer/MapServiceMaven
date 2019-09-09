package cn.platalk.map.core.caching;

public enum TYCachingType {
	IndoorDataPbf(1, "IndoorDataPbf"), IndoorDataGeojson(2, "IndoorDataGeojson"), BeaconDataPbf(3,
			"BeaconDataPbf"), BeaconDataGeojson(4, "BeaconDataGeojson");

	private int value;
	private String name;

	private TYCachingType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}

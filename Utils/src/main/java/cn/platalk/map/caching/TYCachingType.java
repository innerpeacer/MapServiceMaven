package cn.platalk.map.caching;

public enum TYCachingType {
	IndoorDataPbf(1, "IndoorDataPbf"), IndoorDataGeojson(2, "IndoorDataGeojson"), BeaconDataPbf(3,
			"BeaconDataPbf"), BeaconDataGeojson(4, "BeaconDataGeojson"),

	RouteManager(10, "RouteManager"), MultiRouteManager(11, "MultiRouteManager"), RouteManagerV3(12,
			"RouteManagerV3"), MultiRouteManagerV3(13, "MultiRouteManagerV3");

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

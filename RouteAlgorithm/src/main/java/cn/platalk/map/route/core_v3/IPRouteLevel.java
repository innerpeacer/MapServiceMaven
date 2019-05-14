package cn.platalk.map.route.core_v3;

public enum IPRouteLevel {
	Zero(0), One(1), Two(2), Three(3);

	private final int level;

	private IPRouteLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}

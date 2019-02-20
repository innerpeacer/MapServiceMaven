package cn.platalk.map.vectortile.builder;

final class MvtLayerParams {

	public final int tileSize;
	public final int extent;
	public final float ratio;

	public MvtLayerParams() {
		this(256, 4096);
	}

	public MvtLayerParams(int tileSize, int extent) {
		if (tileSize <= 0) {
			throw new IllegalArgumentException("tileSize must be > 0");
		}

		if (extent <= 0) {
			throw new IllegalArgumentException("extent must be > 0");
		}

		this.tileSize = tileSize;
		this.extent = extent;
		this.ratio = extent / tileSize;
	}
}

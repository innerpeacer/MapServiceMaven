package cn.platalk.map.vectortile.builder;

public interface TYVectorTileBuilderListener {
	void progressUpdated();

	void didFinishBuilding();
}

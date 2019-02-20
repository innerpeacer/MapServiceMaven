package cn.platalk.map.vectortile.builder;

public interface TYVectorTileBuilderListener {
	public void progressUpdated();

	public void didFinishBuilding();
}

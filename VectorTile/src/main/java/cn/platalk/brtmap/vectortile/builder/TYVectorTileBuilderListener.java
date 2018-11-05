package cn.platalk.brtmap.vectortile.builder;

public interface TYVectorTileBuilderListener {
	public void progressUpdated();

	public void didFinishBuilding();
}

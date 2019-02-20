package cn.platalk.map.vectortile.builder;

import cn.platalk.map.vectortile.pbf.VectorTile;

final class MvtUserDataIgnoreConverter implements MvtIUserDataConverter {
	@Override
	public void addTags(Object userData, MvtLayerProps layerProps,
			VectorTile.Tile.Feature.Builder featureBuilder) {
	}
}

package cn.platalk.brtmap.vectortile.builder;

import cn.platalk.brtmap.vectortile.pbf.VectorTile;

interface MvtIUserDataConverter {
	void addTags(Object userData, MvtLayerProps layerProps,
			VectorTile.Tile.Feature.Builder featureBuilder);
}

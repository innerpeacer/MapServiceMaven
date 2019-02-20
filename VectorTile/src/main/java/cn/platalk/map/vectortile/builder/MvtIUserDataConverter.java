package cn.platalk.map.vectortile.builder;

import cn.platalk.map.vectortile.pbf.VectorTile;

interface MvtIUserDataConverter {
	void addTags(Object userData, MvtLayerProps layerProps,
			VectorTile.Tile.Feature.Builder featureBuilder);
}

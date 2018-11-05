package cn.platalk.brtmap.vectortile.builder;

import cn.platalk.brtmap.vectortile.pbf.VectorTile;

final class MvtLayerBuild {

	public static VectorTile.Tile.Layer.Builder newLayerBuilder(
			String layerName, MvtLayerParams mvtLayerParams) {
		final VectorTile.Tile.Layer.Builder layerBuilder = VectorTile.Tile.Layer
				.newBuilder();
		layerBuilder.setVersion(2);
		layerBuilder.setName(layerName);
		layerBuilder.setExtent(mvtLayerParams.extent);

		return layerBuilder;
	}

	public static void writeProps(VectorTile.Tile.Layer.Builder layerBuilder,
			MvtLayerProps layerProps) {
		// Add keys
		layerBuilder.addAllKeys(layerProps.getKeys());

		// Add values
		final Iterable<Object> vals = layerProps.getVals();
		for (Object o : vals) {
			layerBuilder.addValues(MvtValue.toValue(o));
		}
	}
}
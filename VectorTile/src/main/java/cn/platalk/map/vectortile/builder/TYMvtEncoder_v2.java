package cn.platalk.map.vectortile.builder;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

import cn.platalk.map.vectortile.pbf.VectorTile;

class TYMvtEncoder_v2 implements TYIMvtEncoder {
	private static final int version = 2;

	private boolean isForNative = false;

	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public boolean isForNative() {
		return isForNative;
	}

	@Override
	public void setForNative(boolean forNative) {
		this.isForNative = forNative;
	}

	public VectorTile.Tile encodeBrtTile(TYGeometrySet geomSet, MvtLayerParams mvtParams, Envelope tileEnvelope,
			Envelope clipEnvelope, TYTileCoord tile) {
		final GeometryFactory geomFactory = new GeometryFactory();
		final VectorTile.Tile.Builder tileBuilder = VectorTile.Tile.newBuilder();
		final TYUserDataConverter brtUserData = new TYUserDataConverter();

		boolean isEmpty = true;
		for (int i = 0; i < TYVectorTileParams.LAYER_LIST_v2.length; ++i) {
			String layerName = TYVectorTileParams.LAYER_LIST_v2[i];

			final VectorTile.Tile.Layer.Builder layerBuilder = MvtLayerBuild.newLayerBuilder(layerName, mvtParams);
			final MvtLayerProps layerProps = new MvtLayerProps();

			List<Geometry> geomList = geomSet.getGeomList(layerName);
			// Do Filtering, Ignore Geometry Not Intersect With the Tile
			List<Geometry> filteredGeomList = new ArrayList<>();
			{
				Polygon enveloperPolygon = envelopeToPolygon(tileEnvelope);
				for (Geometry g : geomList) {
					if (g.intersects(enveloperPolygon)) {
						filteredGeomList.add(g);
						// filteredGeomList.add(g.reverse());
					}
				}
			}

			if (filteredGeomList.size() != 0) {
				isEmpty = false;
			}

			final MvtTileGeomResult bufferedTileGeom = TYJtsAdapter.createTileGeom(filteredGeomList, tileEnvelope,
					clipEnvelope, geomFactory, TYVectorTileParams.DEFAULT_MVT_PARAMS,
					TYVectorTileParams.ACCEPT_ALL_FILTER, tile);
			final List<VectorTile.Tile.Feature> features = TYJtsAdapter.toFeatures(bufferedTileGeom.mvtGeoms,
					layerProps, brtUserData);

			layerBuilder.addAllFeatures(features);
			MvtLayerBuild.writeProps(layerBuilder, layerProps);
			final VectorTile.Tile.Layer layer = layerBuilder.build();
			tileBuilder.addLayers(layer);
		}

		if (isEmpty && isForNative) {
			return null;
		}

		return tileBuilder.build();
	}

	private static Polygon envelopeToPolygon(Envelope e) {
		GeometryFactory factory = new GeometryFactory();
		Coordinate c1 = new Coordinate(e.getMinX(), e.getMinY());
		Coordinate c2 = new Coordinate(e.getMaxX(), e.getMinY());
		Coordinate c3 = new Coordinate(e.getMaxX(), e.getMaxY());
		Coordinate c4 = new Coordinate(e.getMinX(), e.getMaxY());
		return factory.createPolygon(new Coordinate[] { c1, c2, c3, c4, c1 });
	}
}

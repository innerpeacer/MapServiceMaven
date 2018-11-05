package cn.platalk.brtmap.vectortile.builder;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

import cn.platalk.brtmap.vectortile.pbf.VectorTile;

class TYBrtMvtEncoder_v2 implements TYIBrtMvtEncoder {
	private static final int version = 2;

	@Override
	public int getVersion() {
		return version;
	}

	public VectorTile.Tile encodeBrtTile(TYBrtGeometrySet geomSet, MvtLayerParams mvtParams, Envelope tileEnvelope,
			Envelope clipEnvelope, TYBrtTileCoord tile) {
		final GeometryFactory geomFactory = new GeometryFactory();
		final VectorTile.Tile.Builder tileBuilder = VectorTile.Tile.newBuilder();
		final TYBrtUserDataConverter brtUserData = new TYBrtUserDataConverter();

		for (int i = 0; i < TYBrtVectorTileParams.LAYER_LIST_v2.length; ++i) {
			String layerName = TYBrtVectorTileParams.LAYER_LIST_v2[i];

			final VectorTile.Tile.Layer.Builder layerBuilder = MvtLayerBuild.newLayerBuilder(layerName, mvtParams);
			final MvtLayerProps layerProps = new MvtLayerProps();

			List<Geometry> geomList = geomSet.getGeomList(layerName);
			// Do Filtering, Ignore Geometry Not Intersect With the Tile
			List<Geometry> filteredGeomList = new ArrayList<Geometry>();
			{
				Polygon enveloperPolygon = envelopeToPolygon(tileEnvelope);
				for (Geometry g : geomList) {
					if (g.intersects(enveloperPolygon)) {
						filteredGeomList.add(g);
						// filteredGeomList.add(g.reverse());
					}
				}
			}

			final MvtTileGeomResult bufferedTileGeom = TYBrtJtsAdapter.createTileGeom(filteredGeomList, tileEnvelope,
					clipEnvelope, geomFactory, TYBrtVectorTileParams.DEFAULT_MVT_PARAMS,
					TYBrtVectorTileParams.ACCEPT_ALL_FILTER, tile);
			final List<VectorTile.Tile.Feature> features = TYBrtJtsAdapter.toFeatures(bufferedTileGeom.mvtGeoms,
					layerProps, brtUserData);

			layerBuilder.addAllFeatures(features);
			MvtLayerBuild.writeProps(layerBuilder, layerProps);
			final VectorTile.Tile.Layer layer = layerBuilder.build();
			tileBuilder.addLayers(layer);
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

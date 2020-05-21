package cn.platalk.map.vectortile.builder;

import java.util.HashMap;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class TYGeomTransform {
	static GeometryFactory factory = new GeometryFactory();

	public static Geometry brtTransform(Geometry g, TYTileCoord tile,
			MvtLayerParams layerParams) {
		Geometry result = null;
		switch (g.getGeometryType()) {
		case "Point":
			result = transformedPoint((Point) g, tile, layerParams);
			break;

		case "MultiPoint":
			result = transformedMultiPoint((MultiPoint) g, tile, layerParams);
			break;

		case "LineString":
			result = transformedLineString((LineString) g, tile, layerParams);
			break;

		case "MultiLineString":
			result = transformedMultiLineString((MultiLineString) g, tile,
					layerParams);
			break;

		case "Polygon":
			result = transformedPolygon((Polygon) g, tile, layerParams);
			break;

		case "MultiPolygon":
			result = transformedMultiPolygon((MultiPolygon) g, tile,
					layerParams);
			break;

		default:
			System.out.println("Ignore :" + g.getGeometryType());
			break;
		}

		Map<String, Object> test = new HashMap<>();
		test.put("floor", 1);
		result.setUserData(test);
		return result;
	}

	private static Point transformedPoint(Point p, TYTileCoord tile,
			MvtLayerParams layerParams) {
		return createTransformedPoint(p, tile, layerParams);
	}

	private static MultiPoint transformedMultiPoint(MultiPoint mp,
			TYTileCoord tile, MvtLayerParams layerParams) {
		GeometryFactory factory = new GeometryFactory();
		Point[] points = new Point[mp.getNumGeometries()];
		for (int i = 0; i < mp.getNumGeometries(); ++i) {
			points[i] = createTransformedPoint((Point) mp.getGeometryN(i),
					tile, layerParams);
		}
		return factory.createMultiPoint(points);
	}

	private static LineString transformedLineString(LineString ls,
			TYTileCoord tile, MvtLayerParams layerParams) {
		return createTransformedLineString(ls, tile, layerParams);
	}

	private static MultiLineString transformedMultiLineString(
			MultiLineString mls, TYTileCoord tile, MvtLayerParams layerParams) {
		GeometryFactory factory = new GeometryFactory();
		LineString[] lss = new LineString[mls.getNumGeometries()];
		for (int i = 0; i < mls.getNumGeometries(); ++i) {
			lss[i] = createTransformedLineString(
					(LineString) mls.getGeometryN(i), tile, layerParams);
		}
		return factory.createMultiLineString(lss);
	}

	private static Polygon transformedPolygon(Polygon p, TYTileCoord tile,
			MvtLayerParams layerParams) {
		return createTransformedPolygon(p, tile, layerParams);
	}

	private static MultiPolygon transformedMultiPolygon(MultiPolygon mp,
			TYTileCoord tile, MvtLayerParams layerParams) {
		GeometryFactory factory = new GeometryFactory();
		Polygon[] ps = new Polygon[mp.getNumGeometries()];
		for (int i = 0; i < mp.getNumGeometries(); ++i) {
			ps[i] = createTransformedPolygon((Polygon) mp.getGeometryN(i),
					tile, layerParams);
		}
		return factory.createMultiPolygon(ps);
	}

	private static Polygon createTransformedPolygon(Polygon p,
			TYTileCoord tile, MvtLayerParams layerParams) {
		GeometryFactory factory = new GeometryFactory();
		LinearRing shell = createTransformedLinearRing(p.getExteriorRing(),
				tile, layerParams);

		LinearRing[] holes = new LinearRing[p.getNumInteriorRing()];
		for (int i = 0; i < p.getNumInteriorRing(); ++i) {
			holes[i] = createTransformedLinearRing(p.getInteriorRingN(i), tile,
					layerParams);
		}
		return factory.createPolygon(shell, holes);
	}

	private static LineString createTransformedLineString(LineString ls,
			TYTileCoord tile, MvtLayerParams layerParams) {
		GeometryFactory factory = new GeometryFactory();
		Coordinate[] coords = new Coordinate[ls.getNumPoints()];
		for (int i = 0; i < ls.getNumPoints(); ++i) {
			Coordinate c = ls.getCoordinateN(i);
			coords[i] = createTransformedCoordinate(c, tile, layerParams);
		}
		return factory.createLineString(coords);
	}

	private static LinearRing createTransformedLinearRing(LineString ring,
			TYTileCoord tile, MvtLayerParams layerParams) {
		GeometryFactory factory = new GeometryFactory();
		Coordinate[] coords = new Coordinate[ring.getNumPoints()];
		for (int i = 0; i < ring.getNumPoints(); ++i) {
			Coordinate c = ring.getCoordinateN(i);
			coords[i] = createTransformedCoordinate(c, tile, layerParams);
		}
		return factory.createLinearRing(coords);
	}

	private static Point createTransformedPoint(Point p, TYTileCoord tile,
			MvtLayerParams layerParams) {
		return factory.createPoint(createTransformedCoordinate(
				p.getCoordinate(), tile, layerParams));
	}

	private static Coordinate createTransformedCoordinate(Coordinate c,
			TYTileCoord tile, MvtLayerParams layerParams) {
		double xTile = TYTileCoord.lon2Tile(c.x, tile.zoom);
		double yTile = TYTileCoord.lat2Tile(c.y, tile.zoom);

		double x = (xTile - tile.x) * layerParams.extent;
		double y = (yTile - tile.y) * layerParams.extent;
		return new Coordinate(x, y);
	}
}

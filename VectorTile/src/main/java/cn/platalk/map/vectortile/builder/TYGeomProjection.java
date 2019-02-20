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

class TYGeomProjection {

	public static Geometry mercatorToLngLatGeometry(Geometry g) {
		Geometry result = null;
		switch (g.getGeometryType()) {
		case "Point":
			result = pointToPoint((Point) g);
			break;

		case "MultiPoint":
			result = multiPointToMultiPoint((MultiPoint) g);
			break;

		case "LineString":
			result = lineStringToLineString((LineString) g);
			break;

		case "MultiLineString":
			result = multiLineStringToMultiLineString((MultiLineString) g);
			break;

		case "Polygon":
			result = polygonToPolygon((Polygon) g);
			break;

		case "MultiPolygon":
			result = multiPolygonToMultiPolygon((MultiPolygon) g);
			break;

		default:
			System.out.println("Ignore :" + g.getGeometryType());
			break;
		}

		Map<String, Object> test = new HashMap<String, Object>();
		test.put("floor", 1);
		result.setUserData(test);
		return result;
	}

	private static Point pointToPoint(Point p) {
		return createProjectedPoint(p);
	}

	private static MultiPoint multiPointToMultiPoint(MultiPoint mp) {
		GeometryFactory factory = new GeometryFactory();
		Point[] points = new Point[mp.getNumGeometries()];
		for (int i = 0; i < mp.getNumGeometries(); ++i) {
			points[i] = createProjectedPoint((Point) mp.getGeometryN(i));
		}
		return factory.createMultiPoint(points);
	}

	private static LineString lineStringToLineString(LineString ls) {
		return createProjectedLineString(ls);
	}

	private static MultiLineString multiLineStringToMultiLineString(
			MultiLineString mls) {
		GeometryFactory factory = new GeometryFactory();
		LineString[] lss = new LineString[mls.getNumGeometries()];
		for (int i = 0; i < mls.getNumGeometries(); ++i) {
			lss[i] = createProjectedLineString((LineString) mls.getGeometryN(i));
		}
		return factory.createMultiLineString(lss);
	}

	private static Polygon polygonToPolygon(Polygon p) {
		return createProjectedPolygon(p);
	}

	private static MultiPolygon multiPolygonToMultiPolygon(MultiPolygon mp) {
		GeometryFactory factory = new GeometryFactory();
		Polygon[] ps = new Polygon[mp.getNumGeometries()];
		for (int i = 0; i < mp.getNumGeometries(); ++i) {
			ps[i] = createProjectedPolygon((Polygon) mp.getGeometryN(i));
		}
		return factory.createMultiPolygon(ps);
	}

	private static Polygon createProjectedPolygon(Polygon p) {
		GeometryFactory factory = new GeometryFactory();
		LinearRing shell = createProjectedLinearRing(p.getExteriorRing());

		LinearRing[] holes = new LinearRing[p.getNumInteriorRing()];
		for (int i = 0; i < p.getNumInteriorRing(); ++i) {
			holes[i] = createProjectedLinearRing(p.getInteriorRingN(i));
		}
		return factory.createPolygon(shell, holes);
	}

	private static LineString createProjectedLineString(LineString ls) {
		GeometryFactory factory = new GeometryFactory();
		Coordinate[] coords = new Coordinate[ls.getNumPoints()];
		for (int i = 0; i < ls.getNumPoints(); ++i) {
			Coordinate c = ls.getCoordinateN(i);
			double[] xy = TYCoordTool.mercatorToLngLat(c.x, c.y);
			// double[] xy = TYBrtCoordProjection2.mercatorToLngLat(c.x, c.y);

			coords[i] = new Coordinate(xy[0], xy[1]);
		}
		return factory.createLineString(coords);
	}

	private static LinearRing createProjectedLinearRing(LineString ring) {
		GeometryFactory factory = new GeometryFactory();
		Coordinate[] coords = new Coordinate[ring.getNumPoints()];
		for (int i = 0; i < ring.getNumPoints(); ++i) {
			Coordinate c = ring.getCoordinateN(i);
			double[] xy = TYCoordTool.mercatorToLngLat(c.x, c.y);
			// double[] xy = TYBrtCoordProjection2.mercatorToLngLat(c.x, c.y);
			coords[i] = new Coordinate(xy[0], xy[1]);
		}
		return factory.createLinearRing(coords);
	}

	private static Point createProjectedPoint(Point p) {
		GeometryFactory factory = new GeometryFactory();
		double[] xy = TYCoordTool.mercatorToLngLat(p.getX(), p.getY());
		// double[] xy = TYBrtCoordProjection2
		// .mercatorToLngLat(p.getX(), p.getY());
		Coordinate c = new Coordinate(xy[0], xy[1]);
		return factory.createPoint(c);
	}
}

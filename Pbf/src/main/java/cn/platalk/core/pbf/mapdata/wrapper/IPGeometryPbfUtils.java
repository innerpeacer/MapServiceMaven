package cn.platalk.core.pbf.mapdata.wrapper;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import cn.platalk.common.TYCoordProjection;
import cn.platalk.core.pbf.mapdata.TYGeometryPbf;
import cn.platalk.core.pbf.mapdata.TYGeometryPbf.TYFourDimensionCoord;
import cn.platalk.core.pbf.mapdata.TYGeometryPbf.TYGeometryType;
import cn.platalk.core.pbf.mapdata.TYGeometryPbf.TYIndoorFeatureGeometryPbf;
import cn.platalk.core.pbf.mapdata.TYGeometryPbf.TYOneDimensionCoord;
import cn.platalk.core.pbf.mapdata.TYGeometryPbf.TYThreeDimensionCoord;
import cn.platalk.core.pbf.mapdata.TYGeometryPbf.TYTwoDimensionCoord;
import cn.platalk.map.entity.base.impl.TYLngLat;
import cn.platalk.map.entity.base.impl.TYLocalPoint;

class IPGeometryPbfUtils {

	public static TYGeometryPbf.TYIndoorFeatureGeometryPbf geometryToPbf(Geometry g) {
		TYIndoorFeatureGeometryPbf pbf = null;
		switch (g.getGeometryType()) {
		case "Point":
			pbf = pointToPbf((Point) g);
			break;

		case "MultiPoint":
			pbf = multiPointToPbf((MultiPoint) g);
			break;

		case "LineString":
			pbf = lineStringToPbf((LineString) g);
			break;

		case "MultiLineString":
			pbf = multiLineStringToPbf((MultiLineString) g);
			break;

		case "Polygon":
			pbf = polygonToPbf((Polygon) g);
			break;

		case "MultiPolygon":
			pbf = multiPolygonToPbf((MultiPolygon) g);
			break;

		default:
			System.out.println("Ignore :" + g.getGeometryType());
			break;
		}
		return pbf;
	}

	static TYGeometryPbf.TYIndoorFeatureGeometryPbf pointToPbf(Point p) {
		TYGeometryPbf.TYIndoorFeatureGeometryPbf.Builder geometryBuilder = TYGeometryPbf.TYIndoorFeatureGeometryPbf
				.newBuilder();
		geometryBuilder.setGeometryType(TYGeometryType.Point);
		geometryBuilder.setPoint(coordFromXY(p.getX(), p.getY()));
		return geometryBuilder.build();
	}

	static TYGeometryPbf.TYIndoorFeatureGeometryPbf multiPointToPbf(MultiPoint mp) {
		TYGeometryPbf.TYIndoorFeatureGeometryPbf.Builder geometryBuilder = TYGeometryPbf.TYIndoorFeatureGeometryPbf
				.newBuilder();
		geometryBuilder.setGeometryType(TYGeometryType.MulitPoint);
		geometryBuilder.setMultiPoint(coordsFromMultiPoint(mp));
		return geometryBuilder.build();
	}

	static TYGeometryPbf.TYIndoorFeatureGeometryPbf lineStringToPbf(LineString ls) {
		TYGeometryPbf.TYIndoorFeatureGeometryPbf.Builder geometryBuilder = TYGeometryPbf.TYIndoorFeatureGeometryPbf
				.newBuilder();
		geometryBuilder.setGeometryType(TYGeometryType.LineString);
		geometryBuilder.setLineString(coordsFromLineString(ls));
		return geometryBuilder.build();
	}

	static TYGeometryPbf.TYIndoorFeatureGeometryPbf multiLineStringToPbf(MultiLineString ml) {
		TYGeometryPbf.TYIndoorFeatureGeometryPbf.Builder geometryBuilder = TYGeometryPbf.TYIndoorFeatureGeometryPbf
				.newBuilder();
		geometryBuilder.setGeometryType(TYGeometryType.MulitLineString);
		geometryBuilder.setMultiLineString(coordsFromMultiLineString(ml));
		return geometryBuilder.build();
	}

	static TYGeometryPbf.TYIndoorFeatureGeometryPbf polygonToPbf(Polygon p) {
		TYGeometryPbf.TYIndoorFeatureGeometryPbf.Builder geometryBuilder = TYGeometryPbf.TYIndoorFeatureGeometryPbf
				.newBuilder();
		geometryBuilder.setGeometryType(TYGeometryType.Polygon);
		geometryBuilder.setPolygon(coordsFromPolygon(p));
		return geometryBuilder.build();
	}

	static TYGeometryPbf.TYIndoorFeatureGeometryPbf multiPolygonToPbf(MultiPolygon mp) {
		TYGeometryPbf.TYIndoorFeatureGeometryPbf.Builder geometryBuilder = TYGeometryPbf.TYIndoorFeatureGeometryPbf
				.newBuilder();
		geometryBuilder.setGeometryType(TYGeometryType.MultiPolygon);
		geometryBuilder.setMultiPolygon(coordsFromMultiPolygon(mp));
		return geometryBuilder.build();
	}

	static TYOneDimensionCoord coordFromXY(double x, double y) {
		TYGeometryPbf.TYOneDimensionCoord.Builder oneDimenBuilder = TYGeometryPbf.TYOneDimensionCoord.newBuilder();
		// oneDimenBuilder.addXy(x);
		// oneDimenBuilder.addXy(y);
		TYLngLat lngLat = TYCoordProjection.mercatorToLngLat(new TYLocalPoint(x, y));
		oneDimenBuilder.addXy(lngLat.getLng());
		oneDimenBuilder.addXy(lngLat.getLat());
		return oneDimenBuilder.build();
	}

	static TYTwoDimensionCoord coordsFromMultiPoint(MultiPoint mp) {
		TYGeometryPbf.TYTwoDimensionCoord.Builder twoDimenBuilder = TYGeometryPbf.TYTwoDimensionCoord.newBuilder();
		for (int i = 0; i < mp.getLength(); i++) {
			Point p = (Point) mp.getGeometryN(i);
			twoDimenBuilder.addCoords(coordFromXY(p.getX(), p.getY()));
		}
		return twoDimenBuilder.build();
	}

	static TYTwoDimensionCoord coordsFromLineString(LineString ls) {
		TYGeometryPbf.TYTwoDimensionCoord.Builder twoDimenBuilder = TYGeometryPbf.TYTwoDimensionCoord.newBuilder();
		for (int i = 0; i < ls.getNumPoints(); ++i) {
			Point p = ls.getPointN(i);
			twoDimenBuilder.addCoords(coordFromXY(p.getX(), p.getY()));
		}
		return twoDimenBuilder.build();
	}

	static TYThreeDimensionCoord coordsFromMultiLineString(MultiLineString ml) {
		TYGeometryPbf.TYThreeDimensionCoord.Builder threeDimenBuilder = TYGeometryPbf.TYThreeDimensionCoord
				.newBuilder();
		for (int i = 0; i < ml.getNumGeometries(); ++i) {
			LineString ls = (LineString) ml.getGeometryN(i);
			threeDimenBuilder.addRings(coordsFromLineString(ls));
		}
		return threeDimenBuilder.build();
	}

	static TYThreeDimensionCoord coordsFromPolygon(Polygon polygon) {
		TYGeometryPbf.TYThreeDimensionCoord.Builder threeDimenBuilder = TYGeometryPbf.TYThreeDimensionCoord
				.newBuilder();
		threeDimenBuilder.addRings(coordsFromLineString(polygon.getExteriorRing()));
		for (int i = 0; i < polygon.getNumInteriorRing(); ++i) {
			LinearRing ls = (LinearRing) polygon.getInteriorRingN(i);
			threeDimenBuilder.addRings(coordsFromLineString(ls));
		}
		return threeDimenBuilder.build();
	}

	static TYFourDimensionCoord coordsFromMultiPolygon(MultiPolygon mp) {
		TYGeometryPbf.TYFourDimensionCoord.Builder fourDimenBuilder = TYGeometryPbf.TYFourDimensionCoord.newBuilder();
		for (int i = 0; i < mp.getNumGeometries(); i++) {
			Polygon p = (Polygon) mp.getGeometryN(i);
			fourDimenBuilder.addMultiRings(coordsFromPolygon(p));
		}
		return fourDimenBuilder.build();
	}
}

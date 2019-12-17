package cn.platalk.core.pbf.threedata.wrapper;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import cn.platalk.core.pbf.threedata.TYThreeGeometryPbf.ThreeCoordPbf;
import cn.platalk.core.pbf.threedata.TYThreeGeometryPbf.ThreeFeatureGeometryPbf;
import cn.platalk.core.pbf.threedata.TYThreeGeometryPbf.ThreeGeometryType;
import cn.platalk.core.pbf.threedata.TYThreeGeometryPbf.ThreeLineStringPbf;
import cn.platalk.core.pbf.threedata.TYThreeGeometryPbf.ThreeMultiPolygonPbf;
import cn.platalk.core.pbf.threedata.TYThreeGeometryPbf.ThreePolygonPbf;
import cn.platalk.map.entity.base.impl.TYBuilding;

public class IPThreeGeometryPbfUtils {

	private double centerX;
	private double centerY;

	public IPThreeGeometryPbfUtils(TYBuilding building) {
		centerX = building.getCenterX();
		centerY = building.getCenterY();

		if (centerX == 0 && centerY == 0) {
			centerX = (building.getBuildingExtent().getXmin() + building.getBuildingExtent().getXmax()) * 0.5;
			centerY = (building.getBuildingExtent().getYmin() + building.getBuildingExtent().getYmax()) * 0.5;
		}
	}

	public ThreeFeatureGeometryPbf geometryToPbf(Geometry g) {
		ThreeFeatureGeometryPbf pbf = null;
		switch (g.getGeometryType()) {
		case "Point":
			break;

		case "MultiPoint":
			break;

		case "LineString":
			break;

		case "MultiLineString":
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

	ThreeCoordPbf coordFromXY(double x, double y) {
		ThreeCoordPbf.Builder coordBuilder = ThreeCoordPbf.newBuilder();
		coordBuilder.setX(x - centerX);
		coordBuilder.setY(y - centerY);
		return coordBuilder.build();
	}

	ThreeLineStringPbf lineStringFromLineString(LineString ls) {
		ThreeLineStringPbf.Builder lineStringBuilder = ThreeLineStringPbf.newBuilder();
		for (int i = 0; i < ls.getNumPoints(); ++i) {
			Point p = ls.getPointN(i);
			lineStringBuilder.addCoords(coordFromXY(p.getX(), p.getY()));
		}
		return lineStringBuilder.build();
	}

	ThreePolygonPbf polygonFromPolygon(Polygon polygon) {
		ThreePolygonPbf.Builder polygonBuilder = ThreePolygonPbf.newBuilder();
		polygonBuilder.setRing(lineStringFromLineString(polygon.getExteriorRing()));
		for (int i = 0; i < polygon.getNumInteriorRing(); ++i) {
			polygonBuilder.addHoles(lineStringFromLineString(polygon.getInteriorRingN(i)));
		}
		return polygonBuilder.build();
	}

	ThreeMultiPolygonPbf multiPolygonFromMultiPolygon(MultiPolygon mp) {
		ThreeMultiPolygonPbf.Builder multiPolygonBuilder = ThreeMultiPolygonPbf.newBuilder();
		for (int i = 0; i < mp.getNumGeometries(); ++i) {
			multiPolygonBuilder.addPolygons(polygonFromPolygon((Polygon) mp.getGeometryN(i)));
		}
		return multiPolygonBuilder.build();
	}

	ThreeFeatureGeometryPbf polygonToPbf(Polygon p) {
		ThreeFeatureGeometryPbf.Builder geometryBuilder = ThreeFeatureGeometryPbf.newBuilder();
		geometryBuilder.setType(ThreeGeometryType.Polygon);
		geometryBuilder.setPolygon(polygonFromPolygon(p));
		return geometryBuilder.build();
	}

	ThreeFeatureGeometryPbf multiPolygonToPbf(MultiPolygon mp) {
		ThreeFeatureGeometryPbf.Builder geometryBuilder = ThreeFeatureGeometryPbf.newBuilder();
		geometryBuilder.setType(ThreeGeometryType.MultiPolygon);
		geometryBuilder.setMultiPolygon(multiPolygonFromMultiPolygon(mp));
		return geometryBuilder.build();
	}

}

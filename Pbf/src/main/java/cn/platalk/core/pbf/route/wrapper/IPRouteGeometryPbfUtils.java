package cn.platalk.core.pbf.route.wrapper;

import cn.platalk.core.pbf.route.TYRouteGeometryPbf;
import cn.platalk.core.pbf.route.TYRouteGeometryPbf.TYRouteCoordPbf;
import cn.platalk.core.pbf.route.TYRouteGeometryPbf.TYRouteLineStringPbf;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

public class IPRouteGeometryPbfUtils {

	public static TYRouteCoordPbf pointToPbf(Point p) {
		TYRouteCoordPbf.Builder geometryBuilder = TYRouteCoordPbf.newBuilder();
		geometryBuilder.setX(p.getX());
		geometryBuilder.setY(p.getY());
		return geometryBuilder.build();
	}

	public static TYRouteLineStringPbf lineStringToPbf(LineString ls) {
		TYRouteLineStringPbf.Builder geometryBuilder = TYRouteLineStringPbf.newBuilder();
		for (int i = 0; i < ls.getNumPoints(); ++i) {
			Point p = ls.getPointN(i);
			geometryBuilder.addCoords(pointToPbf(p));
		}
		return geometryBuilder.build();
	}
}

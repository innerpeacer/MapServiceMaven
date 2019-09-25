package cn.platalk.common;

import cn.platalk.map.entity.base.impl.TYLngLat;
import cn.platalk.map.entity.base.impl.TYLocalPoint;

public class TYCoordProjection {
	private static final double CIRCUMFERENCE_OF_EARTH = 20037508.342789;
	private static final double DEG_TO_RAD = Math.PI / 180.0;
	private static final double RAD_TO_DEG = 180.0 / Math.PI;

	public static TYLocalPoint lnglatToMercator(TYLngLat lngLat) {
		double xy[] = lngLatToMercator(lngLat.getLng(), lngLat.getLat());
		return new TYLocalPoint(xy[0], xy[1]);
	}

	public static TYLocalPoint lnglatToMercator(TYLngLat lngLat, int floor) {
		double xy[] = lngLatToMercator(lngLat.getLng(), lngLat.getLat());
		return new TYLocalPoint(xy[0], xy[1], floor);
	}

	public static TYLngLat mercatorToLngLat(TYLocalPoint localPoint) {
		double lngLat[] = mercatorToLngLat(localPoint.getX(), localPoint.getY());
		return new TYLngLat(lngLat[0], lngLat[1]);
	}

	private static double[] lngLatToMercator(double lng, double lat) {
		double x = lng * CIRCUMFERENCE_OF_EARTH / 180;
		double y = Math.log(Math.tan((90 + lat) * DEG_TO_RAD / 2)) / (DEG_TO_RAD);
		y = y * CIRCUMFERENCE_OF_EARTH / 180;
		return new double[] { x, y };
	}

	private static double[] mercatorToLngLat(double x, double y) {
		double lng = x / CIRCUMFERENCE_OF_EARTH * 180;
		double lat = y / CIRCUMFERENCE_OF_EARTH * 180;
		lat = RAD_TO_DEG * (2 * Math.atan(Math.exp(lat * DEG_TO_RAD)) - Math.PI / 2);
		return new double[] { lng, lat };
	}
}

package cn.platalk.utils.coordinate;

public class TYCoordProjection {
	private static final double CIRCUMFERENCE_OF_EARTH = 20037508.342789;
	private static final double DEG_TO_RAD = Math.PI / 180.0;
	private static final double RAD_TO_DEG = 180.0 / Math.PI;

	public static double[] lngLatToMercator(double lng, double lat) {
		double x = lng * CIRCUMFERENCE_OF_EARTH / 180;
		double y = Math.log(Math.tan((90 + lat) * DEG_TO_RAD / 2)) / (DEG_TO_RAD);
		y = y * CIRCUMFERENCE_OF_EARTH / 180;
		return new double[] { x, y };
	}

	public static double[] mercatorToLngLat(double x, double y) {
		double lng = x / CIRCUMFERENCE_OF_EARTH * 180;
		double lat = y / CIRCUMFERENCE_OF_EARTH * 180;
		lat = RAD_TO_DEG * (2 * Math.atan(Math.exp(lat * DEG_TO_RAD)) - Math.PI / 2);
		return new double[] { lng, lat };
	}
}

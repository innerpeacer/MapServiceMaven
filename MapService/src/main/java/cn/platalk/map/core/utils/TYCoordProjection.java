package cn.platalk.map.core.utils;

public class TYCoordProjection {
	private static final double DEG_TO_RAD = Math.PI / 180.0;
	private static final double RAD_TO_DEG = 180.0 / Math.PI;

	public static double[] lonLatToMercator(double lon, double lat) {
		double x = lon * 20037508.342789 / 180;
		double y = Math.log(Math.tan((90 + lat) * DEG_TO_RAD / 2)) / (DEG_TO_RAD);
		y = y * 20037508.34789 / 180;
		return new double[] { x, y };
	}

	public static double[] mercatorToLonLat(double mercatorX, double mercatorY) {
		double lon = mercatorX / 20037508.34 * 180;
		double lat = mercatorY / 20037508.34 * 180;
		lat = RAD_TO_DEG * (2 * Math.atan(Math.exp(lat * DEG_TO_RAD)) - Math.PI / 2);
		return new double[] { lon, lat };
	}
}

package cn.platalk.map.vectortile.builder;

public class TYCoordTool {
	static final boolean USE_1 = true;

	static double maxX = Double.MIN_VALUE;
	static double maxY = Double.MIN_VALUE;
	static double minX = Double.MAX_VALUE;
	static double minY = Double.MAX_VALUE;

	public static void printMinMax(double x, double y, double lng, double lat) {
		System.out.println(String.format("%.2f, %.2f => %.4f, %.4f", x, y, lng,
				lat));
	}

	public static double[] mercatorToLngLat(double mercatorX, double mercatorY) {
		double[] res = null;
		if (USE_1) {
			res = TYCoordProjection.mercatorToLngLat(mercatorX, mercatorY);
		} else {
			res = TYCoordProjection2.mercatorToLngLat(mercatorX, mercatorY);
		}

		return res;
	}
}

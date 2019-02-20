package cn.platalk.map.vectortile.builder;

class TYCoordProjection2 {
	// private static final double DEG_TO_RAD = Math.PI / 180.0;
	// private static final double RAD_TO_DEG = 180.0 / Math.PI;

	// public static double[] lngLatToMercator(double lng, double lat) {
	// double x = lng * 20037508.342789 / 180;
	// double y = Math.log(Math.tan((90 + lat) * DEG_TO_RAD / 2))
	// / (DEG_TO_RAD);
	// y = y * 20037508.34789 / 180;
	// return new double[] { x, y };
	// }

	static boolean isCustom = false;

	public static double[] mercatorToLngLat(double mercatorX, double mercatorY) {
		if (isCustom) {
			if (transform == null) {
				try {
					transform = new TYCoordTransform(mercator, lnglat);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return transform.getTransformedCoordinate(new double[] { mercatorX, mercatorY });
		}

		// double lng = mercatorX / 20037508.34 * 180;
		// double lat = mercatorY / 20037508.34 * 180;
		// lat = RAD_TO_DEG
		// * (2 * Math.atan(Math.exp(lat * DEG_TO_RAD)) - Math.PI / 2);
		double lng = WebMercator.xToLongitude(mercatorX);
		double lat = WebMercator.yToLatitude(mercatorY);

		// System.out.println("x: " + mercatorX + ", y:" + mercatorY + ", "
		// + ", lat: " + lat + ", lon: " + lng);
		return new double[] { lng, lat };
		// return new double[] { mercatorX, mercatorY };

	}

	// Just for test
	private static double[] mercator = { 1.352240769E7, 3638157.726, 1.352240769E7, 3638349.814, 1.3522669628E7,
			3638157.726 };
	private static double[] lnglat = { 121.47385718, 31.21187929, 121.47385718, 31.21336255, 121.47621274,
			31.21187929 };
	private static TYCoordTransform transform = null;

	// public static double[] lngLatToMercator(double lng, double lat) {
	// double x = lng * 20037508.342789 / 180;
	// double y = Math.log(Math.tan((90 + lat) * DEG_TO_RAD / 2))
	// / (DEG_TO_RAD);
	// y = y * 20037508.34789 / 180;
	// return new double[] { x, y };
	// }

	// public static double[] mercatorToLngLat(double mercatorX, double
	// mercatorY) {
	// if (transform == null) {
	// try {
	// transform = new TYBrtCoordTransform(mercator, lnglat);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return transform.getTransformedCoordinate(new double[] { mercatorX,
	// mercatorY });
	// }
}

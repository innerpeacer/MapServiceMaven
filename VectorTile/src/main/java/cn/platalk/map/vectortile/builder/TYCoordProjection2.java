package cn.platalk.map.vectortile.builder;

class TYCoordProjection2 {

	public static double[] mercatorToLngLat(double mercatorX, double mercatorY) {
		double lng = WebMercator.xToLongitude(mercatorX);
		double lat = WebMercator.yToLatitude(mercatorY);
		return new double[] { lng, lat };
	}
}

package cn.platalk.map.entity.base.impl;

import cn.platalk.common.TYCoordProjection;
import cn.platalk.map.entity.base.TYILngLat;

public class TYLngLat implements TYILngLat {
	private double lng;
	private double lat;

	public TYLngLat(double lng, double lat) {
		this.lng = lng;
		this.lat = lat;
	}

	@Override
	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@Override
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public TYLocalPoint toLocalPoint() {
		return toLocalPoint(0);
	}

	public TYLocalPoint toLocalPoint(int floor) {
		double xy[] = TYCoordProjection.lngLatToMercator(lng, lat);
		return new TYLocalPoint(xy[0], xy[1], floor);
	}
}

package cn.platalk.map.entity.base.impl;

import org.json.JSONArray;

import com.vividsolutions.jts.geom.Coordinate;

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
		return TYCoordProjection.lnglatToMercator(this, floor);
	}

	public JSONArray toJsonArray() {
		JSONArray json = new JSONArray();
		json.put(lng);
		json.put(lat);
		return json;
	}

	public Coordinate toCoordinate() {
		return new Coordinate(lng, lat);
	}
}

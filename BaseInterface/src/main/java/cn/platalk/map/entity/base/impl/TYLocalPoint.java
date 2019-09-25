package cn.platalk.map.entity.base.impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.common.TYCoordProjection;
import cn.platalk.common.TYIJsonFeature;
import cn.platalk.map.entity.base.TYILocalPoint;

public class TYLocalPoint implements TYILocalPoint, TYIJsonFeature {
	private static final String KEY_X = "x";
	private static final String KEY_Y = "y";
	private static final String KEY_FLOOR = "floor";

	private double x;
	private double y;
	private int floor;
	// private byte[] geometryBytes;

	public TYLocalPoint(double x, double y) {
		this.x = x;
		this.y = y;
		this.floor = 1;
	}

	public TYLocalPoint(double x, double y, int floor) {
		this.x = x;
		this.y = y;
		this.floor = floor;
	}

	public boolean equal(TYLocalPoint p) {
		if (p == null)
			return false;

		if (this.x == p.x && this.y == p.y && this.floor == p.floor) {
			return true;
		}
		return false;
	}

	// public void parseJson(JSONObject jsonObject) {
	// if (jsonObject != null) {
	// setX(jsonObject.optDouble(KEY_X));
	// setY(jsonObject.optDouble(KEY_Y));
	// setFloor(jsonObject.optInt(KEY_FLOOR));
	// }
	// }
	//

	public JSONObject buildJson() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(KEY_X, x);
			jsonObject.put(KEY_Y, y);
			jsonObject.put(KEY_FLOOR, floor);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public String toString() {
		return String.format("(%f, %f) in floor %d", x, y, floor);
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	@Override
	public JSONObject toJson() {
		return null;
	}

	public byte[] getGeometryBytes() {
		return IPXPointConverter.dataFromPoint3D(new IPXPoint3D(x, y, 0));
	}

	public double distanceWithPoint(TYLocalPoint lp) {
		if (lp == null) {
			return -1;
		}

		// if (floor != lp.getFloor()) {
		// return Double.POSITIVE_INFINITY;
		// }

		return Math.sqrt((x - lp.getX()) * (x - lp.getX()) + (y - lp.getY()) * (y - lp.getY()));
	}

	public static double distanceWithPoints(TYLocalPoint lp1, TYLocalPoint lp2) {
		if (lp1 == null) {
			return -1;
		}
		return lp1.distanceWithPoint(lp2);
	}

	public TYLngLat toLngLat() {
		double lngLat[] = TYCoordProjection.mercatorToLngLat(x, y);
		return new TYLngLat(lngLat[0], lngLat[1]);
	}
}

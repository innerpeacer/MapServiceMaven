package cn.platalk.map.entity.base.impl.map;

import cn.platalk.map.entity.base.map.TYILngLat;
import cn.platalk.map.entity.base.map.TYIMapExtent;

public class TYMapExtent implements TYIMapExtent {
	double xmin;
	double ymin;
	double xmax;
	double ymax;

	public TYMapExtent(double xmin, double ymin, double xmax, double ymax) {
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
	}

	public void extendWith(TYIMapExtent e) {
		xmin = Math.min(xmin, e.getXmin());
		ymin = Math.min(ymin, e.getYmin());
		xmax = Math.max(xmax, e.getXmax());
		ymax = Math.max(ymax, e.getYmax());
	}

	public TYMapExtent copy() {
		return new TYMapExtent(xmin, ymin, xmax, ymax);
	}

	public static TYMapExtent copyFrom(TYIMapExtent extent) {
		return new TYMapExtent(extent.getXmin(), extent.getYmin(), extent.getXmax(), extent.getYmax());
	}

	@Override
	public double getXmin() {
		return xmin;
	}

	@Override
	public double getYmin() {
		return ymin;
	}

	@Override
	public double getXmax() {
		return xmax;
	}

	@Override
	public double getYmax() {
		return ymax;
	}

	public TYLocalPoint getMin() {
		return new TYLocalPoint(xmin, ymin);
	}

	public TYLocalPoint getMax() {
		return new TYLocalPoint(xmax, ymax);
	}

	@Override
	public TYILngLat getSw() {
		return new TYLocalPoint(xmin, ymin).toLngLat();
	}

	@Override
	public TYILngLat getNe() {
		return new TYLocalPoint(xmax, ymax).toLngLat();
	}
}

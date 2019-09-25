package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYILngLat;
import cn.platalk.map.entity.base.TYIMapExtent;

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

	public TYILngLat getSw() {
		return new TYLocalPoint(xmin, ymin).toLngLat();
	}

	public TYILngLat getNe() {
		return new TYLocalPoint(xmax, ymax).toLngLat();
	}
}

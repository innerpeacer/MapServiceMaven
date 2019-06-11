package cn.platalk.map.entity.base.impl;

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

	public double getXmin() {
		return xmin;
	}

	public double getYmin() {
		return ymin;
	}

	public double getXmax() {
		return xmax;
	}

	public double getYmax() {
		return ymax;
	}
}

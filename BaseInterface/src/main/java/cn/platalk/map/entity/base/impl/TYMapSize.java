package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYIMapSize;

public class TYMapSize implements TYIMapSize {
	public double x;
	public double y;

	public TYMapSize(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
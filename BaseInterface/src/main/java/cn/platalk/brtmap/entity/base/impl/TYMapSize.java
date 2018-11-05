package cn.platalk.brtmap.entity.base.impl;

import cn.platalk.brtmap.entity.base.TYIMapSize;

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
package cn.platalk.map.entity.base.impl.map;

import cn.platalk.map.entity.base.map.TYIMapSize;

public class TYMapSize implements TYIMapSize {
	public final double x;
	public final double y;

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
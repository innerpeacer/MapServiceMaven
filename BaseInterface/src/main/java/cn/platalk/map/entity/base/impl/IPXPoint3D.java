package cn.platalk.map.entity.base.impl;

public class IPXPoint3D {

	final double x;
	final double y;
	final double z;

	public IPXPoint3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "IPXPoint3D [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

}

package cn.platalk.map.entity.base;

public interface TYIMapExtent {
	public double getXmin();

	public double getYmin();

	public double getXmax();

	public double getYmax();

	public TYILngLat getSw();

	public TYILngLat getNe();
}

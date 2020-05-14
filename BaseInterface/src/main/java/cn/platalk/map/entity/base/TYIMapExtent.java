package cn.platalk.map.entity.base;

public interface TYIMapExtent {
	double getXmin();

	double getYmin();

	double getXmax();

	double getYmax();

	TYILngLat getSw();

	TYILngLat getNe();
}

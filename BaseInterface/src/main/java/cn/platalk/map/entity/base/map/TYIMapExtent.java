package cn.platalk.map.entity.base.map;

public interface TYIMapExtent {
	double getXmin();

	double getYmin();

	double getXmax();

	double getYmax();

	TYILngLat getSw();

	TYILngLat getNe();
}

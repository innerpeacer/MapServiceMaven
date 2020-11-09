package cn.platalk.map.entity.base.map;

public interface TYIRouteNodeRecord {
	int getNodeID();

	byte[] getGeometryData();

	boolean isVirtual();
}

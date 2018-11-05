package cn.platalk.brtmap.entity.base;

public interface TYIRouteNodeRecord {
	public int getNodeID();

	public byte[] getGeometryData();

	public boolean isVirtual();
}

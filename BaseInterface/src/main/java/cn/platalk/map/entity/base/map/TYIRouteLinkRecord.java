package cn.platalk.map.entity.base.map;

public interface TYIRouteLinkRecord {
	int getLinkID();

	byte[] getGeometryData();

	double getLength();

	int getHeadNode();

	int getEndNode();

	boolean isVirtual();

	boolean isOneWay();
}

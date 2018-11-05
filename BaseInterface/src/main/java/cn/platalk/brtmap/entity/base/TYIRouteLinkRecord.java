package cn.platalk.brtmap.entity.base;

public interface TYIRouteLinkRecord {
	public int getLinkID();

	public byte[] getGeometryData();

	public double getLength();

	public int getHeadNode();

	public int getEndNode();

	public boolean isVirtual();

	public boolean isOneWay();
}

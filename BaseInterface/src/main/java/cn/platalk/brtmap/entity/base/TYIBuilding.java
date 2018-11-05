package cn.platalk.brtmap.entity.base;


public interface TYIBuilding {
	public String getCityID();

	public String getBuildingID();

	public String getName();

	public String getAddress();

	public double getLongitude();

	public double getLatitude();

	public double getInitAngle();

	public String getRouteURL();

	public TYIMapSize getOffset();

	public int getStatus();
}

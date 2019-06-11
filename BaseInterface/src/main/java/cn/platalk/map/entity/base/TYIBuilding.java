package cn.platalk.map.entity.base;

public interface TYIBuilding {
	public String getCityID();

	public String getBuildingID();

	public String getName();

	public String getAddress();

	public double getLongitude();

	public double getLatitude();

	public double getInitAngle();

	public int getInitFloorIndex();

	public String getRouteURL();

	public TYIMapSize getOffset();

	public TYIMapExtent getBuildingExtent();

	public int getStatus();
}

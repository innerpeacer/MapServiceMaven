package cn.platalk.brtmap.entity.base;

public interface TYILocatingBeacon extends TYIBeacon {
	public String getRoomID();

	public TYLocalPoint getLocation();

	public String getMapID();

	public String getBuildingID();

	public String getCityID();
}

package cn.platalk.map.entity.base.beacon;

public interface TYIBeacon {
	String getUUID();

	int getMajor();

	int getMinor();

	String getTag();

    Long getBeaconKey();
}

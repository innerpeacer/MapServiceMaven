package cn.platalk.core.beacon.shp;

import java.io.File;

import cn.platalk.core.beacon.base.TYIBeaconShpDataManager;

public class TYBeaconShpDataManager implements TYIBeaconShpDataManager {
	private static final String BEACON_SHP_DIR = "%s_beacon";
	private static final String BEACON_SHP_PATH = "beacon.shp";

	private final String root;

	public TYBeaconShpDataManager(String root) {
		this.root = root;
	}

	public String getBeaconShpPath(String buildingID) {
		String beaconDir = getBeaconShpDir(buildingID);
		return new File(beaconDir, BEACON_SHP_PATH).toString();

	}

	public String getBeaconShpDir(String buildingID) {
		return new File(root, String.format(BEACON_SHP_DIR, buildingID)).toString();
	}
}

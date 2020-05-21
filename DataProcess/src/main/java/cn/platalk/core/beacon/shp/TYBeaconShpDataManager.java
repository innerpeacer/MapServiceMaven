package cn.platalk.core.beacon.shp;

import java.io.File;

import cn.platalk.core.beacon.base.TYIBeaconShpDataManager;

public class TYBeaconShpDataManager implements TYIBeaconShpDataManager {
	private static final String BEACON_SHP_DIR = "beacon";
	private static final String BEACON_SHP_PATH = "beacon.shp";

	private final String root;

	public TYBeaconShpDataManager(String root) {
		this.root = root;
	}

	public String getBeaconShpPath() {
		String beaconDir = getBeaconShpDir();
		return new File(beaconDir, BEACON_SHP_PATH).toString();

	}

	public String getBeaconShpDir() {
		return new File(root, BEACON_SHP_DIR).toString();
	}
}

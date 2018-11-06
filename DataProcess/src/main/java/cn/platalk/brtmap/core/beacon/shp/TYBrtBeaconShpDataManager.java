package cn.platalk.brtmap.core.beacon.shp;

import java.io.File;

import cn.platalk.brtmap.core.beacon.base.TYIBrtBeaconShpDataManager;

public class TYBrtBeaconShpDataManager implements TYIBrtBeaconShpDataManager {
	private static final String BEACON_SHP_DIR = "beacon";
	private static final String BEACON_SHP_PATH = "beacon.shp";

	private String root;

	public TYBrtBeaconShpDataManager(String root) {
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

package cn.platalk.lab.locationengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.lab.blesample.entity.WTBeacon;
import cn.platalk.map.entity.base.impl.TYLocalPoint;
import cn.platalk.map.entity.base.impl.TYLocatingBeacon;
import cn.platalk.mysql.beacon.TYBeaconDBAdapter;

public class TYBleLocator {
	private static final int NUMBER_OF_BEACONS = 15;

	List<TYLocatingBeacon> locatingBeaconArray = new ArrayList<TYLocatingBeacon>();
	Map<Long, TYLocatingBeacon> locatingBeaconMap = new HashMap<Long, TYLocatingBeacon>();

	public TYBleLocator(String buildingID) {
		TYBeaconDBAdapter beaconDB = new TYBeaconDBAdapter(buildingID);
		beaconDB.connectDB();
		beaconDB.createTableIfNotExist();
		List<TYLocatingBeacon> beacons = beaconDB.getAllBeacons();
		locatingBeaconArray.addAll(beacons);
		for (TYLocatingBeacon pb : locatingBeaconArray) {
			locatingBeaconMap.put(pb.getBeaconKey(), pb);
		}
		beaconDB.disconnectDB();
	}

	public Map<String, Object> calculateLocations(List<WTBeacon> beacons) {
		if (beacons == null || beacons.size() == 0) {
			return null;
		}

		List<WTBeacon> scannedBeacons = preprocessBeacons(beacons);
		if (scannedBeacons.size() == 0)
			return null;

		double totalWeighting = 0;
		double totalWeightingX = 0;
		double totalWeightingY = 0;

		int index = Math.min(NUMBER_OF_BEACONS, scannedBeacons.size());
		double minAccuracy = Double.MAX_VALUE;
		int maxRssi = -100;
		int minAccuracyfloor = 0;

		for (int i = 0; i < index; ++i) {
			WTBeacon b = scannedBeacons.get(i);
			Long beaconKey = b.getBeaconKey();
			double accuracy = b.getAccuracy();
			maxRssi = Math.max(maxRssi, b.getRssi());
			TYLocatingBeacon pb = locatingBeaconMap.get(beaconKey);
			TYLocalPoint location = pb.getLocation();

			if (accuracy > 0 && accuracy < minAccuracy) {
				minAccuracy = accuracy;
				minAccuracyfloor = location.getFloor();
			}

			double weighting = 1.0 / (accuracy * accuracy);
			// System.out.println("w: " + weighting);
			totalWeighting += weighting;
			totalWeightingX += location.getX() * weighting;
			totalWeightingY += location.getY() * weighting;
		}

		if (totalWeighting == 0) {
			return null;
		}
		TYLocalPoint location = new TYLocalPoint(totalWeightingX / totalWeighting, totalWeightingY / totalWeighting,
				minAccuracyfloor);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("location", location);
		result.put("minAccuracy", minAccuracy);
		result.put("maxRssi", maxRssi);
		result.put("maxIndex", index);
		result.put("totalWeighting", totalWeighting);
		return result;
	}

	private List<WTBeacon> preprocessBeacons(List<WTBeacon> beacons) {
		List<WTBeacon> scannedBeacons = new ArrayList<WTBeacon>();
		for (int i = 0; i < beacons.size(); ++i) {
			WTBeacon b = beacons.get(i);
			if (b.getRssi() <= -20 && locatingBeaconMap.containsKey(b.getBeaconKey())) {
				scannedBeacons.add(b);
			}
		}
		Collections.sort(scannedBeacons, BEACON_RSSI_COMPARATOR);
		return scannedBeacons;
	}

	static final Comparator<WTBeacon> BEACON_RSSI_COMPARATOR = new Comparator<WTBeacon>() {
		@Override
		public int compare(WTBeacon lb, WTBeacon rb) {
			return Double.compare(rb.getRssi(), lb.getRssi());
		}
	};

	static final Comparator<WTBeacon> BEACON_ACCURACY_COMPARATOR = new Comparator<WTBeacon>() {
		@Override
		public int compare(WTBeacon lb, WTBeacon rb) {
			return Double.compare(lb.getAccuracy(), rb.getAccuracy());
		}
	};

}

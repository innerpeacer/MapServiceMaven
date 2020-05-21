package cn.platalk.lab.analysis.blesample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.lab.analysis.blesample.TYAnalysisEntity.EntityType;
import cn.platalk.lab.blesample.entity.WTBleSample;
import cn.platalk.lab.blesample.entity.WTBleSignal;
import cn.platalk.lab.blesample.entity.WTGpsSignal;
import cn.platalk.lab.locationengine.TYBleLocator;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYLocalPoint;
import cn.platalk.map.entity.base.impl.TYWtWgs84Converter;

public class TYBleSampleAnalysis {
	public static final String KEY_GPS_ERROR = "gpsError";
	public static final String KEY_BLE_ERROR = "bleError";

	public static Map<String, Object> process(TYBuilding building, TYBleLocator locator, WTBleSample sample) {
		// System.out.println("process");
		Map<String, Object> result = new HashMap<>();
		List<TYAnalysisEntity> gpsErrorList = new ArrayList<>();
		List<TYAnalysisEntity> bleErrorList = new ArrayList<>();

		TYWtWgs84Converter converter = new TYWtWgs84Converter(building.getWtCalibrationPoint(),
				building.getWgs84CalibrationPoint());

		TYLocalPoint samplePoint = sample.getLocation();

		// System.out.println("************ gps ************");
		// System.out.println("accuracy -> gpsError");
		List<WTGpsSignal> gpsList = sample.getGpsList();
		for (WTGpsSignal gps : gpsList) {
			TYLocalPoint gpsPoint = converter.converterToMercator(gps.getLngLat());
			double gpsError = samplePoint.distanceWithPoint(gpsPoint);
			gpsErrorList.add(new TYAnalysisEntity(EntityType.GPS, gpsError, gps.getAccuracy()));
			// System.out.println(String.format("%.2f --> %.2f",
			// gps.getAccuracy(), gpsError));
		}

		// System.out.println("************ ble ************");
		// System.out.println("minAccuracy -> bleError");
		List<WTBleSignal> bleList = sample.getBleList();
		for (WTBleSignal ble : bleList) {
			Map<String, Object> bleResult = locator.calculateLocations(ble.getBeacons());
			if (bleResult == null) {
				continue;
			}
			TYLocalPoint blePoint = (TYLocalPoint) bleResult.get("location");
			double minAccuracy = (double) bleResult.get("minAccuracy");
			double bleError = samplePoint.distanceWithPoint(blePoint);
			bleErrorList.add(new TYAnalysisEntity(EntityType.BLE, bleError, minAccuracy));
			// System.out.println(String.format("%.2f --> %.2f", minAccuracy,
			// bleError));
		}

		result.put(KEY_GPS_ERROR, gpsErrorList);
		result.put(KEY_BLE_ERROR, bleErrorList);
		return result;
	}
}

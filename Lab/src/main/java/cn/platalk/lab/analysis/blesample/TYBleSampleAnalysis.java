package cn.platalk.lab.analysis.blesample;

import java.util.List;
import java.util.Map;

import cn.platalk.lab.blesample.entity.WTBleSample;
import cn.platalk.lab.blesample.entity.WTBleSignal;
import cn.platalk.lab.blesample.entity.WTGpsSignal;
import cn.platalk.lab.locationengine.TYBleLocator;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYLocalPoint;
import cn.platalk.map.entity.base.impl.TYWtWgs84Converter;

public class TYBleSampleAnalysis {

	public static void process(TYBuilding building, TYBleLocator locator, WTBleSample sample) {
		System.out.println("process");

		TYWtWgs84Converter converter = new TYWtWgs84Converter(building.getWtCalibrationPoint(),
				building.getWgs84CalibrationPoint());

		TYLocalPoint samplePoint = sample.getLocation();

		System.out.println("************ gps ************");
		List<WTGpsSignal> gpsList = sample.getGpsList();
		for (int i = 0; i < gpsList.size(); ++i) {
			WTGpsSignal gps = gpsList.get(i);
			TYLocalPoint gpsPoint = converter.converterToMercator(gps.getLngLat());
			double gpsError = samplePoint.distanceWithPoint(gpsPoint);
			System.out.println(String.format("%.2f --> %.2f", gps.getAccuracy(), gpsError));
		}

		System.out.println("************ ble ************");
		List<WTBleSignal> bleList = sample.getBleList();
		for (int i = 0; i < bleList.size(); ++i) {
			if (i != 14) {
				// continue;
			}
			WTBleSignal ble = bleList.get(i);
			Map<String, Object> bleResult = locator.calculateLocations(ble.getBeacons());
			TYLocalPoint blePoint = (TYLocalPoint) bleResult.get("location");
			double minAccuracy = (double) bleResult.get("minAccuracy");
			double bleError = samplePoint.distanceWithPoint(blePoint);
			System.out.println(String.format("Index %d: %.2f --> %.2f", i, minAccuracy, bleError));
			// System.out.println(bleResult);

			// break;
		}
	}
}

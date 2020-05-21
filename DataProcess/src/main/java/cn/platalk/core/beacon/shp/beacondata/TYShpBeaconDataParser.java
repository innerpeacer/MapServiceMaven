package cn.platalk.core.beacon.shp.beacondata;

import java.util.ArrayList;
import java.util.List;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;

import cn.platalk.core.beacon.shp.TYBeaconShpHelper;
import cn.platalk.map.entity.base.TYILocatingBeacon;
import cn.platalk.map.entity.base.impl.TYLocatingBeacon;

public class TYShpBeaconDataParser {
	final String shpPath;

	public TYShpBeaconDataParser(String path) {
		this.shpPath = path;
	}

	public void parse() {
		// System.out.println("TYMapShpParser.parse");
		// System.out.println(shpPath);
		ogr.RegisterAll();
		gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
		gdal.SetConfigOption("SHAPE_ENCODING", "");

		DataSource ds = ogr.Open(shpPath, 0);
		if (ds == null) {
			Throwable error = new Throwable("Failed To Open File: " + shpPath);
			notifyFailedParsingBeaconDataList(error);
			return;
		}

		List<TYILocatingBeacon> beacons = new ArrayList<>();
		Layer featureLayer = ds.GetLayer(0);
		for (int i = 0; i < featureLayer.GetFeatureCount(); ++i) {
			TYLocatingBeacon beacon = TYBeaconShpHelper.locationBeaconFromRecord(featureLayer.GetFeature(i));
			beacons.add(beacon);
		}
		notifyFinishParsingBeaconDataList(beacons);
	}

	private void notifyFinishParsingBeaconDataList(List<TYILocatingBeacon> beaconList) {
		for (TYBrtShpBeaconParserListener listener : listeners) {
			listener.didFinishParsingBeaconDataList(beaconList);
		}
	}

	private void notifyFailedParsingBeaconDataList(Throwable error) {
		for (TYBrtShpBeaconParserListener listener : listeners) {
			listener.didFailedParsingBeaconDataList(error);
		}
	}

	private final List<TYBrtShpBeaconParserListener> listeners = new ArrayList<>();

	public void addParserListener(TYBrtShpBeaconParserListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeParserListener(TYBrtShpBeaconParserListener listener) {
		listeners.remove(listener);
	}

	public interface TYBrtShpBeaconParserListener {
		void didFinishParsingBeaconDataList(List<TYILocatingBeacon> beaconList);

		void didFailedParsingBeaconDataList(Throwable error);
	}
}

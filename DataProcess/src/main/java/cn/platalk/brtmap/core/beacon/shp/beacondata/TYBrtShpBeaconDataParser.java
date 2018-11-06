package cn.platalk.brtmap.core.beacon.shp.beacondata;

import java.util.ArrayList;
import java.util.List;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;

import cn.platalk.brtmap.core.beacon.shp.TYBrtBeaconShpHelper;
import cn.platalk.brtmap.entity.base.TYILocatingBeacon;
import cn.platalk.brtmap.entity.base.impl.TYLocatingBeacon;

public class TYBrtShpBeaconDataParser {
	String shpPath;

	public TYBrtShpBeaconDataParser(String path) {
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
		Layer featurelayer = ds.GetLayer(0);
		for (int i = 0; i < featurelayer.GetFeatureCount(); ++i) {
			TYLocatingBeacon beacon = TYBrtBeaconShpHelper.locationBeaconFromRecord(featurelayer.GetFeature(i));
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

	private List<TYBrtShpBeaconParserListener> listeners = new ArrayList<>();

	public void addParserListener(TYBrtShpBeaconParserListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeParserListener(TYBrtShpBeaconParserListener listener) {
		listeners.remove(listener);
	}

	public interface TYBrtShpBeaconParserListener {
		public void didFinishParsingBeaconDataList(List<TYILocatingBeacon> beaconList);

		public void didFailedParsingBeaconDataList(Throwable error);
	}
}

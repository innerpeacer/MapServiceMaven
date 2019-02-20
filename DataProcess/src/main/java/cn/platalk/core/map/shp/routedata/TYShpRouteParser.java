package cn.platalk.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.List;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;

import cn.platalk.core.map.shp.TYShpHelper;

public class TYShpRouteParser {
	String shpPath;
	private int layerIndex;

	public TYShpRouteParser(String path) {
		this.shpPath = path;
	}

	public void setLayerIndex(int index) {
		this.layerIndex = index;
	}

	public void parse() {
		// System.out.println("TYRoutShpParser.parse");

		ogr.RegisterAll();
		gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
		gdal.SetConfigOption("SHAPE_ENCODING", "CP936");
		//
		DataSource ds = ogr.Open(shpPath, 0);
		if (ds == null) {
			Throwable error = new Throwable("Failed To Open File: " + shpPath);
			notifyFailedParsingRouteDataList(error);
			return;
		}

		List<TYShpRouteRecord> recordList = new ArrayList<TYShpRouteRecord>();

		Layer featurelayer = ds.GetLayer(0);
		for (int i = 0; i < featurelayer.GetFeatureCount(); ++i) {
			TYShpRouteRecord record = TYShpHelper.routeRecordFromFeature(featurelayer.GetFeature(i), i);
			recordList.add(record);
		}

		// System.out.println("Notify");
		notifyFinishParsingRouteShpDataList(recordList, layerIndex);
	}

	private void notifyFinishParsingRouteShpDataList(List<TYShpRouteRecord> records, int layer) {
		for (TYBrtRouteShpParserListener listener : listeners) {
			listener.didFinishParsingRouteShpDataList(records, layer);
		}
	}

	private void notifyFailedParsingRouteDataList(Throwable error) {
		for (TYBrtRouteShpParserListener listener : listeners) {
			listener.didFailedParsingRouteDataList(error);
		}
	}

	private List<TYBrtRouteShpParserListener> listeners = new ArrayList<>();

	public void addParserListener(TYBrtRouteShpParserListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeParserListener(TYBrtRouteShpParserListener listener) {
		listeners.remove(listener);
	}

	public interface TYBrtRouteShpParserListener {
		public void didFinishParsingRouteShpDataList(List<TYShpRouteRecord> records, int layer);

		public void didFailedParsingRouteDataList(Throwable error);
	}

}

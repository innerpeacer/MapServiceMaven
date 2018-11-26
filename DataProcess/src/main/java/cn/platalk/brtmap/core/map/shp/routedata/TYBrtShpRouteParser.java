package cn.platalk.brtmap.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.List;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;

import cn.platalk.brtmap.core.map.shp.TYBrtShpHelper;

public class TYBrtShpRouteParser {
	String shpPath;
	private int layerIndex;

	public TYBrtShpRouteParser(String path) {
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

		List<TYBrtShpRouteRecord> recordList = new ArrayList<TYBrtShpRouteRecord>();

		Layer featurelayer = ds.GetLayer(0);
		for (int i = 0; i < featurelayer.GetFeatureCount(); ++i) {
			TYBrtShpRouteRecord record = TYBrtShpHelper.routeRecordFromFeature(featurelayer.GetFeature(i), i);
			recordList.add(record);
		}

		// System.out.println("Notify");
		notifyFinishParsingRouteShpDataList(recordList, layerIndex);
	}

	private void notifyFinishParsingRouteShpDataList(List<TYBrtShpRouteRecord> records, int layer) {
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
		public void didFinishParsingRouteShpDataList(List<TYBrtShpRouteRecord> records, int layer);

		public void didFailedParsingRouteDataList(Throwable error);
	}

}

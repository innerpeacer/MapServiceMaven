package cn.platalk.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.List;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;

import cn.platalk.core.map.shp.TYShpHelperV3;

public class TYShpRouteParserV3 {
	final String shpPath;
	final int shpType; // 0 for Link, 1 for Node
	private int layerIndex;

	public TYShpRouteParserV3(String path, int type) {
		this.shpPath = path;
		this.shpType = type;
	}

	public void setLayerIndex(int index) {
		this.layerIndex = index;
	}

	public void parse() {
		// System.out.println("TYRoutShpParserV3.parse");
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

		List<TYShpRouteRecordV3> recordList = new ArrayList<>();

		Layer featureLayer = ds.GetLayer(0);
		for (int i = 0; i < featureLayer.GetFeatureCount(); ++i) {
			TYShpRouteRecordV3 record = TYShpHelperV3.routeRecordFromFeature(featureLayer.GetFeature(i), shpType);
			recordList.add(record);
		}

		// System.out.println("Notify");
		notifyFinishParsingRouteShpDataList(recordList, layerIndex);
	}

	private void notifyFinishParsingRouteShpDataList(List<TYShpRouteRecordV3> records, int layer) {
		for (TYBrtRouteShpParserListenerV3 listener : listeners) {
			listener.didFinishParsingRouteShpDataList(records, layer);
		}
	}

	private void notifyFailedParsingRouteDataList(Throwable error) {
		for (TYBrtRouteShpParserListenerV3 listener : listeners) {
			listener.didFailedParsingRouteDataList(error);
		}
	}

	private final List<TYBrtRouteShpParserListenerV3> listeners = new ArrayList<>();

	public void addParserListener(TYBrtRouteShpParserListenerV3 listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeParserListener(TYBrtRouteShpParserListenerV3 listener) {
		listeners.remove(listener);
	}

	public interface TYBrtRouteShpParserListenerV3 {
		void didFinishParsingRouteShpDataList(List<TYShpRouteRecordV3> records, int layer);

		void didFailedParsingRouteDataList(Throwable error);
	}
}

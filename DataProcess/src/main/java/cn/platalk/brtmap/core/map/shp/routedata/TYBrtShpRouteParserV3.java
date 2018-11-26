package cn.platalk.brtmap.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.List;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;

import cn.platalk.brtmap.core.map.shp.TYBrtShpHelperV3;

public class TYBrtShpRouteParserV3 {
	String shpPath;
	int shpType; // 0 for Link, 1 for Node
	private int layerIndex;

	public TYBrtShpRouteParserV3(String path, int type) {
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

		List<TYBrtShpRouteRecordV3> recordList = new ArrayList<TYBrtShpRouteRecordV3>();

		Layer featurelayer = ds.GetLayer(0);
		for (int i = 0; i < featurelayer.GetFeatureCount(); ++i) {
			TYBrtShpRouteRecordV3 record = TYBrtShpHelperV3.routeRecordFromFeature(featurelayer.GetFeature(i), shpType);
			recordList.add(record);
		}

		// System.out.println("Notify");
		notifyFinishParsingRouteShpDataList(recordList, layerIndex);
	}

	private void notifyFinishParsingRouteShpDataList(List<TYBrtShpRouteRecordV3> records, int layer) {
		for (TYBrtRouteShpParserListenerV3 listener : listeners) {
			listener.didFinishParsingRouteShpDataList(records, layer);
		}
	}

	private void notifyFailedParsingRouteDataList(Throwable error) {
		for (TYBrtRouteShpParserListenerV3 listener : listeners) {
			listener.didFailedParsingRouteDataList(error);
		}
	}

	private List<TYBrtRouteShpParserListenerV3> listeners = new ArrayList<>();

	public void addParserListener(TYBrtRouteShpParserListenerV3 listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeParserListener(TYBrtRouteShpParserListenerV3 listener) {
		listeners.remove(listener);
	}

	public interface TYBrtRouteShpParserListenerV3 {
		public void didFinishParsingRouteShpDataList(List<TYBrtShpRouteRecordV3> records, int layer);

		public void didFailedParsingRouteDataList(Throwable error);
	}
}

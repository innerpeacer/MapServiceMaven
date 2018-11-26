package cn.platalk.brtmap.core.map.shp.mapdata;

import java.util.ArrayList;
import java.util.List;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;

import cn.platalk.brtmap.core.map.shp.TYBrtShpHelper;
import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;

public class TYBrtShpMapDataParser {
	String shpPath;
	private int layer;

	public TYBrtShpMapDataParser(String path, int index) {
		this.shpPath = path;
		this.layer = getLayerForLayerIndex(index);
	}

	public void parse() {
		// System.out.println("TYMapShpParser.parse");
		// System.out.println(shpPath);
		ogr.RegisterAll();
		gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
		gdal.SetConfigOption("SHAPE_ENCODING", "CP936");

		DataSource ds = ogr.Open(shpPath, 0);
		if (ds == null) {
			Throwable error = new Throwable("Failed To Open File: " + shpPath);
			notifyFailedParsingMapDataList(error);
			return;
		}

		List<TYMapDataFeatureRecord> records = new ArrayList<>();

		Layer featurelayer = ds.GetLayer(0);
		for (int i = 0; i < featurelayer.GetFeatureCount(); ++i) {
			TYMapDataFeatureRecord record = TYBrtShpHelper.mapRecordFromFeature(featurelayer.GetFeature(i), i);
			record.layer = layer;
			records.add(record);
		}
		notifyFinishParsingMapDataList(records);
	}

	private int getLayerForLayerIndex(int index) {
		switch (index) {
		case 0:
			// FLOOR
			return 1;
		case 1:
			// ROOM
			return 2;

		case 2:
			// ASSET
			return 3;

		case 3:
			// FACILITY
			return 4;

		case 4:
			// LABEL
			return 5;

		case 5:
			// SHADE
			return 6;

		default:
			return 0;
		}
	}

	private void notifyFinishParsingMapDataList(List<TYMapDataFeatureRecord> recordList) {
		for (TYBrtMapShpParserListener listener : listeners) {
			listener.didFinishParsingMapDataList(recordList);
		}
	}

	private void notifyFailedParsingMapDataList(Throwable error) {
		for (TYBrtMapShpParserListener listener : listeners) {
			listener.didFailedParsingMapDataList(error);
		}
	}

	private List<TYBrtMapShpParserListener> listeners = new ArrayList<>();

	public void addParserListener(TYBrtMapShpParserListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeParserListener(TYBrtMapShpParserListener listener) {
		listeners.remove(listener);
	}

	public interface TYBrtMapShpParserListener {
		public void didFinishParsingMapDataList(List<TYMapDataFeatureRecord> recordList);

		public void didFailedParsingMapDataList(Throwable error);
	}

}

package cn.platalk.brtmap.core.map.shp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.core.map.base.TYIBrtShpDataManager;
import cn.platalk.brtmap.core.map.shp.mapdata.TYBrtShpMapDataParser;
import cn.platalk.brtmap.core.map.shp.mapdata.TYBrtShpMapDataParser.TYBrtMapShpParserListener;
import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.impl.TYMapInfo;

public class TYBrtShpMapDataTask implements TYBrtMapShpParserListener {
	private static final String[] MAP_LAYERS = { "FLOOR", "ROOM", "ASSET", "FACILITY", "LABEL", "SHADE" };

	private int currentFloorIndex = 0;
	private int currentLayerIndex = 0;

	List<TYMapDataFeatureRecord> mapDataRecords;

	TYIBrtShpDataManager shpDataManager;
	List<TYMapInfo> mapInfos;

	public TYBrtShpMapDataTask() {

	}

	public void setShpDataManager(TYIBrtShpDataManager manager) {
		this.shpDataManager = manager;
	}

	public void setMapInfos(List<TYMapInfo> infos) {
		this.mapInfos = infos;
	}

	public void startProcessMapShp() {
		readMapData();
	}

	private void readMapData() {
		currentFloorIndex = 0;
		currentLayerIndex = 0;

		mapDataRecords = new ArrayList<>();
		doParsingMapShp();
	}

	private void doParsingMapShp() {
		if (hasNextShp()) {
			TYMapInfo info = mapInfos.get(currentFloorIndex);
			String filePrefix = info.getMapID().substring(info.getMapID().length() - 3);
			String layer = MAP_LAYERS[currentLayerIndex];

			String shpPath = shpDataManager.getMapShpPath(layer, filePrefix);
			if (new File(shpPath).exists()) {
				System.out.println("Process " + filePrefix + " : " + layer);
				TYBrtShpMapDataParser parser = new TYBrtShpMapDataParser(shpPath, currentLayerIndex);
				parser.addParserListener(this);
				parser.parse();
			} else {
				System.out.println("Not Exist " + filePrefix + " : " + layer);
				getNextShp();
				doParsingMapShp();
			}
		} else {
			// System.out.println("Map Over!");
			notifyFinishMapShpTask(mapDataRecords);
		}
	}

	@Override
	public void didFailedParsingMapDataList(Throwable error) {
		System.out.println("didFailedParsingMapDataList");
	}

	@Override
	public void didFinishParsingMapDataList(List<TYMapDataFeatureRecord> recordList) {
		// System.out.printlns("didFinishParsingMapDataList: " +
		// recordList.size());
		mapDataRecords.addAll(recordList);
		getNextShp();
		doParsingMapShp();
	}

	private void getNextShp() {
		if (currentLayerIndex == MAP_LAYERS.length - 1) {
			currentLayerIndex = 0;
			currentFloorIndex++;
		} else {
			currentLayerIndex++;
		}
	}

	private boolean hasNextShp() {
		return !(currentFloorIndex == mapInfos.size() - 1 && currentLayerIndex == MAP_LAYERS.length - 1);
	}

	private List<TYBrtMapShpTaskListener> listeners = new ArrayList<TYBrtShpMapDataTask.TYBrtMapShpTaskListener>();

	public void addTaskListner(TYBrtMapShpTaskListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeTaskListener(TYBrtMapShpTaskListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	private void notifyFinishMapShpTask(List<TYMapDataFeatureRecord> records) {
		for (TYBrtMapShpTaskListener listener : listeners) {
			listener.didFinishMapShpTask(records);
		}
	}

	public interface TYBrtMapShpTaskListener {
		public void didFinishMapShpTask(List<TYMapDataFeatureRecord> mapDataRecords);
	}
}

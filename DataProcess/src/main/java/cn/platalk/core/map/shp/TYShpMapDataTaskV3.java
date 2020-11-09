package cn.platalk.core.map.shp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.core.map.base.TYIShpDataManager;
import cn.platalk.core.map.shp.mapdata.TYShpMapDataParser;
import cn.platalk.core.map.shp.mapdata.TYShpMapDataParser.TYBrtMapShpParserListener;
import cn.platalk.map.entity.base.impl.map.TYMapDataFeatureRecord;
import cn.platalk.map.entity.base.impl.map.TYMapInfo;

public class TYShpMapDataTaskV3 implements TYBrtMapShpParserListener {
	private static final String[] MAP_LAYERS = { "FLOOR", "ROOM", "ASSET", "FACILITY", "LABEL", "SHADE" };

	private int currentFloorIndex = 0;
	private int currentLayerIndex = 0;

	List<TYMapDataFeatureRecord> mapDataRecords;

	TYIShpDataManager shpDataManager;
	List<TYMapInfo> mapInfos;

	public TYShpMapDataTaskV3() {

	}

	public void setShpDataManager(TYIShpDataManager manager) {
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
				// System.out.println("Process " + filePrefix + " : " + layer);
				TYShpMapDataParser parser = new TYShpMapDataParser(shpPath, currentLayerIndex);
				parser.addParserListener(this);
				parser.parse();
			} else {
				// System.out.println("Not Exist " + filePrefix + " : " +
				// layer);
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
		// System.out.println("didFinishParsingMapDataList: " +
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

	private final List<TYBrtMapShpTaskListenerV3> listeners = new ArrayList<>();

	public void addTaskListener(TYBrtMapShpTaskListenerV3 listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeTaskListener(TYBrtMapShpTaskListenerV3 listener) {
		listeners.remove(listener);
	}

	private void notifyFinishMapShpTask(List<TYMapDataFeatureRecord> records) {
		for (TYBrtMapShpTaskListenerV3 listener : listeners) {
			listener.didFinishMapShpTask(records);
		}
	}

	public interface TYBrtMapShpTaskListenerV3 {
		void didFinishMapShpTask(List<TYMapDataFeatureRecord> mapDataRecords);
	}
}

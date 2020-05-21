package cn.platalk.core.map.shp.routedata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.core.map.base.TYIShpDataManager;
import cn.platalk.core.map.shp.routedata.TYShpRouteParserV3.TYBrtRouteShpParserListenerV3;
import cn.platalk.map.entity.base.impl.TYMapInfo;

public class TYShpRouteDataGroupV3 implements TYBrtRouteShpParserListenerV3 {
	private static final String[] ROUTE_LAYERS = { "LINK", "NODE" };

	final List<TYShpBuildingNodeV3> nodeArray = new ArrayList<>();
	final List<TYShpBuildingLinkV3> linkArray = new ArrayList<>();

	private int currentFloorIndex = 0;
	private int currentLayerIndex = 0;

	TYIShpDataManager shpDataManager;
	List<TYMapInfo> mapInfos;

	public TYShpRouteDataGroupV3() {

	}

	public void setShpDataManager(TYIShpDataManager manager) {
		this.shpDataManager = manager;
	}

	public void setMapInfos(List<TYMapInfo> infos) {
		this.mapInfos = infos;
	}

	public void startParsingRouteShp() {
		// System.out.println("startParsingRouteShp");
		nodeArray.clear();
		linkArray.clear();

		currentFloorIndex = 0;
		currentLayerIndex = 0;
		doParsingRouteShp();
	}

	public void didFinishParsingRouteShpDataList(List<TYShpRouteRecordV3> records, int layer) {
		// System.out.println("didFinishParsingRouteShpDataList: " + layer);
		processShpData(records, layer);
		getNextShp();
		doParsingRouteShp();
	}

	private void processShpData(List<TYShpRouteRecordV3> records, int layer) {
		switch (layer) {
		case 0:
			linkArray.addAll(processPolylineShp(records, layer));
			break;

		case 1:
			nodeArray.addAll(processPointShp(records, layer));
			break;

		default:
		}
	}

	private List<TYShpBuildingLinkV3> processPolylineShp(List<TYShpRouteRecordV3> records, int layer) {
		// System.out.println("processPolylineShp: " + layer);
		List<TYShpBuildingLinkV3> resultList = new ArrayList<>();
		for (TYShpRouteRecordV3 record : records) {
			resultList.add(record.toLink());
		}
		return resultList;
	}

	private List<TYShpBuildingNodeV3> processPointShp(List<TYShpRouteRecordV3> records, int layer) {
		// System.out.println("processPointShp: " + layer);
		List<TYShpBuildingNodeV3> resultList = new ArrayList<>();
		for (TYShpRouteRecordV3 record : records) {
			resultList.add(record.toNode());
		}
		return resultList;
	}

	@Override
	public void didFailedParsingRouteDataList(Throwable error) {

	}

	private void doParsingRouteShp() {
		// System.out.println("doParsingRouteShp");
		if (hasNextShp()) {
			TYMapInfo info = mapInfos.get(currentFloorIndex);
			String filePrefix = info.getMapID().substring(info.getMapID().length() - 3);

			String layer = ROUTE_LAYERS[currentLayerIndex];
			String shpPath = shpDataManager.getRouteShpPathV3(layer, filePrefix);

			if (new File(shpPath).exists()) {
				TYShpRouteParserV3 parser = new TYShpRouteParserV3(shpPath, currentLayerIndex);
				parser.addParserListener(this);
				parser.setLayerIndex(currentLayerIndex);
				parser.parse();
			} else {
				System.out.println("Not Exist: " + layer);
				getNextShp();
				doParsingRouteShp();
			}
		} else {
			// System.out.println("Route Over!");
			notifyFetchDataGroup();
		}
	}

	private void getNextShp() {
		if (currentLayerIndex == ROUTE_LAYERS.length - 1) {
			currentLayerIndex = 0;
			currentFloorIndex++;
		} else {
			currentLayerIndex++;
		}
	}

	private boolean hasNextShp() {
		// return !(currentFloorIndex == mapInfos.size() - 1 &&
		// currentLayerIndex == ROUTE_LAYERS.length - 1);
		return currentFloorIndex != mapInfos.size();
	}

	private final List<TYBrtShpRouteDataGroupListenerV3> listeners = new ArrayList<>();

	public void addDataGroupListener(TYBrtShpRouteDataGroupListenerV3 listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeDataGroupListener(TYBrtShpRouteDataGroupListenerV3 listener) {
		listeners.remove(listener);
	}

	private void notifyFetchDataGroup() {
		for (TYBrtShpRouteDataGroupListenerV3 listener : listeners) {
			listener.didFinishFetchDataGroup();
		}
	}

	public interface TYBrtShpRouteDataGroupListenerV3 {
		void didFinishFetchDataGroup();
	}

}

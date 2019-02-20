package cn.platalk.brtmap.core.map.shp.routedata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.core.map.base.TYIBrtShpDataManager;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteParserV3.TYBrtRouteShpParserListenerV3;
import cn.platalk.map.entity.base.impl.TYMapInfo;

public class TYBrtShpRouteDataGroupV3 implements TYBrtRouteShpParserListenerV3 {
	private static final String[] ROUTE_LAYERS = { "LINK", "NODE" };

	List<TYBrtShpBuildingNodeV3> nodeArray = new ArrayList<TYBrtShpBuildingNodeV3>();
	List<TYBrtShpBuildingLinkV3> linkArray = new ArrayList<TYBrtShpBuildingLinkV3>();

	private int currentFloorIndex = 0;
	private int currentLayerIndex = 0;

	TYIBrtShpDataManager shpDataManager;
	List<TYMapInfo> mapInfos;

	public TYBrtShpRouteDataGroupV3() {

	}

	public void setShpDataManager(TYIBrtShpDataManager manager) {
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

	public void didFinishParsingRouteShpDataList(List<TYBrtShpRouteRecordV3> records, int layer) {
		// System.out.println("didFinishParsingRouteShpDataList: " + layer);
		processShpData(records, layer);
		getNextShp();
		doParsingRouteShp();
	}

	private void processShpData(List<TYBrtShpRouteRecordV3> records, int layer) {
		switch (layer) {
		case 0:
			linkArray.addAll(processPolylineShp(records, layer));
			break;

		case 1:
			nodeArray.addAll(processPointShp(records, layer));
			break;

		default:
			return;
		}
	}

	private List<TYBrtShpBuildingLinkV3> processPolylineShp(List<TYBrtShpRouteRecordV3> records, int layer) {
		// System.out.println("processPolylineShp: " + layer);
		List<TYBrtShpBuildingLinkV3> resultList = new ArrayList<TYBrtShpBuildingLinkV3>();
		for (TYBrtShpRouteRecordV3 record : records) {
			resultList.add(record.toLink());
		}
		return resultList;
	}

	private List<TYBrtShpBuildingNodeV3> processPointShp(List<TYBrtShpRouteRecordV3> records, int layer) {
		// System.out.println("processPointShp: " + layer);
		List<TYBrtShpBuildingNodeV3> resultList = new ArrayList<TYBrtShpBuildingNodeV3>();
		for (TYBrtShpRouteRecordV3 record : records) {
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
				TYBrtShpRouteParserV3 parser = new TYBrtShpRouteParserV3(shpPath, currentLayerIndex);
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

	private List<TYBrtShpRouteDataGroupListenerV3> listeners = new ArrayList<TYBrtShpRouteDataGroupV3.TYBrtShpRouteDataGroupListenerV3>();

	public void addDataGroupListener(TYBrtShpRouteDataGroupListenerV3 listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeDataGroupListener(TYBrtShpRouteDataGroupListenerV3 listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	private void notifyFetchDataGroup() {
		for (TYBrtShpRouteDataGroupListenerV3 listener : listeners) {
			listener.didFinishFetchDataGroup();
		}
	}

	public interface TYBrtShpRouteDataGroupListenerV3 {
		public void didFinishFetchDataGroup();
	}

}

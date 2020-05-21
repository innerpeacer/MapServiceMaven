package cn.platalk.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.core.map.base.TYIShpDataManager;
import cn.platalk.core.map.shp.routedata.TYShpRouteDataGroup.TYBrtShpRouteDataGroupListener;
import cn.platalk.core.map.shp.routedata.TYShpRouteNDBuildingTool.TYBrtRouteNDBuildingListener;
import cn.platalk.map.entity.base.impl.TYRouteLinkRecord;
import cn.platalk.map.entity.base.impl.TYRouteNodeRecord;

public class TYShpRouteTask implements TYBrtShpRouteDataGroupListener, TYBrtRouteNDBuildingListener {

	final TYIShpDataManager shpDataManager;
	List<TYRouteLinkRecord> linkRecords;
	List<TYRouteNodeRecord> nodeRecords;

	TYShpRouteDataGroup shpDataGroup;
	TYShpRouteNDBuildingTool buildingTool;

	public TYShpRouteTask(TYIShpDataManager manager) {
		this.shpDataManager = manager;
	}

	public void startProcessRouteShp() {
		// System.out.println("startProcessRouteShp");
		shpDataGroup = new TYShpRouteDataGroup();
		shpDataGroup.setShpDataManager(this.shpDataManager);
		shpDataGroup.addDataGroupListener(this);
		shpDataGroup.startParsingRouteShp();
	}

	@Override
	public void didFinishFetchDataGroup() {
		buildingTool = new TYShpRouteNDBuildingTool(shpDataGroup);
		buildingTool.addRouteNDBuildingListener(this);
		buildingTool.buildNetworkDataset();
	}

	@Override
	public void didFinishBuildingRouteND(List<TYRouteLinkRecord> linkList, List<TYRouteNodeRecord> nodeList) {
		// System.out.println("didFinishBuildingRouteND");

		linkRecords = new ArrayList<>();
		linkRecords.addAll(linkList);

		nodeRecords = new ArrayList<>();
		nodeRecords.addAll(nodeList);

		notifyFinishRouteTask(linkRecords, nodeRecords);
	}

	@Override
	public void didFailedBuildingRouteND(Throwable error) {
		notifyFailedRouteTask(error);
	}

	private final List<TYBrtRouteShpTaskListener> listeners = new ArrayList<>();

	public void addTaskListener(TYBrtRouteShpTaskListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeTaskListener(TYBrtRouteShpTaskListener listener) {
		listeners.remove(listener);
	}

	private void notifyFinishRouteTask(List<TYRouteLinkRecord> links, List<TYRouteNodeRecord> nodes) {
		for (TYBrtRouteShpTaskListener listener : listeners) {
			listener.didFinishRouteTask(links, nodes);
		}
	}

	private void notifyFailedRouteTask(Throwable error) {
		for (TYBrtRouteShpTaskListener listener : listeners) {
			listener.didFailedRouteTask(error);
		}
	}

	public interface TYBrtRouteShpTaskListener {
		void didFinishRouteTask(List<TYRouteLinkRecord> links, List<TYRouteNodeRecord> nodes);

		void didFailedRouteTask(Throwable error);
	}
}

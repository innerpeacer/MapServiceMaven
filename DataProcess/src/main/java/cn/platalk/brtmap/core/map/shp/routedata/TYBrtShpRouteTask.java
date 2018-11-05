package cn.platalk.brtmap.core.map.shp.routedata;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.core.map.base.TYIBrtShpDataManager;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteDataGroup.TYBrtShpRouteDataGroupListener;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteNDBuildingTool.TYBrtRouteNDBuildingListener;
import cn.platalk.brtmap.entity.base.impl.TYRouteLinkRecord;
import cn.platalk.brtmap.entity.base.impl.TYRouteNodeRecord;

public class TYBrtShpRouteTask implements TYBrtShpRouteDataGroupListener, TYBrtRouteNDBuildingListener {

	TYIBrtShpDataManager shpDataManager;
	List<TYRouteLinkRecord> linkRecords;
	List<TYRouteNodeRecord> nodeRecords;

	TYBrtShpRouteDataGroup shpDataGroup;
	TYBrtShpRouteNDBuildingTool buildingTool;

	public TYBrtShpRouteTask(TYIBrtShpDataManager manager) {
		this.shpDataManager = manager;
	}

	public void startProcessRouteShp() {
		// System.out.println("startProcessRouteShp");
		shpDataGroup = new TYBrtShpRouteDataGroup();
		shpDataGroup.setShpDataManager(this.shpDataManager);
		shpDataGroup.addDataGroupListener(this);
		shpDataGroup.startParseingRouteShp();
	}

	@Override
	public void didFinishFetchDataGroup() {
		buildingTool = new TYBrtShpRouteNDBuildingTool(shpDataGroup);
		buildingTool.addRouteNDBuildingListener(this);
		buildingTool.buildNetworkDataset();
	}

	@Override
	public void didFinishBuildingRouteND(List<TYRouteLinkRecord> linkList, List<TYRouteNodeRecord> nodeList) {
		// System.out.println("didFinishBuildingRouteND");

		linkRecords = new ArrayList<TYRouteLinkRecord>();
		linkRecords.addAll(linkList);

		nodeRecords = new ArrayList<TYRouteNodeRecord>();
		nodeRecords.addAll(nodeList);

		notifyFinishRouteTask(linkRecords, nodeRecords);
	}

	@Override
	public void didFailedBuildingRouteND(Throwable error) {
		notifyFailedRouteTask(error);
	}

	private List<TYBrtRouteShpTaskListener> listeners = new ArrayList<TYBrtShpRouteTask.TYBrtRouteShpTaskListener>();

	public void addTaskListener(TYBrtRouteShpTaskListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeTaskListener(TYBrtRouteShpTaskListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
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
		public void didFinishRouteTask(List<TYRouteLinkRecord> links, List<TYRouteNodeRecord> nodes);

		public void didFailedRouteTask(Throwable error);
	}
}

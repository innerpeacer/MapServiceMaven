package cn.platalk.core.map.shp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.core.map.base.TYIShpDataManager;
import cn.platalk.core.map.shp.TYShpMapDataTask.TYBrtMapShpTaskListener;
import cn.platalk.core.map.shp.mapdata.TYJsonMapInfoParser;
import cn.platalk.core.map.shp.mapdata.TYJsonMapInfoParser.TYBrtMapInfoJsonParserListener;
import cn.platalk.core.map.shp.routedata.TYShpRouteTask;
import cn.platalk.core.map.shp.routedata.TYShpRouteTask.TYBrtRouteShpTaskListener;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYCity;
import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.map.entity.base.impl.TYMapInfo;
import cn.platalk.map.entity.base.impl.TYRouteLinkRecord;
import cn.platalk.map.entity.base.impl.TYRouteNodeRecord;
import cn.platalk.sqlite.map.IPSqliteSymbolDBAdapter;
import cn.platalk.sqlite.map.IPSqliteMapDBAdapter;

public class TYShpGeneratingTask
		implements TYBrtMapInfoJsonParserListener, TYBrtMapShpTaskListener, TYBrtRouteShpTaskListener {

	private TYIShpDataManager shpDataManager;

	TYCity city;
	TYBuilding building;
	List<TYMapInfo> mapInfos;
	List<TYFillSymbolRecord> fillSymbols;
	List<TYIconSymbolRecord> iconSymbols;
	List<TYRouteLinkRecord> linkRecords;
	List<TYRouteNodeRecord> nodeRecords;
	List<TYMapDataFeatureRecord> mapDataRecords;

	private TYJsonMapInfoParser mapInfoParser;
	private TYShpMapDataTask mapShpTask;

	private TYShpRouteTask routeShpTask;

	public TYShpGeneratingTask(String root, String buildingID) {
		shpDataManager = new TYShpPathManager(root, buildingID);

		if (new File(shpDataManager.getMapDBPath()).exists()) {
			IPSqliteMapDBAdapter mapDB = new IPSqliteMapDBAdapter(shpDataManager.getMapDBPath());
			mapDB.open();
			city = mapDB.queryCities().get(0);
			building = mapDB.queryBuildings().get(0);
			mapDB.close();
		}
	}

	public void startTask() {
		// System.out.println("startTask");
		mapInfoParser = new TYJsonMapInfoParser();
		mapInfoParser.setShpDataManager(shpDataManager);
		mapInfoParser.addParserListener(this);
		mapInfoParser.parseMapInfos();
	}

	private void startMapShpTask() {
		mapShpTask = new TYShpMapDataTask();
		mapShpTask.addTaskListner(this);

		mapShpTask.setMapInfos(mapInfos);
		mapShpTask.setShpDataManager(shpDataManager);
		mapShpTask.startProcessMapShp();
	}

	private void startRouteShpTask() {
		// System.out.println("startRouteShpTask");
		routeShpTask = new TYShpRouteTask(shpDataManager);
		routeShpTask.addTaskListener(this);
		routeShpTask.startProcessRouteShp();
	}

	@Override
	public void didFinishRouteTask(List<TYRouteLinkRecord> links, List<TYRouteNodeRecord> nodes) {
		linkRecords = new ArrayList<TYRouteLinkRecord>();
		linkRecords.addAll(links);

		nodeRecords = new ArrayList<TYRouteNodeRecord>();
		nodeRecords.addAll(nodes);

		notifyFinishGeneratingShpTask();
	}

	@Override
	public void didFailedRouteTask(Throwable error) {
		notifyFailedGeneratingShpTask(error);
	}

	@Override
	public void didFinishParsingMapInfo(List<TYMapInfo> infos) {
		// System.out.prisntln("didFinishParsingMapInfo");
		mapInfos = new ArrayList<TYMapInfo>();
		mapInfos.addAll(infos);

		readSymbols();
		startMapShpTask();
	}

	@Override
	public void didFailedParsingMapInfo(Throwable error) {
		notifyFailedGeneratingShpTask(error);
	}

	@Override
	public void didFinishMapShpTask(List<TYMapDataFeatureRecord> records) {
		// System.out.println("didFinishMapShpTask");
		mapDataRecords = new ArrayList<TYMapDataFeatureRecord>();
		mapDataRecords.addAll(records);
		// System.out.println(mapDataRecords.size() + " records!");
		startRouteShpTask();
	}

	private void readSymbols() {
		IPSqliteSymbolDBAdapter symbolDB = new IPSqliteSymbolDBAdapter(shpDataManager.getSymbolDBPath());
		// System.out.println(shpDataManager.getSymbolDBPath());
		symbolDB.open();
		fillSymbols = symbolDB.getFillSymbolRecords();
		iconSymbols = symbolDB.getIconSymbolRecords();
		symbolDB.close();
	}

	public List<TYIconSymbolRecord> getIconSymbols() {
		return iconSymbols;
	}

	public List<TYFillSymbolRecord> getFillSymbols() {
		return fillSymbols;
	}

	public TYCity getCity() {
		return city;
	}

	public TYBuilding getBuilding() {
		return building;
	}

	public List<TYMapInfo> getMapInfos() {
		return mapInfos;
	}

	public List<TYMapDataFeatureRecord> getMapDataRecords() {
		return mapDataRecords;
	}

	public List<TYRouteLinkRecord> getLinkRecords() {
		return linkRecords;
	}

	public List<TYRouteNodeRecord> getNodeRecords() {
		return nodeRecords;
	}

	private List<TYShpGeneratingTaskListener> listeners = new ArrayList<TYShpGeneratingTaskListener>();

	public void addTaskListener(TYShpGeneratingTaskListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeTaskListener(TYShpGeneratingTaskListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	private void notifyFinishGeneratingShpTask() {
		for (TYShpGeneratingTaskListener listener : listeners) {
			listener.didFinishGeneratingTask(this);
		}
	}

	private void notifyFailedGeneratingShpTask(Throwable error) {
		for (TYShpGeneratingTaskListener listener : listeners) {
			listener.didFailedGeneratingTask(error);
		}
	}

	public interface TYShpGeneratingTaskListener {
		public void didFinishGeneratingTask(TYShpGeneratingTask task);

		public void didFailedGeneratingTask(Throwable error);
	}

}
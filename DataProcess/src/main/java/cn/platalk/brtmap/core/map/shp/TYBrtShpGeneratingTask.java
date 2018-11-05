package cn.platalk.brtmap.core.map.shp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.core.map.base.TYIBrtShpDataManager;
import cn.platalk.brtmap.core.map.shp.TYBrtShpMapDataTask.TYBrtMapShpTaskListener;
import cn.platalk.brtmap.core.map.shp.mapdata.TYBrtJsonMapInfoParser;
import cn.platalk.brtmap.core.map.shp.mapdata.TYBrtJsonMapInfoParser.TYBrtMapInfoJsonParserListener;
import cn.platalk.brtmap.core.map.shp.mapdata.TYBrtSqliteMapDBAdapter;
import cn.platalk.brtmap.core.map.shp.mapdata.TYBrtSqliteSymbolDBAdapter;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteTask;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteTask.TYBrtRouteShpTaskListener;
import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYCity;
import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.impl.TYMapInfo;
import cn.platalk.brtmap.entity.base.impl.TYRouteLinkRecord;
import cn.platalk.brtmap.entity.base.impl.TYRouteNodeRecord;

public class TYBrtShpGeneratingTask
		implements TYBrtMapInfoJsonParserListener, TYBrtMapShpTaskListener, TYBrtRouteShpTaskListener {

	private TYIBrtShpDataManager shpDataManager;

	TYCity city;
	TYBuilding building;
	List<TYMapInfo> mapInfos;
	List<TYFillSymbolRecord> fillSymbols;
	List<TYIconSymbolRecord> iconSymbols;
	List<TYRouteLinkRecord> linkRecords;
	List<TYRouteNodeRecord> nodeRecords;
	List<TYMapDataFeatureRecord> mapDataRecords;

	private TYBrtJsonMapInfoParser mapInfoParser;
	private TYBrtShpMapDataTask mapShpTask;

	private TYBrtShpRouteTask routeShpTask;

	public TYBrtShpGeneratingTask(String root, String buildingID) {
		shpDataManager = new TYBrtShpPathManager(root, buildingID);

		if (new File(shpDataManager.getMapDBPath()).exists()) {
			TYBrtSqliteMapDBAdapter mapDB = new TYBrtSqliteMapDBAdapter(shpDataManager.getMapDBPath());
			mapDB.open();
			city = mapDB.queryCities().get(0);
			building = mapDB.queryBuildings().get(0);
			mapDB.close();
		}
	}

	public void startTask() {
		// System.out.println("startTask");
		mapInfoParser = new TYBrtJsonMapInfoParser();
		mapInfoParser.setShpDataManager(shpDataManager);
		mapInfoParser.addParserListener(this);
		mapInfoParser.parseMapInfos();
	}

	private void startMapShpTask() {
		mapShpTask = new TYBrtShpMapDataTask();
		mapShpTask.addTaskListner(this);

		mapShpTask.setMapInfos(mapInfos);
		mapShpTask.setShpDataManager(shpDataManager);
		mapShpTask.startProcessMapShp();
	}

	private void startRouteShpTask() {
		// System.out.println("startRouteShpTask");
		routeShpTask = new TYBrtShpRouteTask(shpDataManager);
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
		TYBrtSqliteSymbolDBAdapter symbolDB = new TYBrtSqliteSymbolDBAdapter(shpDataManager.getSymbolDBPath());
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
		public void didFinishGeneratingTask(TYBrtShpGeneratingTask task);

		public void didFailedGeneratingTask(Throwable error);
	}

}

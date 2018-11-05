package cn.platalk.brtmap.core.map.shp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.core.map.shp.TYBrtShpMapDataTaskV3.TYBrtMapShpTaskListenerV3;
import cn.platalk.brtmap.core.map.shp.mapdata.TYBrtJsonMapInfoParser;
import cn.platalk.brtmap.core.map.shp.mapdata.TYBrtJsonMapInfoParser.TYBrtMapInfoJsonParserListener;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteTaskV3;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteTaskV3.TYBrtRouteShpTaskListenerV3;
import cn.platalk.brtmap.core.map.shp.mapdata.TYBrtSqliteMapDBAdapter;
import cn.platalk.brtmap.core.map.shp.mapdata.TYBrtSqliteSymbolDBAdapter;
import cn.platalk.brtmap.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYCity;
import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.impl.TYMapInfo;

public class TYBrtShpGeneratingTaskV3
		implements TYBrtMapInfoJsonParserListener, TYBrtMapShpTaskListenerV3, TYBrtRouteShpTaskListenerV3 {
	private TYBrtShpPathManagerV3 shpDataManager;

	TYCity city;
	TYBuilding building;
	List<TYMapInfo> mapInfos;
	List<TYFillSymbolRecord> fillSymbols;
	List<TYIconSymbolRecord> iconSymbols;
	List<TYIRouteLinkRecordV3> linkRecords;
	List<TYIRouteNodeRecordV3> nodeRecords;
	List<TYMapDataFeatureRecord> mapDataRecords;

	private TYBrtJsonMapInfoParser mapInfoParser;
	private TYBrtShpMapDataTaskV3 mapShpTask;
	private TYBrtShpRouteTaskV3 routeShpTask;

	public TYBrtShpGeneratingTaskV3(String root, String buildingID) {
		shpDataManager = new TYBrtShpPathManagerV3(root, buildingID);

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
		mapShpTask = new TYBrtShpMapDataTaskV3();
		mapShpTask.addTaskListner(this);

		mapShpTask.setMapInfos(mapInfos);
		mapShpTask.setShpDataManager(shpDataManager);
		mapShpTask.startProcessMapShp();
	}

	private void startRouteShpTask() {
		// System.out.println("startRouteShpTask");
		routeShpTask = new TYBrtShpRouteTaskV3(shpDataManager);
		routeShpTask.setMapInfos(mapInfos);
		routeShpTask.addTaskListener(this);
		routeShpTask.startProcessRouteShp();
	}

	@Override
	public void didFinishRouteTask(List<TYIRouteLinkRecordV3> links, List<TYIRouteNodeRecordV3> nodes) {
		linkRecords = new ArrayList<TYIRouteLinkRecordV3>();
		linkRecords.addAll(links);

		nodeRecords = new ArrayList<TYIRouteNodeRecordV3>();
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

	public List<TYIRouteLinkRecordV3> getLinkRecords() {
		return linkRecords;
	}

	public List<TYIRouteNodeRecordV3> getNodeRecords() {
		return nodeRecords;
	}

	private List<TYShpGeneratingTaskListenerV3> listeners = new ArrayList<TYShpGeneratingTaskListenerV3>();

	public void addTaskListener(TYShpGeneratingTaskListenerV3 listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeTaskListener(TYShpGeneratingTaskListenerV3 listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	private void notifyFinishGeneratingShpTask() {
		for (TYShpGeneratingTaskListenerV3 listener : listeners) {
			listener.didFinishGeneratingTask(this);
		}
	}

	private void notifyFailedGeneratingShpTask(Throwable error) {
		for (TYShpGeneratingTaskListenerV3 listener : listeners) {
			listener.didFailedGeneratingTask(error);
		}
	}

	public interface TYShpGeneratingTaskListenerV3 {
		public void didFinishGeneratingTask(TYBrtShpGeneratingTaskV3 task);

		public void didFailedGeneratingTask(Throwable error);
	}

}

package cn.platalk.core.map.shp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.core.map.shp.TYShpMapDataTaskV3.TYBrtMapShpTaskListenerV3;
import cn.platalk.core.map.shp.mapdata.TYJsonMapInfoParser;
import cn.platalk.core.map.shp.mapdata.TYJsonMapInfoParser.TYBrtMapInfoJsonParserListener;
import cn.platalk.core.map.shp.routedata.TYShpRouteTaskV3;
import cn.platalk.core.map.shp.routedata.TYShpRouteTaskV3.TYBrtRouteShpTaskListenerV3;
import cn.platalk.map.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYCity;
import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.map.entity.base.impl.TYMapInfo;
import cn.platalk.sqlite.map.IPSqliteSymbolDBAdapter;
import cn.platalk.sqlite.map.IPSqliteMapDBAdapter;

public class TYShpGeneratingTaskV3
		implements TYBrtMapInfoJsonParserListener, TYBrtMapShpTaskListenerV3, TYBrtRouteShpTaskListenerV3 {
	private TYShpPathManagerV3 shpDataManager;

	TYCity city;
	TYBuilding building;
	List<TYMapInfo> mapInfos;
	List<TYFillSymbolRecord> fillSymbols;
	List<TYIconSymbolRecord> iconSymbols;
	List<TYIRouteLinkRecordV3> linkRecords;
	List<TYIRouteNodeRecordV3> nodeRecords;
	List<TYMapDataFeatureRecord> mapDataRecords;

	private TYJsonMapInfoParser mapInfoParser;
	private TYShpMapDataTaskV3 mapShpTask;
	private TYShpRouteTaskV3 routeShpTask;

	public TYShpGeneratingTaskV3(String root, String buildingID) {
		shpDataManager = new TYShpPathManagerV3(root, buildingID);

		if (new File(shpDataManager.getMapDBPath()).exists()) {
			IPSqliteMapDBAdapter mapDB = new IPSqliteMapDBAdapter(shpDataManager.getMapDBPath());
			mapDB.open();
			city = mapDB.queryCities().get(0);
			building = mapDB.queryBuildings().get(0);
			System.out.println("TYBrtShpGeneratingTaskV3");
			System.out.println(city);
			System.out.println(building);

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
		mapShpTask = new TYShpMapDataTaskV3();
		mapShpTask.addTaskListner(this);

		mapShpTask.setMapInfos(mapInfos);
		mapShpTask.setShpDataManager(shpDataManager);
		mapShpTask.startProcessMapShp();
	}

	private void startRouteShpTask() {
		// System.out.println("startRouteShpTask");
		routeShpTask = new TYShpRouteTaskV3(shpDataManager);
		routeShpTask.setMapInfos(mapInfos);
		routeShpTask.setMapData(mapDataRecords);
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
		public void didFinishGeneratingTask(TYShpGeneratingTaskV3 task);

		public void didFailedGeneratingTask(Throwable error);
	}

}

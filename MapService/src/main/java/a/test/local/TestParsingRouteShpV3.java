package a.test.local;

import java.util.List;

import cn.platalk.brtmap.core.config.TYServerEnviroment;
import cn.platalk.brtmap.core.map.shp.TYBrtShpPathManager;
import cn.platalk.brtmap.core.map.shp.TYBrtShpPathManagerV3;
import cn.platalk.brtmap.core.map.shp.TYBrtShpMapDataTaskV3;
import cn.platalk.brtmap.core.map.shp.TYBrtShpMapDataTaskV3.TYBrtMapShpTaskListenerV3;
import cn.platalk.brtmap.core.map.shp.mapdata.TYBrtJsonMapInfoParser;
import cn.platalk.brtmap.core.map.shp.mapdata.TYBrtJsonMapInfoParser.TYBrtMapInfoJsonParserListener;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteTaskV3;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteTaskV3.TYBrtRouteShpTaskListenerV3;
import cn.platalk.brtmap.db.map.TYRouteDBAdapterV3;
import cn.platalk.brtmap.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.impl.TYMapInfo;

public class TestParsingRouteShpV3
		implements TYBrtRouteShpTaskListenerV3, TYBrtMapShpTaskListenerV3, TYBrtMapInfoJsonParserListener {
	public static void main(String[] args) {
		new TestParsingRouteShpV3();
	}

	TYBrtShpPathManagerV3 shpManager;
	TYBrtShpMapDataTaskV3 mapShpTask;
	List<TYMapInfo> mapInfoList;

	// String buildingID = "05120002";
	String buildingID = "05710010";

	TestParsingRouteShpV3() {
		System.out.println("TestParsingRouteShp V3");
		TYServerEnviroment.initialize();

		String root = "src/main/resources/v3" + "/" + buildingID;

		shpManager = new TYBrtShpPathManagerV3(root, buildingID);
		System.out.println(shpManager.getRouteShpPathV3("NODE", "B01"));

		TYBrtJsonMapInfoParser mapInfoParser = new TYBrtJsonMapInfoParser();
		mapInfoParser.setShpDataManager(new TYBrtShpPathManager(root, buildingID));
		mapInfoParser.addParserListener(this);
		mapInfoParser.parseMapInfos();
	}

	@Override
	public void didFinishParsingMapInfo(List<TYMapInfo> mapInfos) {
		System.out.println("didFinishParsingMapInfo");
		// System.out.println(mapInfos);

		mapInfoList = mapInfos;

		mapShpTask = new TYBrtShpMapDataTaskV3();
		mapShpTask.addTaskListner(this);

		mapShpTask.setMapInfos(mapInfos);
		mapShpTask.setShpDataManager(shpManager);
		mapShpTask.startProcessMapShp();

	}

	@Override
	public void didFinishMapShpTask(List<TYMapDataFeatureRecord> records) {
		System.out.println("didFinishMapShpTask");
		TYBrtShpRouteTaskV3 task = new TYBrtShpRouteTaskV3(shpManager);
		task.setMapInfos(mapInfoList);
		task.setMapData(records);
		task.addTaskListener(this);
		task.startProcessRouteShp();
	}

	@Override
	public void didFailedParsingMapInfo(Throwable error) {
		System.out.println("didFailedParsingMapInfo");
	}

	@Override
	public void didFailedRouteTask(Throwable e) {
		System.out.println("didFailedRouteTask");
		System.out.println(e.getLocalizedMessage());
	}

	@Override
	public void didFinishRouteTask(List<TYIRouteLinkRecordV3> links, List<TYIRouteNodeRecordV3> nodes) {
		System.out.println("didFinishRouteTask");
		System.out.println("Links: " + links.size());
		System.out.println("Nodes: " + nodes.size());

		TYRouteDBAdapterV3 db = new TYRouteDBAdapterV3(buildingID);
		db.connectDB();
		db.createTableIfNotExist();
		db.eraseRouteTable();

		int STEP = 1000;
		int batch = links.size() / STEP + 1;
		for (int i = 0; i < batch; ++i) {
			db.insertRouteLinkRecordsInBatch(links.subList(i * 1000, Math.min((i + 1) * 1000, links.size())));
		}

		batch = nodes.size() / STEP + 1;
		for (int i = 0; i < batch; ++i) {
			db.insertRouteNodeRecordsInBatch(nodes.subList(i * 1000, Math.min((i + 1) * 1000, nodes.size())));
		}
		db.disconnectDB();
		System.out.println("Over");
	}
}

package a.tool;

import java.util.List;

import cn.platalk.brtmap.core.config.TYBrtMapEnvironment;
import cn.platalk.brtmap.core.map.shp.TYBrtShpGeneratingTask;
import cn.platalk.brtmap.core.map.shp.TYBrtShpGeneratingTask.TYShpGeneratingTaskListener;
import cn.platalk.brtmap.db.map.TYBuildingDBAdapter;
import cn.platalk.brtmap.db.map.TYCityDBAdapter;
import cn.platalk.brtmap.db.map.TYMapDataDBAdapter;
import cn.platalk.brtmap.db.map.TYMapInfoDBAdapter;
import cn.platalk.brtmap.db.map.TYRouteDBAdapter;
import cn.platalk.brtmap.db.map.TYSymbolDBAdapter;
import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYCity;
import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.impl.TYMapInfo;
import cn.platalk.brtmap.entity.base.impl.TYRouteLinkRecord;
import cn.platalk.brtmap.entity.base.impl.TYRouteNodeRecord;

public class MapShpTool implements TYShpGeneratingTaskListener {
	// String buildingID = "07550023";
	// String buildingID = "05790002";
	// String buildingID = "00210110";
	// String buildingID = "00100044";

	String buildingID = "00270002";

	// String buildingID = "05710006";

	public MapShpTool() {
		TYBrtShpGeneratingTask task = new TYBrtShpGeneratingTask(
				TYBrtMapEnvironment.GetShpRootDir(buildingID), buildingID);
		task.addTaskListener(this);
		task.startTask();
	}

	public void insertData(TYBrtShpGeneratingTask task) {
		if (task.getCity() != null) {
			TYCity city = task.getCity();
			TYCityDBAdapter cityDB = new TYCityDBAdapter();
			cityDB.connectDB();
			cityDB.insertOrUpdateCity(city);
			cityDB.disconnectDB();
			System.out.println(city);
		}

		if (task.getBuilding() != null) {
			TYBuilding building = task.getBuilding();
			TYBuildingDBAdapter buildingDB = new TYBuildingDBAdapter();
			buildingDB.connectDB();
			buildingDB.insertOrUpdateBuilding(building);
			buildingDB.disconnectDB();
			System.out.println(building);
		}

		{
			TYMapInfoDBAdapter infoDB = new TYMapInfoDBAdapter();
			infoDB.connectDB();
			infoDB.insertOrUpdateMapInfos(task.getMapInfos());
			infoDB.disconnectDB();
		}

		{
			TYSymbolDBAdapter symbolDB = new TYSymbolDBAdapter();
			symbolDB.connectDB();
			symbolDB.insertFillSymbolRecords(task.getFillSymbols(), buildingID);
			symbolDB.insertIconSymbolRecords(task.getIconSymbols(), buildingID);
			symbolDB.disconnectDB();
		}

		{
			TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(buildingID);
			mapDB.connectDB();
			mapDB.createTableIfNotExist();
			mapDB.eraseMapDataTable();

			List<TYMapDataFeatureRecord> records = task.getMapDataRecords();
			int STEP = 1000;
			int batch = records.size() / STEP + 1;
			System.out.println(batch + " batches");

			for (int i = 0; i < batch; ++i) {
				System.out.println("[ " + i * 1000 + ", "
						+ Math.min((i + 1) * 1000, records.size()) + "]");
				mapDB.insertMapDataRecordsInBatch(records.subList(i * 1000,
						Math.min((i + 1) * 1000, records.size())));
			}
			mapDB.disconnectDB();
		}

		{
			TYRouteDBAdapter routeDB = new TYRouteDBAdapter(buildingID);
			routeDB.connectDB();
			routeDB.createTableIfNotExist();
			routeDB.eraseRouteTable();
			routeDB.insertRouteLinkRecordsInBatch(task.getLinkRecords());
			routeDB.insertRouteNodeRecordsInBatch(task.getNodeRecords());
			routeDB.disconnectDB();
		}

		System.out.println("Finish Task");
	}

	public void didFinishGeneratingTask(TYBrtShpGeneratingTask task) {
		System.out.println("didFinishGeneratingTask");
		List<TYMapInfo> mapInfos = task.getMapInfos();
		// System.out.println(mapInfos);
		System.out.println(mapInfos.size() + " MapInfos");

		List<TYIconSymbolRecord> iconSymbolRecords = task.getIconSymbols();
		// System.out.println(iconSymbolRecords);
		System.out.println(iconSymbolRecords.size() + " icons");

		List<TYFillSymbolRecord> fillSymbolRecords = task.getFillSymbols();
		// System.out.println(fillSymbolRecords);
		System.out.println(fillSymbolRecords.size() + " fills");

		List<TYMapDataFeatureRecord> mapDataRecords = task.getMapDataRecords();
		System.out.println(mapDataRecords.size() + " map records");

		// for (TYMapDataFeatureRecord record : mapDataRecords) {
		// System.out.println(record.name + ": " + record.poiID);
		// }

		List<TYRouteNodeRecord> nodeRecords = task.getNodeRecords();
		System.out.println(nodeRecords.size() + " nodes");
		// for (TYRouteNodeRecord node : nodeRecords) {
		// System.out.println(node);
		// }

		List<TYRouteLinkRecord> linkRecords = task.getLinkRecords();
		System.out.println(linkRecords.size() + " links");
		// for (TYRouteLinkRecord link : linkRecords) {
		// System.out.println(link + " : [" + link.headNode + " -> "
		// + link.endNode + "]");
		// }

		insertData(task);

	};

	@Override
	public void didFailedGeneratingTask(Throwable error) {
		System.out.println("didFinishGeneratingTask");

	}

	public static void main(String[] args) {
		new MapShpTool();
	}

}

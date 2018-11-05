package a.test.local;

import java.util.List;

import cn.platalk.brtmap.core.map.shp.TYBrtShpPathManagerV3;
import cn.platalk.brtmap.core.map.shp.TYBrtShpGeneratingTaskV3;
import cn.platalk.brtmap.core.map.shp.TYBrtShpGeneratingTaskV3.TYShpGeneratingTaskListenerV3;
import cn.platalk.brtmap.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.impl.TYMapInfo;

public class TestShpTaskV3 {
	public static void main(String[] args) {
		TYBrtShpPathManagerV3 shpManager;
		// String buildingID = "05120002";
		String buildingID = "05710010";

		System.out.println("TestShpTaskV3");

		String root = "src/main/resources/v3" + "/" + buildingID;

		TYBrtShpGeneratingTaskV3 task = new TYBrtShpGeneratingTaskV3(root, buildingID);
		task.addTaskListener(new TYShpGeneratingTaskListenerV3() {

			@Override
			public void didFinishGeneratingTask(TYBrtShpGeneratingTaskV3 task) {
				System.out.println("didFinishGeneratingTask");
				List<TYMapInfo> mapInfos = task.getMapInfos();
				System.out.println(mapInfos.size() + " MapInfos");

				List<TYIconSymbolRecord> iconSymbolRecords = task.getIconSymbols();
				System.out.println(iconSymbolRecords.size() + " icons");

				List<TYFillSymbolRecord> fillSymbolRecords = task.getFillSymbols();
				System.out.println(fillSymbolRecords.size() + " fills");

				List<TYMapDataFeatureRecord> mapDataRecords = task.getMapDataRecords();
				System.out.println(mapDataRecords.size() + " map records");

				List<TYIRouteNodeRecordV3> nodeRecords = task.getNodeRecords();
				System.out.println(nodeRecords.size() + " nodes");

				List<TYIRouteLinkRecordV3> linkRecords = task.getLinkRecords();
				System.out.println(linkRecords.size() + " links");
			}

			@Override
			public void didFailedGeneratingTask(Throwable error) {
				System.out.println("didFailedGeneratingTask");

			}
		});
		task.startTask();
	}

}

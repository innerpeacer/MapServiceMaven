package a.test.local;

import java.util.List;

import cn.platalk.brtmap.core.map.shp.TYBrtShpPathManager;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteTask;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteTask.TYBrtRouteShpTaskListener;
import cn.platalk.brtmap.entity.base.impl.TYRouteLinkRecord;
import cn.platalk.brtmap.entity.base.impl.TYRouteNodeRecord;

public class TestParsingRouteShp implements TYBrtRouteShpTaskListener {
	public static void main(String[] args) {
		new TestParsingRouteShp();
	}

	TestParsingRouteShp() {
		System.out.println("Test Route V2");

		// String root = "File";
		// String buildingID = "05120002";

		String buildingID = "07550023";
		String root = "File" + "/" + buildingID;

		TYBrtShpPathManager shpManager = new TYBrtShpPathManager(root,
				buildingID);
		System.out.println(shpManager.getRouteShpDir());
		System.out.println(shpManager.getRouteShpPath("NODE"));

		TYBrtShpRouteTask task = new TYBrtShpRouteTask(shpManager);
		task.addTaskListener(this);
		task.startProcessRouteShp();
	}

	@Override
	public void didFailedRouteTask(Throwable error) {
		System.out.println("didFinishRouteTask");
	}

	@Override
	public void didFinishRouteTask(List<TYRouteLinkRecord> links,
			List<TYRouteNodeRecord> nodes) {
		System.out.println("didFinishRouteTask");
		System.out.println("Links: " + links.size());
		System.out.println("Nodes: " + nodes.size());
	}

}

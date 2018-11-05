package a.test.route;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.brtmap.core_v3.web.route.TYBrtWebRouteGeojsonDataBuilderV3;
import cn.platalk.brtmap.core_v3.web.route.TYBrtWebRouteGeojsonObjectV3;
import cn.platalk.brtmap.db.map.TYRouteDBAdapterV3;
import cn.platalk.brtmap.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecordV3;

public class TestRouteGeojson {
	public static void main(String[] args) throws JSONException {
		System.out.println("Test Route Geojson");
		String buildingID = "05120002";

		TYRouteDBAdapterV3 routeDB = new TYRouteDBAdapterV3(buildingID);
		routeDB.connectDB();
		List<TYIRouteLinkRecordV3> linkList = routeDB.getAllLinkRecords();
		List<TYIRouteNodeRecordV3> nodeList = routeDB.getAllNodeRecords();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("success", true);
			jsonObject.put("Link", linkList.size());
			jsonObject.put("Node", nodeList.size());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		routeDB.disconnectDB();

		// System.out.println(jsonObject);

		for (TYIRouteLinkRecordV3 link : linkList) {
			JSONObject linkObject = TYBrtWebRouteGeojsonObjectV3.buildingLinkRecord(link);
			System.out.println(linkObject);
			break;
		}

		for (TYIRouteNodeRecordV3 node : nodeList) {
			JSONObject nodeObject = TYBrtWebRouteGeojsonObjectV3.buildingNodeRecord(node);
			System.out.println(nodeObject);
			break;
		}

		JSONObject obj = TYBrtWebRouteGeojsonDataBuilderV3.generateRouteDataObject(linkList, nodeList);
		System.out.println(obj);
	}
}

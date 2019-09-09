package cn.platalk.map.core_v3.web.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.common.TYIGeojsonFeature;
import cn.platalk.foundation.TYGeojsonBuilder;
import cn.platalk.map.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.TYIRouteNodeRecordV3;

public class TYWebRouteGeojsonDataBuilderV3 {
	public static final String KEY_BOX_WEB_ROUTE_DATA_LINK = "link";
	public static final String KEY_BOX_WEB_ROUTE_DATA_NODE = "node";

	public static JSONObject generateRouteDataObject(List<TYIRouteLinkRecordV3> links, List<TYIRouteNodeRecordV3> nodes)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();
		Set<Integer> floorSet = new HashSet<Integer>();
		for (TYIRouteLinkRecordV3 r : links) {
			floorSet.add(r.getFloor());
		}
		for (TYIRouteNodeRecordV3 r : nodes) {
			floorSet.add(r.getFloor());
		}

		Map<Integer, List<TYIRouteLinkRecordV3>> linkListMap = new HashMap<Integer, List<TYIRouteLinkRecordV3>>();
		Map<Integer, List<TYIRouteNodeRecordV3>> nodeListMap = new HashMap<Integer, List<TYIRouteNodeRecordV3>>();

		for (int floor : floorSet) {
			linkListMap.put(floor, new ArrayList<TYIRouteLinkRecordV3>());
			nodeListMap.put(floor, new ArrayList<TYIRouteNodeRecordV3>());
		}

		for (TYIRouteLinkRecordV3 link : links) {
			linkListMap.get(link.getFloor()).add(link);
		}

		for (TYIRouteNodeRecordV3 node : nodes) {
			nodeListMap.get(node.getFloor()).add(node);
		}

		JSONObject allLinkObject = new JSONObject();
		for (int floor : linkListMap.keySet()) {
			List<TYIGeojsonFeature> linkList = new ArrayList<TYIGeojsonFeature>(linkListMap.get(floor));
			allLinkObject.put(floor + "", TYGeojsonBuilder.buildFeatureCollection(linkList));
		}
		jsonObject.put(KEY_BOX_WEB_ROUTE_DATA_LINK, allLinkObject);

		JSONObject allNodeObject = new JSONObject();
		for (int floor : nodeListMap.keySet()) {
			List<TYIGeojsonFeature> nodeList = new ArrayList<TYIGeojsonFeature>(nodeListMap.get(floor));
			allNodeObject.put(floor + "", TYGeojsonBuilder.buildFeatureCollection(nodeList));
		}
		jsonObject.put(KEY_BOX_WEB_ROUTE_DATA_NODE, allNodeObject);
		return jsonObject;
	}
}

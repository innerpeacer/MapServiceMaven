package cn.platalk.brtmap.core_v3.web.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.brtmap.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecordV3;

public class TYBrtWebRouteGeojsonDataBuilderV3 {
	public static JSONObject emptyGeojson;
	static {
		emptyGeojson = new JSONObject();
		try {
			emptyGeojson.put("type", "FeatureCollection");
			emptyGeojson.put("features", new JSONArray());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static final String KEY_BOX_WEB_ROUTE_DATA_LINK = "link";
	public static final String KEY_BOX_WEB_ROUTE_DATA_NODE = "node";

	public static final String GEOJSON_KEY_GEOJSON_TYPE = "type";
	public static final String GEOJSON_VALUE_GEOJSON_TYPE__FEATURECOLLECTION = "FeatureCollection";
	public static final String GEOJSON_KEY_FEATURES = "features";

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
			List<TYIRouteLinkRecordV3> linkList = linkListMap.get(floor);
			if (linkList.size() != 0) {
				JSONObject linkObject = new JSONObject();
				linkObject.put(GEOJSON_KEY_GEOJSON_TYPE, GEOJSON_VALUE_GEOJSON_TYPE__FEATURECOLLECTION);
				JSONArray linkFeatures = new JSONArray();
				for (TYIRouteLinkRecordV3 record : linkList) {
					linkFeatures.put(TYBrtWebRouteGeojsonObjectV3.buildingLinkRecord(record));
				}
				linkObject.put(GEOJSON_KEY_FEATURES, linkFeatures);
				allLinkObject.put(floor + "", linkObject);
			} else {
				allLinkObject.put(floor + "", emptyGeojson);
			}
		}
		jsonObject.put(KEY_BOX_WEB_ROUTE_DATA_LINK, allLinkObject);

		JSONObject allNodeObject = new JSONObject();
		for (int floor : nodeListMap.keySet()) {
			List<TYIRouteNodeRecordV3> nodeList = nodeListMap.get(floor);
			if (nodeList.size() != 0) {
				JSONObject nodeObject = new JSONObject();
				nodeObject.put(GEOJSON_KEY_GEOJSON_TYPE, GEOJSON_VALUE_GEOJSON_TYPE__FEATURECOLLECTION);
				JSONArray nodeFeatures = new JSONArray();
				for (TYIRouteNodeRecordV3 record : nodeList) {
					nodeFeatures.put(TYBrtWebRouteGeojsonObjectV3.buildingNodeRecord(record));
				}
				nodeObject.put(GEOJSON_KEY_FEATURES, nodeFeatures);
				allNodeObject.put(floor + "", nodeObject);
			} else {
				allNodeObject.put(floor + "", emptyGeojson);
			}
		}
		jsonObject.put(KEY_BOX_WEB_ROUTE_DATA_NODE, allNodeObject);
		return jsonObject;
	}
}

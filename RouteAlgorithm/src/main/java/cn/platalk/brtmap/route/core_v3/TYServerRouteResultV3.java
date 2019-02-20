package cn.platalk.brtmap.route.core_v3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TYServerRouteResultV3 {
	private List<TYServerRoutePartV3> allRoutePartArray = new ArrayList<TYServerRoutePartV3>();
	private Map<Integer, List<TYServerRoutePartV3>> allFloorRoutePartDict = new HashMap<Integer, List<TYServerRoutePartV3>>();

	public TYServerRouteResultV3(List<TYServerRoutePartV3> routePartArray) {
		allRoutePartArray.addAll(routePartArray);

		for (int i = 0; i < routePartArray.size(); ++i) {
			TYServerRoutePartV3 rp = routePartArray.get(i);
			int floor = rp.getMapInfo().getFloorNumber();

			if (!allFloorRoutePartDict.containsKey(floor)) {
				List<TYServerRoutePartV3> array = new ArrayList<TYServerRoutePartV3>();
				allFloorRoutePartDict.put(floor, array);
			}
			List<TYServerRoutePartV3> array = allFloorRoutePartDict.get(floor);
			array.add(rp);
		}
	}

	public JSONObject buildJson() {
		JSONObject jsonObject = new JSONObject();

		JSONArray routeArray = new JSONArray();
		for (int i = 0; i < allRoutePartArray.size(); i++) {
			TYServerRoutePartV3 routePart = allRoutePartArray.get(i);
			JSONObject routeObject = routePart.buildJson();
			routeArray.put(routeObject);
		}
		try {
			jsonObject.put("routeResult", routeArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public String toString() {
		return String.format("[%d Parts, %d Floors]", allRoutePartArray.size(), allFloorRoutePartDict.size());
	}
}

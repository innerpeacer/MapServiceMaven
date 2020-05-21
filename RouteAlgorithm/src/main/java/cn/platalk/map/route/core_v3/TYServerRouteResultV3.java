package cn.platalk.map.route.core_v3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TYServerRouteResultV3 {
	private final List<TYServerRoutePartV3> allRoutePartArray = new ArrayList<>();
	private final Map<Integer, List<TYServerRoutePartV3>> allFloorRoutePartDict = new HashMap<>();

	public TYServerRouteResultV3(List<TYServerRoutePartV3> routePartArray) {
		allRoutePartArray.addAll(routePartArray);

		for (TYServerRoutePartV3 rp : routePartArray) {
			int floor = rp.getMapInfo().getFloorNumber();

			if (!allFloorRoutePartDict.containsKey(floor)) {
				List<TYServerRoutePartV3> array = new ArrayList<>();
				allFloorRoutePartDict.put(floor, array);
			}
			List<TYServerRoutePartV3> array = allFloorRoutePartDict.get(floor);
			array.add(rp);
		}
	}

	public JSONObject buildJson() {
		JSONObject jsonObject = new JSONObject();

		JSONArray routeArray = new JSONArray();
		for (TYServerRoutePartV3 routePart : allRoutePartArray) {
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

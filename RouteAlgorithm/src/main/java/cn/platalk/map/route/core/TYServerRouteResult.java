package cn.platalk.map.route.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TYServerRouteResult {
	private final List<TYServerRoutePart> allRoutePartArray = new ArrayList<>();
	private final Map<Integer, List<TYServerRoutePart>> allFloorRoutePartDict = new HashMap<>();

	public TYServerRouteResult(List<TYServerRoutePart> routePartArray) {
		allRoutePartArray.addAll(routePartArray);

		for (TYServerRoutePart rp : routePartArray) {
			int floor = rp.getMapInfo().getFloorNumber();

			if (!allFloorRoutePartDict.containsKey(floor)) {
				List<TYServerRoutePart> array = new ArrayList<>();
				allFloorRoutePartDict.put(floor, array);
			}
			List<TYServerRoutePart> array = allFloorRoutePartDict.get(floor);
			array.add(rp);
		}
	}

	public JSONObject buildJson() {
		JSONObject jsonObject = new JSONObject();

		JSONArray routeArray = new JSONArray();
		for (TYServerRoutePart routePart : allRoutePartArray) {
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
		return String.format("Route Parts: %d, Floor: %d",
				allRoutePartArray.size(), allFloorRoutePartDict.size());
	}
}

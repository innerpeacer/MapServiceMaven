package cn.platalk.brtmap.route.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TYServerRouteResult {
	private List<TYServerRoutePart> allRoutePartArray = new ArrayList<TYServerRoutePart>();
	private Map<Integer, List<TYServerRoutePart>> allFloorRoutePartDict = new HashMap<Integer, List<TYServerRoutePart>>();

	public TYServerRouteResult(List<TYServerRoutePart> routePartArray) {
		allRoutePartArray.addAll(routePartArray);

		for (int i = 0; i < routePartArray.size(); ++i) {
			TYServerRoutePart rp = routePartArray.get(i);
			int floor = rp.getMapInfo().getFloorNumber();

			if (!allFloorRoutePartDict.containsKey(floor)) {
				List<TYServerRoutePart> array = new ArrayList<TYServerRoutePart>();
				allFloorRoutePartDict.put(floor, array);
			}
			List<TYServerRoutePart> array = allFloorRoutePartDict.get(floor);
			array.add(rp);
		}
	}

	public JSONObject buildJson() {
		JSONObject jsonObject = new JSONObject();

		JSONArray routeArray = new JSONArray();
		for (int i = 0; i < allRoutePartArray.size(); i++) {
			TYServerRoutePart routePart = allRoutePartArray.get(i);
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

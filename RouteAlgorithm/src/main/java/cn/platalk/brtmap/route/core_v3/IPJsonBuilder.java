package cn.platalk.brtmap.route.core_v3;

import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.map.entity.base.TYLocalPoint;

class IPJsonBuilder {

	public static JSONObject buildLocalPoint(TYLocalPoint p) {
		JSONObject result = new JSONObject();
		try {
			result.put("x", p.getX());
			result.put("y", p.getY());
			result.put("floor", p.getFloor());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

}

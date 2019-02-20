package cn.platalk.brtmap.api.web.beacon;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.brtmap.api.TYParameterChecker;
import cn.platalk.map.entity.base.impl.TYLocatingBeacon;
import cn.platalk.mysql.beacon.TYBeaconDBAdapter;

@WebServlet("/web/getBeacon")
public class TYBrtGetWebBeaconServlet extends HttpServlet {
	private static final long serialVersionUID = -5796450412740461495L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		String callback = request.getParameter("callback");
		response.setContentType("text/json;charset=UTF-8");

		JSONObject jsonObject = new JSONObject();
		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			PrintWriter out = response.getWriter();
			try {
				jsonObject.put("success", false);
				jsonObject.put("description", "Invalid BuildingID: " + buildingID);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (callback == null) {
				out.print(jsonObject.toString());
			} else {
				out.print(String.format("%s(%s)", callback, jsonObject.toString()));
			}
			out.close();
			return;
		}

		TYBeaconDBAdapter beaconDB = new TYBeaconDBAdapter(buildingID);
		beaconDB.connectDB();
		List<TYLocatingBeacon> beaconList = beaconDB.getAllBeacons();
		beaconDB.disconnectDB();

		JSONArray beaconArray = new JSONArray();
		for (TYLocatingBeacon beacon : beaconList) {
			JSONObject beaconObject = new JSONObject();
			beaconObject.put("x", beacon.getLocation().getX());
			beaconObject.put("y", beacon.getLocation().getY());
			beaconObject.put("floor", beacon.getLocation().getFloor());
			beaconObject.put("uuid", beacon.getUUID());
			beaconObject.put("major", beacon.getMajor());
			beaconObject.put("minor", beacon.getMinor());
			beaconArray.put(beaconObject);
		}
		jsonObject.put("beacons", beaconArray);
		jsonObject.put("count", beaconList.size());

		try {
			jsonObject.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		if (callback == null) {
			out.print(jsonObject.toString());
		} else {
			out.print(String.format("%s(%s)", callback, jsonObject.toString()));
		}
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

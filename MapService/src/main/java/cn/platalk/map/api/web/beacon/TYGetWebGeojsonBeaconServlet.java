package cn.platalk.map.api.web.beacon;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.map.core.config.TYServerEnvironment;
import cn.platalk.map.core.web.beacon.TYWebBeaconGeojsonDataBuilder;
import cn.platalk.map.core.web.beacon.TYWebBeaconGeojsonDataPool;
import cn.platalk.map.entity.base.impl.TYLocatingBeacon;
import cn.platalk.mysql.beacon.TYBeaconDBAdapter;

@WebServlet("/web/geojson/getBeacon")
public class TYGetWebGeojsonBeaconServlet extends HttpServlet {
	private static final long serialVersionUID = -5796450412740461495L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request beacon geojson");
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

		JSONObject beaconDataObject = null;
		if (TYServerEnvironment.isWindows() || TYServerEnvironment.isLinux()) {
			if (TYWebBeaconGeojsonDataPool.existWebBeaconData(buildingID)) {
				beaconDataObject = TYWebBeaconGeojsonDataPool.getWebBeaconData(buildingID);
			} else {
				TYBeaconDBAdapter beaconDB = new TYBeaconDBAdapter(buildingID);
				beaconDB.connectDB();
				List<TYLocatingBeacon> beaconList = beaconDB.getAllBeacons();
				beaconDB.disconnectDB();

				try {
					beaconDataObject = TYWebBeaconGeojsonDataBuilder.generateBeaconDataObject(beaconList);
					TYWebBeaconGeojsonDataPool.setWebBeaconpData(buildingID, beaconDataObject);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else {
			TYBeaconDBAdapter beaconDB = new TYBeaconDBAdapter(buildingID);
			beaconDB.connectDB();
			List<TYLocatingBeacon> beaconList = beaconDB.getAllBeacons();
			beaconDB.disconnectDB();

			try {
				beaconDataObject = TYWebBeaconGeojsonDataBuilder.generateBeaconDataObject(beaconList);
				TYWebBeaconGeojsonDataPool.setWebBeaconpData(buildingID, beaconDataObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println(beaconList.size() + " beacons");
		}

		jsonObject = beaconDataObject;
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

package cn.platalk.brtmap.api.web.beacon;

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

import cn.platalk.brtmap.api.TYParameterChecker;
import cn.platalk.brtmap.core.config.TYServerEnviroment;
import cn.platalk.brtmap.core.web.beacon.TYBrtWebBeaconGeojsonDataBuilder;
import cn.platalk.brtmap.core.web.beacon.TYBrtWebBeaconGeojsonDataPool;
import cn.platalk.brtmap.db.beacon.TYBeaconDBAdapter;
import cn.platalk.brtmap.entity.base.impl.TYLocatingBeacon;

@WebServlet("/web/geojson/getBeacon")
public class TYBrtGetWebGeojsonBeaconServlet extends HttpServlet {
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
		if (TYServerEnviroment.isWindows() || TYServerEnviroment.isLinux()) {
			if (TYBrtWebBeaconGeojsonDataPool.existWebBeaconData(buildingID)) {
				beaconDataObject = TYBrtWebBeaconGeojsonDataPool.getWebBeaconData(buildingID);
			} else {
				TYBeaconDBAdapter beaconDB = new TYBeaconDBAdapter(buildingID);
				beaconDB.connectDB();
				List<TYLocatingBeacon> beaconList = beaconDB.getAllBeacons();
				beaconDB.disconnectDB();

				try {
					beaconDataObject = TYBrtWebBeaconGeojsonDataBuilder.generateBeaconDataObject(beaconList);
					TYBrtWebBeaconGeojsonDataPool.setWebBeaconpData(buildingID, beaconDataObject);
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
				beaconDataObject = TYBrtWebBeaconGeojsonDataBuilder.generateBeaconDataObject(beaconList);
				TYBrtWebBeaconGeojsonDataPool.setWebBeaconpData(buildingID, beaconDataObject);
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

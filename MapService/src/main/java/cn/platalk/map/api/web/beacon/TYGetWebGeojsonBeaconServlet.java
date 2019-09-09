package cn.platalk.map.api.web.beacon;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.common.TYIGeojsonFeature;
import cn.platalk.foundation.TYGeojsonBuilder;
import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.map.core.caching.TYCachingPool;
import cn.platalk.map.core.caching.TYCachingType;
import cn.platalk.map.core.config.TYServerEnvironment;
import cn.platalk.mysql.beacon.TYBeaconDBAdapter;

@WebServlet("/web/geojson/getBeacon")
public class TYGetWebGeojsonBeaconServlet extends HttpServlet {
	private static final long serialVersionUID = -5796450412740461495L;

	@Override
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
			if (TYCachingPool.existDataID(buildingID, TYCachingType.BeaconDataGeojson)) {
				beaconDataObject = (JSONObject) TYCachingPool.getCachingData(buildingID,
						TYCachingType.BeaconDataGeojson);
			} else {
				TYBeaconDBAdapter beaconDB = new TYBeaconDBAdapter(buildingID);
				beaconDB.connectDB();
				List<TYIGeojsonFeature> beaconFeatures = new ArrayList<TYIGeojsonFeature>(beaconDB.getAllBeacons());
				beaconDB.disconnectDB();
				beaconDataObject = TYGeojsonBuilder.buildFeatureCollection(beaconFeatures);
				TYCachingPool.setCachingData(buildingID, beaconDataObject, TYCachingType.BeaconDataGeojson);
			}
		} else {
			TYBeaconDBAdapter beaconDB = new TYBeaconDBAdapter(buildingID);
			beaconDB.connectDB();
			List<TYIGeojsonFeature> beaconFeatures = new ArrayList<TYIGeojsonFeature>(beaconDB.getAllBeacons());
			beaconDB.disconnectDB();

			beaconDataObject = TYGeojsonBuilder.buildFeatureCollection(beaconFeatures);
			TYCachingPool.setCachingData(buildingID, beaconDataObject, TYCachingType.BeaconDataGeojson);
			System.out.println(beaconFeatures.size() + " beacons");
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

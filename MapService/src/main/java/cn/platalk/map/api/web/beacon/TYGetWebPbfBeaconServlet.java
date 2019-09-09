package cn.platalk.map.api.web.beacon;

import java.io.IOException;
import java.io.OutputStream;
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
import cn.platalk.map.core.caching.TYCachingPool;
import cn.platalk.map.core.caching.TYCachingType;
import cn.platalk.map.core.config.TYServerEnvironment;
import cn.platalk.map.core.pbf.beacon.TYWebBeacon2PbfUtils;
import cn.platalk.map.entity.base.impl.TYLocatingBeacon;
import cn.platalk.mysql.beacon.TYBeaconDBAdapter;
import innerpeacer.beacon.pbf.TYBeaconPbf.TYLocatingBeaconListPbf;

@WebServlet("/web/pbf/getBeacon")
public class TYGetWebPbfBeaconServlet extends HttpServlet {

	private static final long serialVersionUID = -6157282965658968156L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request beacon pbf");
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

		TYLocatingBeaconListPbf beaconListPbf = null;
		if (TYServerEnvironment.isWindows() || TYServerEnvironment.isLinux()) {
			// if (TYWebBeaconPbfDataPool.existBeaconData(buildingID)) {
			// beaconListPbf = TYWebBeaconPbfDataPool.getBeaconData(buildingID);
			if (TYCachingPool.existDataID(buildingID, TYCachingType.BeaconDataPbf)) {
				beaconListPbf = (TYLocatingBeaconListPbf) TYCachingPool.getCachingData(buildingID,
						TYCachingType.BeaconDataPbf);
			} else {
				TYBeaconDBAdapter beaconDB = new TYBeaconDBAdapter(buildingID);
				beaconDB.connectDB();
				List<TYLocatingBeacon> beaconList = beaconDB.getAllBeacons();
				beaconDB.disconnectDB();

				beaconListPbf = TYWebBeacon2PbfUtils.beaconListToPbf(beaconList);
				TYCachingPool.setCachingData(buildingID, beaconListPbf, TYCachingType.BeaconDataPbf);
			}
		} else {
			TYBeaconDBAdapter beaconDB = new TYBeaconDBAdapter(buildingID);
			beaconDB.connectDB();
			List<TYLocatingBeacon> beaconList = beaconDB.getAllBeacons();
			beaconDB.disconnectDB();

			beaconListPbf = TYWebBeacon2PbfUtils.beaconListToPbf(beaconList);
			System.out.println(beaconList.size() + " beacons");
		}

		OutputStream output = response.getOutputStream();
		beaconListPbf.writeTo(output);
		output.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

package cn.platalk.brtmap.api.web.beacon;

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

import cn.platalk.brtmap.api.TYParameterChecker;
import cn.platalk.brtmap.core.config.TYServerEnviroment;
import cn.platalk.brtmap.core.pbf.beacon.TYBrtWebBeacon2PbfUtils;
import cn.platalk.brtmap.core.web.beacon.TYBrtWebBeaconPbfDataPool;
import cn.platalk.brtmap.db.beacon.TYBeaconDBAdapter;
import cn.platalk.brtmap.entity.base.impl.TYLocatingBeacon;
import innerpeacer.beacon.pbf.TYBeaconPbf.TYLocatingBeaconListPbf;

@WebServlet("/web/pbf/getBeacon")
public class TYBrtGetWebPbfBeaconServlet extends HttpServlet {

	private static final long serialVersionUID = -6157282965658968156L;

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
		if (TYServerEnviroment.isWindows() || TYServerEnviroment.isLinux()) {
			if (TYBrtWebBeaconPbfDataPool.existBeaconData(buildingID)) {
				beaconListPbf = TYBrtWebBeaconPbfDataPool.getBeaconData(buildingID);
			} else {
				TYBeaconDBAdapter beaconDB = new TYBeaconDBAdapter(buildingID);
				beaconDB.connectDB();
				List<TYLocatingBeacon> beaconList = beaconDB.getAllBeacons();
				beaconDB.disconnectDB();

				beaconListPbf = TYBrtWebBeacon2PbfUtils.beaconListToPbf(beaconList);
				TYBrtWebBeaconPbfDataPool.setBeaconData(buildingID, beaconListPbf);
			}
		} else {
			TYBeaconDBAdapter beaconDB = new TYBeaconDBAdapter(buildingID);
			beaconDB.connectDB();
			List<TYLocatingBeacon> beaconList = beaconDB.getAllBeacons();
			beaconDB.disconnectDB();

			beaconListPbf = TYBrtWebBeacon2PbfUtils.beaconListToPbf(beaconList);
			System.out.println(beaconList.size() + " beacons");
		}

		OutputStream output = response.getOutputStream();
		beaconListPbf.writeTo(output);
		output.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.common.TYIJsonFeature;
import cn.platalk.foundation.TYJsonBuilder;
import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.mysql.TYMysqlDBHelper;

@WebServlet("/web/getBeacon")
public class TYGetWebBeaconServlet extends HttpServlet {
	private static final long serialVersionUID = -5796450412740461495L;

	@Override
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

		List<TYIJsonFeature> beaconList = new ArrayList<TYIJsonFeature>(TYMysqlDBHelper.getAllBeacons(buildingID));
		JSONArray beaconArray = TYJsonBuilder.buildJsonArray(beaconList);

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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

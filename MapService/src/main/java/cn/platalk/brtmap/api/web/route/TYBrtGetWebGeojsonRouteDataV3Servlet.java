package cn.platalk.brtmap.api.web.route;

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
import cn.platalk.brtmap.core_v3.web.route.TYBrtWebRouteGeojsonDataBuilderV3;
import cn.platalk.brtmap.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.mysql.map.TYRouteDBAdapterV3;

@WebServlet("/web/geojson/GetRouteDataV3")
public class TYBrtGetWebGeojsonRouteDataV3Servlet extends HttpServlet {

	private static final long serialVersionUID = -652247259445376881L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request geojson");
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
			}
			out.close();
			return;
		}

		// ====================================================
		JSONObject routeDataObject = null;
		TYRouteDBAdapterV3 routeDB = new TYRouteDBAdapterV3(buildingID);
		routeDB.connectDB();
		List<TYIRouteLinkRecordV3> linkList = routeDB.getAllLinkRecords();
		List<TYIRouteNodeRecordV3> nodeList = routeDB.getAllNodeRecords();
		routeDB.disconnectDB();
		try {
			routeDataObject = TYBrtWebRouteGeojsonDataBuilderV3.generateRouteDataObject(linkList, nodeList);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		jsonObject = routeDataObject;
		try {
			jsonObject.put("success", true);
			jsonObject.put("Link", linkList.size());
			jsonObject.put("Node", nodeList.size());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// ====================================================

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

package cn.platalk.route.api.service;

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

import cn.platalk.brtmap.entity.base.TYIMapInfo;
import cn.platalk.brtmap.entity.base.TYIRouteLinkRecord;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecord;
import cn.platalk.brtmap.entity.base.TYLocalPoint;
import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.route.core.TYServerRouteManager;
import cn.platalk.brtmap.route.core.TYServerRouteResult;
import cn.platalk.mysql.map.TYBuildingDBAdapter;
import cn.platalk.mysql.map.TYMapInfoDBAdapter;
import cn.platalk.mysql.map.TYRouteDBAdapter;
import cn.platalk.route.api.TYApiResponse;
import cn.platalk.route.core.routemanager.TYRouteManagerCollection;

@WebServlet("/route/RouteService")
public class TYRouteServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void initRouteManager(String buildingID) {
		TYBuildingDBAdapter buildingDB = new TYBuildingDBAdapter();
		buildingDB.connectDB();
		TYBuilding currentBuilding = buildingDB.getBuilding(buildingID);
		buildingDB.disconnectDB();

		TYMapInfoDBAdapter mapInfoDB = new TYMapInfoDBAdapter();
		mapInfoDB.connectDB();
		List<TYIMapInfo> mapInfoList = new ArrayList<TYIMapInfo>();
		mapInfoList.addAll(mapInfoDB.getMapInfos(buildingID));
		mapInfoDB.disconnectDB();

		if (currentBuilding != null && mapInfoList.size() > 0) {
			TYRouteDBAdapter routeDB = new TYRouteDBAdapter(currentBuilding.getBuildingID());
			routeDB.connectDB();
			List<TYIRouteNodeRecord> nodeList = new ArrayList<TYIRouteNodeRecord>();
			nodeList.addAll(routeDB.getAllNodeRecords());
			List<TYIRouteLinkRecord> linkList = new ArrayList<TYIRouteLinkRecord>();
			linkList.addAll(routeDB.getAllLinkRecords());
			routeDB.disconnectDB();

			
			TYServerRouteManager routeManager = new TYServerRouteManager(currentBuilding, mapInfoList, nodeList,
					linkList);

			TYRouteManagerCollection.AddRouteManager(currentBuilding.getBuildingID(), routeManager);
		}
	}

	protected TYServerRouteManager getRouteManager(String buildingID) {
		return TYRouteManagerCollection.GetRouteManager(buildingID);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		String buildingID = request.getParameter("buildingID");

		if (buildingID == null) {
			PrintWriter out = response.getWriter();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put(TYApiResponse.TY_RESPONSE_STATUS, false);
				jsonObject.put(TYApiResponse.TY_RESPONSE_DESCRIPTION, "buildingID cannot be null");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			out.print(jsonObject.toString());
			return;
		}

		if (!TYRouteManagerCollection.ExistRouteManager(buildingID)) {
			initRouteManager(buildingID);
		}

		String callback = request.getParameter("callback");
		double startX = Double.parseDouble(request.getParameter("startX"));
		double startY = Double.parseDouble(request.getParameter("startY"));
		int startFloor = Integer.parseInt(request.getParameter("startF"));

		double endX = Double.parseDouble(request.getParameter("endX"));
		double endY = Double.parseDouble(request.getParameter("endY"));
		int endFloor = Integer.parseInt(request.getParameter("endF"));

		TYLocalPoint startPoint = new TYLocalPoint(startX, startY, startFloor);
		TYLocalPoint endPoint = new TYLocalPoint(endX, endY, endFloor);

		TYServerRouteManager routeManager = TYRouteManagerCollection.GetRouteManager(buildingID);
		TYServerRouteResult routeResult = null;
		if (routeManager != null) {
			routeResult = routeManager.requestRoute(startPoint, endPoint);
		}

		JSONObject jsonObject = null;
		if (routeResult != null) {
			jsonObject = routeResult.buildJson();
			try {
				jsonObject.put(TYApiResponse.TY_RESPONSE_STATUS, true);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			jsonObject = new JSONObject();
			try {
				jsonObject.put(TYApiResponse.TY_RESPONSE_STATUS, false);
			} catch (JSONException e) {
				e.printStackTrace();
			}
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

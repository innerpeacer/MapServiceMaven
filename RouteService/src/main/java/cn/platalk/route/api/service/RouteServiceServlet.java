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

import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.map.entity.base.TYIMapInfo;
import cn.platalk.map.entity.base.TYIRouteLinkRecord;
import cn.platalk.map.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.TYIRouteNodeRecord;
import cn.platalk.map.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.map.entity.base.TYLocalPoint;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.route.core.TYServerMultiRouteManager;
import cn.platalk.map.route.core.TYServerMultiRouteResult;
import cn.platalk.map.route.core_v3.TYServerMultiRouteManagerV3;
import cn.platalk.map.route.core_v3.TYServerMultiRouteResultV3;
import cn.platalk.map.route.core_v3.TYServerRouteOptions;
import cn.platalk.mysql.map.TYBuildingDBAdapter;
import cn.platalk.mysql.map.TYMapDataDBAdapter;
import cn.platalk.mysql.map.TYMapInfoDBAdapter;
import cn.platalk.mysql.map.TYRouteDBAdapter;
import cn.platalk.mysql.map.TYRouteDBAdapterV3;
import cn.platalk.route.api.TYApiResponse;
import cn.platalk.route.core.routemanager.TYMultiRouteManagerCollection;
import cn.platalk.route.core_v3.routemanager.TYMultiRouteManagerCollectionV3;

@WebServlet("/route/routeService")
public class RouteServiceServlet extends HttpServlet {
	private static final long serialVersionUID = -5426270768806441823L;

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

		if (currentBuilding.getRouteURL().equals("V3") || currentBuilding.getRouteURL().equals("V4")) {
			if (currentBuilding != null && mapInfoList.size() > 0) {
				TYRouteDBAdapterV3 routeDB = new TYRouteDBAdapterV3(currentBuilding.getBuildingID());
				routeDB.connectDB();
				List<TYIRouteNodeRecordV3> nodeList = new ArrayList<TYIRouteNodeRecordV3>();
				nodeList.addAll(routeDB.getAllNodeRecords());
				List<TYIRouteLinkRecordV3> linkList = new ArrayList<TYIRouteLinkRecordV3>();
				linkList.addAll(routeDB.getAllLinkRecords());
				routeDB.disconnectDB();

				TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(currentBuilding.getBuildingID());
				mapDB.connectDB();
				List<TYIMapDataFeatureRecord> mapRecordList = new ArrayList<TYIMapDataFeatureRecord>();
				mapRecordList.addAll(mapDB.getAllMapDataRecords());
				mapDB.disconnectDB();

				TYServerMultiRouteManagerV3 routeManager = new TYServerMultiRouteManagerV3(currentBuilding, mapInfoList,
						nodeList, linkList, mapRecordList);
				TYMultiRouteManagerCollectionV3.AddRouteManager(currentBuilding.getBuildingID(), routeManager);
			}
		} else {
			if (currentBuilding != null && mapInfoList.size() > 0) {
				TYRouteDBAdapter routeDB = new TYRouteDBAdapter(currentBuilding.getBuildingID());
				routeDB.connectDB();
				List<TYIRouteNodeRecord> nodeList = new ArrayList<TYIRouteNodeRecord>();
				nodeList.addAll(routeDB.getAllNodeRecords());
				List<TYIRouteLinkRecord> linkList = new ArrayList<TYIRouteLinkRecord>();
				linkList.addAll(routeDB.getAllLinkRecords());
				routeDB.disconnectDB();

				TYServerMultiRouteManager routeManager = new TYServerMultiRouteManager(currentBuilding, mapInfoList,
						nodeList, linkList);
				TYMultiRouteManagerCollection.AddRouteManager(currentBuilding.getBuildingID(), routeManager);
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		String buildingID = request.getParameter("buildingID");
		String startParams = request.getParameter("start");
		String endParams = request.getParameter("end");
		String stopParams = request.getParameter("stops");
		String rearrangeStops = request.getParameter("rearrangeStops");
		String vehicle = request.getParameter("vehicle");
		String ignoreParams = request.getParameter("ignore");
		String sameFloorFirst = request.getParameter("sameFloorFirst");

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

		if (startParams == null || endParams == null) {
			PrintWriter out = response.getWriter();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put(TYApiResponse.TY_RESPONSE_STATUS, false);
				jsonObject.put(TYApiResponse.TY_RESPONSE_DESCRIPTION, "start or end cannot be null");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			out.print(jsonObject.toString());
			return;
		}

		TYBuildingDBAdapter buildingDB = new TYBuildingDBAdapter();
		buildingDB.connectDB();
		TYBuilding currentBuilding = buildingDB.getBuilding(buildingID);
		buildingDB.disconnectDB();

		if (currentBuilding.getRouteURL().equals("V3") || currentBuilding.getRouteURL().equals("V4")) {
			if (!TYMultiRouteManagerCollectionV3.ExistRouteManager(buildingID)) {
				initRouteManager(buildingID);
			}
		} else {
			if (!TYMultiRouteManagerCollection.ExistRouteManager(buildingID)) {
				initRouteManager(buildingID);
			}
		}

		String callback = request.getParameter("callback");

		String[] startArray = startParams.split(",");
		double startX = Double.parseDouble(startArray[0]);
		double startY = Double.parseDouble(startArray[1]);
		int startFloor = Integer.parseInt(startArray[2]);

		String[] endArray = endParams.split(",");
		double endX = Double.parseDouble(endArray[0]);
		double endY = Double.parseDouble(endArray[1]);
		int endFloor = Integer.parseInt(endArray[2]);

		TYLocalPoint startPoint = new TYLocalPoint(startX, startY, startFloor);
		TYLocalPoint endPoint = new TYLocalPoint(endX, endY, endFloor);
		List<TYLocalPoint> stopPoints = new ArrayList<TYLocalPoint>();
		if (stopParams != null) {
			String[] stopArray = stopParams.split(",");
			for (int i = 0; i < stopArray.length; i += 3) {
				double spX = Double.parseDouble(stopArray[i]);
				double spY = Double.parseDouble(stopArray[i + 1]);
				int spFloor = Integer.parseInt(stopArray[i + 2]);
				TYLocalPoint sp = new TYLocalPoint(spX, spY, spFloor);
				stopPoints.add(sp);
			}
		}

		boolean rearrange = true;
		if (rearrangeStops != null) {
			rearrange = Boolean.parseBoolean(rearrangeStops);
		}

		// if (rearrange) {
		// System.out.println("重排");
		// } else {
		// System.out.println("不重排");
		// }

		JSONObject jsonObject = null;
		if (currentBuilding.getRouteURL().equals("V3") || currentBuilding.getRouteURL().equals("V4")) {
			TYServerRouteOptions options = new TYServerRouteOptions();
			if (rearrangeStops != null) {
				rearrange = Boolean.parseBoolean(rearrangeStops);
				options.setRearrangeStops(rearrange);
			}

			if (vehicle != null) {
				boolean isVehicle = Boolean.parseBoolean(vehicle);
				if (isVehicle) {
					options.setLinkType(1);
				}
			}

			if (sameFloorFirst != null) {
				boolean isSameFloorFirst = Boolean.parseBoolean(sameFloorFirst);
				options.setSameFloorFirst(isSameFloorFirst);
				// System.out.println("params sameFloorFirst: " +
				// sameFloorFirst);
				// System.out.println("params isSameFloorFirst: " +
				// isSameFloorFirst);
			}

			List<String> ignoredList = new ArrayList<String>();
			if (ignoreParams != null) {
				String[] ignoreArray = ignoreParams.split(",");
				for (int i = 0; i < ignoreArray.length; i++) {
					ignoredList.add(ignoreArray[i]);
				}
			}
			options.setIgnoredNodeCategoryList(ignoredList);

			TYServerMultiRouteManagerV3 routeManager = TYMultiRouteManagerCollectionV3.GetRouteManager(buildingID);
			TYServerMultiRouteResultV3 routeResult = null;
			if (routeManager != null) {
				routeResult = routeManager.requestRoute(startPoint, endPoint, stopPoints, options);
			}
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
		} else {
			TYServerMultiRouteManager routeManager = TYMultiRouteManagerCollection.GetRouteManager(buildingID);
			TYServerMultiRouteResult routeResult = null;
			if (routeManager != null) {
				routeResult = routeManager.requestRoute(startPoint, endPoint, stopPoints, rearrange);
			}
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

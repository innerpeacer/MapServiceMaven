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

import cn.platalk.map.caching.TYCachingPool;
import cn.platalk.map.caching.TYCachingType;
import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.map.entity.base.TYIMapInfo;
import cn.platalk.map.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYLocalPoint;
import cn.platalk.map.route.core_v3.TYServerMultiRouteManagerV3;
import cn.platalk.map.route.core_v3.TYServerMultiRouteResultV3;
import cn.platalk.map.route.core_v3.TYServerRouteOptions;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.route.api.TYApiResponse;

@WebServlet("/route/RouteServiceV3")
public class TYRouteServiceServletV3 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void initRouteManager(String buildingID) {
		TYBuilding currentBuilding = TYMysqlDBHelper.getBuilding(buildingID);
		List<TYIMapInfo> mapInfoList = new ArrayList<TYIMapInfo>(TYMysqlDBHelper.getMapInfos(buildingID));

		if (currentBuilding != null && mapInfoList.size() > 0) {
			List<TYIRouteNodeRecordV3> nodeList = TYMysqlDBHelper
					.getAllRouteNodeRecordV3(currentBuilding.getBuildingID());
			List<TYIRouteLinkRecordV3> linkList = TYMysqlDBHelper
					.getAllRouteLinkRecordV3(currentBuilding.getBuildingID());
			List<TYIMapDataFeatureRecord> mapRecordList = new ArrayList<TYIMapDataFeatureRecord>(
					TYMysqlDBHelper.getMapDataRecords(buildingID));

			TYServerMultiRouteManagerV3 routeManager = new TYServerMultiRouteManagerV3(currentBuilding, mapInfoList,
					nodeList, linkList, mapRecordList);
			TYCachingPool.setCachingData(currentBuilding.getBuildingID(), routeManager,
					TYCachingType.MultiRouteManagerV3);
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

		if (!TYCachingPool.existDataID(buildingID, TYCachingType.MultiRouteManagerV3)) {
			initRouteManager(buildingID);
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

		// if (rearrange) {
		// System.out.println("重排");
		// } else {
		// System.out.println("不重排");
		// }

		TYServerRouteOptions options = new TYServerRouteOptions();
		if (rearrangeStops != null) {
			boolean rearrange = Boolean.parseBoolean(rearrangeStops);
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
			// System.out.println("params sameFloorFirst: " + sameFloorFirst);
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

		TYServerMultiRouteManagerV3 routeManager = (TYServerMultiRouteManagerV3) TYCachingPool
				.getCachingData(buildingID, TYCachingType.MultiRouteManagerV3);
		TYServerMultiRouteResultV3 routeResult = null;
		if (routeManager != null) {
			routeResult = routeManager.requestRoute(startPoint, endPoint, stopPoints, options);
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

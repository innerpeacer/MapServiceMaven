package cn.platalk.route.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.platalk.map.caching.TYCachingPool;
import cn.platalk.map.caching.TYCachingType;
import cn.platalk.map.entity.base.TYIMapInfo;
import cn.platalk.map.entity.base.TYIRouteLinkRecord;
import cn.platalk.map.entity.base.TYIRouteNodeRecord;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYLocalPoint;
import cn.platalk.map.route.core.TYServerMultiRouteManager;
import cn.platalk.map.route.core.TYServerMultiRouteResult;
import cn.platalk.map.route.core_v3.TYServerRouteOptions;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;
import cn.platalk.servlet.TYParameterParser;

@WebServlet("/route/RouteServiceV2")
public class TYRouteServiceServletV2 extends TYBaseHttpServlet {
	private static final long serialVersionUID = 1L;

	protected void initRouteManager(String buildingID) {
		TYBuilding currentBuilding = TYMysqlDBHelper.getBuilding(buildingID);
		List<TYIMapInfo> mapInfoList = new ArrayList<TYIMapInfo>(TYMysqlDBHelper.getMapInfos(buildingID));

		if (currentBuilding != null && mapInfoList.size() > 0) {
			List<TYIRouteNodeRecord> nodeList = TYMysqlDBHelper.getAllRouteNodeRecord(currentBuilding.getBuildingID());
			List<TYIRouteLinkRecord> linkList = TYMysqlDBHelper.getAllRouteLinkRecord(currentBuilding.getBuildingID());

			TYServerMultiRouteManager routeManager = new TYServerMultiRouteManager(currentBuilding, mapInfoList,
					nodeList, linkList);
			TYCachingPool.setCachingData(currentBuilding.getBuildingID(), routeManager,
					TYCachingType.MultiRouteManager);
		}
	}

	protected TYServerMultiRouteManager getRouteManager(String buildingID) {
		return (TYServerMultiRouteManager) TYCachingPool.getCachingData(buildingID, TYCachingType.MultiRouteManager);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		String buildingID = request.getParameter("buildingID");
		TYLocalPoint startPoint = TYParameterParser.getLocalPoint(request, "start");
		TYLocalPoint endPoint = TYParameterParser.getLocalPoint(request, "end");
		List<TYLocalPoint> stopPoints = TYParameterParser.getLocalPointList(request, "stops");
		boolean rearrange = TYParameterParser.getBoolean(request, "rearrangeStops",
				TYServerRouteOptions.DEFAULT_REARRANGE_STOPS);

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		if (startPoint == null || endPoint == null) {
			respondError(request, response, "start or end cannot be null");
			return;
		}

		if (!TYCachingPool.existDataID(buildingID, TYCachingType.MultiRouteManager)) {
			initRouteManager(buildingID);
		}

		TYServerMultiRouteManager routeManager = (TYServerMultiRouteManager) TYCachingPool.getCachingData(buildingID,
				TYCachingType.MultiRouteManager);
		TYServerMultiRouteResult routeResult = null;
		if (routeManager != null) {
			routeResult = routeManager.requestRoute(startPoint, endPoint, stopPoints, rearrange);
		}

		JSONObject jsonObject = null;
		if (routeResult != null) {
			jsonObject = routeResult.buildJson();
			respondResult(request, response, jsonObject);
		} else {
			respondError(request, response, "routeResult is null");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

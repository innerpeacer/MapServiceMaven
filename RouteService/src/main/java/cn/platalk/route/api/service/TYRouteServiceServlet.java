package cn.platalk.route.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.map.entity.base.impl.map.TYBuilding;
import cn.platalk.map.entity.base.impl.map.TYLocalPoint;
import cn.platalk.map.entity.base.map.TYIMapInfo;
import cn.platalk.map.entity.base.map.TYIRouteLinkRecord;
import cn.platalk.map.entity.base.map.TYIRouteNodeRecord;
import org.json.JSONObject;

import cn.platalk.map.caching.TYCachingPool;
import cn.platalk.map.caching.TYCachingType;
import cn.platalk.map.route.core.TYServerRouteManager;
import cn.platalk.map.route.core.TYServerRouteResult;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;
import cn.platalk.servlet.TYParameterParser;

@WebServlet("/route/RouteService")
public class TYRouteServiceServlet extends TYBaseHttpServlet {
	private static final long serialVersionUID = 1L;

	protected void initRouteManager(String buildingID) {
		TYBuilding currentBuilding = TYMysqlDBHelper.getBuilding(buildingID);
		List<TYIMapInfo> mapInfoList = new ArrayList<TYIMapInfo>(TYMysqlDBHelper.getMapInfos(buildingID));

		if (currentBuilding != null && mapInfoList.size() > 0) {
			List<TYIRouteNodeRecord> nodeList = TYMysqlDBHelper.getAllRouteNodeRecord(currentBuilding.getBuildingID());
			List<TYIRouteLinkRecord> linkList = TYMysqlDBHelper.getAllRouteLinkRecord(currentBuilding.getBuildingID());

			TYServerRouteManager routeManager = new TYServerRouteManager(currentBuilding, mapInfoList, nodeList,
					linkList);
			TYCachingPool.setCachingData(currentBuilding.getBuildingID(), routeManager, TYCachingType.RouteManager);
		}
	}

	protected TYServerRouteManager getRouteManager(String buildingID) {
		return (TYServerRouteManager) TYCachingPool.getCachingData(buildingID, TYCachingType.RouteManager);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		String buildingID = request.getParameter("buildingID");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		if (!TYCachingPool.existDataID(buildingID, TYCachingType.RouteManager)) {
			initRouteManager(buildingID);
		}

		double startX = TYParameterParser.getDouble(request, "startX");
		double startY = TYParameterParser.getDouble(request, "startY");
		int startFloor = TYParameterParser.getInteger(request, "startF");

		double endX = TYParameterParser.getDouble(request, "endX");
		double endY = TYParameterParser.getDouble(request, "endY");
		int endFloor = TYParameterParser.getInteger(request, "endF");

		TYLocalPoint startPoint = new TYLocalPoint(startX, startY, startFloor);
		TYLocalPoint endPoint = new TYLocalPoint(endX, endY, endFloor);

		TYServerRouteManager routeManager = (TYServerRouteManager) TYCachingPool.getCachingData(buildingID,
				TYCachingType.RouteManager);

		TYServerRouteResult routeResult = null;
		if (routeManager != null) {
			routeResult = routeManager.requestRoute(startPoint, endPoint);
		}

		JSONObject jsonObject;
		if (routeResult != null) {
			jsonObject = routeResult.buildJson();
			respondResult(request, response, jsonObject);
		} else {
			respondError(request, response, "routeResult is null");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}

}

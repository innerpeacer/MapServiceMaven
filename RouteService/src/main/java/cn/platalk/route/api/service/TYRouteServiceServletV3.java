package cn.platalk.route.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;
import cn.platalk.servlet.TYParameterParser;

@WebServlet("/route/RouteServiceV3")
public class TYRouteServiceServletV3 extends TYBaseHttpServlet {
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
			throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		String buildingID = request.getParameter("buildingID");
		TYLocalPoint startPoint = TYParameterParser.getLocalPoint(request, "start");
		TYLocalPoint endPoint = TYParameterParser.getLocalPoint(request, "end");
		List<TYLocalPoint> stopPoints = TYParameterParser.getLocalPointList(request, "stops");

		boolean rearrange = TYParameterParser.getBoolean(request, "rearrangeStops",
				TYServerRouteOptions.DEFAULT_REARRANGE_STOPS);
		boolean isVehicle = TYParameterParser.getBoolean(request, "vehicle", false);
		boolean isSameFloorFirst = TYParameterParser.getBoolean(request, "sameFloorFirst",
				TYServerRouteOptions.DEFAULT_SAME_FLOOR_FIRST);
		List<String> ignoredList = TYParameterParser.getStringList(request, "ignore");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		if (startPoint == null || endPoint == null) {
			respondError(request, response, "start or end cannot be null");
			return;
		}

		if (!TYCachingPool.existDataID(buildingID, TYCachingType.MultiRouteManagerV3)) {
			initRouteManager(buildingID);
		}

		TYServerRouteOptions options = new TYServerRouteOptions();
		options.setRearrangeStops(rearrange);
		if (isVehicle) {
			options.setLinkType(1);
		}
		options.setSameFloorFirst(isSameFloorFirst);
		options.setIgnoredNodeCategoryList(ignoredList);

		TYServerMultiRouteManagerV3 routeManager = (TYServerMultiRouteManagerV3) TYCachingPool
				.getCachingData(buildingID, TYCachingType.MultiRouteManagerV3);
		TYServerMultiRouteResultV3 routeResult = null;
		if (routeManager != null) {
			routeResult = routeManager.requestRoute(startPoint, endPoint, stopPoints, options);
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

package cn.platalk.map.api.web.map;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.map.caching.TYCachingPool;
import cn.platalk.map.caching.TYCachingType;
import cn.platalk.map.entity.base.impl.map.TYBuilding;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;

@WebServlet("/web/ResetMapData")
public class TYGetWebResetMapDataServlet extends TYBaseHttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String buildingID = request.getParameter("buildingID");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		TYBuilding building = TYMysqlDBHelper.getBuilding(buildingID);
		if (building == null) {
			respondError(request, response, errorDescriptionNotExistBuildingID(buildingID));
			return;
		}

		TYCachingPool.resetDataByBuildingID(buildingID, TYCachingType.IndoorDataPbf);
		TYCachingPool.resetDataByBuildingID(buildingID, TYCachingType.IndoorDataGeojson);

		Map<String, Object> result = new HashMap<>();
		result.put(RESPONSE_JSON_KEY_DESCRIPTION, "Reset " + buildingID);
		respondResult(request, response, result);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}

}

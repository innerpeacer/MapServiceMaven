package cn.platalk.map.api.web.map;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import cn.platalk.foundation.TYJsonBuilder;
import cn.platalk.map.core.web.TYWebMapFields;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYCity;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;

@WebServlet("/web/GetCBM")
public class TYGetWebCBMServlet extends TYBaseHttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		TYCity city = TYMysqlDBHelper.getCity(building.getCityID());
		JSONArray cityJsonArray = TYJsonBuilder.buildJsonArray(city);
		JSONArray buildingJsonArray = TYJsonBuilder.buildJsonArray(building);
		JSONArray mapInfoJsonArray = TYJsonBuilder.buildMapInfoJsonArray(TYMysqlDBHelper.getMapInfos(buildingID));

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(TYWebMapFields.KEY_WEB_CITIES, cityJsonArray);
		result.put(TYWebMapFields.KEY_WEB_BUILDINGS, buildingJsonArray);
		result.put(TYWebMapFields.KEY_WEB_MAPINFOS, mapInfoJsonArray);
		respondResult(request, response, result);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}

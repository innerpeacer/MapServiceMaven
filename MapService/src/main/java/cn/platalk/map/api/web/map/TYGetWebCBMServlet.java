package cn.platalk.map.api.web.map;

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
import cn.platalk.map.core.web.TYWebMapFields;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYCity;
import cn.platalk.map.entity.base.impl.TYMapInfo;
import cn.platalk.mysql.TYMysqlDBHelper;

@WebServlet("/web/GetCBM")
public class TYGetWebCBMServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		String callback = request.getParameter("callback");
		response.setContentType("text/json;charset=UTF-8");

		JSONObject jsonObject = new JSONObject();

		System.out.println("GetCBM");

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

		TYBuilding building = TYMysqlDBHelper.getBuilding(buildingID);
		if (building == null) {
			PrintWriter out = response.getWriter();
			try {
				jsonObject.put("success", false);
				jsonObject.put("description", "Building Not Exist: " + buildingID);
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

		TYCity city = TYMysqlDBHelper.getCity(building.getCityID());
		List<TYMapInfo> mapInfoList = TYMysqlDBHelper.getMapInfos(buildingID);

		JSONArray cityJsonArray = TYJsonBuilder.buildJsonArray(city);
		JSONArray buildingJsonArray = TYJsonBuilder.buildJsonArray(building);
		JSONArray mapInfoJsonArray = TYJsonBuilder.buildJsonArray(new ArrayList<TYIJsonFeature>(mapInfoList));

		try {
			jsonObject.put(TYWebMapFields.KEY_WEB_CITIES, cityJsonArray);
			jsonObject.put(TYWebMapFields.KEY_WEB_BUILDINGS, buildingJsonArray);
			jsonObject.put(TYWebMapFields.KEY_WEB_MAPINFOS, mapInfoJsonArray);
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
		doPost(request, response);
	}

}

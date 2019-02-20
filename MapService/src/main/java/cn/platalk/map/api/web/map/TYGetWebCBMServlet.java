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

import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.map.core.web.TYWebMapFields;
import cn.platalk.map.core.web.TYWebMapObjectBuilder;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYCity;
import cn.platalk.map.entity.base.impl.TYMapInfo;
import cn.platalk.mysql.map.TYBuildingDBAdapter;
import cn.platalk.mysql.map.TYCityDBAdapter;
import cn.platalk.mysql.map.TYMapInfoDBAdapter;

@WebServlet("/web/GetCBM")
public class TYGetWebCBMServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		String callback = request.getParameter("callback");
		response.setContentType("text/json;charset=UTF-8");

		JSONObject jsonObject = new JSONObject();

		System.out.println("GetCBM");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			PrintWriter out = response.getWriter();
			try {
				jsonObject.put("success", false);
				jsonObject.put("description", "Invalid BuildingID: "
						+ buildingID);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (callback == null) {
				out.print(jsonObject.toString());
			} else {
				out.print(String.format("%s(%s)", callback,
						jsonObject.toString()));
			}
			out.close();
			return;
		}

		TYBuildingDBAdapter buildingDB = new TYBuildingDBAdapter();
		buildingDB.connectDB();
		TYBuilding building = buildingDB.getBuilding(buildingID);
		buildingDB.disconnectDB();

		if (building == null) {
			PrintWriter out = response.getWriter();
			try {
				jsonObject.put("success", false);
				jsonObject.put("description", "Building Not Exist: "
						+ buildingID);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (callback == null) {
				out.print(jsonObject.toString());
			} else {
				out.print(String.format("%s(%s)", callback,
						jsonObject.toString()));
			}
			out.close();
			return;
		}

		TYCityDBAdapter cityDB = new TYCityDBAdapter();
		cityDB.connectDB();
		TYCity city = cityDB.getCity(building.getCityID());
		cityDB.disconnectDB();

		TYMapInfoDBAdapter db = new TYMapInfoDBAdapter();
		db.connectDB();
		List<TYMapInfo> mapInfoList = db.getMapInfos(buildingID);
		db.disconnectDB();

		List<TYCity> cityList = new ArrayList<TYCity>();
		cityList.add(city);
		JSONArray cityJsonArray = new JSONArray();
		JSONObject cityObject;
		try {
			cityObject = TYWebMapObjectBuilder.generateCityJson(city);
			cityJsonArray.put(cityObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		List<TYBuilding> buildingList = new ArrayList<TYBuilding>();
		buildingList.add(building);
		JSONArray buildingJsonArray = new JSONArray();
		JSONObject buildingObject;
		try {
			buildingObject = TYWebMapObjectBuilder
					.generateBuildingJson(building);
			buildingJsonArray.put(buildingObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONArray mapInfoJsonArray = new JSONArray();
		try {

			for (int i = 0; i < mapInfoList.size(); ++i) {
				TYMapInfo mapInfo = mapInfoList.get(i);
				JSONObject mapInfoObject = TYWebMapObjectBuilder
						.generateMapInfoJson(mapInfo);
				mapInfoJsonArray.put(mapInfoObject);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(TYWebMapFields.KEY_WEB_CITIES, cityJsonArray);
			jsonObject.put(TYWebMapFields.KEY_WEB_BUILDINGS,
					buildingJsonArray);
			jsonObject
					.put(TYWebMapFields.KEY_WEB_MAPINFOS, mapInfoJsonArray);
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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}

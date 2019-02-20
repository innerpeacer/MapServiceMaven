package cn.platalk.brtmap.api.web.map;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.brtmap.api.TYParameterChecker;
import cn.platalk.brtmap.core.web.TYBrtWebMapGeojsonDataPool;
import cn.platalk.brtmap.core.web.TYBrtWebMapPbfDataPool;
import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.mysql.map.TYBuildingDBAdapter;

@WebServlet("/web/ResetMapData")
public class TYBrtGetWebResetMapDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		String callback = request.getParameter("callback");
		response.setContentType("text/json;charset=UTF-8");

		JSONObject jsonObject = new JSONObject();

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

		TYBrtWebMapPbfDataPool.resetWebMapDataByBuildingID(buildingID);
		TYBrtWebMapGeojsonDataPool.resetWebMapDataByBuildingID(buildingID);

		try {
			jsonObject.put("success", true);
			jsonObject.put("description", "Reset " + buildingID);

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

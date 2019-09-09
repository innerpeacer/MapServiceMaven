package cn.platalk.map.api.web.map.file;

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

import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.vectortile.fontbuilder.TYExtractLabelString;
import cn.platalk.mysql.TYMysqlDBHelper;

@WebServlet("/web/GetLabelString")
public class TYGetLabelStringServlet extends HttpServlet {
	private static final long serialVersionUID = 7540952724296868847L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		response.setContentType("text/json;charset=UTF-8");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			PrintWriter out = response.getWriter();
			out.println("Invalid BuildingID: " + buildingID);
			out.close();
		}

		TYBuilding building = TYMysqlDBHelper.getBuilding(buildingID);

		if (building == null) {
			PrintWriter out = response.getWriter();
			System.out.println("BuildingID: " + buildingID + " not Exist!");
			out.println("BuildingID: " + buildingID + " not Exist!");
			out.close();
			return;
		}

		List<TYIMapDataFeatureRecord> allRecords = new ArrayList<TYIMapDataFeatureRecord>(
				TYMysqlDBHelper.getMapDataRecords(buildingID));

		System.out.println(allRecords.size() + " records");
		String labelString = TYExtractLabelString.GetLabelString(allRecords);

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("records", allRecords.size());
			jsonObject.put("content", labelString);
			jsonObject.put("length", labelString.length());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println(jsonObject.toString());
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

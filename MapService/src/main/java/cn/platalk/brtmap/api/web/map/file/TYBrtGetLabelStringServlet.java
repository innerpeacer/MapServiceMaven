package cn.platalk.brtmap.api.web.map.file;

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

import cn.platalk.brtmap.api.TYParameterChecker;
import cn.platalk.brtmap.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.mysql.map.TYBuildingDBAdapter;
import cn.platalk.mysql.map.TYMapDataDBAdapter;

@WebServlet("/web/GetLabelString")
public class TYBrtGetLabelStringServlet extends HttpServlet {
	private static final long serialVersionUID = 7540952724296868847L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		response.setContentType("text/json;charset=UTF-8");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			PrintWriter out = response.getWriter();
			out.println("Invalid BuildingID: " + buildingID);
			out.close();
		}

		TYBuildingDBAdapter db = new TYBuildingDBAdapter();
		db.connectDB();
		TYBuilding building = db.getBuilding(buildingID);
		db.disconnectDB();

		if (building == null) {
			PrintWriter out = response.getWriter();
			System.out.println("BuildingID: " + buildingID + " not Exist!");
			out.println("BuildingID: " + buildingID + " not Exist!");
			out.close();
			return;
		}

		TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(buildingID);
		mapDB.connectDB();
		// List<TYMapDataFeatureRecord> allRecords =
		// mapDB.getAllMapDataRecords();
		List<TYIMapDataFeatureRecord> allRecords = new ArrayList<TYIMapDataFeatureRecord>();
		allRecords.addAll(mapDB.getAllMapDataRecords());
		mapDB.disconnectDB();

		System.out.println(allRecords.size() + " records");
		String labelString = ExtractLabelString.GetLabelString(allRecords);

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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

package cn.platalk.brtmap.api.web.map.file;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.brtmap.api.TYParameterChecker;
import cn.platalk.brtmap.core.web.file.TYBrtWebGeneratingMapFileTask;
import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.mysql.map.TYBuildingDBAdapter;

@WebServlet("/web/GenerateMapData")
public class TYBrtGenerateMapDataServlet extends HttpServlet {
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

		TYBrtWebGeneratingMapFileTask task = new TYBrtWebGeneratingMapFileTask(
				building);
		task.startTask();

		PrintWriter out = response.getWriter();
		out.println("Generate Box Data for BuildingID: " + buildingID);
		out.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

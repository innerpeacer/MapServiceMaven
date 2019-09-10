package cn.platalk.map.api.web.map.file;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.map.api.web.map.TYBaseHttpServlet;
import cn.platalk.map.core.web.file.TYWebGeneratingMapFileTask;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.mysql.TYMysqlDBHelper;

@WebServlet("/web/GenerateMapData")
public class TYGenerateMapDataServlet extends TYBaseHttpServlet {
	private static final long serialVersionUID = 7540952724296868847L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		response.setContentType("text/json;charset=UTF-8");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		TYBuilding building = TYMysqlDBHelper.getBuilding(buildingID);
		if (building == null) {
			respondError(request, response, errorDescriptionNotExistBuildingID(buildingID));
			return;
		}

		TYWebGeneratingMapFileTask task = new TYWebGeneratingMapFileTask(building);
		task.startTask();

		PrintWriter out = response.getWriter();
		out.println("Generate Box Data for BuildingID: " + buildingID);
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

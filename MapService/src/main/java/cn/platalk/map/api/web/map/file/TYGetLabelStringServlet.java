package cn.platalk.map.api.web.map.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.vectortile.fontbuilder.TYExtractLabelString;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;

@WebServlet("/web/GetLabelString")
public class TYGetLabelStringServlet extends TYBaseHttpServlet {
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

		List<TYIMapDataFeatureRecord> allRecords = new ArrayList<TYIMapDataFeatureRecord>(
				TYMysqlDBHelper.getMapDataRecords(buildingID));

		System.out.println(allRecords.size() + " records");
		String labelString = TYExtractLabelString.GetLabelString(allRecords);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("records", allRecords.size());
		jsonObject.put("content", labelString);
		jsonObject.put("length", labelString.length());
		respondResult(request, response, jsonObject);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

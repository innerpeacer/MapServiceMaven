package cn.platalk.map.api.web.beacon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.platalk.common.TYIJsonFeature;
import cn.platalk.foundation.TYJsonBuilder;
import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.map.api.web.map.TYBaseHttpServlet;
import cn.platalk.mysql.TYMysqlDBHelper;

@WebServlet("/web/getBeacon")
public class TYGetWebBeaconServlet extends TYBaseHttpServlet {
	private static final long serialVersionUID = -5796450412740461495L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		List<TYIJsonFeature> beaconList = new ArrayList<TYIJsonFeature>(TYMysqlDBHelper.getAllBeacons(buildingID));
		JSONArray beaconArray = TYJsonBuilder.buildJsonArray(beaconList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("beacons", beaconArray);
		jsonObject.put("count", beaconList.size());
		respondResult(request, response, jsonObject);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

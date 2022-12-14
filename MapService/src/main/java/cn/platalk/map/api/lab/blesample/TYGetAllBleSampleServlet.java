package cn.platalk.map.api.lab.blesample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.platalk.common.TYIJsonFeature;
import cn.platalk.foundation.TYJsonBuilder;
import cn.platalk.lab.blesample.entity.WTBleSample;
import cn.platalk.lab.blesample.mysql.WTMysqlBleSampleDBAdapter;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;

@WebServlet("/lab/GetAllSamples")
public class TYGetAllBleSampleServlet extends TYBaseHttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println("request all sample");
		String buildingID = request.getParameter("buildingID");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		WTMysqlBleSampleDBAdapter db = new WTMysqlBleSampleDBAdapter();
		db.connectDB();
		db.createTableIfNotExist();
		List<TYIJsonFeature> sampleList = new ArrayList<TYIJsonFeature>(db.getAllSample(buildingID));
		db.disconnectDB();

		JSONObject jsonObject = new JSONObject();
		JSONObject dataObject = new JSONObject();
		dataObject.put(WTBleSample.KEY_LAB_SAMPLE_POINTS, TYJsonBuilder.buildJsonArray(sampleList));
		jsonObject.put("data", dataObject);
		respondResult(request, response, jsonObject);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}

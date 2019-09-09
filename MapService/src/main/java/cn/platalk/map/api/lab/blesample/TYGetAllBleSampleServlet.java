package cn.platalk.map.api.lab.blesample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.lab.blesample.entity.WTBleSample;
import cn.platalk.lab.blesample.mysql.WTMysqlBleSampleDBAdapter;
import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.map.core.lab.blesample.WTBleSampleFields;
import cn.platalk.map.core.lab.blesample.WTBleSampleObjectBuilder;

@WebServlet("/lab/GetAllSamples")
public class TYGetAllBleSampleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		String callback = request.getParameter("callback");
		response.setContentType("text/json;charset=UTF-8");

		JSONObject jsonObject = new JSONObject();

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

		WTMysqlBleSampleDBAdapter db = new WTMysqlBleSampleDBAdapter();
		db.connectDB();
		db.createTableIfNotExist();
		List<WTBleSample> sampleList = db.getAllSample(buildingID);
		db.disconnectDB();

		try {
			jsonObject.put(WTBleSampleFields.KEY_LAB_SAMPLE_POINTS,
					WTBleSampleObjectBuilder.generateSamplePointList(sampleList));
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}

package cn.platalk.map.api.lab.blesample;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.platalk.lab.blesample.entity.WTBleSample;
import cn.platalk.lab.blesample.mysql.WTMysqlBleSampleDBAdapter;
import cn.platalk.map.api.web.map.TYBaseHttpServlet;

@WebServlet("/lab/GetSampleJson")
public class TYGetSampleJsonServlet extends TYBaseHttpServlet {
	private static final long serialVersionUID = -652247259445376881L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request sample json");
		String sampleID = request.getParameter("sampleID");

		WTMysqlBleSampleDBAdapter db = new WTMysqlBleSampleDBAdapter();
		db.connectDB();
		WTBleSample sample = db.getSample(sampleID);
		db.disconnectDB();

		if (sample == null) {
			String description = "Sample Not Exist: " + sampleID;
			respondError(request, response, description);
			return;
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sample", sample.toJson());
		respondResult(request, response, jsonObject);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

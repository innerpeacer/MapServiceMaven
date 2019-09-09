package cn.platalk.map.api.lab.blesample;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.lab.blesample.entity.WTBleSample;
import cn.platalk.lab.blesample.mysql.WTMysqlBleSampleDBAdapter;
import cn.platalk.map.core.lab.blesample.WTBleSampleObjectBuilder;

@WebServlet("/lab/GetSampleJson")
public class TYGetSampleJsonServlet extends HttpServlet {
	private static final long serialVersionUID = -652247259445376881L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sampleID = request.getParameter("sampleID");
		response.setContentType("text/plain;charset=UTF-8");

		WTMysqlBleSampleDBAdapter db = new WTMysqlBleSampleDBAdapter();
		db.connectDB();
		WTBleSample sample = db.getSample(sampleID);
		db.disconnectDB();

		JSONObject jsonObject = new JSONObject();

		if (sample == null) {
			PrintWriter out = response.getWriter();
			try {
				jsonObject.put("success", false);
				jsonObject.put("description", "Sample Not Exist: " + sampleID);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			out.print(jsonObject.toString());
			out.close();
			return;
		}

		jsonObject.put("success", true);
		jsonObject.put("sample", WTBleSampleObjectBuilder.generateSampleJson(sample));

		PrintWriter out = response.getWriter();
		out.print(jsonObject.toString());
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

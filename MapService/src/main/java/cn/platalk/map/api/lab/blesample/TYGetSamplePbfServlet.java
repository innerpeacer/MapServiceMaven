package cn.platalk.map.api.lab.blesample;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.lab.blesample.entity.WTBleSample;
import cn.platalk.lab.blesample.mysql.WTMysqlBleSampleDBAdapter;
import cn.platalk.lab.blesample.pbf.proto.WTBleSamplePbf.BleSamplePbf;
import cn.platalk.lab.blesample.pbf.wrapper.WTBleSample2PbfUtils;

@WebServlet("/lab/GetSamplePbf")
public class TYGetSamplePbfServlet extends HttpServlet {
	private static final long serialVersionUID = -652247259445376881L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sampleID = request.getParameter("sampleID");
		response.setContentType("text/plain;charset=UTF-8");

		WTMysqlBleSampleDBAdapter db = new WTMysqlBleSampleDBAdapter();
		db.connectDB();
		WTBleSample sample = db.getSample(sampleID);
		db.disconnectDB();

		BleSamplePbf pbf = WTBleSample2PbfUtils.toBleSamplePbf(sample);

		OutputStream output = response.getOutputStream();
		pbf.writeTo(output);
		output.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

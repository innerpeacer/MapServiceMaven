package cn.platalk.brtmap.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.brtmap.core.config.TYServerEnviroment;

@WebServlet(urlPatterns = "/initEnv", loadOnStartup = 1)
public class TYInitServlet extends HttpServlet {
	private static final long serialVersionUID = -3535588845108542020L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		StringBuffer buffer = new StringBuffer();
		if (TYServerEnviroment.resourceManager == null) {
			buffer.append("init failed!");
		} else {
			buffer.append("init success!").append("\n");
			buffer.append("DB Host: ").append(TYServerEnviroment.resourceManager.getDBHost()).append("\n");
		}

		PrintWriter out = response.getWriter();
		out.print(buffer.toString());
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		TYServerEnviroment.initialize();
	}

}

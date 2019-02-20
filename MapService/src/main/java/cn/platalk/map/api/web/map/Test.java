package cn.platalk.map.api.web.map;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test")
public class Test extends HttpServlet {

	private static final long serialVersionUID = -8022875600236828734L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		// response.setIntHeader("refresh", 1);
		final PrintWriter out = response.getWriter();

		for (long i = 0; i < 10000000000L; i++) {
			// try {
			// Thread.sleep(1000);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			if (i % 10000000 == 0) {
				System.out.print(" " + i / 10000000);
				out.print(" " + i / 10000000);
				out.flush();
			}

			// out.flush();
		}

		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// for (int i = 0; i < 100; i++) {
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		//
		// System.out.println("index: " + i);
		// out.println("Index: " + i);
		// // out.flush();
		// }
		// }
		// }).start();

		// Random r = new Random();
		// System.out.println(r.nextInt(10) + 1);
		// response.getWriter().write(r.nextInt(10) + 1 + "");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}

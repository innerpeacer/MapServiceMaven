package cn.platalk.brtmap.api.web.map;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.brtmap.core.config.TYServerEnviroment;

@WebServlet("/GetOSInfo")
public class TYBrtGetOSInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 6384701773672319414L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();

		Properties sysProperty = System.getProperties(); // 系统属性

		out.println("是否远程Window系统：" + TYServerEnviroment.isWindows());
		out.println("是否Linux系统：" + TYServerEnviroment.isLinux());
		out.println("Java的运行环境版本：" + sysProperty.getProperty("java.version"));
		out.println("Java的安装路径：" + sysProperty.getProperty("java.home"));

		out.println("Java运行时环境规范版本：" + sysProperty.getProperty("java.specification.version"));
		out.println("Java的类路径：" + sysProperty.getProperty("java.class.path"));
		out.println("加载库时搜索的路径列表：" + sysProperty.getProperty("java.library.path"));
		out.println("默认的临时文件路径：" + sysProperty.getProperty("java.io.tmpdir"));
		out.println("一个或多个扩展目录的路径：" + sysProperty.getProperty("java.ext.dirs"));
		out.println("操作系统的名称：" + sysProperty.getProperty("os.name"));
		out.println("操作系统的构架：" + sysProperty.getProperty("os.arch"));
		out.println("操作系统的版本：" + sysProperty.getProperty("os.version"));
		out.println("文件分隔符：" + sysProperty.getProperty("file.separator"));
		out.println("路径分隔符：" + sysProperty.getProperty("path.separator"));

		out.println("用户的账户名称：" + sysProperty.getProperty("user.name"));
		out.println("用户的主目录：" + sysProperty.getProperty("user.home"));
		out.println("用户的当前工作目录：" + sysProperty.getProperty("user.dir"));

		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

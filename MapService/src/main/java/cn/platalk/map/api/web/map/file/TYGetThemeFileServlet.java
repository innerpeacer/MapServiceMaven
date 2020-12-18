package cn.platalk.map.api.web.map.file;

import cn.platalk.map.core.config.TYMapEnvironment;
import cn.platalk.servlet.TYBaseHttpServlet;
import org.json.JSONArray;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/web/GetThemeFiles")
public class TYGetThemeFileServlet extends TYBaseHttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String themeDirString = TYMapEnvironment.GetRawThemeDir();
		File themeDir = new File(themeDirString);
		File[] themeFiles = themeDir.listFiles();

		JSONArray fileArray = new JSONArray();
		for (int i = 0; i < themeFiles.length; ++i) {
			File f = themeFiles[i];
			String fileName = f.getName();
			if (fileName.toUpperCase().indexOf("THEME") != -1) {
				fileArray.put(f.getName());
			}
		}

		Map<String, Object> result = new HashMap<>();
		result.put("themeFiles", fileArray);
		respondResult(request, response, result);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
}

package cn.platalk.map.api.web.map;

import cn.platalk.map.entity.base.impl.map.TYTheme;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/web/GetTheme")
public class TYGetWebThemeServlet extends TYBaseHttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String themeID = request.getParameter("themeID");

		List<TYTheme> themeList = new ArrayList<>();
		if (themeID == null) {
			themeList = TYMysqlDBHelper.getAllThemes();
		} else {
			TYTheme theme = TYMysqlDBHelper.getTheme(themeID);
			if (theme != null) {
				themeList.add(theme);
			}
		}

		JSONObject result = new JSONObject();
		JSONArray themeArray = new JSONArray();
		for (int i = 0; i < themeList.size(); ++i) {
			TYTheme theme = themeList.get(i);
			themeArray.put(theme.toJson());
		}
		result.put("themes", themeArray);
		respondResult(request, response, result);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}

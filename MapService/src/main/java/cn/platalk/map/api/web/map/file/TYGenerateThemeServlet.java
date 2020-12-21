package cn.platalk.map.api.web.map.file;

import cn.platalk.core.pbf.cbm.TYThemePbf;
import cn.platalk.map.core.config.TYMapEnvironment;
import cn.platalk.map.entity.base.impl.map.TYTheme;
import cn.platalk.map.entity.base.map.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.map.TYIIconSymbolRecord;
import cn.platalk.map.entity.base.map.TYIIconTextSymbolRecord;
import cn.platalk.map.vectortile.cbm.pbf.TYThemePbfBuilder;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.utils.TYMD5Utils;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/web/GenerateTheme")
public class TYGenerateThemeServlet extends TYBaseHttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String themeID = request.getParameter("themeID");

		if (themeID == null) {
			respondError(request, response, String.format("ThemeID is null"));
			return;
		}

		TYTheme theme = TYMysqlDBHelper.getTheme(themeID);
		if (theme == null) {
			respondError(request, response, String.format("Theme data is null: %s", themeID));
			return;
		}

		File themeDir = new File(TYMapEnvironment.GetThemeDir());
		if (!themeDir.exists()) {
			themeDir.mkdirs();
		}
		TYThemePbfBuilder.SetThemeRoot(themeDir.toString());

		List<TYIFillSymbolRecord> fillSymbolRecords = TYMysqlDBHelper.getThemeFillSymbolRecords(themeID);
		List<TYIIconTextSymbolRecord> iconTextSymbolRecords = TYMysqlDBHelper.getThemeIconTextSymbolRecords(themeID);
		TYThemePbfBuilder.generateThemePbf(theme, fillSymbolRecords, iconTextSymbolRecords);

		JSONObject result = new JSONObject();
		result.put("theme", theme.toJson());
		result.put("fill", fillSymbolRecords.size());
		result.put("iconText", iconTextSymbolRecords.size());
		result.put("path", TYThemePbfBuilder.getThemePbfPath(themeID));

		respondResult(request, response, result);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
}

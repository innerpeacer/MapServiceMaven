package cn.platalk.map.api.web.map.file;

import cn.platalk.map.core.config.TYMapEnvironment;
import cn.platalk.map.entity.base.impl.map.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.map.TYIconTextSymbolRecord;
import cn.platalk.map.entity.base.impl.map.TYTheme;
import cn.platalk.mysql.map.TYThemeDBAdapter;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.sqlite.map.IPSqliteThemeDBAdapter;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/web/ParseTheme")
public class TYParseThemeDataServlet extends TYBaseHttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = request.getParameter("file");
		if (fileName == null) {
			respondError(request, response, String.format("Parameter file is null"));
			return;
		}

		File themeDir = new File(TYMapEnvironment.GetRawThemeDir());
		File themeFile = new File(themeDir, fileName);
		if (!themeFile.exists()) {
			respondError(request, response, String.format("Theme file not exist: " + themeFile.toString()));
			return;
		}

		IPSqliteThemeDBAdapter sqliteDB = new IPSqliteThemeDBAdapter(themeFile.toString());
		sqliteDB.open();
		List<TYTheme> themeList = sqliteDB.getThemeRecords();
		List<TYFillSymbolRecord> fillSymbolRecordList = sqliteDB.getFillSymbolRecords();
		List<TYIconTextSymbolRecord> iconTextSymbolRecordList = sqliteDB.getIconTextSymbolRecords();
		sqliteDB.close();

		if (themeList.size() == 0) {
			respondError(request, response, String.format("No Theme record found"));
			return;
		}

		TYTheme theme = themeList.get(0);

		TYThemeDBAdapter db = new TYThemeDBAdapter();
		db.connectDB();
		db.createTableIfNotExist();
		int themeCount = db.insertOrUpdateTheme(theme);
		db.deleteFillSymbolRecords(theme.getThemeID());
		db.deleteIconTextSymbolRecords(theme.getThemeID());
		int fillCount = db.insertFillSymbolRecords(fillSymbolRecordList, theme.getThemeID());
		int iconTextCount = db.insertIconTextSymbolRecords(iconTextSymbolRecordList, theme.getThemeID());
		db.disconnectDB();

		JSONObject result = new JSONObject();
		result.put("theme", theme.getThemeID());
		result.put("themeCount", themeCount);
		result.put("fill", fillCount);
		result.put("iconText", iconTextCount);
		respondResult(request, response, result);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
}

package cn.platalk.map.api.web.map.file;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.map.core.config.TYMapEnvironment;
import cn.platalk.map.entity.base.impl.map.TYBuilding;
import cn.platalk.map.entity.base.map.TYIMapDataFeatureRecord;
import cn.platalk.map.vectortile.fontbuilder.TYFontBuilder;
import cn.platalk.map.vectortile.fontbuilder.TYFontBuilder.TYBrtFontBuilderInterface;
import cn.platalk.map.vectortile.fontbuilder.TYFontSettings;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;

@WebServlet("/web/BuildMapGlyphs")
public class TYBuildMapGlyphsServlet extends TYBaseHttpServlet {
	private static final long serialVersionUID = 7540952724296868847L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String buildingID = request.getParameter("buildingID");
		String fontName = request.getParameter("fontName");
		String fontType = request.getParameter("fontType");

		response.setContentType("text/json;charset=UTF-8");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		TYBuilding building = TYMysqlDBHelper.getBuilding(buildingID);
		if (building == null) {
			respondError(request, response, errorDescriptionNotExistBuildingID(buildingID));
			return;
		}

		if (fontName == null || fontType == null) {
			String description = "font cannot be null: " + fontName + "." + fontType;
			respondError(request, response, description);
			return;
		}

		List<TYIMapDataFeatureRecord> records = new ArrayList<TYIMapDataFeatureRecord>(
				TYMysqlDBHelper.getMapDataRecords(buildingID));

		final StringBuffer result = new StringBuffer();
		final Map<String, Boolean> status = new HashMap<>();

		TYFontSettings.setScriptPath(TYMapEnvironment.GetNodePath(), // node??????????????????????????????which
																		// node???????????????
				TYMapEnvironment.GetFontScriptPath(), // BrtFontTool??????node-fontmin.js??????
				TYMapEnvironment.GetGlyphsScriptPath());// BrtFontTool??????node-glyphs.js??????
		TYFontSettings.setFontDirs(TYMapEnvironment.GetFontDir(), // ????????????????????????
				TYMapEnvironment.GetFontminDir(), // ???????????????????????????????????????
				TYMapEnvironment.GetGlyphsDir()); // ?????????????????????,
													// ?????????????????????????????????glyphs
		TYFontBuilder builder = new TYFontBuilder(buildingID);
		builder.addListener(new TYBrtFontBuilderInterface() {

			@Override
			public void didFinishBuildingFont() {
				status.put("status", true);
			}

			@Override
			public void didFailedBuildingFont(String info) {
				status.put("status", false);
				result.append(info);
			}

			@Override
			public void buildingProgress(String info) {
				result.append(info);
			}
		});
		builder.buildFont(fontName, fontType, records);

		PrintWriter out = response.getWriter();
		out.println("Status: " + status.get("status"));
		out.println();
		out.println("-----------------------------------------------");
		out.println(result.toString());
		out.println("-----------------------------------------------");
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
}

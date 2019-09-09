package cn.platalk.map.api.web.map.file;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.map.core.config.TYMapEnvironment;
import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.vectortile.fontbuilder.TYFontBuilder;
import cn.platalk.map.vectortile.fontbuilder.TYFontBuilder.TYBrtFontBuilderInterface;
import cn.platalk.map.vectortile.fontbuilder.TYFontSettings;
import cn.platalk.mysql.TYMysqlDBHelper;

@WebServlet("/web/BuildMapGlyphs")
public class TYBuildMapGlyphsServlet extends HttpServlet {
	private static final long serialVersionUID = 7540952724296868847L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		String fontName = request.getParameter("fontName");
		String fontType = request.getParameter("fontType");

		response.setContentType("text/json;charset=UTF-8");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			PrintWriter out = response.getWriter();
			out.println("Invalid BuildingID: " + buildingID);
			out.close();
			return;
		}

		TYBuilding building = TYMysqlDBHelper.getBuilding(buildingID);

		if (building == null) {
			PrintWriter out = response.getWriter();
			System.out.println("BuildingID: " + buildingID + " not Exist!");
			out.println("BuildingID: " + buildingID + " not Exist!");
			out.close();
			return;
		}

		if (fontName == null || fontType == null) {
			PrintWriter out = response.getWriter();
			out.println("font cannot be null: " + fontName + "." + fontType);
			out.close();
			return;
		}

		List<TYIMapDataFeatureRecord> records = new ArrayList<TYIMapDataFeatureRecord>(
				TYMysqlDBHelper.getMapDataRecords(buildingID));

		final StringBuffer result = new StringBuffer();
		final Map<String, Boolean> status = new HashMap<String, Boolean>();

		TYFontSettings.setScriptPath(TYMapEnvironment.GetNodePath(), // node路径，最好用全称，用which
																		// node的路径就行
				TYMapEnvironment.GetFontScriptPath(), // BrtFontTool下面node-fontmin.js路径
				TYMapEnvironment.GetGlyphsScriptPath());// BrtFontTool下面node-glyphs.js路径
		TYFontSettings.setFontDirs(TYMapEnvironment.GetFontDir(), // 源字体文件夹路径
				TYMapEnvironment.GetFontminDir(), // 设置一个中间数据文件夹路径
				TYMapEnvironment.GetGlyphsDir()); // 输出文件夹路径,
													// 设置为最终数据目录下的glyphs
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
			throws ServletException, IOException {
		doGet(request, response);
	}
}

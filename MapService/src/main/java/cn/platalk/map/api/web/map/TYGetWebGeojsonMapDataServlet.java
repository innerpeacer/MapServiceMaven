package cn.platalk.map.api.web.map;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.map.core.config.TYServerEnvironment;
import cn.platalk.map.core.web.TYWebMapGeojsonDataBuilder;
import cn.platalk.map.core.web.TYWebMapGeojsonDataPool;
import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.mysql.map.TYMapDataDBAdapter;
import cn.platalk.mysql.map.TYSymbolDBAdapter;

@WebServlet("/web/geojson/GetMapData")
public class TYGetWebGeojsonMapDataServlet extends HttpServlet {

	private static final long serialVersionUID = -652247259445376881L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TYServerEnvironment.initialize();

		System.out.println("request geojson");
		String buildingID = request.getParameter("buildingID");
		String mapID = request.getParameter("mapID");
		String callback = request.getParameter("callback");
		response.setContentType("text/json;charset=UTF-8");

		JSONObject jsonObject = new JSONObject();

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			PrintWriter out = response.getWriter();
			try {
				jsonObject.put("success", false);
				jsonObject.put("description", "Invalid BuildingID: " + buildingID);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (callback == null) {
				out.print(jsonObject.toString());
			} else {
				out.print(String.format("%s(%s)", callback, jsonObject.toString()));
			}
			out.close();
			return;
		}

		JSONObject mapDataObject = null;
		if (TYServerEnvironment.isWindows() || TYServerEnvironment.isLinux()) {
			if (TYWebMapGeojsonDataPool.existWebMapData(mapID)) {
				mapDataObject = TYWebMapGeojsonDataPool.getWebMapData(mapID);
			} else {
				TYMapDataDBAdapter mapdb = new TYMapDataDBAdapter(buildingID);
				mapdb.connectDB();
				List<TYMapDataFeatureRecord> mapDataRecordList = mapdb.getAllMapDataRecords(mapID);
				mapdb.disconnectDB();

				TYSymbolDBAdapter symboldb = new TYSymbolDBAdapter();
				symboldb.connectDB();
				List<TYFillSymbolRecord> fillSymbolList = symboldb.getFillSymbolRecords(buildingID);
				List<TYIconSymbolRecord> iconSymbolList = symboldb.getIconSymbolRecords(buildingID);
				symboldb.disconnectDB();

				try {
					mapDataObject = TYWebMapGeojsonDataBuilder.generateMapDataObject(mapDataRecordList,
							fillSymbolList, iconSymbolList);
					TYWebMapGeojsonDataPool.setWebMapData(mapID, mapDataObject);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else {
			TYMapDataDBAdapter mapdb = new TYMapDataDBAdapter(buildingID);
			mapdb.connectDB();
			List<TYMapDataFeatureRecord> mapDataRecordList = mapdb.getAllMapDataRecords(mapID);
			mapdb.disconnectDB();

			TYSymbolDBAdapter symboldb = new TYSymbolDBAdapter();
			symboldb.connectDB();
			List<TYFillSymbolRecord> fillSymbolList = symboldb.getFillSymbolRecords(buildingID);
			List<TYIconSymbolRecord> iconSymbolList = symboldb.getIconSymbolRecords(buildingID);
			symboldb.disconnectDB();

			try {
				mapDataObject = TYWebMapGeojsonDataBuilder.generateMapDataObject(mapDataRecordList, fillSymbolList,
						iconSymbolList);
				TYWebMapGeojsonDataPool.setWebMapData(mapID, mapDataObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println(mapDataRecordList.size() + " records");
		}

		jsonObject = mapDataObject;
		try {
			jsonObject.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		if (callback == null) {
			out.print(jsonObject.toString());
		} else {
			out.print(String.format("%s(%s)", callback, jsonObject.toString()));
		}
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

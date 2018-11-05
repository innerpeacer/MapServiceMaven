package cn.platalk.brtmap.api.web.map;

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

import cn.platalk.brtmap.api.TYParameterChecker;
import cn.platalk.brtmap.core.config.TYServerEnviroment;
import cn.platalk.brtmap.core.web.TYBrtWebMapGeojsonDataBuilder;
import cn.platalk.brtmap.core.web.TYBrtWebMapGeojsonDataPool;
import cn.platalk.brtmap.db.map.TYMapDataDBAdapter;
import cn.platalk.brtmap.db.map.TYSymbolDBAdapter;
import cn.platalk.brtmap.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;

@WebServlet("/web/geojson/GetMapData")
public class TYBrtGetWebGeojsonMapDataServlet extends HttpServlet {

	private static final long serialVersionUID = -652247259445376881L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TYServerEnviroment.initialize();

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
		if (TYServerEnviroment.isWindows() || TYServerEnviroment.isLinux()) {
			if (TYBrtWebMapGeojsonDataPool.existWebMapData(mapID)) {
				mapDataObject = TYBrtWebMapGeojsonDataPool.getWebMapData(mapID);
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
					mapDataObject = TYBrtWebMapGeojsonDataBuilder.generateMapDataObject(mapDataRecordList,
							fillSymbolList, iconSymbolList);
					TYBrtWebMapGeojsonDataPool.setWebMapData(mapID, mapDataObject);
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
				mapDataObject = TYBrtWebMapGeojsonDataBuilder.generateMapDataObject(mapDataRecordList, fillSymbolList,
						iconSymbolList);
				TYBrtWebMapGeojsonDataPool.setWebMapData(mapID, mapDataObject);
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

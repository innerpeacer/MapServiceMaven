package cn.platalk.map.api.web.map;

import innerpeacer.mapdata.pbf.TYMapDataPbf.TYIndoorDataPbf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.map.core.pbf.TYWebMapPbfDataBuilder;
import cn.platalk.map.core.web.TYWebMapPbfDataPool;
import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.mysql.map.TYMapDataDBAdapter;
import cn.platalk.mysql.map.TYSymbolDBAdapter;

@WebServlet("/web/pbf/GetMapData")
public class TYGetWebPbfMapDataServlet extends HttpServlet {

	private static final long serialVersionUID = -652247259445376881L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		String mapID = request.getParameter("mapID");
		// String callback = request.getParameter("callback");
		response.setContentType("text/plain;charset=UTF-8");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			PrintWriter out = response.getWriter();
			out.println("Invalid BuildingID: " + buildingID);
			out.close();
		}

		TYIndoorDataPbf dataPbf = null;
		if (TYWebMapPbfDataPool.existWebMapData(mapID)) {
			dataPbf = TYWebMapPbfDataPool.getWebMapData(mapID);
		} else {
			TYMapDataDBAdapter mapdb = new TYMapDataDBAdapter(buildingID);
			mapdb.connectDB();
			List<TYMapDataFeatureRecord> mapDataRecordList = mapdb
					.getAllMapDataRecords(mapID);
			mapdb.disconnectDB();

			TYSymbolDBAdapter symboldb = new TYSymbolDBAdapter();
			symboldb.connectDB();
			List<TYFillSymbolRecord> fillSymbolList = symboldb
					.getFillSymbolRecords(buildingID);
			List<TYIconSymbolRecord> iconSymbolList = symboldb
					.getIconSymbolRecords(buildingID);
			symboldb.disconnectDB();

			dataPbf = TYWebMapPbfDataBuilder.generateMapDataObject(mapID,
					mapDataRecordList, fillSymbolList, iconSymbolList);
			TYWebMapPbfDataPool.setWebMapData(mapID, dataPbf);
		}

		// TYIndoorDataPbf dataPbf = null;
		// {
		// TYMapDataDBAdapter mapdb = new TYMapDataDBAdapter(buildingID);
		// mapdb.connectDB();
		// List<TYMapDataFeatureRecord> mapDataRecordList = mapdb
		// .getAllMapDateRecords(mapID);
		// mapdb.disconnectDB();
		//
		// TYSymbolDBAdapter symboldb = new TYSymbolDBAdapter();
		// symboldb.connectDB();
		// List<TYFillSymbolRecord> fillSymbolList = symboldb
		// .getFillSymbolRecords(buildingID);
		// List<TYIconSymbolRecord> iconSymbolList = symboldb
		// .getIconSymbolRecords(buildingID);
		// symboldb.disconnectDB();
		// dataPbf = TYBrtWebMapPbfDataBuilder.generateMapDataObject(mapID,
		// mapDataRecordList, fillSymbolList, iconSymbolList);
		// }

		// JSONObject jsonObject
		// PrintWriter out = response.getWriter();
		// if (callback == null) {
		// out.print(jsonObject.toString());
		// } else {
		// out.print(String.format("%s(%s)", callback, jsonObject.toString()));
		// }
		// out.close();

		OutputStream output = response.getOutputStream();
		dataPbf.writeTo(output);
		output.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

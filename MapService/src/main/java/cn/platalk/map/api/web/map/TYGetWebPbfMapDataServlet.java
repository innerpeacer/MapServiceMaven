package cn.platalk.map.api.web.map;

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
import cn.platalk.map.core.caching.TYCachingPool;
import cn.platalk.map.core.caching.TYCachingType;
import cn.platalk.map.core.pbf.TYWebMapPbfDataBuilder;
import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.mysql.map.TYMapDataDBAdapter;
import cn.platalk.mysql.map.TYSymbolDBAdapter;
import innerpeacer.mapdata.pbf.TYMapDataPbf.TYIndoorDataPbf;

@WebServlet("/web/pbf/GetMapData")
public class TYGetWebPbfMapDataServlet extends HttpServlet {

	private static final long serialVersionUID = -652247259445376881L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		if (TYCachingPool.existDataID(mapID, TYCachingType.IndoorDataPbf)) {
			dataPbf = (TYIndoorDataPbf) TYCachingPool.getCachingData(mapID, TYCachingType.IndoorDataPbf);
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

			dataPbf = TYWebMapPbfDataBuilder.generateMapDataObject(mapID, mapDataRecordList, fillSymbolList,
					iconSymbolList);
			TYCachingPool.setCachingData(mapID, dataPbf, TYCachingType.IndoorDataPbf);
		}
		// if (TYWebMapPbfDataPool.existWebMapData(mapID)) {
		// dataPbf = TYWebMapPbfDataPool.getWebMapData(mapID);
		// } else {
		// TYMapDataDBAdapter mapdb = new TYMapDataDBAdapter(buildingID);
		// mapdb.connectDB();
		// List<TYMapDataFeatureRecord> mapDataRecordList =
		// mapdb.getAllMapDataRecords(mapID);
		// mapdb.disconnectDB();
		//
		// TYSymbolDBAdapter symboldb = new TYSymbolDBAdapter();
		// symboldb.connectDB();
		// List<TYFillSymbolRecord> fillSymbolList =
		// symboldb.getFillSymbolRecords(buildingID);
		// List<TYIconSymbolRecord> iconSymbolList =
		// symboldb.getIconSymbolRecords(buildingID);
		// symboldb.disconnectDB();
		//
		// dataPbf = TYWebMapPbfDataBuilder.generateMapDataObject(mapID,
		// mapDataRecordList, fillSymbolList,
		// iconSymbolList);
		// TYWebMapPbfDataPool.setWebMapData(mapID, dataPbf);
		// }

		OutputStream output = response.getOutputStream();
		dataPbf.writeTo(output);
		output.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

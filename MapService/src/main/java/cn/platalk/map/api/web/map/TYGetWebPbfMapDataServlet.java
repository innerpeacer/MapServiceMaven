package cn.platalk.map.api.web.map;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.core.pbf.mapdata.TYMapDataPbf.TYIndoorDataPbf;
import cn.platalk.core.pbf.mapdata.wrapper.TYIndoorDataPbfBuilder;
import cn.platalk.map.caching.TYCachingPool;
import cn.platalk.map.caching.TYCachingType;
import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;

@WebServlet("/web/pbf/GetMapData")
public class TYGetWebPbfMapDataServlet extends TYBaseHttpServlet {

	private static final long serialVersionUID = -652247259445376881L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		String mapID = request.getParameter("mapID");
		response.setContentType("text/plain;charset=UTF-8");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		TYIndoorDataPbf dataPbf = null;
		if (TYCachingPool.existDataID(mapID, TYCachingType.IndoorDataPbf)) {
			dataPbf = (TYIndoorDataPbf) TYCachingPool.getCachingData(mapID, TYCachingType.IndoorDataPbf);
		} else {
			List<TYMapDataFeatureRecord> mapDataRecordList = TYMysqlDBHelper.getMapDataRecords(buildingID, mapID);
			List<TYFillSymbolRecord> fillSymbolList = TYMysqlDBHelper.getFillSymbolRecords(buildingID);
			List<TYIconSymbolRecord> iconSymbolList = TYMysqlDBHelper.getIconSymbolRecords(buildingID);
			dataPbf = TYIndoorDataPbfBuilder.generateMapDataObject(mapID, mapDataRecordList, fillSymbolList,
					iconSymbolList);
			TYCachingPool.setCachingData(mapID, dataPbf, TYCachingType.IndoorDataPbf);
		}

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

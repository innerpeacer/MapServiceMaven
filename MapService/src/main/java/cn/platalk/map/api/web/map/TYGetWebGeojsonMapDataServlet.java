package cn.platalk.map.api.web.map;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.map.caching.TYCachingPool;
import cn.platalk.map.caching.TYCachingType;
import cn.platalk.map.core.config.TYServerEnvironment;
import cn.platalk.map.core.web.TYWebMapGeojsonDataBuilder;
import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.mysql.TYMysqlDBHelper;

@WebServlet("/web/geojson/GetMapData")
public class TYGetWebGeojsonMapDataServlet extends TYBaseHttpServlet {

	private static final long serialVersionUID = -652247259445376881L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request mapdata geojson");
		String buildingID = request.getParameter("buildingID");
		String mapID = request.getParameter("mapID");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		JSONObject mapDataObject = null;
		if (TYServerEnvironment.isWindows() || TYServerEnvironment.isLinux()) {
			if (TYCachingPool.existDataID(mapID, TYCachingType.IndoorDataGeojson)) {
				mapDataObject = (JSONObject) TYCachingPool.getCachingData(mapID, TYCachingType.IndoorDataGeojson);
			} else {
				List<TYMapDataFeatureRecord> mapDataRecordList = TYMysqlDBHelper.getMapDataRecords(buildingID, mapID);
				List<TYFillSymbolRecord> fillSymbolList = TYMysqlDBHelper.getFillSymbolRecords(buildingID);
				List<TYIconSymbolRecord> iconSymbolList = TYMysqlDBHelper.getIconSymbolRecords(buildingID);

				mapDataObject = TYWebMapGeojsonDataBuilder.generateMapDataObject(mapDataRecordList, fillSymbolList,
						iconSymbolList);
				TYCachingPool.setCachingData(mapID, mapDataObject, TYCachingType.IndoorDataGeojson);
			}
		} else {
			List<TYMapDataFeatureRecord> mapDataRecordList = TYMysqlDBHelper.getMapDataRecords(buildingID, mapID);
			List<TYFillSymbolRecord> fillSymbolList = TYMysqlDBHelper.getFillSymbolRecords(buildingID);
			List<TYIconSymbolRecord> iconSymbolList = TYMysqlDBHelper.getIconSymbolRecords(buildingID);

			mapDataObject = TYWebMapGeojsonDataBuilder.generateMapDataObject(mapDataRecordList, fillSymbolList,
					iconSymbolList);
			TYCachingPool.setCachingData(mapID, mapDataObject, TYCachingType.IndoorDataGeojson);
			System.out.println(mapDataRecordList.size() + " records");
		}

		respondResult(request, response, mapDataObject);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

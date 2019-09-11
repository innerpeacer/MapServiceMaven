package cn.platalk.map.api.web.map.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.map.core.config.TYMapEnvironment;
import cn.platalk.map.entity.base.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.TYIIconSymbolRecord;
import cn.platalk.map.entity.base.TYIIconTextSymbolRecord;
import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.map.entity.base.TYIMapInfo;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYCity;
import cn.platalk.map.vectortile.builder.TYVectorTileBuilder;
import cn.platalk.map.vectortile.builder.TYVectorTileSettings;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;
import cn.platalk.utils.third.TYFileUtils;

@WebServlet("/web/BuildVectorTile")
public class TYBuildVectorTileServlet extends TYBaseHttpServlet {

	private static final long serialVersionUID = 7540952724296868847L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		String cbmOnly = request.getParameter("cbmOnly");
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

		TYVectorTileSettings.SetTileRoot(TYMapEnvironment.GetVectorTileRoot());
		TYVectorTileSettings.SetDefaultMaxZoom(21);
		// TYVectorTileSettings.setDefaultMinZoom(15);

		File tileDir = new File(TYMapEnvironment.GetVectorTileRoot(), buildingID);
		TYFileUtils.deleteFile(tileDir.toString());

		TYVectorTileBuilder builder = new TYVectorTileBuilder(buildingID);

		TYCity city = TYMysqlDBHelper.getCity(building.getCityID());
		List<TYIMapInfo> mapInfos = new ArrayList<TYIMapInfo>(TYMysqlDBHelper.getMapInfos(buildingID));

		List<TYIFillSymbolRecord> fillSymbols = new ArrayList<TYIFillSymbolRecord>(
				TYMysqlDBHelper.getFillSymbolRecords(buildingID));
		List<TYIIconSymbolRecord> iconSymbols = new ArrayList<TYIIconSymbolRecord>(
				TYMysqlDBHelper.getIconSymbolRecords(buildingID));
		List<TYIIconTextSymbolRecord> iconTextSymbols = new ArrayList<TYIIconTextSymbolRecord>(
				TYMysqlDBHelper.getIconTextSymbolRecords(buildingID));
		List<TYIMapDataFeatureRecord> mapDataRecords = new ArrayList<TYIMapDataFeatureRecord>(
				TYMysqlDBHelper.getMapDataRecords(buildingID));

		fillSymbols = filterFillRecords(mapDataRecords, fillSymbols);
		iconTextSymbols = filterIconTextRecords(mapDataRecords, iconTextSymbols);

		builder.addData(city, building, mapInfos, mapDataRecords, fillSymbols, iconSymbols, iconTextSymbols);
		try {
			builder.generateCBM();
			builder.generateCBMPbf();
			if (cbmOnly == null) {
				builder.buildTile(false);
			}
			PrintWriter out = response.getWriter();
			out.println("Build Vector Tile for BuildingID: " + buildingID);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("Failed Building Vector Tile for BuildingID: " + buildingID);
			out.println(e.getMessage());
			out.close();
		}
	}

	private List<TYIFillSymbolRecord> filterFillRecords(List<TYIMapDataFeatureRecord> mapRecords,
			List<TYIFillSymbolRecord> fillRecords) {
		List<TYIFillSymbolRecord> resultList = new ArrayList<TYIFillSymbolRecord>();

		Set<Integer> tempSet = new HashSet<Integer>();
		for (TYIMapDataFeatureRecord mapRecord : mapRecords) {
			if (mapRecord.getLayer() == TYIMapDataFeatureRecord.LAYER_FLOOR
					|| mapRecord.getLayer() == TYIMapDataFeatureRecord.LAYER_ROOM
					|| mapRecord.getLayer() == TYIMapDataFeatureRecord.LAYER_ASSET) {
				tempSet.add(mapRecord.getSymbolID());
			}
		}

		for (TYIFillSymbolRecord fillRecord : fillRecords) {
			if (tempSet.contains(fillRecord.getSymbolID())) {
				resultList.add(fillRecord);
			}
		}
		return resultList;
	}

	private List<TYIIconTextSymbolRecord> filterIconTextRecords(List<TYIMapDataFeatureRecord> mapRecords,
			List<TYIIconTextSymbolRecord> iconTextRecords) {
		List<TYIIconTextSymbolRecord> resultList = new ArrayList<TYIIconTextSymbolRecord>();

		Set<Integer> tempSet = new HashSet<Integer>();
		for (TYIMapDataFeatureRecord mapRecord : mapRecords) {
			if (mapRecord.getLayer() == TYIMapDataFeatureRecord.LAYER_FACILITY
					|| mapRecord.getLayer() == TYIMapDataFeatureRecord.LAYER_LABEL) {
				tempSet.add(mapRecord.getSymbolID());
			}
		}

		for (TYIIconTextSymbolRecord iconRecord : iconTextRecords) {
			if (tempSet.contains(iconRecord.getSymbolID())) {
				resultList.add(iconRecord);
			}
		}
		return resultList;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

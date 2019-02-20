package cn.platalk.brtmap.api.web.map.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.brtmap.api.TYParameterChecker;
import cn.platalk.brtmap.core.config.TYBrtMapEnvironment;
import cn.platalk.brtmap.entity.base.TYIFillSymbolRecord;
import cn.platalk.brtmap.entity.base.TYIIconSymbolRecord;
import cn.platalk.brtmap.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.brtmap.entity.base.TYIMapInfo;
import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYCity;
import cn.platalk.brtmap.vectortile.builder.TYVectorTileBuilder;
import cn.platalk.brtmap.vectortile.builder.TYVectorTileSettings;
import cn.platalk.mysql.map.TYBuildingDBAdapter;
import cn.platalk.mysql.map.TYCityDBAdapter;
import cn.platalk.mysql.map.TYMapDataDBAdapter;
import cn.platalk.mysql.map.TYMapInfoDBAdapter;
import cn.platalk.mysql.map.TYSymbolDBAdapter;
import cn.platalk.utils.third.TYFileUtils;

@WebServlet("/web/BuildVectorTile")
public class TYBrtBuildVectorTileServlet extends HttpServlet {

	private static final long serialVersionUID = 7540952724296868847L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String buildingID = request.getParameter("buildingID");
		response.setContentType("text/json;charset=UTF-8");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			PrintWriter out = response.getWriter();
			out.println("Invalid BuildingID: " + buildingID);
			out.close();
		}

		TYBuildingDBAdapter db = new TYBuildingDBAdapter();
		db.connectDB();
		TYBuilding building = db.getBuilding(buildingID);
		db.disconnectDB();

		if (building == null) {
			PrintWriter out = response.getWriter();
			System.out.println("BuildingID: " + buildingID + " not Exist!");
			out.println("BuildingID: " + buildingID + " not Exist!");
			out.close();
			return;
		}

		TYVectorTileSettings.SetTileRoot(TYBrtMapEnvironment
				.GetVectorTileRoot());
		TYVectorTileSettings.SetDefaultMaxZoom(21);
		// TYVectorTileSettings.setDefaultMinZoom(15);

		File tileDir = new File(TYBrtMapEnvironment.GetVectorTileRoot(),
				buildingID);
		TYFileUtils.deleteFile(tileDir.toString());

		TYVectorTileBuilder builder = new TYVectorTileBuilder(buildingID);

		TYCityDBAdapter cityDB = new TYCityDBAdapter();
		cityDB.connectDB();
		TYCity city = cityDB.getCity(building.getCityID());
		cityDB.disconnectDB();

		TYMapInfoDBAdapter infoDB = new TYMapInfoDBAdapter();
		infoDB.connectDB();
		List<TYIMapInfo> mapInfos = new ArrayList<TYIMapInfo>();
		mapInfos.addAll(infoDB.getMapInfos(buildingID));
		infoDB.disconnectDB();

		List<TYIFillSymbolRecord> fillSymbols = new ArrayList<TYIFillSymbolRecord>();
		List<TYIIconSymbolRecord> iconSymbols = new ArrayList<TYIIconSymbolRecord>();
		TYSymbolDBAdapter symbolDB = new TYSymbolDBAdapter();
		symbolDB.connectDB();
		fillSymbols.addAll(symbolDB.getFillSymbolRecords(buildingID));
		iconSymbols.addAll(symbolDB.getIconSymbolRecords(buildingID));
		symbolDB.disconnectDB();

		List<TYIMapDataFeatureRecord> mapDataRecords = new ArrayList<TYIMapDataFeatureRecord>();
		TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(buildingID);
		mapDB.connectDB();
		mapDataRecords.addAll(mapDB.getAllMapDataRecords());
		mapDB.disconnectDB();

		builder.addData(city, building, mapInfos, mapDataRecords, fillSymbols,
				iconSymbols);
		try {
			builder.buildTile();
			PrintWriter out = response.getWriter();
			out.println("Build Vector Tile for BuildingID: " + buildingID);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("Failed Building Vector Tile for BuildingID: "
					+ buildingID);
			out.println(e.getMessage());
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

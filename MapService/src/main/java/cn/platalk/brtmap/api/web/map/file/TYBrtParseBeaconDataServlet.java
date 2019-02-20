package cn.platalk.brtmap.api.web.map.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.brtmap.api.TYParameterChecker;
import cn.platalk.brtmap.core.config.TYBrtMapEnvironment;
import cn.platalk.core.beacon.shp.TYBeaconShpDataManager;
import cn.platalk.core.beacon.shp.beacondata.TYShpBeaconDataParser;
import cn.platalk.core.beacon.shp.beacondata.TYShpBeaconDataParser.TYBrtShpBeaconParserListener;
import cn.platalk.map.entity.base.TYILocatingBeacon;
import cn.platalk.map.entity.base.TYIMapInfo;
import cn.platalk.map.entity.base.impl.TYBeaconRegion;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYLocatingBeacon;
import cn.platalk.mysql.beacon.TYBeaconDBAdapter;
import cn.platalk.mysql.beacon.TYBeaconRegionDBAdapter;
import cn.platalk.mysql.map.TYBuildingDBAdapter;
import cn.platalk.mysql.map.TYMapInfoDBAdapter;
import cn.platalk.utils.third.TYZipUtil;

@WebServlet("/web/ParseBeaconData")
public class TYBrtParseBeaconDataServlet extends HttpServlet {
	private static final long serialVersionUID = -2518472730406384232L;

	protected void doGet(HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final String buildingID = request.getParameter("buildingID");
		response.setContentType("text/json;charset=UTF-8");

		final StringBuffer resBuffer = new StringBuffer();

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			PrintWriter out = response.getWriter();
			resBuffer.append("Invalid BuildingID: " + buildingID);
			out.println(resBuffer.toString());
			out.close();
			return;
		}

		TYBuildingDBAdapter db = new TYBuildingDBAdapter();
		db.connectDB();
		db.createTableIfNotExist();
		final TYBuilding building = db.getBuilding(buildingID);
		db.disconnectDB();

		// 解压数据
		{
			File zipFile = new File(TYBrtMapEnvironment.GetRawDataRootDir(), String.format("%s.zip", buildingID));
			if (zipFile.exists()) {
				resBuffer.append("exist\n");
				System.out.println("exist");

				try {
					// TYZipUtil.unzip(zipFile.getAbsolutePath(),
					// TYBrtMapEnvironment.GetRawDataRootDir(), false);
					TYZipUtil.unzip(zipFile.getAbsolutePath(),
							new File(TYBrtMapEnvironment.GetRawDataRootDir(), buildingID).toString(), false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				resBuffer.append("not exist\n");
				System.out.println("not exist");
			}
		}

		TYBeaconShpDataManager shpManager = new TYBeaconShpDataManager(
				TYBrtMapEnvironment.GetShpRootDir(buildingID));
		TYShpBeaconDataParser parser = new TYShpBeaconDataParser(shpManager.getBeaconShpPath());
		parser.addParserListener(new TYBrtShpBeaconParserListener() {
			public void didFailedParsingBeaconDataList(Throwable error) {
				PrintWriter out;
				try {
					out = response.getWriter();
					resBuffer.append("Parse Beaconn Data Failed For BuildingID: " + buildingID).append("\n");
					resBuffer.append(error.toString()).append("\n");
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			public void didFinishParsingBeaconDataList(List<TYILocatingBeacon> beaconList) {
				TYMapInfoDBAdapter infoDB = new TYMapInfoDBAdapter();
				infoDB.connectDB();
				infoDB.createTableIfNotExist();
				List<TYIMapInfo> mapInfoList = new ArrayList<TYIMapInfo>();
				mapInfoList.addAll(infoDB.getMapInfos(buildingID));
				infoDB.disconnectDB();

				Map<Integer, TYIMapInfo> infoMap = new HashMap<Integer, TYIMapInfo>();
				for (TYIMapInfo info : mapInfoList) {
					infoMap.put(info.getFloorNumber(), info);
				}

				String uuid = null;
				Set<Integer> majorSet = new HashSet<Integer>();
				Integer anyMajor = null;
				for (TYILocatingBeacon beacon : beaconList) {
					uuid = beacon.getUUID();
					anyMajor = beacon.getMajor();
					majorSet.add(beacon.getMajor());
					TYIMapInfo info = infoMap.get(beacon.getLocation().getFloor());
					TYLocatingBeacon b = (TYLocatingBeacon) beacon;
					b.setBuildingID(info.getBuildingID());
					b.setMapID(info.getMapID());
					b.setCityID(info.getCityID());
				}

				TYBeaconDBAdapter beaconDB = new TYBeaconDBAdapter(buildingID);
				beaconDB.connectDB();
				beaconDB.createTableIfNotExist();
				beaconDB.eraseBeaconTable();
				beaconDB.insertOrUpdateBeacons(beaconList);
				beaconDB.disconnectDB();

				TYBeaconRegion region = new TYBeaconRegion();
				region.setBuildingID(building.getBuildingID());
				region.setCityID(building.getCityID());
				region.setBuildingName(building.getName());
				region.setUuid(uuid);
				if (majorSet.size() == 1) {
					region.setMajor(anyMajor);
				}

				TYBeaconRegionDBAdapter regionDB = new TYBeaconRegionDBAdapter();
				regionDB.connectDB();
				regionDB.createTableIfNotExist();
				regionDB.insertOrUpdateBeaconRegion(region);
				regionDB.disconnectDB();

				PrintWriter out;
				try {
					out = response.getWriter();
					resBuffer.append("Success Parsing Beacon Data for BuildingID: " + buildingID).append("\n");
					resBuffer.append(beaconList.size() + " beacons").append("\n");
					resBuffer.append("BeaconRegion: " + region.toString()).append("\n");
					out.println(resBuffer.toString());
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		parser.parse();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

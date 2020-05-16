package cn.platalk.map.api.web.map.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.core.beacon.shp.TYBeaconShpDataManager;
import cn.platalk.core.beacon.shp.beacondata.TYShpBeaconDataParser;
import cn.platalk.core.beacon.shp.beacondata.TYShpBeaconDataParser.TYBrtShpBeaconParserListener;
import cn.platalk.map.core.config.TYMapEnvironment;
import cn.platalk.map.entity.base.TYILocatingBeacon;
import cn.platalk.map.entity.base.TYIMapInfo;
import cn.platalk.map.entity.base.impl.TYBeaconRegion;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYLocatingBeacon;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.mysql.beacon.TYBeaconDBAdapter;
import cn.platalk.mysql.beacon.TYBeaconRegionDBAdapter;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;
import cn.platalk.utils.third.TYZipUtil;

@WebServlet("/web/ParseBeaconData")
public class TYParseBeaconDataServlet extends TYBaseHttpServlet {
	private static final long serialVersionUID = -2518472730406384232L;

	@Override
	protected void doGet(HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		final String buildingID = request.getParameter("buildingID");
		response.setContentType("text/json;charset=UTF-8");

		final StringBuffer resBuffer = new StringBuffer();

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		final TYBuilding building = TYMysqlDBHelper.getBuilding(buildingID);

		// 解压数据
		{
			File zipFile = new File(TYMapEnvironment.GetRawDataRootDir(), String.format("%s.zip", buildingID));
			if (zipFile.exists()) {
				resBuffer.append("exist\n");
				System.out.println("exist");

				try {
					// TYZipUtil.unzip(zipFile.getAbsolutePath(),
					// TYBrtMapEnvironment.GetRawDataRootDir(), false);
					TYZipUtil.unzip(zipFile.getAbsolutePath(),
							new File(TYMapEnvironment.GetRawDataRootDir(), buildingID).toString(), false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				resBuffer.append("not exist\n");
				System.out.println("not exist");
			}
		}

		TYBeaconShpDataManager shpManager = new TYBeaconShpDataManager(TYMapEnvironment.GetShpRootDir(buildingID));
		TYShpBeaconDataParser parser = new TYShpBeaconDataParser(shpManager.getBeaconShpPath());
		parser.addParserListener(new TYBrtShpBeaconParserListener() {
			@Override
			public void didFailedParsingBeaconDataList(Throwable error) {
				PrintWriter out;
				try {
					out = response.getWriter();
					resBuffer.append("Parse Beacon Data Failed For BuildingID: ").append(buildingID).append("\n");
					resBuffer.append(error.toString()).append("\n");
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void didFinishParsingBeaconDataList(List<TYILocatingBeacon> beaconList) {
				List<TYIMapInfo> mapInfoList = new ArrayList<TYIMapInfo>(TYMysqlDBHelper.getMapInfos(buildingID));

				Map<Integer, TYIMapInfo> infoMap = new HashMap<>();
				for (TYIMapInfo info : mapInfoList) {
					infoMap.put(info.getFloorNumber(), info);
				}

				String uuid = null;
				Set<Integer> majorSet = new HashSet<>();
				Integer anyMajor = null;
				for (TYILocatingBeacon beacon : beaconList) {
					uuid = beacon.getUUID();
					anyMajor = beacon.getMajor();
					majorSet.add(beacon.getMajor());
					TYIMapInfo info = infoMap.get(beacon.getLocation().getFloor());
					if (info == null) continue;
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
					resBuffer.append("Success Parsing Beacon Data for BuildingID: ").append(buildingID).append("\n");
					resBuffer.append(beaconList.size()).append(" beacons").append("\n");
					resBuffer.append("BeaconRegion: ").append(region.toString()).append("\n");
					out.println(resBuffer.toString());
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		parser.parse();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
}

package cn.platalk.map.api.web.map.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.core.map.shp.TYShpGeneratingTask;
import cn.platalk.core.map.shp.TYShpGeneratingTask.TYShpGeneratingTaskListener;
import cn.platalk.map.core.config.TYMapEnvironment;
import cn.platalk.map.entity.base.impl.map.*;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.mysql.map.TYBuildingDBAdapter;
import cn.platalk.mysql.map.TYCityDBAdapter;
import cn.platalk.mysql.map.TYMapDataDBAdapter;
import cn.platalk.mysql.map.TYMapInfoDBAdapter;
import cn.platalk.mysql.map.TYRouteDBAdapter;
import cn.platalk.mysql.map.TYSymbolDBAdapter;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;
import cn.platalk.utils.third.TYZipUtil;

@WebServlet("/web/ParseMapData")
public class TYParseMapDataServlet extends TYBaseHttpServlet {
	private static final long serialVersionUID = -5584955122349462054L;

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

		TYShpGeneratingTask task = new TYShpGeneratingTask(TYMapEnvironment.GetShpRootDir(buildingID), buildingID);
		task.addTaskListener(new TYShpGeneratingTaskListener() {

			@Override
			public void didFinishGeneratingTask(TYShpGeneratingTask task) {
				if (building == null && task.getBuilding() == null) {
					PrintWriter out;
					try {
						out = response.getWriter();
						resBuffer.append("没有任何建筑信息: ").append(buildingID).append("\n");
						out.println(resBuffer.toString());
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}

				System.out.println("didFinishGeneratingTask");
				List<TYMapInfo> mapInfos = task.getMapInfos();
				System.out.println(mapInfos.size() + " MapInfos");

				List<TYIconSymbolRecord> iconSymbolRecords = task.getIconSymbols();
				System.out.println(iconSymbolRecords.size() + " icons");

				List<TYFillSymbolRecord> fillSymbolRecords = task.getFillSymbols();
				System.out.println(fillSymbolRecords.size() + " fills");

				List<TYMapDataFeatureRecord> mapDataRecords = task.getMapDataRecords();
				System.out.println(mapDataRecords.size() + " map records");

				List<TYRouteNodeRecord> nodeRecords = task.getNodeRecords();
				System.out.println(nodeRecords.size() + " nodes");

				List<TYRouteLinkRecord> linkRecords = task.getLinkRecords();
				System.out.println(linkRecords.size() + " links");

				if (task.getCity() != null) {
					TYCity city = task.getCity();
					TYCityDBAdapter cityDB = new TYCityDBAdapter();
					cityDB.connectDB();
					cityDB.createTableIfNotExist();
					cityDB.insertOrUpdateCity(city);
					cityDB.disconnectDB();
				}

				if (task.getBuilding() != null) {
					TYBuilding building = task.getBuilding();
					TYBuildingDBAdapter buildingDB = new TYBuildingDBAdapter();
					buildingDB.connectDB();
					buildingDB.createTableIfNotExist();
					building.setRouteURL("V2");
					buildingDB.insertOrUpdateBuilding(building);
					buildingDB.disconnectDB();
				}

				{
					TYMapInfoDBAdapter infoDB = new TYMapInfoDBAdapter();
					infoDB.connectDB();
					infoDB.createTableIfNotExist();
					infoDB.insertOrUpdateMapInfos(task.getMapInfos());
					infoDB.disconnectDB();
				}

				{
					TYSymbolDBAdapter symbolDB = new TYSymbolDBAdapter();
					symbolDB.connectDB();
					symbolDB.createTableIfNotExist();
					symbolDB.insertFillSymbolRecords(task.getFillSymbols(), buildingID);
					symbolDB.insertIconSymbolRecords(task.getIconSymbols(), buildingID);
					symbolDB.disconnectDB();
				}

				{
					TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(buildingID);
					mapDB.connectDB();
					mapDB.createTableIfNotExist();
					mapDB.eraseMapDataTable();

					List<TYMapDataFeatureRecord> records = task.getMapDataRecords();
					int STEP = 1000;
					int batch = records.size() / STEP + 1;
					System.out.println(batch + " batches");

					for (int i = 0; i < batch; ++i) {
						System.out.println("[ " + i * 1000 + ", " + Math.min((i + 1) * 1000, records.size()) + "]");
						mapDB.insertMapDataRecordsInBatch(
								records.subList(i * 1000, Math.min((i + 1) * 1000, records.size())));
					}
					mapDB.disconnectDB();
				}

				{
					TYRouteDBAdapter routeDB = new TYRouteDBAdapter(buildingID);
					routeDB.connectDB();
					routeDB.createTableIfNotExist();
					routeDB.eraseRouteTable();
					// routeDB.insertRouteLinkRecordsInBatch(task.getLinkRecords());
					// routeDB.insertRouteNodeRecordsInBatch(task.getNodeRecords());

					int STEP = 500;
					int linkBatch = linkRecords.size() / STEP + 1;
					System.out.println(linkBatch + " link batches");
					resBuffer.append(linkBatch).append(" link batches").append("\n");
					for (int i = 0; i < linkBatch; ++i) {
						System.out.println("[ " + i * STEP + ", " + Math.min((i + 1) * STEP, linkRecords.size()) + "]");
						resBuffer.append("[ ").append(i * STEP).append(", ").append(Math.min((i + 1) * STEP, linkRecords.size())).append("]")
								.append("\n");
						int res = routeDB.insertRouteLinkRecordsInBatch(
								linkRecords.subList(i * STEP, Math.min((i + 1) * STEP, linkRecords.size())));
						resBuffer.append("Insert ").append(res).append("\n");
					}

					int nodeBatch = nodeRecords.size() / STEP + 1;
					System.out.println(nodeBatch + " batches");
					resBuffer.append(nodeBatch).append(" nodes batches").append("\n");

					for (int i = 0; i < nodeBatch; ++i) {
						System.out.println("[ " + i * STEP + ", " + Math.min((i + 1) * STEP, nodeRecords.size()) + "]");
						resBuffer.append("[ ").append(i * STEP).append(", ").append(Math.min((i + 1) * STEP, nodeRecords.size())).append("]")
								.append("\n");
						int res = routeDB.insertRouteNodeRecordsInBatch(
								nodeRecords.subList(i * STEP, Math.min((i + 1) * STEP, nodeRecords.size())));
						resBuffer.append("Insert ").append(res).append("\n");
					}
					routeDB.disconnectDB();
				}
				PrintWriter out;
				try {
					out = response.getWriter();
					resBuffer.append("Success Parsing Data for BuildingID: ").append(buildingID).append("\n");
					resBuffer.append(mapInfos.size()).append(" MapInfos").append("\n");
					resBuffer.append(iconSymbolRecords.size()).append(" icons").append("\n");
					resBuffer.append(fillSymbolRecords.size()).append(" fills").append("\n");
					resBuffer.append(mapDataRecords.size()).append(" map records").append("\n");
					resBuffer.append(nodeRecords.size()).append(" nodes").append("\n");
					resBuffer.append(linkRecords.size()).append(" links").append("\n");
					out.println(resBuffer.toString());
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void didFailedGeneratingTask(Throwable error) {
				PrintWriter out;
				try {
					out = response.getWriter();
					resBuffer.append("Parse Data Failed For BuildingID: ").append(buildingID).append("\n");
					resBuffer.append(error.toString()).append("\n");
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		task.startTask();

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
}

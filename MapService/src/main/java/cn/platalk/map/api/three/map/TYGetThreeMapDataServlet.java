package cn.platalk.map.api.three.map;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.map.entity.base.impl.map.TYBuilding;
import cn.platalk.map.entity.base.impl.map.TYMapDataFeatureRecord;
import org.json.JSONObject;

import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;

@WebServlet("/web/three/GetMapData")
public class TYGetThreeMapDataServlet extends TYBaseHttpServlet {

	private static final long serialVersionUID = -652247259445376881L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println("request three mapdata");
		String buildingID = request.getParameter("buildingID");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		TYBuilding building = TYMysqlDBHelper.getBuilding(buildingID);
		if (building == null) {
			respondError(request, response, errorDescriptionNotExistBuildingID(buildingID));
			return;
		}

		JSONObject threeMapDataObject;

		List<TYMapDataFeatureRecord> mapDataRecordList = TYMysqlDBHelper.getMapDataRecords(buildingID);
		TYThreeMapDataBuilder builder = new TYThreeMapDataBuilder(building);
		threeMapDataObject = builder.generateThreeMapDataObject(mapDataRecordList);
		System.out.println(mapDataRecordList.size() + " records");

		respondResult(request, response, threeMapDataObject);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
}

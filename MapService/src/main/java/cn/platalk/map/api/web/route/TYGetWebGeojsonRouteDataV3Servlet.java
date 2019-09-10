package cn.platalk.map.api.web.route;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.platalk.map.api.TYParameterChecker;
import cn.platalk.map.api.web.map.TYBaseHttpServlet;
import cn.platalk.map.core_v3.web.route.TYWebRouteGeojsonDataBuilderV3;
import cn.platalk.map.entity.base.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.TYIRouteNodeRecordV3;
import cn.platalk.mysql.TYMysqlDBHelper;

@WebServlet("/web/geojson/GetRouteDataV3")
public class TYGetWebGeojsonRouteDataV3Servlet extends TYBaseHttpServlet {

	private static final long serialVersionUID = -652247259445376881L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request route geojson");
		String buildingID = request.getParameter("buildingID");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		List<TYIRouteLinkRecordV3> linkList = TYMysqlDBHelper.getAllRouteLinkRecordV3(buildingID);
		List<TYIRouteNodeRecordV3> nodeList = TYMysqlDBHelper.getAllRouteNodeRecordV3(buildingID);
		JSONObject routeDataObject = TYWebRouteGeojsonDataBuilderV3.generateRouteDataObject(linkList, nodeList);
		routeDataObject.put("Link", linkList.size());
		routeDataObject.put("Node", nodeList.size());
		respondResult(request, response, routeDataObject);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

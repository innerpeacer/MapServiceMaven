package cn.platalk.map.api.web.route;

import cn.platalk.core.pbf.route.TYRouteNetworkPbf.TYRouteNetworkV3Pbf;
import cn.platalk.core.pbf.route.wrapper.TYRouteNetworkPbfBuilder;
import cn.platalk.map.entity.base.map.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.map.TYIRouteNodeRecordV3;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/web/pbf/GetRouteDataV3")
public class TYGetWebPbfRouteDataV3Servlet extends TYBaseHttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String buildingID = request.getParameter("buildingID");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		List<TYIRouteLinkRecordV3> linkList = TYMysqlDBHelper.getAllRouteLinkRecordV3(buildingID);
		List<TYIRouteNodeRecordV3> nodeList = TYMysqlDBHelper.getAllRouteNodeRecordV3(buildingID);
		TYRouteNetworkV3Pbf routePbf = TYRouteNetworkPbfBuilder.generateRouteNetworkObject(buildingID, linkList, nodeList);

		OutputStream output = response.getOutputStream();
		routePbf.writeTo(output);
		output.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
}

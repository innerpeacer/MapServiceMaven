package cn.platalk.map.api.web.beacon;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.core.pbf.beacon.TYBeaconPbf.TYLocatingBeaconListPbf;
import cn.platalk.core.pbf.beacon.wrapper.TYBeaconPbfBuilder;
import cn.platalk.map.caching.TYCachingPool;
import cn.platalk.map.caching.TYCachingType;
import cn.platalk.map.core.config.TYServerEnvironment;
import cn.platalk.map.entity.base.impl.TYLocatingBeacon;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;

@WebServlet("/web/pbf/getBeacon")
public class TYGetWebPbfBeaconServlet extends TYBaseHttpServlet {

	private static final long serialVersionUID = -6157282965658968156L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request beacon pbf");
		String buildingID = request.getParameter("buildingID");
		response.setContentType("text/plain;charset=UTF-8");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		TYLocatingBeaconListPbf beaconListPbf = null;
		if (TYServerEnvironment.isWindows() || TYServerEnvironment.isLinux()) {
			if (TYCachingPool.existDataID(buildingID, TYCachingType.BeaconDataPbf)) {
				beaconListPbf = (TYLocatingBeaconListPbf) TYCachingPool.getCachingData(buildingID,
						TYCachingType.BeaconDataPbf);
			} else {
				List<TYLocatingBeacon> beaconList = TYMysqlDBHelper.getAllBeacons(buildingID);
				beaconListPbf = TYBeaconPbfBuilder.beaconListToPbf(beaconList);
				TYCachingPool.setCachingData(buildingID, beaconListPbf, TYCachingType.BeaconDataPbf);
			}
		} else {
			List<TYLocatingBeacon> beaconList = TYMysqlDBHelper.getAllBeacons(buildingID);
			beaconListPbf = TYBeaconPbfBuilder.beaconListToPbf(beaconList);
			System.out.println(beaconList.size() + " beacons");
		}

		OutputStream output = response.getOutputStream();
		beaconListPbf.writeTo(output);
		output.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

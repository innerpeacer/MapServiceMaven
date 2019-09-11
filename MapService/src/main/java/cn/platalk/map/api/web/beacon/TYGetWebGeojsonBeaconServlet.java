package cn.platalk.map.api.web.beacon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.platalk.common.TYIGeojsonFeature;
import cn.platalk.foundation.TYGeojsonBuilder;
import cn.platalk.map.caching.TYCachingPool;
import cn.platalk.map.caching.TYCachingType;
import cn.platalk.map.core.config.TYServerEnvironment;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;

@WebServlet("/web/geojson/getBeacon")
public class TYGetWebGeojsonBeaconServlet extends TYBaseHttpServlet {
	private static final long serialVersionUID = -5796450412740461495L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request beacon geojson");
		String buildingID = request.getParameter("buildingID");

		if (!TYParameterChecker.isValidBuildingID(buildingID)) {
			respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
			return;
		}

		JSONObject beaconDataObject = null;
		if (TYServerEnvironment.isWindows() || TYServerEnvironment.isLinux()) {
			if (TYCachingPool.existDataID(buildingID, TYCachingType.BeaconDataGeojson)) {
				beaconDataObject = (JSONObject) TYCachingPool.getCachingData(buildingID,
						TYCachingType.BeaconDataGeojson);
			} else {
				List<TYIGeojsonFeature> beaconFeatures = new ArrayList<TYIGeojsonFeature>(
						TYMysqlDBHelper.getAllBeacons(buildingID));
				beaconDataObject = TYGeojsonBuilder.buildFeatureCollection(beaconFeatures);
				TYCachingPool.setCachingData(buildingID, beaconDataObject, TYCachingType.BeaconDataGeojson);
			}
		} else {
			List<TYIGeojsonFeature> beaconFeatures = new ArrayList<TYIGeojsonFeature>(
					TYMysqlDBHelper.getAllBeacons(buildingID));
			beaconDataObject = TYGeojsonBuilder.buildFeatureCollection(beaconFeatures);
			TYCachingPool.setCachingData(buildingID, beaconDataObject, TYCachingType.BeaconDataGeojson);
			System.out.println(beaconFeatures.size() + " beacons");
		}

		respondResult(request, response, beaconDataObject);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

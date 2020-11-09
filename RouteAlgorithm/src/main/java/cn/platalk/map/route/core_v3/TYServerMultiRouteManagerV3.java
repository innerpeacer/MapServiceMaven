package cn.platalk.map.route.core_v3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.impl.map.TYLocalPoint;
import cn.platalk.map.entity.base.map.*;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class TYServerMultiRouteManagerV3 {
	static final String TAG = TYServerMultiRouteManagerV3.class.getSimpleName();

	Point startPoint;
	Point endPoint;
	IPMrParamsV3 params;

	final IPServerRouteNetworkDatasetV3 networkDataset;

	GeometryFactory factory = new GeometryFactory();
	final List<TYIMapInfo> allMapInfoArray = new ArrayList<>();

	public TYServerMultiRouteManagerV3(TYIBuilding building, List<TYIMapInfo> mapInfoArray,
									   List<TYIRouteNodeRecordV3> nodes, List<TYIRouteLinkRecordV3> links, List<TYIMapDataFeatureRecord> mapdata) {
		allMapInfoArray.addAll(mapInfoArray);
		networkDataset = new IPServerRouteNetworkDatasetV3(allMapInfoArray, nodes, links, mapdata);
	}

	public synchronized TYServerMultiRouteResultV3 requestRoute(TYLocalPoint startPoint, TYLocalPoint endPoint,
																List<TYLocalPoint> stopPoints, TYServerRouteOptions options) {
		TYServerMultiRouteResultV3 result;
		System.out.println("requestRoute: " + options.isSameFloorFirst());

		{
			IPRouteDebugger.debugLog("============ Route Level 0===============");
			// options.setEnableRouteLevel(true);
			options.setRouteLevel(IPRouteLevel.Zero);
			if (options.isSameFloorFirst()) {
				options.setUseSameFloor(true);
				result = requestRoute1(startPoint, endPoint, stopPoints, options);
				if (result == null) {
					options.setUseSameFloor(false);
					result = requestRoute1(startPoint, endPoint, stopPoints, options);
				}
			} else {
				options.setUseSameFloor(false);
				result = requestRoute1(startPoint, endPoint, stopPoints, options);
			}
		}

		if (result == null) {
			IPRouteDebugger.debugLog("============ Route Level 0 Failed ===============");
			IPRouteDebugger.debugLog("============ Route Level 1 =====================");

			// options.setEnableRouteLevel(false);
			options.setRouteLevel(IPRouteLevel.One);
			if (options.isSameFloorFirst()) {
				options.setUseSameFloor(true);
				result = requestRoute1(startPoint, endPoint, stopPoints, options);
				if (result == null) {
					options.setUseSameFloor(false);
					result = requestRoute1(startPoint, endPoint, stopPoints, options);
				}
			} else {
				options.setUseSameFloor(false);
				result = requestRoute1(startPoint, endPoint, stopPoints, options);
			}
		}

		if (result == null) {
			IPRouteDebugger.debugLog("============ Route Level 1 Failed ===============");
			IPRouteDebugger.debugLog("============ Route Level 2 =====================");

			// options.setEnableRouteLevel(false);
			options.setRouteLevel(IPRouteLevel.Two);
			if (options.isSameFloorFirst()) {
				options.setUseSameFloor(true);
				result = requestRoute1(startPoint, endPoint, stopPoints, options);
				if (result == null) {
					options.setUseSameFloor(false);
					result = requestRoute1(startPoint, endPoint, stopPoints, options);
				}
			} else {
				options.setUseSameFloor(false);
				result = requestRoute1(startPoint, endPoint, stopPoints, options);
			}
		}

		if (result == null) {
			IPRouteDebugger.debugLog("============ Route Level 2 Failed ===============");
			IPRouteDebugger.debugLog("============ Route Level 3 =====================");

			// options.setEnableRouteLevel(false);
			options.setRouteLevel(IPRouteLevel.Three);
			if (options.isSameFloorFirst()) {
				options.setUseSameFloor(true);
				result = requestRoute1(startPoint, endPoint, stopPoints, options);
				if (result == null) {
					options.setUseSameFloor(false);
					result = requestRoute1(startPoint, endPoint, stopPoints, options);
				}
			} else {
				options.setUseSameFloor(false);
				result = requestRoute1(startPoint, endPoint, stopPoints, options);
			}
		}
		return result;
	}

	private synchronized TYServerMultiRouteResultV3 requestRoute1(TYLocalPoint startPoint, TYLocalPoint endPoint,
			List<TYLocalPoint> stopPoints, TYServerRouteOptions options) {
		TYServerMultiRouteResultV3 multiResult = null;

		if (stopPoints == null || stopPoints.size() == 0) {
			IPServerRouteResultObjectV3 res = networkDataset.getShortestPathV3(startPoint, endPoint, options);
			if (res != null) {
				TYServerRouteResultV3 result = res.routeResult;
				List<TYServerRouteResultV3> details = new ArrayList<>();
				details.add(result);
				multiResult = new TYServerMultiRouteResultV3(result, details);
				multiResult.setStartPoint(startPoint);
				multiResult.setEndPoint(endPoint);
				multiResult.setStopPoints(stopPoints);
				multiResult.startRoomID = networkDataset.targetStartRoomID;
				multiResult.endRoomID = networkDataset.targetEndRoomID;
			}
		} else {
			params = new IPMrParamsV3(startPoint, endPoint, stopPoints);
			IPMrCalculatorV3 calculator = new IPMrCalculatorV3(params, networkDataset, options);
			calculator.prepare();
			TYServerRouteResultV3 result = calculator.calculateV3(options.isRearrangeStops());

			Map<String, List<Object>> detailedRoute = calculator.getDetailedRouteV3();
			// List<Object> lines = detailedRoute.get("lines");
			List<Object> routeArray = detailedRoute.get("routes");
			List<Object> indices = detailedRoute.get("indices");
			List<Integer> indexArray = new ArrayList<>();

			List<TYLocalPoint> rearrangedPoints = new ArrayList<>();
			for (Object value : indices) {
				int index = (Integer) value;
				rearrangedPoints.add(stopPoints.get(index));
				indexArray.add(index);
			}

			if (result != null) {
				List<TYServerRouteResultV3> details = new ArrayList<>();
				for (Object obj : routeArray) {
					details.add((TYServerRouteResultV3) obj);
				}
				multiResult = new TYServerMultiRouteResultV3(result, details);
				multiResult.setStartPoint(startPoint);
				multiResult.setEndPoint(endPoint);
				multiResult.setStopPoints(stopPoints);
				multiResult.setIndices(indexArray);
				multiResult.setRearrangedPoints(rearrangedPoints);
				multiResult.startRoomID = networkDataset.targetStartRoomID;
				multiResult.endRoomID = networkDataset.targetEndRoomID;
			}
		}
		return multiResult;
	}
}

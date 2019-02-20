package cn.platalk.brtmap.route.core_v3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.platalk.map.entity.base.TYIMapInfo;

class IPMrCalculatorV3 {
	static final double LARGE_DISTANCE = 1000000000;

	private IPMrParamsV3 routeParams;
	private IPServerRouteNetworkDatasetV3 routeNetwork;
	private TYServerRouteOptions options;
	private int stopCount;

	private Map<String, IPServerRouteResultObjectV3> resDict;

	private double minDistance = LARGE_DISTANCE;
	private List<Integer> shortestRoute;

	IPMrCalculatorV3(IPMrParamsV3 params, IPServerRouteNetworkDatasetV3 dataset, TYServerRouteOptions options) {
		this.routeParams = params;
		this.routeNetwork = dataset;
		this.options = options;
		this.stopCount = params.stopCount();
	}

	void prepare() {
		resDict = new HashMap<String, IPServerRouteResultObjectV3>();
		List<IPMrStopV3> stopNodeArray = routeParams.getMiddleStops();

		// System.out.println("============= BuildKey =============");
		for (int i = 0; i < stopNodeArray.size(); ++i) {
			IPMrStopV3 startStop = routeParams.getStartStop();
			IPMrStopV3 middleStop = stopNodeArray.get(i);
			IPServerRouteResultObjectV3 res = routeNetwork.getShorestPathV3(startStop.getPos(), middleStop.getPos(),
					options);
			String key = getKey(startStop, middleStop);
			// if (res != null &&
			// IPCoordinateArray.processCoordinateArray2(res)) {
			if (res != null) {
				resDict.put(key, res);
			} else {
				// System.out.println("No route between stops: " + key);
			}
		}

		for (int i = 0; i < stopNodeArray.size(); ++i) {
			IPMrStopV3 endStop = routeParams.getEndStop();
			IPMrStopV3 middleStop = stopNodeArray.get(i);
			IPServerRouteResultObjectV3 res = routeNetwork.getShorestPathV3(middleStop.getPos(), endStop.getPos(),
					options);
			String key = getKey(middleStop, endStop);
			// if (res != null &&
			// IPCoordinateArray.processCoordinateArray2(res)) {
			if (res != null) {
				resDict.put(key, res);
			} else {
				// System.out.println("No route between stops: " + key);
			}
		}

		for (int i = 0; i < stopNodeArray.size(); ++i) {
			for (int j = 0; j < stopNodeArray.size(); ++j) {
				if (i == j)
					continue;

				IPMrStopV3 stop1 = stopNodeArray.get(i);
				IPMrStopV3 stop2 = stopNodeArray.get(j);
				IPServerRouteResultObjectV3 res = routeNetwork.getShorestPathV3(stop1.getPos(), stop2.getPos(),
						options);
				String key = getKey(stop1, stop2);
				// if (res != null &&
				// IPCoordinateArray.processCoordinateArray2(res)) {
				if (res != null) {
					// routeDict.put(key, line);
					resDict.put(key, res);
				} else {
					// System.out.println("No route between stops: " + key);
				}
			}
		}
	}

	TYServerRouteResultV3 calculateV3(boolean rearranageStops) {
		// System.out.println("calculate");
		List<IPMrStopV3> stopArray = routeParams.getMiddleStops();
		int indices[] = new int[stopArray.size()];
		for (int i = 0; i < stopArray.size(); ++i) {
			indices[i] = i;
		}

		minDistance = LARGE_DISTANCE;
		shortestRoute = new ArrayList<Integer>();

		if (rearranageStops) {
			fullArray(indices, 0, stopArray.size() - 1);
		} else {
			shortestRoute = new ArrayList<Integer>();
			for (int i = 0; i < stopArray.size(); ++i) {
				shortestRoute.add(i);
			}
			minDistance = getDistance(indices);
		}

		return getShortestRouteLineV3();
	}

	Map<String, List<Object>> getDetailedRouteV3() {
		Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();

		List<Object> indices = new ArrayList<Object>(shortestRoute);
		List<Object> routeArray = new ArrayList<Object>();

		String key = null;

		List<IPMrStopV3> middleStopArray = routeParams.getMiddleStops();

		{
			IPMrStopV3 startStop = routeParams.getStartStop();
			IPMrStopV3 firstStop = middleStopArray.get(shortestRoute.get(0));
			key = getKey(startStop, firstStop);
			IPServerRouteResultObjectV3 res = resDict.get(key);
			routeArray.add(res.routeResult);
		}

		{
			for (int i = 0; i < stopCount - 1; ++i) {
				IPMrStopV3 stop1 = middleStopArray.get(shortestRoute.get(i));
				IPMrStopV3 stop2 = middleStopArray.get(shortestRoute.get(i + 1));
				key = getKey(stop1, stop2);
				IPServerRouteResultObjectV3 res = resDict.get(key);
				routeArray.add(res.routeResult);
			}
		}

		{
			IPMrStopV3 endStop = routeParams.getEndStop();
			IPMrStopV3 lastStop = middleStopArray.get(shortestRoute.get(stopCount - 1));
			key = getKey(lastStop, endStop);
			IPServerRouteResultObjectV3 res = resDict.get(key);
			routeArray.add(res.routeResult);
		}

		resultMap.put("indices", indices);
		// resultMap.put("lines", lineArray);
		resultMap.put("routes", routeArray);
		return resultMap;
	}

	TYServerRouteResultV3 getShortestRouteLineV3() {
		List<IPServerRouteResultObjectV3> resList = new ArrayList<IPServerRouteResultObjectV3>();
		String key = null;

		IPMrStopV3 startStop = routeParams.getStartStop();
		IPMrStopV3 endStop = routeParams.getEndStop();
		List<IPMrStopV3> middleStopArray = routeParams.getMiddleStops();

		if (minDistance == LARGE_DISTANCE) {
			return null;
		}

		// System.out.println("nodes: " + shortestRoute);
		// System.out.println("================ ComputeKey ===============");

		{
			IPMrStopV3 firstStop = middleStopArray.get(shortestRoute.get(0));
			key = getKey(startStop, firstStop);
			resList.add(resDict.get(key));
		}

		{
			for (int i = 0; i < stopCount - 1; ++i) {
				IPMrStopV3 stop1 = middleStopArray.get(shortestRoute.get(i));
				IPMrStopV3 stop2 = middleStopArray.get(shortestRoute.get(i + 1));
				key = getKey(stop1, stop2);
				resList.add(resDict.get(key));
			}
		}

		{
			IPMrStopV3 lastStop = middleStopArray.get(shortestRoute.get(stopCount - 1));
			key = getKey(lastStop, endStop);
			resList.add(resDict.get(key));
		}

		List<IPServerRouteElement> elements = new ArrayList<IPServerRouteElement>();
		List<TYIMapInfo> mapInfos = resList.get(0).allMapInfoArray;
		for (IPServerRouteResultObjectV3 res : resList) {
			elements.addAll(res.allElementList);
		}
		IPServerRouteResultObjectV3 obj = new IPServerRouteResultObjectV3(startStop.getPos(), endStop.getPos(),
				mapInfos, elements);
		return obj.routeResult;
	}

	List<Object> getRouteCollection() {
		return null;
	}

	private double getDistance(int[] array) {
		double distance = 0;
		String key = null;

		IPMrStopV3 startStop = routeParams.getStartStop();
		IPMrStopV3 endStop = routeParams.getEndStop();
		List<IPMrStopV3> middleStopArray = routeParams.getMiddleStops();

		{
			IPMrStopV3 firstStop = middleStopArray.get(array[0]);
			key = getKey(startStop, firstStop);
			IPServerRouteResultObjectV3 res = resDict.get(key);
			if (res != null) {
				distance += res.length;
			} else {
				// System.out.println("No route between stop: %" + key
				// + ", so distance = max");
				return LARGE_DISTANCE;
			}
		}

		{
			for (int i = 0; i < stopCount - 1; ++i) {
				IPMrStopV3 stop1 = middleStopArray.get(array[i]);
				IPMrStopV3 stop2 = middleStopArray.get(array[i + 1]);
				key = getKey(stop1, stop2);
				IPServerRouteResultObjectV3 res = resDict.get(key);
				if (res != null) {
					distance += res.length;
				} else {
					// System.out.println("No route between stop: %" + key
					// + ", so distance = max");
					return LARGE_DISTANCE;
				}
			}
		}

		{
			IPMrStopV3 lastStop = middleStopArray.get(array[stopCount - 1]);
			key = getKey(lastStop, endStop);
			IPServerRouteResultObjectV3 res = resDict.get(key);
			if (res != null) {
				distance += res.length;
			} else {
				System.out.println("No route between stop: %" + key + ", so distance = max");
				return LARGE_DISTANCE;
			}
		}
		return distance;
	}

	private void fullArray(int[] array, int cursor, int end) {
		if (cursor == end) {
			double distance = getDistance(array);
			if (distance < minDistance) {
				minDistance = distance;
				shortestRoute = new ArrayList<Integer>();
				for (int i = 0; i < stopCount; ++i) {
					shortestRoute.add(array[i]);
				}
			}
		} else {
			for (int i = cursor; i <= end; ++i) {
				swap(array, cursor, i);
				fullArray(array, cursor + 1, end);
				swap(array, cursor, i);
			}
		}
	}

	private void swap(int[] array, int cursor, int index) {
		int temp = array[cursor];
		array[cursor] = array[index];
		array[index] = temp;
	}

	private String getKey(IPMrStopV3 stop1, IPMrStopV3 stop2) {
		return String.format("%d-%d", stop1.getStopID(), stop2.getStopID());
	}
}

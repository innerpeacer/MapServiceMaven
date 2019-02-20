package cn.platalk.map.route.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

class IPMrCalculator {
	static final double LARGE_DISTANCE = 1000000000;

	private IPMrParams routeParams;
	private IPServerRouteNetworkDataset routeNetwork;
	private int stopCount;

	private Map<String, LineString> routeDict;

	private double minDistance = LARGE_DISTANCE;
	private List<Integer> shortestRoute;

	IPMrCalculator(IPMrParams params, IPServerRouteNetworkDataset dataset) {
		this.routeParams = params;
		this.routeNetwork = dataset;
		this.stopCount = params.stopCount();
	}

	void prepare() {
		routeDict = new HashMap<String, LineString>();
		List<IPMrStop> stopNodeArray = routeParams.getMiddleStops();

		// System.out.println("============= BuildKey =============");
		for (int i = 0; i < stopNodeArray.size(); ++i) {
			IPMrStop startStop = routeParams.getStartStop();
			IPMrStop middleStop = stopNodeArray.get(i);
			LineString line = routeNetwork.getShorestPath(startStop.getPos(), middleStop.getPos());
			// String key = String.format("%d-%d", startStop.getStopID(),
			// middleStop.getStopID());
			String key = getKey(startStop, middleStop);
			// if (line != null
			// && IPCoordinateArray.processCoordinateArray2(line
			// .getCoordinates())) {
			if (line != null) {
				routeDict.put(key, line);
			} else {
				// System.out.println("No route between stops: " + key);
			}
		}

		for (int i = 0; i < stopNodeArray.size(); ++i) {
			IPMrStop endStop = routeParams.getEndStop();
			IPMrStop middleStop = stopNodeArray.get(i);
			LineString line = routeNetwork.getShorestPath(middleStop.getPos(), endStop.getPos());
			// String key = String.format("%d-%d", middleStop.getStopID(),
			// endStop.getStopID());
			String key = getKey(middleStop, endStop);
			// if (line != null &&
			// IPCoordinateArray.processCoordinateArray2(line.getCoordinates()))
			// {
			if (line != null) {
				routeDict.put(key, line);
			} else {
				// System.out.println("No route between stops: " + key);
			}
		}

		for (int i = 0; i < stopNodeArray.size(); ++i) {
			for (int j = 0; j < stopNodeArray.size(); ++j) {
				if (i == j)
					continue;

				IPMrStop stop1 = stopNodeArray.get(i);
				IPMrStop stop2 = stopNodeArray.get(j);
				LineString line = routeNetwork.getShorestPath(stop1.getPos(), stop2.getPos());
				// String key = String.format("%d-%d", stop1.getStopID(),
				// stop2.getStopID());
				String key = getKey(stop1, stop2);
				// if (line != null &&
				// IPCoordinateArray.processCoordinateArray2(line.getCoordinates()))
				// {
				if (line != null) {
					routeDict.put(key, line);
				} else {
					// System.out.println("No route between stops: " + key);
				}
			}
		}
	}

	LineString calculate(boolean rearranageStops) {
		// System.out.println("calculate");
		List<IPMrStop> stopArray = routeParams.getMiddleStops();
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

		return getShortestRouteLine();
	}

	Map<String, List<Object>> getDetailedRoute() {
		Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();

		List<Object> indices = new ArrayList<Object>(shortestRoute);
		List<Object> lineArray = new ArrayList<Object>();

		LineString line = null;
		String key = null;

		List<IPMrStop> middleStopArray = routeParams.getMiddleStops();

		{
			IPMrStop startStop = routeParams.getStartStop();
			IPMrStop firstStop = middleStopArray.get(shortestRoute.get(0));
			key = getKey(startStop, firstStop);
			line = routeDict.get(key);
			lineArray.add(line);
		}

		{
			for (int i = 0; i < stopCount - 1; ++i) {
				IPMrStop stop1 = middleStopArray.get(shortestRoute.get(i));
				IPMrStop stop2 = middleStopArray.get(shortestRoute.get(i + 1));
				key = getKey(stop1, stop2);
				line = routeDict.get(key);
				lineArray.add(line);
			}
		}

		{
			IPMrStop endStop = routeParams.getEndStop();
			IPMrStop lastStop = middleStopArray.get(shortestRoute.get(stopCount - 1));
			key = getKey(lastStop, endStop);
			line = routeDict.get(key);
			lineArray.add(line);
		}

		resultMap.put("indices", indices);
		resultMap.put("lines", lineArray);
		return resultMap;
	}

	LineString getShortestRouteLine() {
		GeometryFactory factory = new GeometryFactory();
		List<Coordinate> coordList = new ArrayList<Coordinate>();
		LineString result = null;

		LineString line = null;
		String key = null;

		IPMrStop startStop = routeParams.getStartStop();
		IPMrStop endStop = routeParams.getEndStop();
		List<IPMrStop> middleStopArray = routeParams.getMiddleStops();

		if (minDistance == LARGE_DISTANCE) {
			return result;
		}

		// System.out.println("nodes: " + shortestRoute);
		// System.out.println("================ ComputeKey ===============");

		{
			IPMrStop firstStop = middleStopArray.get(shortestRoute.get(0));
			key = getKey(startStop, firstStop);
			line = routeDict.get(key);
			for (int i = 0; i < line.getNumPoints(); ++i) {
				coordList.add(line.getCoordinateN(i));
			}
		}

		{
			for (int i = 0; i < stopCount - 1; ++i) {
				IPMrStop stop1 = middleStopArray.get(shortestRoute.get(i));
				IPMrStop stop2 = middleStopArray.get(shortestRoute.get(i + 1));
				key = getKey(stop1, stop2);
				line = routeDict.get(key);
				for (int j = 0; j < line.getNumPoints(); ++j) {
					coordList.add(line.getCoordinateN(j));
				}
			}
		}

		{
			IPMrStop lastStop = middleStopArray.get(shortestRoute.get(stopCount - 1));
			key = getKey(lastStop, endStop);
			line = routeDict.get(key);
			for (int j = 0; j < line.getNumPoints(); ++j) {
				coordList.add(line.getCoordinateN(j));
			}
		}

		Coordinate[] coords = new Coordinate[coordList.size()];
		coordList.toArray(coords);
		result = factory.createLineString(coords);
		return result;
	}

	List<Object> getRouteCollection() {
		return null;
	}

	private double getDistance(int[] array) {
		double distance = 0;
		LineString line = null;
		String key = null;

		IPMrStop startStop = routeParams.getStartStop();
		IPMrStop endStop = routeParams.getEndStop();
		List<IPMrStop> middleStopArray = routeParams.getMiddleStops();

		{
			IPMrStop firstStop = middleStopArray.get(array[0]);
			key = getKey(startStop, firstStop);
			line = routeDict.get(key);
			if (line != null) {
				distance += line.getLength();
			} else {
				// System.out.println("No route between stop: %" + key
				// + ", so distance = max");
				return LARGE_DISTANCE;
			}
		}

		{
			for (int i = 0; i < stopCount - 1; ++i) {
				IPMrStop stop1 = middleStopArray.get(array[i]);
				IPMrStop stop2 = middleStopArray.get(array[i + 1]);
				key = getKey(stop1, stop2);
				line = routeDict.get(key);
				if (line != null) {
					distance += line.getLength();
				} else {
					// System.out.println("No route between stop: %" + key
					// + ", so distance = max");
					return LARGE_DISTANCE;
				}
			}
		}

		{
			IPMrStop lastStop = middleStopArray.get(array[stopCount - 1]);
			key = getKey(lastStop, endStop);
			line = routeDict.get(key);
			if (line != null) {
				distance += line.getLength();
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

	private String getKey(IPMrStop stop1, IPMrStop stop2) {
		return String.format("%d-%d", stop1.getStopID(), stop2.getStopID());
	}
}

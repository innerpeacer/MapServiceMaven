package cn.platalk.brtmap.route.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import cn.platalk.map.entity.base.TYIBuilding;
import cn.platalk.map.entity.base.TYIMapInfo;
import cn.platalk.map.entity.base.TYIRouteLinkRecord;
import cn.platalk.map.entity.base.TYIRouteNodeRecord;
import cn.platalk.map.entity.base.TYLocalPoint;

public class TYServerMultiRouteManager {
	static final String TAG = TYServerRouteManager.class.getSimpleName();

	Point startPoint;
	Point endPoint;
	IPMrParams params;

	IPServerRouteNetworkDataset networkDataset;
	GeometryFactory factory = new GeometryFactory();
	private IPServerRoutePointConverter routePointConverter;
	List<TYIMapInfo> allMapInfoArray = new ArrayList<TYIMapInfo>();

	// private TYIBuilding currentBuilding;

	public TYServerMultiRouteManager(TYIBuilding building, List<TYIMapInfo> mapInfoArray,
			List<TYIRouteNodeRecord> nodes, List<TYIRouteLinkRecord> links) {
		// currentBuilding = building;
		allMapInfoArray.addAll(mapInfoArray);
		TYIMapInfo info = allMapInfoArray.get(0);
		routePointConverter = new IPServerRoutePointConverter(info.getMapExtent(), building.getOffset());

		networkDataset = new IPServerRouteNetworkDataset(nodes, links);
	}

	// public synchronized TYServerRouteResult requestRoute(TYLocalPoint start,
	// TYLocalPoint end, List<TYLocalPoint> stopPoints) {
	// System.out.println("Request Mutli Route: " + stopPoints.size()
	// + " stops");
	// startPoint = routePointConverter.getRoutePointFromLocalPoint(start);
	// endPoint = routePointConverter.getRoutePointFromLocalPoint(end);
	//
	// LineString resultRoute = networkDataset.getShorestPath(startPoint,
	// endPoint);
	// return processPolyline(resultRoute);
	// }

	public synchronized TYServerMultiRouteResult requestRoute(TYLocalPoint start, TYLocalPoint end,
			List<TYLocalPoint> stops, boolean rearrangeStops) {
		// System.out.println("Request Mutli Route: " + stops.size() +
		// " stops");
		startPoint = routePointConverter.getRoutePointFromLocalPoint(start);
		endPoint = routePointConverter.getRoutePointFromLocalPoint(end);

		LineString line = null;
		TYServerMultiRouteResult multiResult = null;

		if (stops == null || stops.size() == 0) {
			// System.out.println("Compute Single");
			line = networkDataset.getShorestPath(startPoint, endPoint);
			if (line != null && line.getNumPoints() != 0) {
				TYServerRouteResult result = processPolyline(line);
				List<TYServerRouteResult> details = new ArrayList<TYServerRouteResult>();
				details.add(result);
				multiResult = new TYServerMultiRouteResult(result, details);
				multiResult.setStartPoint(start);
				multiResult.setEndPoint(end);
				multiResult.setStopPoints(stops);
			}
		} else {
			// System.out.println("Compute Multiple");
			List<Point> stopPoints = new ArrayList<Point>();
			for (int i = 0; i < stops.size(); ++i) {
				Point sp = routePointConverter.getRoutePointFromLocalPoint(stops.get(i));
				stopPoints.add(sp);
			}

			params = new IPMrParams(startPoint, endPoint, stopPoints);
			IPMrCalculator calculator = new IPMrCalculator(params, networkDataset);
			calculator.prepare();
			line = calculator.calculate(rearrangeStops);
			Map<String, List<Object>> detailedRoute = calculator.getDetailedRoute();
			List<Object> lines = detailedRoute.get("lines");
			List<Object> indices = detailedRoute.get("indices");
			List<Integer> indiceArray = new ArrayList<Integer>();

			List<TYLocalPoint> rearrangedPoints = new ArrayList<TYLocalPoint>();
			for (int i = 0; i < indices.size(); ++i) {
				int index = (Integer) indices.get(i);
				rearrangedPoints.add(stops.get(index));
				indiceArray.add(index);
			}

			if (line != null && line.getNumPoints() > 0) {
				TYServerRouteResult result = processPolyline(line);
				List<TYServerRouteResult> details = new ArrayList<TYServerRouteResult>();
				for (int i = 0; i < lines.size(); ++i) {
					details.add(processPolyline((LineString) lines.get(i)));
				}
				multiResult = new TYServerMultiRouteResult(result, details);
				multiResult.setStartPoint(start);
				multiResult.setEndPoint(end);
				multiResult.setStopPoints(stops);
				multiResult.setIndices(indiceArray);
				multiResult.setRearrangedPoints(rearrangedPoints);
			}
		}
		return multiResult;

		// LineString resultRoute = networkDataset.getShorestPath(startPoint,
		// endPoint);
		// return processPolyline(resultRoute);
	}

	TYServerRouteResult processPolyline(LineString routeLine) {
		if (routeLine == null) {
			return null;
		}

		List<List<TYLocalPoint>> pointArray = new ArrayList<List<TYLocalPoint>>();
		List<Integer> floorArray = new ArrayList<Integer>();

		int currentFloor = 0;
		List<TYLocalPoint> currentArray = null;

		int num = routeLine.getNumPoints();
		for (int i = 0; i < num; i++) {
			Coordinate c = routeLine.getCoordinateN(i);
			TYLocalPoint lp = routePointConverter.getLocalPointFromRouteCoordinate(c);
			boolean isValid = routePointConverter.checkPointValidity(lp);
			if (isValid) {
				if (lp.getFloor() != currentFloor) {
					currentFloor = lp.getFloor();
					currentArray = new ArrayList<TYLocalPoint>();
					pointArray.add(currentArray);
					floorArray.add(currentFloor);
				}
				currentArray.add(lp);
			}
		}

		if (floorArray.size() < 1) {
			return null;
		}

		List<TYServerRoutePart> routePartArray = new ArrayList<TYServerRoutePart>();
		for (int i = 0; i < floorArray.size(); i++) {
			int floor = floorArray.get(i);
			List<TYLocalPoint> pArray = pointArray.get(i);
			if (pArray.size() < 2) {
				continue;
			}

			Coordinate[] coordinateList = new Coordinate[pArray.size()];
			for (int j = 0; j < pArray.size(); ++j) {
				TYLocalPoint lp = pArray.get(j);
				coordinateList[j] = new Coordinate(lp.getX(), lp.getY());
			}

			LineString line = factory.createLineString(coordinateList);
			TYIMapInfo info = IPMapInfoHelper.searchMapInfoFromArray(allMapInfoArray, floor);
			TYServerRoutePart rp = new TYServerRoutePart(line, info);
			routePartArray.add(rp);
		}

		int routePartNum = (int) routePartArray.size();
		for (int i = 0; i < routePartNum; i++) {
			TYServerRoutePart rp = routePartArray.get(i);
			if (i > 0) {
				rp.setPreviousPart(routePartArray.get(i - 1));
			}

			if (i < routePartNum - 1) {
				rp.setNextPart(routePartArray.get(i + 1));
			}
			rp.setPartIndex(i);
		}
		return new TYServerRouteResult(routePartArray);
	}
}

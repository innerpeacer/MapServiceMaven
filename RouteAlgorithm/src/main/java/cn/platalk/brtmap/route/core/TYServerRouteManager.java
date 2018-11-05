package cn.platalk.brtmap.route.core;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.entity.base.TYIBuilding;
import cn.platalk.brtmap.entity.base.TYIMapInfo;
import cn.platalk.brtmap.entity.base.TYIRouteLinkRecord;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecord;
import cn.platalk.brtmap.entity.base.TYLocalPoint;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

public class TYServerRouteManager {
	static final String TAG = TYServerRouteManager.class.getSimpleName();

	Point startPoint;
	Point endPoint;

	IPServerRouteNetworkDataset networkDataset;
	GeometryFactory factory = new GeometryFactory();
	private IPServerRoutePointConverter routePointConverter;
	List<TYIMapInfo> allMapInfoArray = new ArrayList<TYIMapInfo>();

	// private TYIBuilding currentBuilding;

	public TYServerRouteManager(TYIBuilding building,
			List<TYIMapInfo> mapInfoArray, List<TYIRouteNodeRecord> nodes,
			List<TYIRouteLinkRecord> links) {
		// currentBuilding = building;
		allMapInfoArray.addAll(mapInfoArray);
		TYIMapInfo info = allMapInfoArray.get(0);
		routePointConverter = new IPServerRoutePointConverter(
				info.getMapExtent(), building.getOffset());

		networkDataset = new IPServerRouteNetworkDataset(nodes, links);
	}

	public synchronized TYServerRouteResult requestRoute(TYLocalPoint start,
			TYLocalPoint end) {
		startPoint = routePointConverter.getRoutePointFromLocalPoint(start);
		endPoint = routePointConverter.getRoutePointFromLocalPoint(end);

		LineString resultRoute = networkDataset.getShorestPath(startPoint,
				endPoint);
		return processPolyline(resultRoute);
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
			TYLocalPoint lp = routePointConverter
					.getLocalPointFromRouteCoordinate(c);
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
			TYIMapInfo info = IPMapInfoHelper.searchMapInfoFromArray(
					allMapInfoArray, floor);
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

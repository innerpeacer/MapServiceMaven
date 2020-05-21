package cn.platalk.map.route.core;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Point;

class IPMrParams {

	private final Point startPoint;
	private final Point endPoint;
	private final List<Point> stopPoints;

	List<IPMrStop> stopArray;
	IPMrStop startStop;
	IPMrStop endStop;
	List<IPMrStop> middleStopArray;

	IPMrParams(Point start, Point end, List<Point> stops) {
		this.startPoint = start;
		this.endPoint = end;
		this.stopPoints = new ArrayList<>(stops);

		buildStops();
	}

	private void buildStops() {
		stopArray = new ArrayList<>();
		middleStopArray = new ArrayList<>();

		int nodeIndex = 0;

		startStop = new IPMrStop(nodeIndex++);
		startStop.setPos(startPoint);
		stopArray.add(startStop);

		for (Point stopPoint : stopPoints) {
			IPMrStop middleStop = new IPMrStop(nodeIndex++);
			middleStop.setPos(stopPoint);
			stopArray.add(middleStop);
			middleStopArray.add(middleStop);
		}

		endStop = new IPMrStop(nodeIndex);
		endStop.setPos(endPoint);
		stopArray.add(endStop);
	}

	Point getStopPoint(int i) {
		return stopPoints.get(i);
	}

	int stopCount() {
		return stopPoints.size();
	}

	IPMrStop getStartStop() {
		return startStop;
	}

	IPMrStop getEndStop() {
		return endStop;
	}

	List<IPMrStop> getMiddleStops() {
		return middleStopArray;
	}
}

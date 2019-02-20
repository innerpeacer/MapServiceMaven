package cn.platalk.map.route.core;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Point;

class IPMrParams {

	private Point startPoint;
	private Point endPoint;
	private List<Point> stopPoints;

	List<IPMrStop> stopArray;
	IPMrStop startStop;
	IPMrStop endStop;
	List<IPMrStop> middleStopArray;

	IPMrParams(Point start, Point end, List<Point> stops) {
		this.startPoint = start;
		this.endPoint = end;
		this.stopPoints = new ArrayList<Point>(stops);

		buildStops();
	}

	private void buildStops() {
		stopArray = new ArrayList<IPMrStop>();
		middleStopArray = new ArrayList<IPMrStop>();

		int nodeIndex = 0;

		startStop = new IPMrStop(nodeIndex++);
		startStop.setPos(startPoint);
		stopArray.add(startStop);

		for (int i = 0; i < stopPoints.size(); ++i) {
			IPMrStop middleStop = new IPMrStop(nodeIndex++);
			middleStop.setPos(stopPoints.get(i));
			stopArray.add(middleStop);
			middleStopArray.add(middleStop);
		}

		endStop = new IPMrStop(nodeIndex++);
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

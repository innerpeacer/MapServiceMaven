package cn.platalk.map.route.core_v3;

import cn.platalk.map.entity.base.impl.map.TYLocalPoint;

import java.util.ArrayList;
import java.util.List;

class IPMrParamsV3 {
	private final TYLocalPoint startPoint;
	private final TYLocalPoint endPoint;
	private final List<TYLocalPoint> stopPoints;

	List<IPMrStopV3> stopArray;
	IPMrStopV3 startStop;
	IPMrStopV3 endStop;
	List<IPMrStopV3> middleStopArray;

	IPMrParamsV3(TYLocalPoint start, TYLocalPoint end, List<TYLocalPoint> stops) {
		this.startPoint = start;
		this.endPoint = end;
		this.stopPoints = new ArrayList<>(stops);

		buildStops();
	}

	private void buildStops() {
		stopArray = new ArrayList<>();
		middleStopArray = new ArrayList<>();

		int nodeIndex = 0;

		startStop = new IPMrStopV3(nodeIndex++);
		startStop.setPos(startPoint);
		stopArray.add(startStop);

		for (TYLocalPoint stopPoint : stopPoints) {
			IPMrStopV3 middleStop = new IPMrStopV3(nodeIndex++);
			middleStop.setPos(stopPoint);
			stopArray.add(middleStop);
			middleStopArray.add(middleStop);
		}

		endStop = new IPMrStopV3(nodeIndex);
		endStop.setPos(endPoint);
		stopArray.add(endStop);
	}

	TYLocalPoint getStopPoint(int i) {
		return stopPoints.get(i);
	}

	int stopCount() {
		return stopPoints.size();
	}

	IPMrStopV3 getStartStop() {
		return startStop;
	}

	IPMrStopV3 getEndStop() {
		return endStop;
	}

	List<IPMrStopV3> getMiddleStops() {
		return middleStopArray;
	}
}

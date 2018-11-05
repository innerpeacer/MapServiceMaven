package cn.platalk.brtmap.route.core;

import com.vividsolutions.jts.geom.Point;

class IPMrStop {
	private int stopID;
	private Point pos;

	IPMrStop(int sID) {
		this.stopID = sID;
	}

	int getStopID() {
		return stopID;
	}

	Point getPos() {
		return pos;
	}

	void setPos(Point pos) {
		this.pos = pos;
	}

	@Override
	public String toString() {
		return String.format("Stop: %d", stopID);
	}

}

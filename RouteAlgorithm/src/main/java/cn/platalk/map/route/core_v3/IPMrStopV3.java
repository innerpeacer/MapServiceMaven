package cn.platalk.map.route.core_v3;

import cn.platalk.map.entity.base.impl.TYLocalPoint;

class IPMrStopV3 {
	private final int stopID;
	private TYLocalPoint pos;

	IPMrStopV3(int sID) {
		this.stopID = sID;
	}

	int getStopID() {
		return stopID;
	}

	TYLocalPoint getPos() {
		return pos;
	}

	void setPos(TYLocalPoint pos) {
		this.pos = pos;
	}

	@Override
	public String toString() {
		return String.format("Stop: %d", stopID);
	}

}

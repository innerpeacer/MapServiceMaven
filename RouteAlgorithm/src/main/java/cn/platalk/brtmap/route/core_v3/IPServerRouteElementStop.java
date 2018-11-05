package cn.platalk.brtmap.route.core_v3;

import cn.platalk.brtmap.entity.base.TYLocalPoint;

public class IPServerRouteElementStop extends IPServerRouteElement {

	public TYLocalPoint m_pos;

	public IPServerRouteElementStop(TYLocalPoint lp) {
		m_pos = lp;
	}

	@Override
	public int getFloor() {
		return m_pos.getFloor();
	}

	@Override
	public boolean isNode() {
		return false;
	}

	@Override
	public boolean isStop() {
		return true;
	}

	@Override
	public String toString() {
		return String.format("Element Stop: %s", m_pos);

	}
}

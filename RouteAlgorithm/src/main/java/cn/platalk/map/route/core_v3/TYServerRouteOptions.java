package cn.platalk.map.route.core_v3;

import java.util.ArrayList;
import java.util.List;

public class TYServerRouteOptions {
	public static final boolean DEFAULT_SAME_FLOOR_FIRST = true;
	public static final boolean DEFAULT_REARRANGE_STOPS = true;

	private int linkType = 0;
	private List<String> ignoredNodeCategoryList = new ArrayList<>();
	private boolean rearrangeStops = DEFAULT_REARRANGE_STOPS;
	private boolean useSameFloor = false;
	private boolean sameFloorFirst = DEFAULT_SAME_FLOOR_FIRST;

	// private boolean enableRouteLevel = true;
	private IPRouteLevel routeLevel = IPRouteLevel.Zero;

	public TYServerRouteOptions() {

	}

	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}

	public int getLinkType() {
		return linkType;
	}

	public void setIgnoredNodeCategoryList(List<String> ignoredNodeCategoryList) {
		this.ignoredNodeCategoryList = ignoredNodeCategoryList;
	}

	public List<String> getIgnoredNodeCategoryList() {
		return ignoredNodeCategoryList;
	}

	public void setRearrangeStops(boolean rearrangeStops) {
		this.rearrangeStops = rearrangeStops;
	}

	public boolean isRearrangeStops() {
		return rearrangeStops;
	}

	public void setSameFloorFirst(boolean sameFloorFirst) {
		this.sameFloorFirst = sameFloorFirst;
	}

	public boolean isSameFloorFirst() {
		return sameFloorFirst;
	}

	public void setUseSameFloor(boolean useSameFloor) {
		this.useSameFloor = useSameFloor;
	}

	public boolean isUseSameFloor() {
		return useSameFloor;
	}

	// public void setEnableRouteLevel(boolean enableRouteLevel) {
	// this.enableRouteLevel = enableRouteLevel;
	// }
	//
	// public boolean isEnableRouteLevel() {
	// return enableRouteLevel;
	// }

	public void setRouteLevel(IPRouteLevel level) {
		this.routeLevel = level;
	}

	public IPRouteLevel getRouteLevel() {
		return this.routeLevel;
	}
}

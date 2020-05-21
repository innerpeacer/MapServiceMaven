package cn.platalk.map.route.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import cn.platalk.map.entity.base.TYIMapInfo;

class TYServerRoutePart {
	final LineString route;
	final TYIMapInfo info;

	TYServerRoutePart previousPart = null;
	TYServerRoutePart nextPart = null;
	int partIndex;

	static final GeometryFactory factory = new GeometryFactory();

	public TYServerRoutePart(LineString polyline, TYIMapInfo info) {
		this.route = polyline;
		this.info = info;
	}

	public JSONObject buildJson() {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("partIndex", partIndex);
			jsonObject.put("floor", info.getFloorNumber());
			JSONArray coordinateArray = new JSONArray();
			Coordinate[] coordinates = route.getCoordinates();
			for (Coordinate c : coordinates) {
				JSONArray array = new JSONArray();
				array.put(c.x);
				array.put(c.y);
				coordinateArray.put(array);
			}
			jsonObject.put("coordinates", coordinateArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public boolean isFirstPart() {
		return previousPart == null;
	}

	public boolean isLastPart() {
		return nextPart == null;
	}

	public boolean isMiddlePart() {
		return ((previousPart != null) && (nextPart != null));
	}

	public Point getFirstPoint() {
		Point result = null;
		if (route != null) {
			result = factory.createPoint(route.getCoordinateN(0));
		}
		return result;
	}

	public Point getLastPoint() {
		Point result = null;
		if (route != null) {
			int numPoint = route.getNumPoints();
			result = factory.createPoint(route.getCoordinateN(numPoint - 1));
		}
		return result;
	}

	public LineString getRoute() {
		return route;
	}

	public TYIMapInfo getMapInfo() {
		return info;
	}

	public TYServerRoutePart getPreviousPart() {
		return previousPart;
	}

	public void setPreviousPart(TYServerRoutePart previousPart) {
		this.previousPart = previousPart;
	}

	public TYServerRoutePart getNextPart() {
		return nextPart;
	}

	public void setNextPart(TYServerRoutePart nextPart) {
		this.nextPart = nextPart;
	}

	public void setPartIndex(int i) {
		partIndex = i;
	}

	protected int getPartIndex() {
		return partIndex;
	}

	@Override
	public String toString() {
		return partIndex + ": " + route;
	}
}

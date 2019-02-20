package cn.platalk.brtmap.route.core_v3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateArrays;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import cn.platalk.map.entity.base.TYIMapInfo;
import cn.platalk.map.entity.base.TYLocalPoint;

class TYServerRoutePartV3 {
	List<IPServerRouteElement> elementList = new ArrayList<IPServerRouteElement>();

	public TYLocalPoint startPoint;
	public TYLocalPoint endPoint;

	LineString route = null;
	double length = 0;
	TYIMapInfo info = null;
	List<IPServerRouteElementNode> elementNodeList = new ArrayList<IPServerRouteElementNode>();

	TYServerRoutePartV3 previousPart = null;
	TYServerRoutePartV3 nextPart = null;
	int partIndex;

	static GeometryFactory factory = new GeometryFactory();

	public TYServerRoutePartV3(TYLocalPoint start, TYLocalPoint end, List<IPServerRouteElement> elements,
			TYIMapInfo info) {
		startPoint = start;
		endPoint = end;
		elementList.addAll(elements);
		processElements();
		this.info = info;
	}

	private void processElements() {
		// System.out.println("------- processElements -----------");

		List<Coordinate> coordList = new ArrayList<Coordinate>();
		// System.out.println("elementList.size()");
		// System.out.println(elementList.size());

		for (int i = 0; i < elementList.size(); i++) {
			IPServerRouteElement element = elementList.get(i);
			if (element.isStop()) {
				IPServerRouteElementStop stop = (IPServerRouteElementStop) element;
				coordList.add(new Coordinate(stop.m_pos.getX(), stop.m_pos.getY()));
			} else if (element.isNode()) {
				IPServerRouteElementNode node = (IPServerRouteElementNode) element;
				if (node.m_nodeName != null && node.m_nodeName.length() > 0) {
					elementNodeList.add(node);
				}
			} else {
				IPServerRouteElementLink link = (IPServerRouteElementLink) element;
				LineString line = link.m_line;
				if (line != null) {
					Collections.addAll(coordList, line.getCoordinates());
				}
			}
		}

		Coordinate[] coordArray = coordList.toArray(new Coordinate[coordList.size()]);
		// System.out.println(coordArray.length);
		coordArray = CoordinateArrays.removeRepeatedPoints(coordArray);
		// System.out.println(coordArray.length);

		// if (elementList.size() == 2) {
		// for (int i = 0; i < elementList.size(); i++) {
		// System.out.println(elementList.get(i));
		// }
		// }

		if (coordArray.length == 1) {
			Coordinate[] coordArray2 = new Coordinate[2];
			coordArray2[0] = coordArray[0];
			coordArray2[1] = coordArray[0];
			coordArray = coordArray2;
		}
		route = factory.createLineString(coordArray);
		length = route.getLength();
	}

	private JSONObject buildElementNode(IPServerRouteElementNode node) {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("nodeID", node.m_nodeID);
			jsonObject.put("x", node.m_pos.getX());
			jsonObject.put("y", node.m_pos.getY());
			jsonObject.put("floor", node.m_floor);
			jsonObject.put("name", node.m_nodeName);
			jsonObject.put("categoryID", node.m_categoryID);
			jsonObject.put("level", node.m_level);
			jsonObject.put("isSwitching", node.m_isSwitching);
			jsonObject.put("switchingID", node.m_switchingID);
			jsonObject.put("direction", node.m_direction);
			jsonObject.put("nodeType", node.m_nodeType);
			jsonObject.put("open", node.m_open);
			jsonObject.put("openTime", node.m_openTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public JSONObject buildJson() {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("partIndex", partIndex);
			jsonObject.put("floor", info.getFloorNumber());
			JSONArray coordinateArray = new JSONArray();
			Coordinate[] coordinates = route.getCoordinates();
			for (int i = 0; i < coordinates.length; i++) {
				Coordinate c = coordinates[i];
				JSONArray array = new JSONArray();
				array.put(c.x);
				array.put(c.y);
				coordinateArray.put(array);
			}
			jsonObject.put("coordinates", coordinateArray);

			JSONArray elementNodeArray = new JSONArray();
			for (int i = 0; i < elementNodeList.size(); ++i) {
				JSONObject nodeObj = buildElementNode(elementNodeList.get(i));
				elementNodeArray.put(nodeObj);
			}
			jsonObject.put("nodes", elementNodeArray);
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

	public double getLength() {
		return length;
	}

	public LineString getRoute() {
		return route;
	}

	public TYIMapInfo getMapInfo() {
		return info;
	}

	public TYServerRoutePartV3 getPreviousPart() {
		return previousPart;
	}

	public void setPreviousPart(TYServerRoutePartV3 previousPart) {
		this.previousPart = previousPart;
	}

	public TYServerRoutePartV3 getNextPart() {
		return nextPart;
	}

	public void setNextPart(TYServerRoutePartV3 nextPart) {
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

package cn.platalk.map.route.core_v3;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.impl.map.TYLocalPoint;
import cn.platalk.map.entity.base.map.TYIMapInfo;
import com.vividsolutions.jts.geom.GeometryFactory;

public class IPServerRouteResultObjectV3 {

	static GeometryFactory factory = new GeometryFactory();

	public final TYLocalPoint startPoint;
	public final TYLocalPoint endPoint;

	final List<IPServerRouteElement> allElementList = new ArrayList<>();
	final List<TYIMapInfo> allMapInfoArray = new ArrayList<>();

	public final TYServerRouteResultV3 routeResult;
	public double length;

	public IPServerRouteResultObjectV3(TYLocalPoint start, TYLocalPoint end, List<TYIMapInfo> mapInfos,
			List<IPServerRouteElement> elements) {
		startPoint = start;
		endPoint = end;
		allMapInfoArray.addAll(mapInfos);

		allElementList.add(new IPServerRouteElementStop(start));
		allElementList.addAll(elements);
		allElementList.add(new IPServerRouteElementStop(end));

		routeResult = processV3();
	}

	private TYServerRouteResultV3 processV3() {
		// System.out.println("----- processV3 --------");
		if (allElementList.size() == 0) {
			return null;
		}

		// System.out.println("Elements: " + allElementList.size());
		length = 0;

		List<List<IPServerRouteElement>> sortedElementList = new ArrayList<>();
		List<Integer> floorArray = new ArrayList<>();

		int currentFloor = 0;
		List<IPServerRouteElement> currentElementArray = new ArrayList<>();

		for (IPServerRouteElement element : allElementList) {
			if (element.getFloor() == 0) {
				System.out.println(element);
			}

			if (element.getFloor() != currentFloor) {
				currentFloor = element.getFloor();
				currentElementArray = new ArrayList<>();
				sortedElementList.add(currentElementArray);
				floorArray.add(currentFloor);
			}
			currentElementArray.add(element);
		}

		if (floorArray.size() < 1) {
			return null;
		}

		List<TYServerRoutePartV3> routePartArray = new ArrayList<>();
		for (int i = 0; i < floorArray.size(); i++) {
			int floor = floorArray.get(i);
			List<IPServerRouteElement> elementArray = sortedElementList.get(i);
			TYIMapInfo info = IPMapInfoHelper.searchMapInfoFromArray(allMapInfoArray, floor);
			TYServerRoutePartV3 rp = new TYServerRoutePartV3(startPoint, endPoint, elementArray, info);
			length += rp.getLength();
			routePartArray.add(rp);
		}

		int routePartNum = routePartArray.size();
		for (int i = 0; i < routePartNum; i++) {
			TYServerRoutePartV3 rp = routePartArray.get(i);
			if (i > 0) {
				rp.setPreviousPart(routePartArray.get(i - 1));
			}

			if (i < routePartNum - 1) {
				rp.setNextPart(routePartArray.get(i + 1));
			}
			rp.setPartIndex(i);
		}

		// System.out.println("Length: " + length);
		// System.out.println("----- processV3 overs--------");

		return new TYServerRouteResultV3(routePartArray);
	}
}

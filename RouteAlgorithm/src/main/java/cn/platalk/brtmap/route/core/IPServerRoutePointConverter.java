package cn.platalk.brtmap.route.core;

import cn.platalk.brtmap.entity.base.TYIMapExtent;
import cn.platalk.brtmap.entity.base.TYIMapSize;
import cn.platalk.brtmap.entity.base.TYLocalPoint;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

class IPServerRoutePointConverter {

	private TYIMapExtent baseExtent;
	private TYIMapSize baseOffset;

	public IPServerRoutePointConverter(TYIMapExtent extent, TYIMapSize offset) {
		this.baseExtent = extent;
		this.baseOffset = offset;
	}

	public Point getRoutePointFromLocalPoint(TYLocalPoint lp) {
		double newX = lp.getX() + baseOffset.getX() * (lp.getFloor() - 1);
		Coordinate c = new Coordinate(newX, lp.getY());
		return new GeometryFactory().createPoint(c);
	}

	// public TYLocalPoint getLocalPointFromRoutePoint(Point routePoint) {
	// double xOffset = routePoint.getX() - baseExtent.getXmin();
	//
	// double grid = xOffset / baseOffset.getX();
	// int index = (int) Math.floor(grid);
	//
	// double originX = routePoint.getX() - index * baseOffset.getX();
	// double originY = routePoint.getY();
	// int floor = index + 1;
	//
	// return new TYLocalPoint(originX, originY, floor);
	// }

	public TYLocalPoint getLocalPointFromRouteCoordinate(
			Coordinate routeCoordinate) {
		double xOffset = routeCoordinate.x - baseExtent.getXmin();

		double grid = xOffset / baseOffset.getX();
		int index = (int) Math.floor(grid);

		double originX = routeCoordinate.x - index * baseOffset.getX();
		double originY = routeCoordinate.y;
		int floor = index + 1;

		return new TYLocalPoint(originX, originY, floor);
	}

	public boolean checkPointValidity(TYLocalPoint point) {
		if (point.getX() >= baseExtent.getXmin()
				&& point.getX() <= baseExtent.getXmax()
				&& point.getY() >= baseExtent.getYmin()
				&& point.getY() <= baseExtent.getYmax()) {
			return true;
		} else {
			return false;
		}
	}
}
package cn.platalk.map.route.core_v3;

import com.vividsolutions.jts.geom.Point;

public class IPAngleUtils {

	public static double AngleForPoints(Point prevP, Point p, Point nextP) {
		double prevDis = Math.sqrt(Math.pow(prevP.getX() - p.getX(), 2) + Math.pow(prevP.getY() - p.getY(), 2));
		double nextDis = Math.sqrt(Math.pow(p.getX() - nextP.getX(), 2) + Math.pow(p.getY() - nextP.getY(), 2));
		double crossProduct = (prevP.getX() - p.getX()) * (p.getX() - nextP.getX())
				+ (prevP.getY() - p.getY()) * (p.getY() - nextP.getY());
		double cosOfAngle = crossProduct / (prevDis * nextDis);
		return Math.toDegrees(Math.acos(cosOfAngle));
	}

}

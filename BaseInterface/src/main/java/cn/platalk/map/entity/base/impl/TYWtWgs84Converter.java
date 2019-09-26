package cn.platalk.map.entity.base.impl;

import cn.platalk.common.TYCoordTransform;

public class TYWtWgs84Converter {
	private boolean isValid = false;
	private TYCoordTransform transform = null;

	public TYWtWgs84Converter(double wt[], double[] wgs84) {
		if (wt == null || wt.length != 6 || wgs84 == null || wgs84.length != 6) {
			return;
		}

		double mercatorXY[] = new double[6];

		TYLocalPoint xy1 = new TYLngLat(wgs84[0], wgs84[1]).toLocalPoint();
		TYLocalPoint xy2 = new TYLngLat(wgs84[2], wgs84[3]).toLocalPoint();
		TYLocalPoint xy3 = new TYLngLat(wgs84[4], wgs84[5]).toLocalPoint();

		mercatorXY[0] = xy1.getX();
		mercatorXY[1] = xy1.getY();
		mercatorXY[2] = xy2.getX();
		mercatorXY[3] = xy2.getY();
		mercatorXY[4] = xy3.getX();
		mercatorXY[5] = xy3.getY();
		try {
			transform = new TYCoordTransform(mercatorXY, wt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.isValid = true;
	}

	public TYLocalPoint converterToMercator(TYLngLat lngLat) {
		if (!this.isValid)
			return null;
		TYLocalPoint lp = lngLat.toLocalPoint();
		double xy[] = transform.getTransformedCoordinate(new double[] { lp.getX(), lp.getY() });
		return new TYLocalPoint(xy[0], xy[1]);
	}
}

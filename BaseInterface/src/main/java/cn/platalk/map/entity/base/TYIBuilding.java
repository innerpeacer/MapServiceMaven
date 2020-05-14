package cn.platalk.map.entity.base;

import cn.platalk.common.TYIJsonFeature;

public interface TYIBuilding extends TYIJsonFeature {
	String getCityID();

	String getBuildingID();

	String getName();

	String getAddress();

	double getLongitude();

	double getLatitude();

	double getInitAngle();

	int getInitFloorIndex();

	String getRouteURL();

	TYIMapSize getOffset();

	TYIMapExtent getBuildingExtent();

	String getDataVersion();

	int getStatus();

	double[] getWgs84CalibrationPoint();

	double[] getWtCalibrationPoint();

	double getCenterX();

	double getCenterY();

}

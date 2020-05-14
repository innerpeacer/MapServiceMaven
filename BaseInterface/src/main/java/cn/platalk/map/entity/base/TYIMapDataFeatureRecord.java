package cn.platalk.map.entity.base;

import com.vividsolutions.jts.geom.Geometry;

import cn.platalk.common.TYIGeojsonFeature;

public interface TYIMapDataFeatureRecord extends TYIGeojsonFeature {
	int LAYER_FLOOR = 1;
	int LAYER_ROOM = 2;
	int LAYER_ASSET = 3;
	int LAYER_FACILITY = 4;
	int LAYER_LABEL = 5;
	int LAYER_SHADE = 6;

	Geometry getGeometryData();

	String getObjectID();

	byte[] getGeometry();

	String getGeoID();

	String getPoiID();

	String getFloorID();

	String getBuildingID();

	String getCategoryID();

	String getName();

	String getNameEn();

	String getNameOther();

	String getIcon();

	int getSymbolID();

	int getFloorNumber();

	String getFloorName();

	double getShapeLength();

	double getShapeArea();

	double getLabelX();

	double getLabelY();

	int getLayer();

	double getLevelMax();

	double getLevelMin();

	boolean isVisible();

	boolean isExtrusion();

	double getExtrusionHeight();

	double getExtrusionBase();

	int getPriority();
}

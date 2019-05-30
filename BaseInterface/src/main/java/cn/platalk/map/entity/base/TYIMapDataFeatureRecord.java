package cn.platalk.map.entity.base;

import com.vividsolutions.jts.geom.Geometry;

public interface TYIMapDataFeatureRecord {
	public static final int LAYER_FLOOR = 1;
	public static final int LAYER_ROOM = 2;
	public static final int LAYER_ASSET = 3;
	public static final int LAYER_FACILITY = 4;
	public static final int LAYER_LABEL = 5;
	public static final int LAYER_SHADE = 6;

	public Geometry getGeometryData();

	public String getObjectID();

	public byte[] getGeometry();

	public String getGeoID();

	public String getPoiID();

	public String getFloorID();

	public String getBuildingID();

	public String getCategoryID();

	public String getName();

	public String getNameEn();

	public String getNameOther();

	public int getSymbolID();

	public int getFloorNumber();

	public String getFloorName();

	public double getShapeLength();

	public double getShapeArea();

	public double getLabelX();

	public double getLabelY();

	public int getLayer();

	public int getLevelMax();

	public int getLevelMin();

	public boolean isExtrusion();

	public double getExtrusionHeight();

	public double getExtrusionBase();
}

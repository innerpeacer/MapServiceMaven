package cn.platalk.brtmap.entity.base.impl;

import cn.platalk.brtmap.entity.base.TYIMapDataFeatureRecord;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

public class TYMapDataFeatureRecord implements TYIMapDataFeatureRecord {
	public static final int LAYER_FLOOR = 1;
	public static final int LAYER_ROOM = 2;
	public static final int LAYER_ASSET = 3;
	public static final int LAYER_FACILITY = 4;
	public static final int LAYER_LABEL = 5;
	public static final int LAYER_SHADE = 6;

	static final String FIELD_MAP_DATA_1_OBJECT_ID = "OBJECT_ID";
	static final String FIELD_MAP_DATA_2_GEOMETRY = "GEOMETRY";
	static final String FIELD_MAP_DATA_3_GEO_ID = "GEO_ID";
	static final String FIELD_MAP_DATA_4_POI_ID = "POI_ID";
	static final String FIELD_MAP_DATA_5_FLOOR_ID = "FLOOR_ID";
	static final String FIELD_MAP_DATA_6_BUILDING_ID = "BUILDING_ID";
	static final String FIELD_MAP_DATA_7_CATEGORY_ID = "CATEGORY_ID";
	static final String FIELD_MAP_DATA_8_NAME = "NAME";
	static final String FIELD_MAP_DATA_9_SYMBOL_ID = "SYMBOL_ID";
	static final String FIELD_MAP_DATA_10_FLOOR_NUMBER = "FLOOR_NUMBER";
	static final String FIELD_MAP_DATA_11_FLOOR_NAME = "FLOOR_NAME";
	static final String FIELD_MAP_DATA_12_SHAPE_LENGTH = "SHAPE_LENGTH";
	static final String FIELD_MAP_DATA_13_SHAPE_AREA = "SHAPE_AREA";
	static final String FIELD_MAP_DATA_14_LABEL_X = "LABEL_X";
	static final String FIELD_MAP_DATA_15_LABEL_Y = "LABEL_Y";
	static final String FIELD_MAP_DATA_16_LAYER = "LAYER";
	static final String FIELD_MAP_DATA_17_LEVEL_MAX = "LEVEL_MAX";
	static final String FIELD_MAP_DATA_18_LEVEL_MIN = "LEVEL_MIN";

	public String objectID;
	public byte[] geometry;
	public String geoID;
	public String poiID;
	public String floorID;
	public String buildingID;
	public String categoryID;
	public String name;
	public int symbolID;
	public int floorNumber;
	public String floorName;
	public double shapeLength;
	public double shapeArea;
	public double labelX;
	public double labelY;
	public int layer;
	public int levelMax;
	public int levelMin;

	public boolean extrusion = false;
	public double extrusionHeight = 0;
	public double extrusionBase = 0;

	private Geometry geometryData;
	private String geometryType;

	public TYMapDataFeatureRecord() {

	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	public byte[] getGeometry() {
		return geometry;
	}

	public void setGeometry(byte[] geometry) {
		this.geometry = geometry;
	}

	public String getGeoID() {
		return geoID;
	}

	public void setGeoID(String geoID) {
		this.geoID = geoID;
	}

	public String getPoiID() {
		return poiID;
	}

	public void setPoiID(String poiID) {
		this.poiID = poiID;
	}

	public String getFloorID() {
		return floorID;
	}

	public void setFloorID(String floorID) {
		this.floorID = floorID;
	}

	public String getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(String buildingID) {
		this.buildingID = buildingID;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSymbolID() {
		return symbolID;
	}

	public void setSymbolID(int symbolID) {
		this.symbolID = symbolID;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public double getShapeLength() {
		return shapeLength;
	}

	public void setShapeLength(double shapeLength) {
		this.shapeLength = shapeLength;
	}

	public double getShapeArea() {
		return shapeArea;
	}

	public void setShapeArea(double shapeArea) {
		this.shapeArea = shapeArea;
	}

	public double getLabelX() {
		return labelX;
	}

	public void setLabelX(double labelX) {
		this.labelX = labelX;
	}

	public double getLabelY() {
		return labelY;
	}

	public void setLabelY(double labelY) {
		this.labelY = labelY;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public int getLevelMax() {
		return levelMax;
	}

	public void setLevelMax(int levelMax) {
		this.levelMax = levelMax;
	}

	public int getLevelMin() {
		return levelMin;
	}

	public void setLevelMin(int levelMin) {
		this.levelMin = levelMin;
	}

	public boolean isExtrusion() {
		return extrusion;
	}

	public void setExtrusion(boolean extrusion) {
		this.extrusion = extrusion;
	}

	public double getExtrusionHeight() {
		return extrusionHeight;
	}

	public void setExtrusionHeight(double extrusionHeight) {
		this.extrusionHeight = extrusionHeight;
	}

	public double getExtrusionBase() {
		return extrusionBase;
	}

	public void setExtrusionBase(double extrusionBase) {
		this.extrusionBase = extrusionBase;
	}

	public Geometry getGeometryData() {
		if (geometryData == null) {
			readGeometry();
		}
		return geometryData;
	}

	public String getGeometryType() {
		if (geometryType == null) {
			readGeometry();
		}
		return geometryType;
	}

	private void readGeometry() {
		if (geometry != null) {
			try {
				WKBReader wkb = new WKBReader();
				geometryData = wkb.read(IPEncryption.encryptBytes(geometry));
				geometryType = geometryData.getGeometryType();
			} catch (ParseException e) {
				geometryData = null;
				geometryType = null;
				System.out.println("catch: " + poiID);
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return String
				.format("GeoID: %s, PoiID: %s, Geometry: %d bytes, Name: %s, Floor: %d",
						geoID, poiID, geometry.length, name, floorNumber);
	}

}

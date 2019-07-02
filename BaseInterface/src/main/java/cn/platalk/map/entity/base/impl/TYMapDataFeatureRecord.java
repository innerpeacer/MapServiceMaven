package cn.platalk.map.entity.base.impl;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;

public class TYMapDataFeatureRecord implements TYIMapDataFeatureRecord {
	public static final int LAYER_FLOOR = 1;
	public static final int LAYER_ROOM = 2;
	public static final int LAYER_ASSET = 3;
	public static final int LAYER_FACILITY = 4;
	public static final int LAYER_LABEL = 5;
	public static final int LAYER_SHADE = 6;

	public String objectID;
	public byte[] geometry;
	public String geoID;
	public String poiID;
	public String floorID;
	public String buildingID;
	public String categoryID;
	public String name;
	public String name_en;
	public String name_other;
	public String icon;
	public int symbolID;
	public int floorNumber;
	public String floorName;
	public double shapeLength;
	public double shapeArea;
	public double labelX;
	public double labelY;
	public int layer;
	public double levelMax;
	public double levelMin;
	public boolean visible = true;

	public boolean extrusion = false;
	public double extrusionHeight = 0;
	public double extrusionBase = 0;

	public int priority = 0;

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

	public String getNameEn() {
		return name_en;
	}

	public void setNameEn(String nameEn) {
		this.name_en = nameEn;
	}

	public String getNameOther() {
		return name_other;
	}

	public void setNameOther(String nameOther) {
		this.name_other = nameOther;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public double getLevelMax() {
		return levelMax;
	}

	public void setLevelMax(double levelMax) {
		this.levelMax = levelMax;
	}

	public double getLevelMin() {
		return levelMin;
	}

	public void setLevelMin(double levelMin) {
		this.levelMin = levelMin;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean isVisible) {
		this.visible = isVisible;
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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
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
		return String.format("GeoID: %s, PoiID: %s, Geometry: %d bytes, Name: %s, Floor: %d", geoID, poiID,
				geometry.length, name, floorNumber);
	}

}

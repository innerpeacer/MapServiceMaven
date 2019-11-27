package cn.platalk.map.entity.base.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKBWriter;

import cn.platalk.foundation.TYGeojsonBuilder;
import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;

public class TYMapDataFeatureRecord implements TYIMapDataFeatureRecord {
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_GEO_ID = "GEO_ID";
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_POI_ID = "POI_ID";
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_NAME = "NAME";
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_CATEGORY_ID = "CATEGORY_ID";
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_SYMBOL_ID = "COLOR";
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_LEVEL_MIN = "LEVEL_MIN";
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_LEVEL_MAX = "LEVEL_MAX";
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_LABEL_X = "LABEL_X";
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_LABEL_Y = "LABEL_Y";
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_FLOOR_NUMBER = "floor";

	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_EXTRUSION = "extrusion";
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_EXTRUSION_HEIGHT = "extrusion-height";
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_EXTRUSION_BASE = "extrusion-base";
	static final String KEY_GEOJSON_MAP_DATA_ATTRIBUTE_EXTRUSION_OPACITY = "fill-extrusion-opacity";

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

	@Override
	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	@Override
	public byte[] getGeometry() {
		return geometry;
	}

	public void setGeometry(byte[] geometry) {
		this.geometry = geometry;
	}

	@Override
	public String getGeoID() {
		return geoID;
	}

	public void setGeoID(String geoID) {
		this.geoID = geoID;
	}

	@Override
	public String getPoiID() {
		return poiID;
	}

	public void setPoiID(String poiID) {
		this.poiID = poiID;
	}

	@Override
	public String getFloorID() {
		return floorID;
	}

	public void setFloorID(String floorID) {
		this.floorID = floorID;
	}

	@Override
	public String getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(String buildingID) {
		this.buildingID = buildingID;
	}

	@Override
	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getNameEn() {
		return name_en;
	}

	public void setNameEn(String nameEn) {
		this.name_en = nameEn;
	}

	@Override
	public String getNameOther() {
		return name_other;
	}

	public void setNameOther(String nameOther) {
		this.name_other = nameOther;
	}

	@Override
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public int getSymbolID() {
		return symbolID;
	}

	public void setSymbolID(int symbolID) {
		this.symbolID = symbolID;
	}

	@Override
	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	@Override
	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	@Override
	public double getShapeLength() {
		return shapeLength;
	}

	public void setShapeLength(double shapeLength) {
		this.shapeLength = shapeLength;
	}

	@Override
	public double getShapeArea() {
		return shapeArea;
	}

	public void setShapeArea(double shapeArea) {
		this.shapeArea = shapeArea;
	}

	@Override
	public double getLabelX() {
		return labelX;
	}

	public void setLabelX(double labelX) {
		this.labelX = labelX;
	}

	@Override
	public double getLabelY() {
		return labelY;
	}

	public void setLabelY(double labelY) {
		this.labelY = labelY;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public double getLevelMax() {
		return levelMax;
	}

	public void setLevelMax(double levelMax) {
		this.levelMax = levelMax;
	}

	@Override
	public double getLevelMin() {
		return levelMin;
	}

	public void setLevelMin(double levelMin) {
		this.levelMin = levelMin;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean isVisible) {
		this.visible = isVisible;
	}

	@Override
	public boolean isExtrusion() {
		return extrusion;
	}

	public void setExtrusion(boolean extrusion) {
		this.extrusion = extrusion;
	}

	@Override
	public double getExtrusionHeight() {
		return extrusionHeight;
	}

	public void setExtrusionHeight(double extrusionHeight) {
		this.extrusionHeight = extrusionHeight;
	}

	@Override
	public double getExtrusionBase() {
		return extrusionBase;
	}

	public void setExtrusionBase(double extrusionBase) {
		this.extrusionBase = extrusionBase;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setGeometryData(Geometry g) {
		geometryData = g;
		geometryType = geometryData.getGeometryType();

		if (geometryData != null) {
			WKBWriter wkw = new WKBWriter();
			byte[] bytes = wkw.write(geometryData);
			geometry = IPEncryption.encryptBytes(bytes);
		}
	}

	@Override
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
	public JSONObject toGeojson() {
		Geometry geometry = getGeometryData();
		if (geometry == null) {
			return null;
		}
		return TYGeojsonBuilder.buildGeometry(geometry, dataPropertyMap());

	}

	Map<String, Object> dataPropertyMap() {
		Map<String, Object> propMap = new HashMap<String, Object>();
		propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_GEO_ID, geoID);
		propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_POI_ID, poiID);
		if (name != null) {
			propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_NAME, name);
		} else {
			propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_NAME, "null");
		}
		propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_CATEGORY_ID, categoryID);
		propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_SYMBOL_ID, symbolID);
		propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_LEVEL_MIN, levelMin);
		propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_LEVEL_MAX, levelMax);

		propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_LABEL_X, labelX);
		propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_LABEL_Y, labelY);

		propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_FLOOR_NUMBER, floorNumber);
		propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_EXTRUSION, extrusion);
		propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_EXTRUSION_HEIGHT, extrusionHeight);
		propMap.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_EXTRUSION_BASE, extrusionBase);
		// propertiesObject.put(KEY_GEOJSON_MAP_DATA_ATTRIBUTE_EXTRUSION_OPACITY,
		// extrusionOpacity);
		return propMap;
	}

	@Override
	public String toString() {
		return String.format("GeoID: %s, PoiID: %s, Geometry: %d bytes, Name: %s, Floor: %d", geoID, poiID,
				geometry.length, name, floorNumber);
	}

}

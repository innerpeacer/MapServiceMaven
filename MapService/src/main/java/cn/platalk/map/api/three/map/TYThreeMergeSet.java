package cn.platalk.map.api.three.map;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Geometry;

import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;

public class TYThreeMergeSet {
	public int floor;
	public int layer;
	public int symbolID;
	public double extrusionHeight;

	public TYMapDataFeatureRecord templateRecord;

	private String key = "";

	public List<Geometry> geometryList = new ArrayList<Geometry>();

	public TYThreeMergeSet(int floor, int layer, int symbolID, double height) {
		this.floor = floor;
		this.layer = layer;
		this.symbolID = symbolID;
		this.extrusionHeight = height;
		this.key = buildKey(layer, floor, symbolID, height);
	}

	public void addGeometry(Geometry g) {
		geometryList.add(g);
	}

	public TYMapDataFeatureRecord buildMapRecord() {
		TYMapDataFeatureRecord record = new TYMapDataFeatureRecord();
		record.setObjectID(templateRecord.getObjectID());
		record.setGeometryData(getMergedGeometry());
		record.setGeoID(templateRecord.getGeoID());
		record.setPoiID(templateRecord.getPoiID());
		record.setFloorID(templateRecord.getFloorID());
		record.setBuildingID(templateRecord.getBuildingID());
		record.setCategoryID(templateRecord.getCategoryID());
		// record.setName(templateRecord.getName());;
		// record.setNameEn(templateRecord.getNameEn());;
		// record.setNameOther(templateRecord.getNameOther());;
		record.setIcon(templateRecord.getIcon());
		record.setSymbolID(templateRecord.getSymbolID());
		record.setFloorNumber(templateRecord.getFloorNumber());
		record.setFloorName(templateRecord.getFloorName());
		record.setShapeLength(0);
		record.setShapeArea(0);
		record.setLabelX(0);
		record.setLabelY(0);
		record.setLayer(templateRecord.getLayer());
		record.setLevelMin(0);
		record.setLevelMax(0);
		record.setVisible(true);
		record.setExtrusion(true);
		record.setExtrusionBase(0);
		record.setExtrusionHeight(templateRecord.getExtrusionHeight());
		record.setPriority(0);
		return record;
	}

	public Geometry getMergedGeometry() {
		if (geometryList.size() == 0) {
			return null;
		}

		Geometry g = (Geometry) geometryList.get(0).clone();
		for (int i = 1; i < geometryList.size(); ++i) {
			g = g.union(geometryList.get(i));
		}
		return g;
	}

	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return String.format("%s: %d geometries", key, geometryList.size());
	}

	private static String buildKey(int layer, int floor, int symbolID, double height) {
		return String.format("%d-%d-%d-%.2f", layer, floor, symbolID, height);
	}

	public static String getKey(TYMapDataFeatureRecord record) {
		return buildKey(record.layer, record.floorNumber, record.symbolID, record.extrusionHeight);
	}
}
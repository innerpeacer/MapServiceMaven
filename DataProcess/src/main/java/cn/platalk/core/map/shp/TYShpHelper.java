package cn.platalk.core.map.shp;

import org.gdal.ogr.Feature;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.core.map.shp.routedata.TYShpRouteRecord;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;
import cn.platalk.utils.TYEncryption;

public class TYShpHelper {

	public static TYShpRouteRecord routeRecordFromFeature(Feature feature, int fid) {
		TYShpRouteRecord record = new TYShpRouteRecord();
		record.geometryID = fid;
		record.geometryData = feature.GetGeometryRef().ExportToWkb();
		try {
			WKBReader reader = new WKBReader();
			record.geometry = reader.read(record.geometryData);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if ("LineString".equals(record.geometry.getGeometryType())) {
			record.oneWay = (short) feature.GetFieldAsInteger("ONEWAY");
		}
		return record;
	}

	public static TYMapDataFeatureRecord mapRecordFromFeature(Feature feature, int fid) {
		TYMapDataFeatureRecord record = new TYMapDataFeatureRecord();
		record.objectID = fid + "";

		byte[] geometryBytes = feature.GetGeometryRef().ExportToIsoWkb();
		Geometry g = null;
		try {
			WKBReader reader = new WKBReader();
			g = reader.read(geometryBytes);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		record.geometry = TYEncryption.encryptBytes(feature.GetGeometryRef().ExportToWkb());

		record.geoID = feature.GetFieldAsString("GEO_ID");
		record.poiID = feature.GetFieldAsString("POI_ID");
		record.floorID = feature.GetFieldAsString("FLOOR_ID");
		record.buildingID = feature.GetFieldAsString("BUILDING_I");
		record.categoryID = feature.GetFieldAsString("CATEGORY_I");
		record.name = feature.GetFieldAsString("NAME");

		if (feature.GetFieldIndex("NAME_EN") != -1) {
			record.name_en = feature.GetFieldAsString("NAME_EN");
		}

		if (feature.GetFieldIndex("NAME_OTHER") != -1) {
			record.name_other = feature.GetFieldAsString("NAME_OTHER");
		}

		record.symbolID = feature.GetFieldAsInteger("COLOR");
		record.floorNumber = feature.GetFieldAsInteger("FLOOR_INDE");
		record.floorName = feature.GetFieldAsString("FLOOR_NAME");

		if ("Polygon".equals(g.getGeometryType())) {
			record.shapeLength = feature.GetFieldAsDouble("SHAPE_Leng");
			record.shapeArea = feature.GetFieldAsDouble("SHAPE_Area");
			Point centroid = g.getCentroid();
			record.labelX = centroid.getX();
			record.labelY = centroid.getY();
		} else if ("Point".equals(g.getGeometryType())) {
			record.shapeLength = 0;
			record.shapeArea = 0;

			Point point = (Point) g;
			record.labelX = point.getX();
			record.labelY = point.getY();
		}

		record.levelMax = feature.GetFieldAsInteger("LEVEL_MAX");
		record.levelMin = feature.GetFieldAsInteger("LEVEL_MIN");

		if (feature.GetFieldIndex("EXTRU") != -1) {
			record.extrusion = (feature.GetFieldAsInteger("EXTRU") != 0);
		} else {
			record.extrusion = false;
		}

		if (feature.GetFieldIndex("EXTRU_BASE") != -1) {
			record.extrusionBase = feature.GetFieldAsDouble("EXTRU_BASE");
		} else {
			record.extrusionBase = 0;
		}

		if (feature.GetFieldIndex("EXTRU_HEIG") != -1) {
			record.extrusionHeight = feature.GetFieldAsDouble("EXTRU_HEIG");
		} else {
			record.extrusionHeight = 0;
		}

		return record;
	}
}

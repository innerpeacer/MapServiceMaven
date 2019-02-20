package cn.platalk.core.map.shp;

import org.gdal.ogr.Feature;

import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.core.map.shp.routedata.TYShpRouteRecordV3;

public class TYShpHelperV3 {
	public static TYShpRouteRecordV3 routeRecordFromFeature(Feature feature, int layer) {
		if (layer == 0) {
			return linkRecordFromFeature(feature);
		} else if (layer == 1) {
			return nodeRecordFromFeature(feature);
		}
		return null;
	}

	private static TYShpRouteRecordV3 linkRecordFromFeature(Feature feature) {
		TYShpRouteRecordV3 record = new TYShpRouteRecordV3();
		record.geometryData = feature.GetGeometryRef().ExportToWkb();
		try {
			WKBReader reader = new WKBReader();
			record.geometry = reader.read(record.geometryData);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		record.floor = feature.GetFieldAsInteger("FLOOR_INDE");
		record.level = feature.GetFieldAsInteger("LEVEL");
		record.open = feature.GetFieldAsInteger("OPEN") != 0;
		record.openTime = feature.GetFieldAsString("OPEN_TIME");

		// For Link
		record.linkID = feature.GetFieldAsString("LINK_ID");
		record.isOneWay = feature.GetFieldAsInteger("ONE_WAY") != 0;
		record.linkName = feature.GetFieldAsString("LINK_NAME");
		record.reverse = feature.GetFieldAsInteger("REVERSE") != 0;
		record.roomID = feature.GetFieldAsString("ROOM_ID");
		record.allowSnap = feature.GetFieldAsInteger("ALLOE_SNAP") != 0;
		record.linkType = feature.GetFieldAsString("LINK_TYPE");
		return record;
	}

	private static TYShpRouteRecordV3 nodeRecordFromFeature(Feature feature) {
		TYShpRouteRecordV3 record = new TYShpRouteRecordV3();
		record.geometryData = feature.GetGeometryRef().ExportToWkb();
		try {
			WKBReader reader = new WKBReader();
			record.geometry = reader.read(record.geometryData);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		record.floor = feature.GetFieldAsInteger("FLOOR_INDE");

		record.level = feature.GetFieldAsInteger("LEVEL");
		record.open = feature.GetFieldAsInteger("OPEN") != 0;
		record.openTime = feature.GetFieldAsString("OPEN_TIME");

		// For Node
		record.nodeID = feature.GetFieldAsString("NODE_ID");
		record.nodeName = feature.GetFieldAsString("NAME");
		record.categoryID = feature.GetFieldAsString("CATEGORY_I");
		record.isSwitching = feature.GetFieldAsInteger("IS_SWITCH") != 0;
		record.switchingID = feature.GetFieldAsInteger("SWITCH_ID");
		record.direction = feature.GetFieldAsInteger("DIRECTION");
		record.nodeType = feature.GetFieldAsInteger("NODE_TYPE");
		return record;
	}
}

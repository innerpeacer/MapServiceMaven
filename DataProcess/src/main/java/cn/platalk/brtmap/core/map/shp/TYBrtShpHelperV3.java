package cn.platalk.brtmap.core.map.shp;

import org.gdal.ogr.Feature;

import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteRecordV3;

public class TYBrtShpHelperV3 {
	public static TYBrtShpRouteRecordV3 routeRecordFromFeature(Feature feature, int layer) {
		if (layer == 0) {
			return linkRecordFromFeature(feature);
		} else if (layer == 1) {
			return nodeRecordFromFeature(feature);
		}
		return null;
	}

	private static TYBrtShpRouteRecordV3 linkRecordFromFeature(Feature feature) {
		TYBrtShpRouteRecordV3 record = new TYBrtShpRouteRecordV3();
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

	private static TYBrtShpRouteRecordV3 nodeRecordFromFeature(Feature feature) {
		TYBrtShpRouteRecordV3 record = new TYBrtShpRouteRecordV3();
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

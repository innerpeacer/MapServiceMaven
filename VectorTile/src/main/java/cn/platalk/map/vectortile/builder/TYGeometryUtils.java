package cn.platalk.map.vectortile.builder;

import java.util.HashMap;
import java.util.Map;

import cn.platalk.map.entity.base.map.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.map.TYIIconSymbolRecord;
import cn.platalk.map.entity.base.map.TYIMapDataFeatureRecord;
import com.vividsolutions.jts.geom.Geometry;


class TYGeometryUtils {

	public static Geometry geometryFromMapDataRecord(TYIMapDataFeatureRecord record) {
		Geometry result = TYGeomProjection.mercatorToLngLatGeometry(record.getGeometryData());

		Map<String, Object> userData = new HashMap<>();

		String poiID = record.getPoiID();
		int id = 0;
		if (poiID.length() > 11) {
			id = Integer.parseInt(poiID.substring(11));
		}
		userData.put("id", id);
		userData.put("GEO_ID", record.getGeoID());
		userData.put("POI_ID", record.getPoiID());
		userData.put("NAME", record.getName());
		userData.put("NAME_EN", record.getNameEn());
		userData.put("NAME_OTHER", record.getNameOther());
		userData.put("ICON", record.getIcon());
		userData.put("CATEGORY_ID", record.getCategoryID());
		// userData.put("COLOR", record.symbolID);
		// userData.put("LEVEL_MIN", record.levelMin);
		// userData.put("LEVEL_MAX", record.levelMax);
		userData.put("LABEL_X", record.getLabelX());
		userData.put("LABEL_Y", record.getLabelY());
		userData.put("floor", record.getFloorNumber());
		userData.put("layer", record.getLayer());
		userData.put("v", TYVectorTileSettings.GetMvtVersion());

		userData.put("symbolID", record.getSymbolID());

		// if (record.getLayer() == TYIMapDataFeatureRecord.LAYER_FLOOR
		// || record.getLayer() == TYIMapDataFeatureRecord.LAYER_ROOM
		// || record.getLayer() == TYIMapDataFeatureRecord.LAYER_ASSET) {
		userData.put("extrusion", record.isExtrusion());
		userData.put("extrusion-base", record.getExtrusionBase());
		userData.put("extrusion-height", record.getExtrusionHeight());
		// }

		// userData.put("levelMax", record.getLevelMax());
		// userData.put("levelMin", record.getLevelMin());
		userData.put("visible", record.isVisible());

		result.setUserData(userData);
		return result;
	}

	// public static Geometry geometryFromMapDataRecord(
	// TYMapDataFeatureRecord record) {
	// Geometry result = TYBrtGeomProjection.mercatorToLngLatGeometry(record
	// .getGeometryData());
	//
	// Map<String, Object> userData = new HashMap<String, Object>();
	//
	// userData.put("GEO_ID", record.geoID);
	// userData.put("POI_ID", record.poiID);
	// userData.put("NAME", record.name);
	// userData.put("CATEGORY_ID", record.categoryID);
	// userData.put("COLOR", record.symbolID);
	// userData.put("LEVEL_MIN", record.levelMin);
	// userData.put("LEVEL_MAX", record.levelMax);
	// userData.put("LABEL_X", record.labelX);
	// userData.put("LABEL_Y", record.labelY);
	// userData.put("floor", record.floorNumber);
	//
	// userData.put("extrusion", record.extrusion);
	// userData.put("extrusion-base", record.extrusionBase);
	// userData.put("extrusion-height", record.extrusionHeight);
	//
	// result.setUserData(userData);
	// return result;
	// }

	public static Geometry geometryFromFillRecord(TYIMapDataFeatureRecord record, TYIFillSymbolRecord fillRecord) {
		Geometry result = geometryFromMapDataRecord(record);
		@SuppressWarnings("unchecked")
		Map<String, Object> userData = (Map<String, Object>) result.getUserData();
		if (fillRecord != null) {
			String _fillColor = String.format("#%s", fillRecord.getFillColor().substring(3, 9));
			float _opacity = Integer.parseInt(fillRecord.getFillColor().substring(1, 3), 16) / 255.0f;
			userData.put("fill-color", _fillColor);
			userData.put("fill-opacity", _opacity);

			String _outlineColor = String.format("#%s", fillRecord.getOutlineColor().substring(3, 9));
			float _outlineWidth = (float) fillRecord.getLineWidth();
			userData.put("outline-color", _outlineColor);
			userData.put("outline-width", _outlineWidth);
		}
		// result.setUserData(userData);
		return result;
	}

	public static Geometry geometryFromIconRecord(TYIMapDataFeatureRecord record, TYIIconSymbolRecord iconRecord) {
		Geometry result = geometryFromMapDataRecord(record);
		@SuppressWarnings("unchecked")
		Map<String, Object> userData = (Map<String, Object>) result.getUserData();
		if (iconRecord != null) {
			String iconName = iconRecord.getIcon();
			userData.put("image-normal", String.format("%s_normal", iconName));
			// userData.put("image-highlighted",
			// String.format("%s_highlighted", iconName));
		}
		return result;
	}
}

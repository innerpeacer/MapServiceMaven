package cn.platalk.core.pbf.mapdata.wrapper;

import cn.platalk.core.pbf.mapdata.TYPropertyPbf;
import cn.platalk.core.pbf.mapdata.TYPropertyPbf.TYIndoorFeaturePropertisPbf;
import cn.platalk.map.entity.base.impl.map.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.map.TYIconSymbolRecord;
import cn.platalk.map.entity.base.impl.map.TYMapDataFeatureRecord;

class IPProperityPbfUtils {

	public static TYPropertyPbf.TYIndoorFeaturePropertisPbf propertiesToPbf(TYMapDataFeatureRecord record) {
		TYIndoorFeaturePropertisPbf.Builder builder = TYIndoorFeaturePropertisPbf.newBuilder();
		builder.setGeoID(record.geoID);
		builder.setPoiID(record.poiID);
		if (record.name != null) {
			builder.setName(record.name);
		}

		builder.setCategoryID(record.categoryID);
		builder.setSymbolID(record.symbolID);
		builder.setLevelMin(record.levelMin);
		builder.setLevelMax(record.levelMax);

		builder.setLabelX(record.labelX);
		builder.setLabelY(record.labelY);
		builder.setFloor(record.floorNumber);

		builder.setExtrusion(record.extrusion);
		builder.setExtrusionHeight((float) record.extrusionHeight);
		builder.setExtrusionBase((float) record.extrusionBase);
		// builder.setExtrusionFillOpacity((float) record.extrusionOpacity);

		return builder.build();
	}

	public static TYPropertyPbf.TYIndoorFeaturePropertisPbf fillPropertiesToPbf(TYMapDataFeatureRecord record,
			TYFillSymbolRecord fillRecord) {
		TYIndoorFeaturePropertisPbf.Builder builder = TYIndoorFeaturePropertisPbf.newBuilder();
		builder.setGeoID(record.geoID);
		builder.setPoiID(record.poiID);
		if (record.name != null) {
			builder.setName(record.name);
		}

		builder.setCategoryID(record.categoryID);
		builder.setSymbolID(record.symbolID);
		builder.setLevelMin(record.levelMin);
		builder.setLevelMax(record.levelMax);

		builder.setLabelX(record.labelX);
		builder.setLabelY(record.labelY);
		builder.setFloor(record.floorNumber);

		builder.setExtrusion(record.extrusion);
		builder.setExtrusionHeight((float) record.extrusionHeight);
		builder.setExtrusionBase((float) record.extrusionBase);
		// builder.setExtrusionFillOpacity((float) record.extrusionOpacity);

		if (fillRecord != null) {
			String _fillColor = String.format("#%s", fillRecord.fillColor.substring(3, 9));
			float _opacity = Integer.parseInt(fillRecord.fillColor.substring(1, 3), 16) / 255.0f;
			builder.setFillColor(_fillColor);
			builder.setFillOpacity(_opacity);

			String _outlineColor = String.format("#%s", fillRecord.outlineColor.substring(3, 9));
			float _outlineWidth = (float) fillRecord.lineWidth;
			builder.setOutlineColor(_outlineColor);
			builder.setOutlineWidth(_outlineWidth);
		}

		return builder.build();
	}

	public static TYPropertyPbf.TYIndoorFeaturePropertisPbf iconPropertiesToPbf(TYMapDataFeatureRecord record,
			TYIconSymbolRecord iconRecord) {
		TYIndoorFeaturePropertisPbf.Builder builder = TYIndoorFeaturePropertisPbf.newBuilder();
		builder.setGeoID(record.geoID);
		builder.setPoiID(record.poiID);
		if (record.name != null) {
			builder.setName(record.name);
		}

		builder.setCategoryID(record.categoryID);
		builder.setSymbolID(record.symbolID);
		builder.setLevelMin(record.levelMin);
		builder.setLevelMax(record.levelMax);

		builder.setLabelX(record.labelX);
		builder.setLabelY(record.labelY);
		builder.setFloor(record.floorNumber);

		builder.setExtrusion(record.extrusion);
		builder.setExtrusionHeight((float) record.extrusionHeight);
		builder.setExtrusionBase((float) record.extrusionBase);
		// builder.setExtrusionFillOpacity((float) record.extrusionOpacity);

		if (iconRecord != null) {
			String iconName = iconRecord.icon;
			builder.setImageNormal(String.format("%s_normal", iconName));
			builder.setImageHighlighted(String.format("%s_highlighted", iconName));
		}

		return builder.build();
	}
}

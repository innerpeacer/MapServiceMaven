package cn.platalk.core.pbf.threedata.wrapper;

import cn.platalk.core.pbf.threedata.TYThreePropertyPbf.ThreeFeaturePropertiesPbf;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;

public class IPThreePropertyPbfUtils {

    public static ThreeFeaturePropertiesPbf propertiesToPbf(TYMapDataFeatureRecord record) {
        ThreeFeaturePropertiesPbf.Builder builder = ThreeFeaturePropertiesPbf.newBuilder();
        builder.setFloor(record.getFloorNumber());
        builder.setLayer(record.getLayer());
        if (record.getName() != null) {
            builder.setName(record.getName());
        }
        builder.setCategoryID(record.getCategoryID());
        builder.setSymbolID(record.getSymbolID());
        builder.setPoiID(record.getPoiID());
        builder.setExtrusion(record.isExtrusion());
        builder.setBase(record.getExtrusionBase());
        builder.setHeight(record.getExtrusionHeight());
        builder.setLabelX(record.getLabelX());
        builder.setLabelY(record.getLabelY());
        if (record.getIcon() != null) {
            builder.setIcon(record.getIcon());
        }
        return builder.build();
    }
}

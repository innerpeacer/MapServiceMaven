package cn.platalk.core.pbf.threedata.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Geometry;

import cn.platalk.core.pbf.threedata.TYThreeDataPbf.ThreeDataPbf;
import cn.platalk.core.pbf.threedata.TYThreeDataPbf.ThreeFeaturePbf;
import cn.platalk.core.pbf.threedata.TYThreeDataPbf.ThreeLayerPbf;
import cn.platalk.map.entity.base.impl.TYBuilding;
import cn.platalk.map.entity.base.impl.TYMapDataFeatureRecord;

public class TYThreePbfDataBuilder {
	TYBuilding building;
	private IPThreeGeometryPbfUtils geometryUtil;

	public TYThreePbfDataBuilder(TYBuilding building) {
		this.building = building;
		this.geometryUtil = new IPThreeGeometryPbfUtils(building);
	}

	public ThreeDataPbf buildingThreeDataPbf(List<TYMapDataFeatureRecord> dataList) {
		ThreeDataPbf.Builder builder = ThreeDataPbf.newBuilder();

		List<TYMapDataFeatureRecord> floorList = new ArrayList<TYMapDataFeatureRecord>();
		List<TYMapDataFeatureRecord> roomList = new ArrayList<TYMapDataFeatureRecord>();
		List<TYMapDataFeatureRecord> assetList = new ArrayList<TYMapDataFeatureRecord>();
		// List<TYMapDataFeatureRecord> facilityList = new
		// ArrayList<TYMapDataFeatureRecord>();
		// List<TYMapDataFeatureRecord> labelList = new
		// ArrayList<TYMapDataFeatureRecord>();

		for (TYMapDataFeatureRecord record : dataList) {
			// System.out.println(record.name);
			if (record.layer == TYMapDataFeatureRecord.LAYER_FLOOR) {
				floorList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_ROOM) {
				roomList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_ASSET) {
				assetList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_FACILITY) {
				// facilityList.add(record);
			} else if (record.layer == TYMapDataFeatureRecord.LAYER_LABEL) {
				// labelList.add(record);
			}
		}

		builder.setBuildingID(building.getBuildingID());
		builder.setFloor(buildLayerPbf(floorList));
		builder.setRoom(buildLayerPbf(roomList));
		builder.setAsset(buildLayerPbf(assetList));

		return builder.build();
	}

	ThreeLayerPbf buildLayerPbf(List<TYMapDataFeatureRecord> features) {
		ThreeLayerPbf.Builder layerBuilder = ThreeLayerPbf.newBuilder();
		for (int i = 0; i < features.size(); i++) {
			ThreeFeaturePbf fpbf = featureToPbf(features.get(i));
			if (fpbf != null) {
				layerBuilder.addFeatures(fpbf);
			}
		}
		return layerBuilder.build();
	}

	ThreeFeaturePbf featureToPbf(TYMapDataFeatureRecord fillRecord) {
		ThreeFeaturePbf.Builder featureBuilder = ThreeFeaturePbf.newBuilder();

		Geometry geometry = fillRecord.getGeometryData();
		if (geometry == null) {
			return null;
		}

		featureBuilder.setGeometry(geometryUtil.geometryToPbf(geometry));
		featureBuilder.setProperties(IPThreePropertyPbfUtils.propertiesToPbf(fillRecord));
		return featureBuilder.build();
	}
}

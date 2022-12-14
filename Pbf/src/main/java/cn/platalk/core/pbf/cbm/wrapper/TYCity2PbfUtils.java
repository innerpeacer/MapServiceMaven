package cn.platalk.core.pbf.cbm.wrapper;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.core.pbf.cbm.TYCbmPbf;
import cn.platalk.core.pbf.cbm.TYCbmPbf.BuildingPbf;
import cn.platalk.core.pbf.cbm.TYCbmPbf.CityPbf;
import cn.platalk.core.pbf.cbm.TYCbmPbf.MapInfoPbf;
import cn.platalk.map.entity.base.map.TYIBuilding;
import cn.platalk.map.entity.base.map.TYICity;
import cn.platalk.map.entity.base.map.TYIMapInfo;

public class TYCity2PbfUtils {

	public static TYCbmPbf.CityPbf cityToPbf(TYICity city) {
		CityPbf.Builder builder = CityPbf.newBuilder();
		builder.setId(city.getCityID());
		builder.setName(city.getName());
		builder.setSname(city.getSname());
		builder.setLongitude(city.getLongitude());
		builder.setLatitude(city.getLatitude());
		return builder.build();
	}

	public static TYCbmPbf.BuildingPbf buildingToPbf(TYIBuilding building) {
		BuildingPbf.Builder builder = BuildingPbf.newBuilder();
		builder.setId(building.getBuildingID());
		builder.setCityID(building.getCityID());
		builder.setName(building.getName());
		builder.setLongitude(building.getLongitude());
		builder.setLatitude(building.getLatitude());
		builder.setAddress(building.getAddress());
		builder.setInitAngle(building.getInitAngle());
		builder.setRouteURL(building.getRouteURL());
		builder.setXmin(building.getBuildingExtent().getXmin());
		builder.setYmin(building.getBuildingExtent().getYmin());
		builder.setXmax(building.getBuildingExtent().getXmax());
		builder.setYmax(building.getBuildingExtent().getYmax());
		builder.setInitFloorIndex(building.getInitFloorIndex());
		if (building.getWgs84CalibrationPoint() != null) {
			double[] coord = building.getWgs84CalibrationPoint();
			List<Double> doubleList = new ArrayList<Double>();
			for (int i = 0; i < coord.length; ++i) {
				doubleList.add(coord[i]);
			}
			builder.addAllWgs84CalibrationPoint(doubleList);
		}

		if (building.getWtCalibrationPoint() != null) {
			double[] coord = building.getWtCalibrationPoint();
			List<Double> doubleList = new ArrayList<Double>();
			for (int i = 0; i < coord.length; ++i) {
				doubleList.add(coord[i]);
			}
			builder.addAllWtCalibrationPoint(doubleList);
		}
		builder.setDataVersion(building.getDataVersion());
		builder.setCenterX(building.getCenterX());
		builder.setCenterY(building.getCenterY());
		return builder.build();
	}

	public static TYCbmPbf.MapInfoPbf mapInfoToPbf(TYIMapInfo info) {
		MapInfoPbf.Builder builder = MapInfoPbf.newBuilder();
		builder.setMapID(info.getMapID());
		builder.setCityID(info.getCityID());
		builder.setBuildingID(info.getBuildingID());
		builder.setFloorName(info.getFloorName());
		builder.setFloorNumber(info.getFloorNumber());
		builder.setSizeX(info.getMapSize().getX());
		builder.setSizeY(info.getMapSize().getY());
		builder.setXmin(info.getMapExtent().getXmin());
		builder.setYmin(info.getMapExtent().getYmin());
		builder.setXmax(info.getMapExtent().getXmax());
		builder.setYmax(info.getMapExtent().getYmax());
		return builder.build();
	}
}

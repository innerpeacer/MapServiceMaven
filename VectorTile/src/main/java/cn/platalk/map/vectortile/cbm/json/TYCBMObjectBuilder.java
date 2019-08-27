package cn.platalk.map.vectortile.cbm.json;

import org.json.JSONObject;

import cn.platalk.map.entity.base.TYIBuilding;
import cn.platalk.map.entity.base.TYICity;
import cn.platalk.map.entity.base.TYIMapInfo;

class TYCBMObjectBuilder {

	public static JSONObject generateCityJson(TYICity city) {
		JSONObject cityObject = new JSONObject();
		cityObject.put(TYCBMFields.KEY_WEB_CITY_ID, city.getCityID());
		cityObject.put(TYCBMFields.KEY_WEB_CITY_NAME, city.getName());
		cityObject.put(TYCBMFields.KEY_WEB_CITY_SHORT_NAME, city.getSname());
		cityObject.put(TYCBMFields.KEY_WEB_CITY_LONGITUDE, city.getLongitude());
		cityObject.put(TYCBMFields.KEY_WEB_CITY_LATITUDE, city.getLatitude());
		cityObject.put(TYCBMFields.KEY_WEB_CITY_STATUS, city.getStatus());
		return cityObject;
	}

	public static JSONObject generateBuildingJson(TYIBuilding building) {
		JSONObject buildingObject = new JSONObject();
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_CITY_ID, building.getCityID());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_ID, building.getBuildingID());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_NAME, building.getName());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_LONGITUDE, building.getLongitude());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_LATITUDE, building.getLatitude());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_ADDRESS, building.getAddress());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_INIT_ANGLE, building.getInitAngle());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_INIT_FLOOR, building.getInitFloorIndex());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_ROUTE_URL, building.getRouteURL());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_OFFSET_X, building.getOffset().getX());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_OFFSET_Y, building.getOffset().getY());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_XMIN, building.getBuildingExtent().getXmin());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_XMAX, building.getBuildingExtent().getXmax());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_YMIN, building.getBuildingExtent().getYmin());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_YMAX, building.getBuildingExtent().getYmax());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_WGS84_CALIBRATION_POINT, building.getWgs84CalibrationPoint());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_WT_CALIBRATION_POINT, building.getWtCalibrationPoint());
		buildingObject.put(TYCBMFields.KEY_WEB_BUILDING_STATUS, building.getStatus());
		return buildingObject;
	}

	public static JSONObject generateMapInfoJson(TYIMapInfo mapInfo) {
		JSONObject mapInfoObject = new JSONObject();
		mapInfoObject.put(TYCBMFields.KEY_WEB_MAPINFO_CITYID, mapInfo.getCityID());
		mapInfoObject.put(TYCBMFields.KEY_WEB_MAPINFO_BUILDINGID, mapInfo.getBuildingID());
		mapInfoObject.put(TYCBMFields.KEY_WEB_MAPINFO_MAPID, mapInfo.getMapID());
		mapInfoObject.put(TYCBMFields.KEY_WEB_MAPINFO_FLOOR, mapInfo.getFloorName());
		mapInfoObject.put(TYCBMFields.KEY_WEB_MAPINFO_FLOOR_INDEX, mapInfo.getFloorNumber());
		mapInfoObject.put(TYCBMFields.KEY_WEB_MAPINFO_SIZEX, mapInfo.getMapSize().getX());
		mapInfoObject.put(TYCBMFields.KEY_WEB_MAPINFO_SIZEY, mapInfo.getMapSize().getY());
		mapInfoObject.put(TYCBMFields.KEY_WEB_MAPINFO_XMIN, mapInfo.getMapExtent().getXmin());
		mapInfoObject.put(TYCBMFields.KEY_WEB_MAPINFO_XMAX, mapInfo.getMapExtent().getXmax());
		mapInfoObject.put(TYCBMFields.KEY_WEB_MAPINFO_YMIN, mapInfo.getMapExtent().getYmin());
		mapInfoObject.put(TYCBMFields.KEY_WEB_MAPINFO_YMAX, mapInfo.getMapExtent().getYmax());
		return mapInfoObject;
	}

}

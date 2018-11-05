package cn.platalk.brtmap.vectortile.builder;

import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.brtmap.entity.base.TYIBuilding;
import cn.platalk.brtmap.entity.base.TYICity;
import cn.platalk.brtmap.entity.base.TYIMapInfo;

class TYBrtCBMObjectBuilder {

	public static JSONObject generateCityJson(TYICity city)
			throws JSONException {
		JSONObject cityObject = new JSONObject();
		cityObject.put(TYBrtCBMFields.KEY_WEB_CITY_ID, city.getCityID());
		cityObject.put(TYBrtCBMFields.KEY_WEB_CITY_NAME, city.getName());
		cityObject.put(TYBrtCBMFields.KEY_WEB_CITY_SHORT_NAME, city.getSname());
		cityObject.put(TYBrtCBMFields.KEY_WEB_CITY_LONGITUDE,
				city.getLongitude());
		cityObject
				.put(TYBrtCBMFields.KEY_WEB_CITY_LATITUDE, city.getLatitude());
		cityObject.put(TYBrtCBMFields.KEY_WEB_CITY_STATUS, city.getStatus());
		return cityObject;
	}

	public static JSONObject generateBuildingJson(TYIBuilding building)
			throws JSONException {
		JSONObject buildingObject = new JSONObject();
		buildingObject.put(TYBrtCBMFields.KEY_WEB_BUILDING_CITY_ID,
				building.getCityID());
		buildingObject.put(TYBrtCBMFields.KEY_WEB_BUILDING_ID,
				building.getBuildingID());
		buildingObject.put(TYBrtCBMFields.KEY_WEB_BUILDING_NAME,
				building.getName());
		buildingObject.put(TYBrtCBMFields.KEY_WEB_BUILDING_LONGITUDE,
				building.getLongitude());
		buildingObject.put(TYBrtCBMFields.KEY_WEB_BUILDING_LATITUDE,
				building.getLatitude());
		buildingObject.put(TYBrtCBMFields.KEY_WEB_BUILDING_ADDRESS,
				building.getAddress());
		buildingObject.put(TYBrtCBMFields.KEY_WEB_BUILDING_INIT_ANGLE,
				building.getInitAngle());
		buildingObject.put(TYBrtCBMFields.KEY_WEB_BUILDING_ROUTE_URL,
				building.getRouteURL());
		buildingObject.put(TYBrtCBMFields.KEY_WEB_BUILDING_OFFSET_X, building
				.getOffset().getX());
		buildingObject.put(TYBrtCBMFields.KEY_WEB_BUILDING_OFFSET_Y, building
				.getOffset().getY());
		buildingObject.put(TYBrtCBMFields.KEY_WEB_BUILDING_STATUS,
				building.getStatus());
		return buildingObject;
	}

	public static JSONObject generateMapInfoJson(TYIMapInfo mapInfo)
			throws JSONException {
		JSONObject mapInfoObject = new JSONObject();
		mapInfoObject.put(TYBrtCBMFields.KEY_WEB_MAPINFO_CITYID,
				mapInfo.getCityID());
		mapInfoObject.put(TYBrtCBMFields.KEY_WEB_MAPINFO_BUILDINGID,
				mapInfo.getBuildingID());
		mapInfoObject.put(TYBrtCBMFields.KEY_WEB_MAPINFO_MAPID,
				mapInfo.getMapID());
		mapInfoObject.put(TYBrtCBMFields.KEY_WEB_MAPINFO_FLOOR,
				mapInfo.getFloorName());
		mapInfoObject.put(TYBrtCBMFields.KEY_WEB_MAPINFO_FLOOR_INDEX,
				mapInfo.getFloorNumber());
		mapInfoObject.put(TYBrtCBMFields.KEY_WEB_MAPINFO_SIZEX, mapInfo
				.getMapSize().getX());
		mapInfoObject.put(TYBrtCBMFields.KEY_WEB_MAPINFO_SIZEY, mapInfo
				.getMapSize().getY());
		mapInfoObject.put(TYBrtCBMFields.KEY_WEB_MAPINFO_XMIN, mapInfo
				.getMapExtent().getXmin());
		mapInfoObject.put(TYBrtCBMFields.KEY_WEB_MAPINFO_XMAX, mapInfo
				.getMapExtent().getXmax());
		mapInfoObject.put(TYBrtCBMFields.KEY_WEB_MAPINFO_YMIN, mapInfo
				.getMapExtent().getYmin());
		mapInfoObject.put(TYBrtCBMFields.KEY_WEB_MAPINFO_YMAX, mapInfo
				.getMapExtent().getYmax());
		return mapInfoObject;
	}

}

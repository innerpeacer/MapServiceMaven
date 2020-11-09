package cn.platalk.mysql;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.impl.beacon.TYLocatingBeacon;
import cn.platalk.map.entity.base.impl.map.*;
import cn.platalk.map.entity.base.map.TYIRouteLinkRecord;
import cn.platalk.map.entity.base.map.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.map.TYIRouteNodeRecord;
import cn.platalk.map.entity.base.map.TYIRouteNodeRecordV3;
import cn.platalk.mysql.beacon.TYBeaconDBAdapter;
import cn.platalk.mysql.map.TYBuildingDBAdapter;
import cn.platalk.mysql.map.TYCityDBAdapter;
import cn.platalk.mysql.map.TYMapDataDBAdapter;
import cn.platalk.mysql.map.TYMapInfoDBAdapter;
import cn.platalk.mysql.map.TYRouteDBAdapter;
import cn.platalk.mysql.map.TYRouteDBAdapterV3;
import cn.platalk.mysql.map.TYSymbolDBAdapter;

public class TYMysqlDBHelper {

    public static TYCity getCity(String cityID) {
        TYCityDBAdapter db = new TYCityDBAdapter();
        db.connectDB();
        db.createTableIfNotExist();
        TYCity city = db.getCity(cityID);
        db.disconnectDB();
        return city;
    }

    public static TYBuilding getBuilding(String buildingID) {
        TYBuildingDBAdapter db = new TYBuildingDBAdapter();
        db.connectDB();
        db.createTableIfNotExist();
        TYBuilding building = db.getBuilding(buildingID);
        db.disconnectDB();
        return building;
    }

    public static List<TYMapInfo> getMapInfos(String buildingID) {
        TYMapInfoDBAdapter db = new TYMapInfoDBAdapter();
        db.connectDB();
        db.createTableIfNotExist();
        List<TYMapInfo> mapInfoList = db.getMapInfos(buildingID);
        db.disconnectDB();
        return mapInfoList;
    }

    public static List<TYMapDataFeatureRecord> getMapDataRecords(String buildingID, String mapID) {
        TYMapDataDBAdapter db = new TYMapDataDBAdapter(buildingID);
        db.connectDB();
        List<TYMapDataFeatureRecord> mapDataRecordList = db.getAllMapDataRecords(mapID);
        db.disconnectDB();
        return mapDataRecordList;
    }

    public static List<TYMapDataFeatureRecord> getMapDataRecords(String buildingID) {
        TYMapDataDBAdapter db = new TYMapDataDBAdapter(buildingID);
        db.connectDB();
        List<TYMapDataFeatureRecord> mapDataRecordList = db.getAllMapDataRecords();
        db.disconnectDB();
        return mapDataRecordList;
    }

    public static List<TYFillSymbolRecord> getFillSymbolRecords(String buildingID) {
        TYSymbolDBAdapter db = new TYSymbolDBAdapter();
        db.connectDB();
        List<TYFillSymbolRecord> fillSymbolList = db.getFillSymbolRecords(buildingID);
        db.disconnectDB();
        return fillSymbolList;
    }

    public static List<TYIconSymbolRecord> getIconSymbolRecords(String buildingID) {
        TYSymbolDBAdapter db = new TYSymbolDBAdapter();
        db.connectDB();
        List<TYIconSymbolRecord> iconSymbolList = db.getIconSymbolRecords(buildingID);
        db.disconnectDB();
        return iconSymbolList;
    }

    public static List<TYIconTextSymbolRecord> getIconTextSymbolRecords(String buildingID) {
        TYSymbolDBAdapter db = new TYSymbolDBAdapter();
        db.connectDB();
        List<TYIconTextSymbolRecord> iconTextSymbolList = db.getIconTextSymbolRecords(buildingID);
        db.disconnectDB();
        return iconTextSymbolList;
    }

    public static List<TYIRouteLinkRecordV3> getAllRouteLinkRecordV3(String buildingID) {
        TYRouteDBAdapterV3 routeDB = new TYRouteDBAdapterV3(buildingID);
        routeDB.connectDB();
        List<TYIRouteLinkRecordV3> linkList = new ArrayList<>(routeDB.getAllLinkRecords());
        routeDB.disconnectDB();
        return linkList;
    }

    public static List<TYIRouteNodeRecordV3> getAllRouteNodeRecordV3(String buildingID) {
        TYRouteDBAdapterV3 routeDB = new TYRouteDBAdapterV3(buildingID);
        routeDB.connectDB();
        List<TYIRouteNodeRecordV3> nodeList = new ArrayList<>();
        nodeList.addAll(routeDB.getAllNodeRecords());
        routeDB.disconnectDB();
        return nodeList;
    }

    public static List<TYIRouteLinkRecord> getAllRouteLinkRecord(String buildingID) {
        TYRouteDBAdapter routeDB = new TYRouteDBAdapter(buildingID);
        routeDB.connectDB();
        List<TYIRouteLinkRecord> linkList = new ArrayList<>();
        linkList.addAll(routeDB.getAllLinkRecords());
        routeDB.disconnectDB();
        return linkList;
    }

    public static List<TYIRouteNodeRecord> getAllRouteNodeRecord(String buildingID) {
        TYRouteDBAdapter routeDB = new TYRouteDBAdapter(buildingID);
        routeDB.connectDB();
        List<TYIRouteNodeRecord> nodeList = new ArrayList<>();
        nodeList.addAll(routeDB.getAllNodeRecords());
        routeDB.disconnectDB();
        return nodeList;
    }

    public static List<TYLocatingBeacon> getAllBeacons(String buildingID) {
        TYBeaconDBAdapter db = new TYBeaconDBAdapter(buildingID);
        db.connectDB();
        List<TYLocatingBeacon> beacons = db.getAllBeacons();
        db.disconnectDB();
        return beacons;
    }
}

package cn.platalk.lbs.api;

import java.util.concurrent.ConcurrentHashMap;

public class LocationUpSocketCaches {
    private static final ConcurrentHashMap<String, LocationUpLinkSocket> UpLinkMap = new ConcurrentHashMap<>();

    public static void AddLink(String key, LocationUpLinkSocket socket) {
        UpLinkMap.put(key, socket);
    }

    public static void RemoveLink(String key) {
        UpLinkMap.remove(key);
    }

    public static boolean ExistLink(String key) {
        return UpLinkMap.containsKey(key);
    }

    public static LocationUpLinkSocket GetLink(String key) {
        return UpLinkMap.get(key);
    }
}

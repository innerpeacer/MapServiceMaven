package cn.platalk.lbs.api;

import java.util.concurrent.ConcurrentHashMap;

public class LocationDownSocketCaches {
    private static final ConcurrentHashMap<String, LocationDownLinkSocket> DownLinkMap = new ConcurrentHashMap<>();

    public static void AddLink(String key, LocationDownLinkSocket socket) {
        DownLinkMap.put(key, socket);
    }

    public static void RemoveLink(String key) {
        DownLinkMap.remove(key);
    }

    public static boolean ExistLink(String key) {
        return DownLinkMap.containsKey(key);
    }

    public static LocationDownLinkSocket GetLink(String key) {
        return DownLinkMap.get(key);
    }
}

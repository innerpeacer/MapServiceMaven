package cn.platalk.map.vectortile.builder;

import java.util.ArrayList;
import java.util.List;

public class TYTileCoord {
    public int zoom;
    public int x;
    public int y;

    public TYTileCoord(int zoom, int x, int y) {
        this.zoom = zoom;
        this.x = x;
        this.y = y;
    }

    public TYBoundingBox getBoundingBox() {
        TYBoundingBox bb = new TYBoundingBox();
        bb.north = tile2lat(y, zoom);
        bb.south = tile2lat(y + 1, zoom);
        bb.west = tile2lng(x, zoom);
        bb.east = tile2lng(x + 1, zoom);
        return bb;
    }

    public List<TYTileCoord> getChildren() {
        int zoom = this.zoom + 1;
        int x = this.x * 2;
        int y = this.y * 2;

        List<TYTileCoord> result = new ArrayList<>();
        result.add(new TYTileCoord(zoom, x, y));
        result.add(new TYTileCoord(zoom, x + 1, y));
        result.add(new TYTileCoord(zoom, x, y + 1));
        result.add(new TYTileCoord(zoom, x + 1, y + 1));
        return result;
    }

    public static TYTileCoord getTileCoord(final double lng, final double lat, final int zoom) {
        int xtile = (int) Math.floor(lng2Tile(lng, zoom));
        int ytile = (int) Math.floor(lat2Tile(lat, zoom));

        if (xtile < 0)
            xtile = 0;
        if (xtile >= (1 << zoom))
            xtile = ((1 << zoom) - 1);
        if (ytile < 0)
            ytile = 0;
        if (ytile >= (1 << zoom))
            ytile = ((1 << zoom) - 1);
        return new TYTileCoord(zoom, xtile, ytile);
    }

    static double tile2lng(int x, int z) {
        return x / Math.pow(2.0, z) * 360.0 - 180;
    }

    static double lng2Tile(double lng, int z) {
        return (lng + 180) / 360 * Math.pow(2.0, z);
    }

    static double tile2lat(int y, int z) {
        double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
        return Math.toDegrees(Math.atan(Math.sinh(n)));
    }

    static double lat2Tile(double lat, int z) {
        return (1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2
                * (Math.pow(2.0, z));
    }

    // static double tile2lat(int y, int z) {
    // double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
    // return Math.toDegrees(Math.atan(Math.sinh(n)));
    // // double LatBounds = 85.05112999999746;
    // // return LatBounds - y / Math.pow(2.0, z) * LatBounds * 2;
    // }
    //
    // static double lat2Tile(double lat, int z) {
    // double LatBounds = 85.05112999999746;
    // return (lat + LatBounds) / (LatBounds * 2) * Math.pow(2.0, z);
    // }

    static TYBoundingBox tile2boundingBox(final int zoom, final int x, final int y) {
        TYBoundingBox bb = new TYBoundingBox();
        bb.north = tile2lat(y, zoom);
        bb.south = tile2lat(y + 1, zoom);
        bb.west = tile2lng(x, zoom);
        bb.east = tile2lng(x + 1, zoom);
        return bb;
    }

    @Override
    public String toString() {
        // return String.format("Zoom: %d, X: %d, Y: %d", zoom, x, y);
        return String.format("%d: (%d, %d)", zoom, x, y);
    }
}

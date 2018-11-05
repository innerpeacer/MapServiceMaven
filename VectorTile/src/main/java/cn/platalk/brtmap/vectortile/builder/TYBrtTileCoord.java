package cn.platalk.brtmap.vectortile.builder;

import java.util.ArrayList;
import java.util.List;

class TYBrtTileCoord {
	public int zoom;
	public int x;
	public int y;

	public static void main(String[] args) {
		// int zoom = 17;
		// double lat = 22.411329058366093d;
		// double lon = 113.96251217713262d;
		//
		// TYBrtTileCoord tc = getTileCoord(lon, lat, zoom);
		// System.out.println("http://tile.openstreetmap.org/" + tc.zoom + "//"
		// + tc.x + "//" + tc.y + ".png");

		// int zoom = 0;
		TYBrtTileCoord tc = getTileCoord(1, 0, 0);
		System.out.println(tc);

		// double lat = tile2lat(0, 0);
		// System.out.println(tile2lat(3, 2));
		// System.out.println(tile2lon(2, 5));

		System.out.println(lat2Tile(4, 2));
	}

	public TYBrtTileCoord(int zoom, int x, int y) {
		this.zoom = zoom;
		this.x = x;
		this.y = y;
	}

	public TYBrtBoundingBox getBoundingBox() {
		TYBrtBoundingBox bb = new TYBrtBoundingBox();
		bb.north = tile2lat(y, zoom);
		bb.south = tile2lat(y + 1, zoom);
		bb.west = tile2lon(x, zoom);
		bb.east = tile2lon(x + 1, zoom);
		return bb;
	}

	public List<TYBrtTileCoord> getChildren() {
		int zoom = this.zoom + 1;
		int x = this.x * 2;
		int y = this.y * 2;

		List<TYBrtTileCoord> result = new ArrayList<TYBrtTileCoord>();
		result.add(new TYBrtTileCoord(zoom, x, y));
		result.add(new TYBrtTileCoord(zoom, x + 1, y));
		result.add(new TYBrtTileCoord(zoom, x, y + 1));
		result.add(new TYBrtTileCoord(zoom, x + 1, y + 1));
		return result;
	}

	public static TYBrtTileCoord getTileCoord(final double lon, final double lat, final int zoom) {
		int xtile = (int) Math.floor(lon2Tile(lon, zoom));
		int ytile = (int) Math.floor(lat2Tile(lat, zoom));

		if (xtile < 0)
			xtile = 0;
		if (xtile >= (1 << zoom))
			xtile = ((1 << zoom) - 1);
		if (ytile < 0)
			ytile = 0;
		if (ytile >= (1 << zoom))
			ytile = ((1 << zoom) - 1);
		return new TYBrtTileCoord(zoom, xtile, ytile);
	}

	static double tile2lon(int x, int z) {
		return x / Math.pow(2.0, z) * 360.0 - 180;
	}

	static double lon2Tile(double lon, int z) {
		return (lon + 180) / 360 * Math.pow(2.0, z);
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

	static TYBrtBoundingBox tile2boundingBox(final int zoom, final int x, final int y) {
		TYBrtBoundingBox bb = new TYBrtBoundingBox();
		bb.north = tile2lat(y, zoom);
		bb.south = tile2lat(y + 1, zoom);
		bb.west = tile2lon(x, zoom);
		bb.east = tile2lon(x + 1, zoom);
		return bb;
	}

	@Override
	public String toString() {
		// return String.format("Zoom: %d, X: %d, Y: %d", zoom, x, y);
		return String.format("%d: (%d, %d)", zoom, x, y);
	}
}

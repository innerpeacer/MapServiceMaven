package cn.platalk.map.vectortile.builder;

import cn.platalk.map.entity.base.TYILngLat;
import cn.platalk.map.entity.base.TYIMapExtent;
import cn.platalk.map.entity.base.TYIMapInfo;

class TYBoundingBox {
	public double north;
	public double south;
	public double east;
	public double west;

	public TYBoundingBox() {

	}

	public TYBoundingBox(double east, double west, double south, double north) {
		this.east = Math.max(east, west);
		this.west = Math.min(east, west);
		this.south = Math.min(south, north);
		this.north = Math.max(south, north);
	}

	public void expandByPercentage(double scale) {
		double width = this.east - this.west;
		double height = this.north - this.south;

		this.east += width * scale;
		this.west -= width * scale;
		this.north += height * scale;
		this.south -= height * scale;
	}

	// public static TYBrtBoundingBox boundingBoxForMapInfo(TYMapInfo info,
	// Double expanding) {
	// MapExtent extent = info.getMapExtent();
	// double[] xy1 = TYBrtCoordProjection.mercatorToLngLat(extent.getXmin(),
	// extent.getYmin());
	// double[] xy2 = TYBrtCoordProjection.mercatorToLngLat(extent.getXmax(),
	// extent.getYmax());
	// TYBrtBoundingBox bb = new TYBrtBoundingBox(xy1[0], xy2[0], xy1[1],
	// xy2[1]);
	// if (expanding != null && expanding > 0) {
	// bb.expandByPercentage(expanding);
	// }
	// return bb;
	// }

	public static TYBoundingBox boundingBoxForMapInfo(TYIMapInfo info, Double expanding) {
		TYIMapExtent extent = info.getMapExtent();
		TYILngLat sw = extent.getSw();
		TYILngLat ne = extent.getNe();
		TYBoundingBox bb = new TYBoundingBox(sw.getLng(), ne.getLng(), sw.getLat(), ne.getLat());
		if (expanding != null && expanding > 0) {
			bb.expandByPercentage(expanding);
		}
		return bb;
	}

	public TYTileCoord getMaxCoveringTile() {
		// int zoom = 0;
		TYTileCoord result = null;
		for (int z = 0; z <= 22; ++z) {
			TYTileCoord upLeftTile = TYTileCoord.getTileCoord(west, north, z);
			TYTileCoord bottomRightTile = TYTileCoord.getTileCoord(east, south, z);

			if (upLeftTile.x == bottomRightTile.x && upLeftTile.y == bottomRightTile.y) {
				// zoom = z;
				result = upLeftTile;
			} else {
				break;
			}
			// System.out.println("=================================");
			// System.out.println(upLeftTile.toString());
			// System.out.println(bottomRightTile.toString());
		}
		return result;
	}

	public int[] getTileRange(int zoom) {
		TYTileCoord upLeftTile = TYTileCoord.getTileCoord(west, north, zoom);
		TYTileCoord bottomRightTile = TYTileCoord.getTileCoord(east, south, zoom);
		// System.out.println("=================================");
		// System.out.println(upLeftTile.toString());
		// System.out.println(bottomRightTile.toString());

		return new int[] { upLeftTile.x, bottomRightTile.x, upLeftTile.y, bottomRightTile.y };
	}

	@Override
	public String toString() {
		return String.format("[%f, %f, %f, %f]", west, south, east, north);
	}

	public static void main(String[] args) {
		// TYBrtBoundingBox box = new TYBrtBoundingBox();
		// double[] wn = TYBrtCoordProjection.mercatorToLngLat(1.2686049917E7,
		// 2561144.87);
		// double[] es = TYBrtCoordProjection.mercatorToLngLat(1.2686480528E7,
		// 2560871.026);
		// box.east = es[0];
		// box.west = wn[0];
		// box.north = wn[1];
		// box.south = es[1];
		//
		// // TYTileCoord coord = box.getMaxCoveringTile();
		// // System.out.println(coord.toString());
		//
		// int[] tileRange = box.getTileRange(17);
		// System.out.println(tileRange);
	}
}

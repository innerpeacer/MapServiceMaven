package cn.platalk.map.vectortile.builder;

import cn.platalk.map.vectortile.pbf.VectorTile;

final class MvtUtil {

	public static boolean shouldClosePath(VectorTile.Tile.GeomType geomType) {
		final boolean closeReq;

		switch (geomType) {
		case POLYGON:
			closeReq = true;
			break;
		default:
			closeReq = false;
			break;
		}

		return closeReq;
	}
}

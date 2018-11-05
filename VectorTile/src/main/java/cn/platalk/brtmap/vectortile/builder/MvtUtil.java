package cn.platalk.brtmap.vectortile.builder;

import cn.platalk.brtmap.vectortile.pbf.VectorTile;

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

package cn.platalk.brtmap.vectortile.builder;

import cn.platalk.brtmap.vectortile.pbf.VectorTile;

import com.vividsolutions.jts.geom.Envelope;

interface TYIBrtMvtEncoder {

	public int getVersion();

	public VectorTile.Tile encodeBrtTile(TYBrtGeometrySet geomSet,
			MvtLayerParams mvtParams, Envelope tileEnvelope,
			Envelope clipEnvelope, TYBrtTileCoord tile);
}

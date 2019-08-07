package cn.platalk.map.vectortile.builder;

import com.vividsolutions.jts.geom.Envelope;

import cn.platalk.map.vectortile.pbf.VectorTile;

interface TYIMvtEncoder {

	public int getVersion();

	public boolean isForNative();

	public void setForNative(boolean forNative);

	public VectorTile.Tile encodeBrtTile(TYGeometrySet geomSet, MvtLayerParams mvtParams, Envelope tileEnvelope,
			Envelope clipEnvelope, TYTileCoord tile);
}

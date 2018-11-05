package cn.platalk.brtmap.vectortile.builder;

import java.util.List;

import cn.platalk.brtmap.vectortile.pbf.VectorTile;

import com.google.protobuf.ProtocolStringList;

interface MvtITagConverter {
	Object toUserData(Long id, List<Integer> tags, ProtocolStringList keysList,
			List<VectorTile.Tile.Value> valuesList);
}

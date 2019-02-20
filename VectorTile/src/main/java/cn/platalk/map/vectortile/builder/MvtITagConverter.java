package cn.platalk.map.vectortile.builder;

import java.util.List;

import com.google.protobuf.ProtocolStringList;

import cn.platalk.map.vectortile.pbf.VectorTile;

interface MvtITagConverter {
	Object toUserData(Long id, List<Integer> tags, ProtocolStringList keysList,
			List<VectorTile.Tile.Value> valuesList);
}

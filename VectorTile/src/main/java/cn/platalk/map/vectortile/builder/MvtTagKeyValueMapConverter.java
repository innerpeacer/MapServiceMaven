package cn.platalk.map.vectortile.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.protobuf.ProtocolStringList;

import cn.platalk.map.vectortile.pbf.VectorTile;

final class MvtTagKeyValueMapConverter implements MvtITagConverter {

	private final boolean nullIfEmpty;
	private final boolean addId;

	private final String idKey;

	public MvtTagKeyValueMapConverter() {
		this(false);
	}

	public MvtTagKeyValueMapConverter(boolean nullIfEmpty) {
		this.nullIfEmpty = nullIfEmpty;
		this.addId = false;
		this.idKey = null;
	}

	public MvtTagKeyValueMapConverter(boolean nullIfEmpty, String idKey) {
		Objects.requireNonNull(idKey);

		this.nullIfEmpty = nullIfEmpty;
		this.addId = true;
		this.idKey = idKey;
	}

	@Override
	public Object toUserData(Long id, List<Integer> tags,
			ProtocolStringList keysList, List<VectorTile.Tile.Value> valuesList) {
		if (nullIfEmpty && tags.size() < 1 && (!addId || id == null)) {
			return null;
		}

		final Map<String, Object> userData = new HashMap<>(
				((tags.size() + 1) / 2));

		// Add feature properties
		int keyIndex;
		int valIndex;
		boolean valid;

		for (int i = 0; i < tags.size() - 1; i += 2) {
			keyIndex = tags.get(i);
			valIndex = tags.get(i + 1);

			valid = keyIndex >= 0 && keyIndex < keysList.size()
					&& valIndex >= 0 && valIndex < valuesList.size();

			if (valid) {
				userData.put(keysList.get(keyIndex),
						MvtValue.toObject(valuesList.get(valIndex)));
			}
		}

		// Add ID, value may be null
		if (addId) {
			userData.put(idKey, id);
		}

		return userData;
	}
}

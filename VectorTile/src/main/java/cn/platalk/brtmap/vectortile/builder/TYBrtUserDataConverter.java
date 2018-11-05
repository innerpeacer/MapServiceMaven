package cn.platalk.brtmap.vectortile.builder;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import cn.platalk.brtmap.vectortile.pbf.VectorTile;

final class TYBrtUserDataConverter implements MvtIUserDataConverter {
	private final boolean setId;
	private final String idKey;

	public TYBrtUserDataConverter() {
		this.setId = false;
		this.idKey = null;
	}

	public TYBrtUserDataConverter(String idKey) {
		Objects.requireNonNull(idKey);
		this.setId = true;
		this.idKey = idKey;
	}

	@Override
	public void addTags(Object userData, MvtLayerProps layerProps,
			VectorTile.Tile.Feature.Builder featureBuilder) {
		if (userData == null)
			return;

		@SuppressWarnings("unchecked")
		final Map<String, Object> userDataMap = (Map<String, Object>) userData;

		for (Entry<String, Object> e : userDataMap.entrySet()) {
			final String key = e.getKey();
			final Object value = e.getValue();

			if (key != null && value != null) {
				final int valueIndex = layerProps.addValue(value);

				if (valueIndex >= 0) {
					featureBuilder.addTags(layerProps.addKey(key));
					featureBuilder.addTags(valueIndex);
				}
			}
		}

		if (setId) {
			final Object idValue = userDataMap.get(idKey);
			if (idValue != null) {
				if (idValue instanceof Long || idValue instanceof Integer
						|| idValue instanceof Float
						|| idValue instanceof Double || idValue instanceof Byte
						|| idValue instanceof Short) {
					featureBuilder.setId((long) idValue);
				} else if (idValue instanceof String) {
					try {
						featureBuilder.setId(Long.valueOf((String) idValue));
					} catch (NumberFormatException ignored) {
					}
				}
			}
		}
	}
}
package cn.platalk.map.vectortile.builder;

import java.util.Map;
import java.util.Map.Entry;

import cn.platalk.map.vectortile.pbf.VectorTile;

import java.util.Objects;

final class MvtUserDataKeyValueMapConverter implements MvtIUserDataConverter {

	private final boolean setId;
	private final String idKey;

	public MvtUserDataKeyValueMapConverter() {
		this.setId = false;
		this.idKey = null;
	}

	public MvtUserDataKeyValueMapConverter(String idKey) {
		Objects.requireNonNull(idKey);
		this.setId = true;
		this.idKey = idKey;
	}

	@Override
	public void addTags(Object userData, MvtLayerProps layerProps,
			VectorTile.Tile.Feature.Builder featureBuilder) {
		if (userData != null) {
			try {
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

				// Set feature id value
				if (setId) {
					final Object idValue = userDataMap.get(idKey);
					if (idValue != null) {
						if (idValue instanceof Long
								|| idValue instanceof Integer
								|| idValue instanceof Float
								|| idValue instanceof Double
								|| idValue instanceof Byte
								|| idValue instanceof Short) {
							featureBuilder.setId((long) idValue);
						} else if (idValue instanceof String) {
							try {
								featureBuilder.setId(Long
										.parseLong((String) idValue));
							} catch (NumberFormatException ignored) {
							}
						}
					}
				}
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
		}
	}
}

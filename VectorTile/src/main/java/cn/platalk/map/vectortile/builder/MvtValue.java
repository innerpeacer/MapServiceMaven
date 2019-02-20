package cn.platalk.map.vectortile.builder;

import cn.platalk.map.vectortile.pbf.VectorTile;

final class MvtValue {

	public static VectorTile.Tile.Value toValue(Object value) {
		final VectorTile.Tile.Value.Builder tileValue = VectorTile.Tile.Value
				.newBuilder();

		if (value instanceof Boolean) {
			tileValue.setBoolValue((Boolean) value);

		} else if (value instanceof Integer) {
			tileValue.setSintValue((Integer) value);

		} else if (value instanceof Long) {
			tileValue.setSintValue((Long) value);

		} else if (value instanceof Float) {
			tileValue.setFloatValue((Float) value);

		} else if (value instanceof Double) {
			tileValue.setDoubleValue((Double) value);

		} else if (value instanceof String) {
			tileValue.setStringValue((String) value);
		}

		return tileValue.build();
	}

	public static Object toObject(VectorTile.Tile.Value value) {
		Object result = null;

		if (value.hasDoubleValue()) {
			result = value.getDoubleValue();

		} else if (value.hasFloatValue()) {
			result = value.getFloatValue();

		} else if (value.hasIntValue()) {
			result = value.getIntValue();

		} else if (value.hasBoolValue()) {
			result = value.getBoolValue();

		} else if (value.hasStringValue()) {
			result = value.getStringValue();

		} else if (value.hasSintValue()) {
			result = value.getSintValue();

		} else if (value.hasUintValue()) {
			result = value.getUintValue();
		}

		return result;
	}

	public static boolean isValidPropValue(Object value) {
		boolean isValid = false;

		if (value instanceof Boolean || value instanceof Integer
				|| value instanceof Long || value instanceof Float
				|| value instanceof Double || value instanceof String) {
			isValid = true;
		}

		return isValid;
	}
}

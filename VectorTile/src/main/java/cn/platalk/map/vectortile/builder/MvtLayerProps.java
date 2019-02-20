package cn.platalk.map.vectortile.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

final class MvtLayerProps {
	private ConcurrentHashMap<String, Integer> keys;
	private List<String> keyList;
	private ConcurrentHashMap<Object, Integer> vals;
	private List<Object> valList;

	public MvtLayerProps() {
		keys = new ConcurrentHashMap<>();
		keyList = new ArrayList<String>();
		vals = new ConcurrentHashMap<>();
		valList = new ArrayList<Object>();
	}

	public Integer keyIndex(String k) {
		return keys.get(k);
	}

	public Integer valueIndex(Object v) {
		return vals.get(v);
	}

	public int addKey(String key) {
		Objects.requireNonNull(key);
		int nextIndex = keys.size();
		final Integer mapIndex = keys.putIfAbsent(key, nextIndex);
		if (mapIndex == null) {
			keyList.add(key);
		}
		return mapIndex == null ? nextIndex : mapIndex;
	}

	public int addValue(Object value) {
		Objects.requireNonNull(value);
		if (!MvtValue.isValidPropValue(value)) {
			return -1;
		}

		int nextIndex = vals.size();
		final Integer mapIndex = vals.putIfAbsent(value, nextIndex);
		if (mapIndex == null) {
			valList.add(value);
		}
		return mapIndex == null ? nextIndex : mapIndex;
	}

	public Iterable<String> getKeys() {
		// return keys.keySet();
		return keyList;
	}

	public Iterable<Object> getVals() {
		// return vals.keySet();
		return valList;
	}
}

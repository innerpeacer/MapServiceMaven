package cn.platalk.utils.third;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class TYMapUtils {

	public static final String DEFAULT_KEY_AND_VALUE_SEPARATOR = ":";
	public static final String DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR = ",";

	public static <K, V> boolean isEmpty(Map<K, V> sourceMap) {
		return (sourceMap == null || sourceMap.size() == 0);
	}

	@SuppressWarnings("UnusedReturnValue")
	public static boolean putMapNotEmptyKey(Map<String, String> map,
											String key, String value) {
		if (map == null || TYStringUtils.isEmpty(key)) {
			return false;
		}

		map.put(key, value);
		return true;
	}

	public static boolean putMapNotEmptyKeyAndValue(Map<String, String> map,
			String key, String value) {
		if (map == null || TYStringUtils.isEmpty(key)
				|| TYStringUtils.isEmpty(value)) {
			return false;
		}

		map.put(key, value);
		return true;
	}

	public static boolean putMapNotEmptyKeyAndValue(Map<String, String> map,
			String key, String value, String defaultValue) {
		if (map == null || TYStringUtils.isEmpty(key)) {
			return false;
		}

		map.put(key, TYStringUtils.isEmpty(value) ? defaultValue : value);
		return true;
	}

	public static <K, V> boolean putMapNotNullKey(Map<K, V> map, K key, V value) {
		if (map == null || key == null) {
			return false;
		}

		map.put(key, value);
		return true;
	}

	public static <K, V> boolean putMapNotNullKeyAndValue(Map<K, V> map, K key,
			V value) {
		if (map == null || key == null || value == null) {
			return false;
		}

		map.put(key, value);
		return true;
	}

	public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
		if (isEmpty(map)) {
			return null;
		}

		for (Entry<K, V> entry : map.entrySet()) {
			if (TYObjectUtils.isEquals(entry.getValue(), value)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static Map<String, String> parseKeyAndValueToMap(String source,
			String keyAndValueSeparator, String keyAndValuePairSeparator,
			boolean ignoreSpace) {
		if (TYStringUtils.isEmpty(source)) {
			return null;
		}

		if (TYStringUtils.isEmpty(keyAndValueSeparator)) {
			keyAndValueSeparator = DEFAULT_KEY_AND_VALUE_SEPARATOR;
		}

		if (TYStringUtils.isEmpty(keyAndValuePairSeparator)) {
			keyAndValuePairSeparator = DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR;
		}

		Map<String, String> keyAndValueMap = new HashMap<>();
		String[] keyAndValueArray = source.split(keyAndValuePairSeparator);
		if (keyAndValueArray == null) {
			return null;
		}

		int separator;
		for (String valueEntity : keyAndValueArray) {
			if (!TYStringUtils.isEmpty(valueEntity)) {
				separator = valueEntity.indexOf(keyAndValueSeparator);
				if (separator != -1) {
					if (ignoreSpace) {
						TYMapUtils.putMapNotEmptyKey(keyAndValueMap,
								valueEntity.substring(0, separator).trim(),
								valueEntity.substring(separator + 1).trim());
					} else {
						TYMapUtils.putMapNotEmptyKey(keyAndValueMap,
								valueEntity.substring(0, separator),
								valueEntity.substring(separator + 1));
					}
				}
			}
		}
		return keyAndValueMap;
	}

	public static Map<String, String> parseKeyAndValueToMap(String source,
			boolean ignoreSpace) {
		return parseKeyAndValueToMap(source, DEFAULT_KEY_AND_VALUE_SEPARATOR,
				DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR, ignoreSpace);
	}

	public static Map<String, String> parseKeyAndValueToMap(String source) {
		return parseKeyAndValueToMap(source, DEFAULT_KEY_AND_VALUE_SEPARATOR,
				DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR, true);
	}

	public static String toJson(Map<String, String> map) {
		if (map == null || map.size() == 0) {
			return null;
		}

		StringBuilder paras = new StringBuilder();
		paras.append("{");
		Iterator<Map.Entry<String, String>> ite = map.entrySet().iterator();
		while (ite.hasNext()) {
			Map.Entry<String, String> entry = ite
					.next();
			paras.append("\"").append(entry.getKey()).append("\":\"")
					.append(entry.getValue()).append("\"");
			if (ite.hasNext()) {
				paras.append(",");
			}
		}
		paras.append("}");
		return paras.toString();
	}
}

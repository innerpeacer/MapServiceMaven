package cn.platalk.utils.third;

public class TYObjectUtils {

	public static boolean isEquals(Object actual, Object expected) {
		return actual == expected
				|| (actual == null ? expected == null : actual.equals(expected));
	}

	public static Long[] transformLongArray(long[] source) {
		Long[] dest = new Long[source.length];
		for (int i = 0; i < source.length; i++) {
			dest[i] = source[i];
		}
		return dest;
	}

	public static long[] transformLongArray(Long[] source) {
		long[] dest = new long[source.length];
		for (int i = 0; i < source.length; i++) {
			dest[i] = source[i];
		}
		return dest;
	}

	public static Integer[] transformIntArray(int[] source) {
		Integer[] dest = new Integer[source.length];
		for (int i = 0; i < source.length; i++) {
			dest[i] = source[i];
		}
		return dest;
	}

	public static int[] transformIntArray(Integer[] source) {
		int[] dest = new int[source.length];
		for (int i = 0; i < source.length; i++) {
			dest[i] = source[i];
		}
		return dest;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <V> int compare(V v1, V v2) {
		return v1 == null ? (v2 == null ? 0 : -1) : (v2 == null ? 1
				: ((Comparable) v1).compareTo(v2));
	}
}

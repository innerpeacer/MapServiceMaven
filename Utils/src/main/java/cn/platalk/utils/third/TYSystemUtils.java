package cn.platalk.utils.third;

public class TYSystemUtils {

	public static final int DEFAULT_THREAD_POOL_SIZE = getDefaultThreadPoolSize();

	public static int getDefaultThreadPoolSize() {
		return getDefaultThreadPoolSize(8);
	}

	public static int getDefaultThreadPoolSize(int max) {
		int availableProcessors = 2 * Runtime.getRuntime()
				.availableProcessors() + 1;
		return availableProcessors > max ? max : availableProcessors;
	}
}

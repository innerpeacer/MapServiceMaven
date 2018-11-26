package cn.platalk.brtmap.route.core_v3;

public class IPRouteDebugger {
	static final boolean IS_DEBUGGING = false;

	static void debugLog(String s) {
		if (IS_DEBUGGING) {
			System.out.println(s);
		}
	}
}

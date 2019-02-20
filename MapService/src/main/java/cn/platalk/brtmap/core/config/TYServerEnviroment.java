package cn.platalk.brtmap.core.config;

import cn.platalk.brtmap.db.map.TYDatabaseManager;
import cn.platalk.brtmap.res.TYLinuxResourceManager;
import cn.platalk.brtmap.res.TYMacResourceManager;
import cn.platalk.brtmap.res.TYIResourceManager;
import cn.platalk.brtmap.utils.TYOSUtils;
import cn.platalk.brtmap.utils.TYOSUtils.OSType;

public class TYServerEnviroment {
	public static TYIResourceManager resourceManager = null;
	public static OSType osType = null;

	public static void initialize() {
		OSType type = TYOSUtils.GetOSType();
		switch (type) {
		case MAC:
			resourceManager = new TYMacResourceManager();
			osType = OSType.MAC;
			break;

		case LINUX:
			resourceManager = new TYLinuxResourceManager();
			osType = OSType.LINUX;
			break;

		// case WINDOWS:
		// resourceManager = new TYBrtWindowsResourceManager();
		// osType = OSType.WINDOWS;
		// break;

		default:
			break;
		}

		TYDatabaseManager.initialize(resourceManager.getDBHost(), resourceManager.getDBUserName(),
				resourceManager.getDBPassword());
	}

	public static boolean isLinux() {
		return osType == OSType.LINUX;
	}

	public static boolean isMac() {
		return osType == OSType.MAC;
	}

	public static boolean isWindows() {
		return osType == OSType.WINDOWS;
	}
}

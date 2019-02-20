package cn.platalk.brtmap.core.config;

import cn.platalk.map.res.TYIResourceManager;
import cn.platalk.map.res.TYLinuxResourceManager;
import cn.platalk.map.res.TYMacResourceManager;
import cn.platalk.mysql.map.TYDatabaseManager;
import cn.platalk.utils.TYOSUtils;
import cn.platalk.utils.TYOSUtils.OSType;

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

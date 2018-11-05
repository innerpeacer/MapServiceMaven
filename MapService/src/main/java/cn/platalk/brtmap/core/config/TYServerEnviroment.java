package cn.platalk.brtmap.core.config;

import cn.platalk.brtmap.db.map.TYDatabaseManager;
import cn.platalk.brtmap.res.TYBrtLinuxResourceManager;
import cn.platalk.brtmap.res.TYBrtMacResourceManager;
import cn.platalk.brtmap.res.TYBrtWindowsResourceManager;
import cn.platalk.brtmap.res.TYIBrtResourceManager;
import cn.platalk.brtmap.utils.TYOSUtils;
import cn.platalk.brtmap.utils.TYOSUtils.OSType;

public class TYServerEnviroment {
	public static TYIBrtResourceManager resourceManager = null;
	public static OSType osType = null;

	public static void initialize() {
		OSType type = TYOSUtils.GetOSType();
		switch (type) {
		case MAC:
			resourceManager = new TYBrtMacResourceManager();
			osType = OSType.MAC;
			break;

		case LINUX:
			resourceManager = new TYBrtLinuxResourceManager();
			osType = OSType.LINUX;
			break;

		case WINDOWS:
			resourceManager = new TYBrtWindowsResourceManager();
			osType = OSType.WINDOWS;
			break;

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

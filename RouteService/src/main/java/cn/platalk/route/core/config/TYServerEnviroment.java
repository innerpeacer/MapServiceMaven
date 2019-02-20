package cn.platalk.route.core.config;

import cn.platalk.map.res.TYIResourceManager;
import cn.platalk.map.res.TYLinuxResourceManager;
import cn.platalk.map.res.TYMacResourceManager;
import cn.platalk.mysql.map.TYDatabaseManager;
import cn.platalk.utils.TYOSUtils;
import cn.platalk.utils.TYOSUtils.OSType;

public class TYServerEnviroment {
	public static TYIResourceManager resourceManager = null;

	public static void initialize() {
		OSType type = TYOSUtils.GetOSType();
		switch (type) {
		case MAC:
			resourceManager = new TYMacResourceManager();
			break;

		case LINUX:
			resourceManager = new TYLinuxResourceManager();
			break;

		// case WINDOWS:
		// resourceManager = new TYBrtWindowsResourceManager();
		// break;

		default:
			break;
		}

		TYDatabaseManager.initialize(resourceManager.getDBHost(), resourceManager.getDBUserName(),
				resourceManager.getDBPassword());
	}
}

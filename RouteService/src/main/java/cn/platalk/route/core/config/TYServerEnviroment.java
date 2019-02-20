package cn.platalk.route.core.config;

import cn.platalk.brtmap.db.map.TYDatabaseManager;
import cn.platalk.brtmap.res.TYLinuxResourceManager;
import cn.platalk.brtmap.res.TYMacResourceManager;
import cn.platalk.brtmap.res.TYIResourceManager;
import cn.platalk.brtmap.utils.TYOSUtils;
import cn.platalk.brtmap.utils.TYOSUtils.OSType;

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

package cn.platalk.route.core.config;

import cn.platalk.brtmap.db.map.TYDatabaseManager;
import cn.platalk.brtmap.res.TYBrtLinuxResourceManager;
import cn.platalk.brtmap.res.TYBrtMacResourceManager;
import cn.platalk.brtmap.res.TYBrtWindowsResourceManager;
import cn.platalk.brtmap.res.TYIBrtResourceManager;
import cn.platalk.brtmap.utils.TYOSUtils;
import cn.platalk.brtmap.utils.TYOSUtils.OSType;

public class TYServerEnviroment {
	public static TYIBrtResourceManager resourceManager = null;

	public static void initialize() {
		OSType type = TYOSUtils.GetOSType();
		switch (type) {
		case MAC:
			resourceManager = new TYBrtMacResourceManager();
			break;

		case LINUX:
			resourceManager = new TYBrtLinuxResourceManager();
			break;

		case WINDOWS:
			resourceManager = new TYBrtWindowsResourceManager();
			break;

		default:
			break;
		}

		TYDatabaseManager.initialize(resourceManager.getDBHost(), resourceManager.getDBUserName(),
				resourceManager.getDBPassword());
	}
}

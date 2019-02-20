package cn.platalk.mysql.map;

public class TYDatabaseManager {
	private static final String MAP_DATABASE_URL = "jdbc:mysql://%s/MAP_DB?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&useServerPrepStmts=true";
	private static final String ROUTE_DATABASE_URL = "jdbc:mysql://%s/ROUTE_DB?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&useServerPrepStmts=true";
	private static final String BEACON_DATABASE_URL = "jdbc:mysql://%s/BEACON_DB?characterEncoding=utf8";
	private static final String POI_DATABASE_URL = "jdbc:mysql://%s/MAP_DB?characterEncoding=utf8&rewriteBatchedStatements=true&useServerPrepStmts=true";

	private static String HOST_NAME = null;
	private static String USER_NAME = null;
	private static String PASSWORD = null;

	public static void initialize(String host, String user, String password) {
		HOST_NAME = host;
		USER_NAME = user;
		PASSWORD = password;
	}

	public static String GetUserName() {
		return USER_NAME;
	}

	public static String GetPassword() {
		return PASSWORD;
	}

	public static String GetMapDBUrl() {
		return String.format(MAP_DATABASE_URL, HOST_NAME);
	}

	public static String GetBeaconDBUrl() {
		return String.format(BEACON_DATABASE_URL, HOST_NAME);
	}

	public static String GetRouteDBUrl() {
		return String.format(ROUTE_DATABASE_URL, HOST_NAME);
	}

	public static String GetPoiDBUrl() {
		return String.format(POI_DATABASE_URL, HOST_NAME);
	}
}

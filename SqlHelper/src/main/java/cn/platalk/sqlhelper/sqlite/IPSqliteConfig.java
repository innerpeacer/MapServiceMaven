package cn.platalk.sqlhelper.sqlite;

class IPSqliteConfig {

	static String getDriverName(IPJdbcVersion version) {
		switch (version) {
		case JAVA:
			return "org.sqlite.JDBC";

		case ANDROID:
			return "org.sqldroid.SQLDroidDriver";

		default:
			return "";
		}
	}

	static String getDBPrefix(IPJdbcVersion version) {
		switch (version) {
		case JAVA:
			return "jdbc:sqlite:";

		case ANDROID:
			return "jdbc:sqldroid:";

		default:
			return "";
		}
	}
}

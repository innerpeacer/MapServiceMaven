package cn.platalk.sqlhelper.sqlite;

class IPSqliteConfig {

	static String getDriverName(JDBCVersion version) {
		switch (version) {
		case JAVA:
			return "org.sqlite.JDBC";

		case ANDROID:
			return "org.sqldroid.SQLDroidDriver";

		default:
			return "";
		}
	}

	static String getDBPrefix(JDBCVersion version) {
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

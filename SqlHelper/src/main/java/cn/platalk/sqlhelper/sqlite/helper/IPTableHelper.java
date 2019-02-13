package cn.platalk.sqlhelper.sqlite.helper;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.sqlhelper.sqlite.SqliteField;
import cn.platalk.sqlhelper.sqlite.SqliteTable;

public class IPTableHelper {
	public static SqliteTable buildTableObject(String name, Object[][] defs, String primaryKey) {
		List<SqliteField> fields = new ArrayList<SqliteField>();
		for (int i = 0; i < defs.length; ++i) {
			Object[] def = defs[i];
			if (def.length != 3) {
				return null;
			}

			String fieldName = (String) def[0];
			String className = (String) def[1];
			boolean allowNull = (Boolean) def[2];
			SqliteField field = new SqliteField(fieldName, className, allowNull);
			fields.add(field);
		}
		return new SqliteTable(name, fields, primaryKey);
	}
}

package cn.platalk.sqlhelper.sql;

public class IPSqlOrder {
    final IPSqlField field;
    final boolean desc;

    public IPSqlOrder(IPSqlField field, boolean isDesc) {
        this.field = field;
        this.desc = isDesc;
    }

    public String toSql() {
        return String.format(" %s %s ", field.fieldName, desc ? "desc" : "asc");
    }
}

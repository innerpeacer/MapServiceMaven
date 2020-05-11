package cn.platalk.sqlhelper.sql;

public class IPSqlLimit {
    final Integer start;
    final Integer end;

    public IPSqlLimit(Integer end) {
        this.start = null;
        this.end = end;
    }

    public IPSqlLimit(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public String toSql() {
        if (start == null && end == null) return "";
        if (start == null) {
            return String.format(" limit %d", end);
        }
        return String.format(" limit %d, %d", start, end);
    }
}

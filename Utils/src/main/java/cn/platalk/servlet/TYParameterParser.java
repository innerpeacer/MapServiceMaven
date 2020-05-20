package cn.platalk.servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.platalk.map.entity.base.impl.TYLocalPoint;

public class TYParameterParser {

    public static Double getDouble(HttpServletRequest request, String key, Double defaultValue) {
        String value = request.getParameter(key);
        if (value != null) {
            return Double.parseDouble(value);
        }
        return defaultValue;
    }

    public static Double getDouble(HttpServletRequest request, String key) {
        return getDouble(request, key, null);
    }

    public static Integer getInteger(HttpServletRequest request, String key, Integer defaultValue) {
        String value = request.getParameter(key);
        if (value != null) {
            return Integer.parseInt(value);
        }
        return defaultValue;
    }

    public static Integer getInteger(HttpServletRequest request, String key) {
        return getInteger(request, key, null);
    }

    public static Long getLong(HttpServletRequest request, String key, Long defaultValue) {
        String value = request.getParameter(key);
        if (value != null) {
            return Long.parseLong(value);
        }
        return defaultValue;
    }

    public static Long getLong(HttpServletRequest request, String key) {
        return getLong(request, key, null);
    }

    public static String getString(HttpServletRequest request, String key) {
        return getString(request, key, null);
    }

    public static String getString(HttpServletRequest request, String key, String defaultValue) {
        String value = request.getParameter(key);
        return value != null ? value : defaultValue;
    }

    public static List<String> getStringList(HttpServletRequest request, String key) {
        List<String> strList = new ArrayList<>();
        String str = request.getParameter(key);
        if (str != null) {
            String[] strArray = str.split(",");
            strList.addAll(Arrays.asList(strArray));
        }
        return strList;
    }

    public static Boolean getBoolean(HttpServletRequest request, String key) {
        return getBoolean(request, key, null);
    }

    public static Boolean getBoolean(HttpServletRequest request, String key, Boolean defaultValue) {
        String value = request.getParameter(key);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }

    public static TYLocalPoint getLocalPoint(HttpServletRequest request, String key) {
        String str = request.getParameter(key);
        String[] strArray = str.split(",");

        if (str == null || strArray.length != 3) {
            return null;
        }

        double x = Double.parseDouble(strArray[0]);
        double y = Double.parseDouble(strArray[1]);
        int floor = Integer.parseInt(strArray[2]);
        return new TYLocalPoint(x, y, floor);
    }

    public static List<TYLocalPoint> getLocalPointList(HttpServletRequest request, String key) {
        List<TYLocalPoint> points = new ArrayList<>();

        String str = request.getParameter(key);
        if (str != null) {
            String[] strArray = str.split(",");
            for (int i = 0; i < strArray.length; i += 3) {
                double x = Double.parseDouble(strArray[i]);
                double y = Double.parseDouble(strArray[i + 1]);
                int floor = Integer.parseInt(strArray[i + 2]);
                TYLocalPoint p = new TYLocalPoint(x, y, floor);
                points.add(p);
            }
        }
        return points;
    }

}

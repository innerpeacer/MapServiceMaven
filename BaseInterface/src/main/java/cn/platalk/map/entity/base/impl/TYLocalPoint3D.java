package cn.platalk.map.entity.base.impl;

import cn.platalk.map.entity.base.TYILocalPoint3D;
import org.json.JSONException;
import org.json.JSONObject;

public class TYLocalPoint3D extends TYLocalPoint implements TYILocalPoint3D {
    private static final String KEY_HEIGHT = "height";

    private double height = 0;

    public TYLocalPoint3D(double x, double y) {
        super(x, y);
    }

    public TYLocalPoint3D(double x, double y, int floor) {
        super(x, y, floor);
    }

    public boolean equal(TYLocalPoint3D p) {
        return super.equal(p) && this.height == p.height;
    }

    public JSONObject buildJson() {
        JSONObject jsonObject = super.buildJson();
        try {
            jsonObject.put(KEY_HEIGHT, height);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return String.format("(%f, %f, %f) in floor %d", x, y, height, floor);
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public double distanceWithPoint3D(TYLocalPoint3D lp) {
        if (lp == null) {
            return -1;
        }

        // if (floor != lp.getFloor()) {
        // return Double.POSITIVE_INFINITY;
        // }

        return Math.sqrt((x - lp.getX()) * (x - lp.getX()) + (y - lp.getY()) * (y - lp.getY() + (height - lp.getHeight()) * (height - lp.getHeight())));
    }

    public double horizontalDistanceWithPoint3D(TYLocalPoint3D lp) {
        return super.distanceWithPoint(lp);
    }

    public static double distanceWithPoint3Ds(TYLocalPoint3D lp1, TYLocalPoint3D lp2) {
        if (lp1 == null) {
            return -1;
        }
        return lp1.distanceWithPoint3D(lp2);
    }
}

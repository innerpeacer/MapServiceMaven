package cn.platalk.core.beacon.shp;

import cn.platalk.map.entity.base.impl.beacon.TYLocatingBeacon;
import cn.platalk.map.entity.base.impl.map.TYLocalPoint;
import org.gdal.ogr.Feature;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

public class TYBeaconShpHelper {
	public static TYLocatingBeacon locationBeaconFromRecord(Feature feature) {
		TYLocatingBeacon beacon = new TYLocatingBeacon();

//		byte[] geom = feature.GetGeometryRef().ExportToIsoWkb();
		byte[] geom = feature.GetGeometryRef().ExportToWkb();
		Point p = null;
		try {
			WKBReader reader = new WKBReader();
			p = (Point) reader.read(geom);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		beacon.setUUID(feature.GetFieldAsString("uuid"));
		beacon.setMajor(feature.GetFieldAsInteger("major"));
		beacon.setMinor(feature.GetFieldAsInteger("minor"));

		int floor = feature.GetFieldAsInteger("floor");
		assert p != null;
		TYLocalPoint lp = new TYLocalPoint(p.getX(), p.getY(), floor);
		beacon.setLocation(lp);
		return beacon;
	}
}

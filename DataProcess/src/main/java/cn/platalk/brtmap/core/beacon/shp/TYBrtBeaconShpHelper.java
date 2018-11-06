package cn.platalk.brtmap.core.beacon.shp;

import org.gdal.ogr.Feature;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.brtmap.entity.base.TYLocalPoint;
import cn.platalk.brtmap.entity.base.impl.TYLocatingBeacon;

public class TYBrtBeaconShpHelper {
	public static TYLocatingBeacon locationBeaconFromRecord(Feature feature) {
		TYLocatingBeacon beacon = new TYLocatingBeacon();

		byte[] geom = feature.GetGeometryRef().ExportToIsoWkb();
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
		TYLocalPoint lp = new TYLocalPoint(p.getX(), p.getY(), floor);
		beacon.setLocation(lp);
		return beacon;
	}
}

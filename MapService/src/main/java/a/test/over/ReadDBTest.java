package a.test.over;

import java.util.List;

import cn.platalk.brtmap.db.map.TYBuildingDBAdapter;
import cn.platalk.brtmap.db.map.TYMapDataDBAdapter;
import cn.platalk.brtmap.entity.base.impl.TYBuilding;
import cn.platalk.brtmap.entity.base.impl.TYMapDataFeatureRecord;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

public class ReadDBTest {
	public static void main(String[] args) {
		String buildingID = "07550023";
		TYBuildingDBAdapter db = new TYBuildingDBAdapter();
		db.connectDB();
		TYBuilding building = db.getBuilding(buildingID);
		db.disconnectDB();

//		TYMapDataDBAdapter mapdb = new TYMapDataDBAdapter(buildingID);
//		mapdb.connectDB();
//		List<TYMapDataFeatureRecord> mapDataRecordList = mapdb
//				.getAllMapDataRecords("07550023F01");
//		mapdb.disconnectDB();
//
//		for (TYMapDataFeatureRecord record : mapDataRecordList) {
//			System.out.println(record);
//			WKBReader reader = new WKBReader();
//			try {
//				Geometry g = reader.read(record.geometry);
//				System.out.println(g.getGeometryType());
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			// record.getGeometryData();
//			break;
//		}
	}
}

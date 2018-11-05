//package a.test.over;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import cn.platalk.brtmap.entity.base.TYIMapDataFeatureRecord;
//import cn.platalk.mapserver.core.db.map.TYMapDataDBAdapter;
//
//public class RunNodeFontmin {
//
//	public static void main(String[] args) {
//		String buildingID = "07550023";
//		TYBrtFontParams params = new TYBrtFontParams("simhei", "ttf",
//				buildingID);
//
//		TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(buildingID);
//		mapDB.connectDB();
//		// List<TYMapDataFeatureRecord> records = mapDB.getAllMapDataRecords();
//		List<TYIMapDataFeatureRecord> records = new ArrayList<TYIMapDataFeatureRecord>();
//		records.addAll(mapDB.getAllMapDataRecords());
//		mapDB.disconnectDB();
//
//		String labelString = ExtractLabelString.GetLabelString(records);
//		String script = params.getFontScript(labelString);
//		try {
//			Process ps = Runtime.getRuntime().exec(script);
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(
//					ps.getInputStream()));
//			String line = null;
//			while ((line = br.readLine()) != null) {
//				System.out.println(line);
//			}
//
//			BufferedReader brError = new BufferedReader(new InputStreamReader(
//					ps.getErrorStream(), "gb2312"));
//			String errline = null;
//			while ((errline = brError.readLine()) != null) {
//				System.out.println(errline);
//			}
//
//			int c = ps.waitFor();
//			System.out.println("Result: " + c);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		File fontFile = new File(params.getIntermediateFontPath());
//		fontFile.renameTo(new File(params.getFontminPath()));
//	}
// }

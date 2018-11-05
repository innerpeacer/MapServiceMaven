//package a.test.over;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//
//import cn.platalk.brtmap.core.fontbuilder.TYBrtFontParams;
//import cn.platalk.brtmap.core.fontbuilder.TYBrtGlyphsParams;
//
//public class RunNodeGlyphs {
//	public static void main(String[] args) {
//		// node
//		// /Users/innerpeacer/BrtMapProject/BrtMapTools/FontTool/BrtFontTool/script/node-glyphs.js
//		// Test.ttf
//		// /Users/innerpeacer/BrtMapProject/BrtMapTools/FontTool/Resources
//		// /Users/innerpeacer/BrtMapProject/BrtMapTools/FontTool/Resources
//		String buildingID = "07550023";
//		TYBrtFontParams params = new TYBrtFontParams("T2", "ttf", buildingID);
//
//		String script = TYBrtGlyphsParams.GetGlyphsScript(params
//				.getFontminFileName());
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
//	}
// }

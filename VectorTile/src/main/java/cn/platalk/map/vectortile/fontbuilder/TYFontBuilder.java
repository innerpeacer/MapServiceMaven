package cn.platalk.map.vectortile.fontbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;

public class TYFontBuilder {

	String buildingID;

	public TYFontBuilder(String buildingID) {
		this.buildingID = buildingID;
	}

	public void buildFont(String fontName, String fontType, List<TYIMapDataFeatureRecord> records) {
		buildFontmin(fontName, fontType, records);
		buildGlyphs(fontName, fontType);
	}

	void buildFontmin(String fontName, String fontType, List<TYIMapDataFeatureRecord> records) {
		// System.out.println("Build Fontmin");
		notifyProcess("Build Fontmin\n");
		TYFontParams params = new TYFontParams(fontName, fontType, buildingID);

		// TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(buildingID);
		// mapDB.connectDB();
		// List<TYMapDataFeatureRecord> records = mapDB.getAllMapDataRecords();
		// List<TYIMapDataFeatureRecord> records = new
		// ArrayList<TYIMapDataFeatureRecord>();
		// records.addAll(mapDB.getAllMapDataRecords());
		// mapDB.disconnectDB();

		notifyProcess(records.size() + " records\n");

		String labelString = TYExtractLabelString.GetLabelString(records);

		// System.out.println("Label String: " + labelString.length());
		// System.out.println(labelString);
		notifyProcess("Label String: " + labelString + "\n");

		// for (int i = 0; i < labelString.length(); ++i) {
		// System.out.println(labelString.charAt(i));
		// }

		String script = params.getFontScript(labelString);
		notifyProcess("script: " + script + "\n");
		// System.out.println(script);
		try {
			Process ps = Runtime.getRuntime().exec(script);

			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}

			BufferedReader brError = new BufferedReader(new InputStreamReader(ps.getErrorStream(), "gb2312"));
			String errline = null;
			while ((errline = brError.readLine()) != null) {
				System.out.println(errline);
			}

			int c = ps.waitFor();
			if (c == 0) {
				notifyProcess("Success Fontmin: " + c + "\n");
			} else {
				notifyFailed("Failed Fontmin: " + c + "\n");
			}
			// System.out.println("Result: " + c);

		} catch (Exception e) {
			e.printStackTrace();
		}

		File fontFile = new File(params.getIntermediateFontPath());

		// System.out.println();
		// System.out.println(fontFile.toString());
		// System.out.println(params.getFontminPath());

		fontFile.renameTo(new File(params.getFontminPath()));
		notifyProcess("Finish Fontmin: " + new File(params.getFontminPath()).toString() + "\n");
	}

	void buildGlyphs(String fontName, String fontType) {
		// System.out.println("Build Glyphs");
		notifyProcess("Build Glyphs\n");

		TYFontParams params = new TYFontParams(fontName, fontType, buildingID);
		// System.out.println(params.getGlyphFolder());

		File glyphDir = new File(params.getGlyphFolder());
		if (!glyphDir.exists()) {
			glyphDir.mkdirs();
		}

		String script = TYGlyphsParams.GetGlyphsScript(params.getFontminFileName());
		notifyProcess("script: " + script + "\n");
		try {
			Process ps = Runtime.getRuntime().exec(script);

			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}

			BufferedReader brError = new BufferedReader(new InputStreamReader(ps.getErrorStream(), "gb2312"));
			String errline = null;
			while ((errline = brError.readLine()) != null) {
				System.out.println(errline);
			}

			int c = ps.waitFor();
			if (c == 0) {
				notifyProcess("Success Glyphs: " + c + "\n");
			} else {
				notifyFailed("Failed Glyphs: " + c + "\n");
			}
			// System.out.println("Result: " + c);
			notifyProcess("Finish Fontmin\n");

			if (c == 0) {
				notifyFinish();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<TYBrtFontBuilderInterface> listeners = new ArrayList<TYFontBuilder.TYBrtFontBuilderInterface>();

	public void addListener(TYBrtFontBuilderInterface listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeListener(TYBrtFontBuilderInterface listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	private void notifyFinish() {
		for (TYBrtFontBuilderInterface listener : listeners) {
			listener.didFinishBuildingFont();
		}
	}

	private void notifyFailed(String info) {
		for (TYBrtFontBuilderInterface listener : listeners) {
			listener.didFailedBuildingFont(info);
		}
	}

	private void notifyProcess(String info) {
		for (TYBrtFontBuilderInterface listener : listeners) {
			listener.buildingProgress(info);
		}
	}

	public interface TYBrtFontBuilderInterface {
		void didFinishBuildingFont();

		void didFailedBuildingFont(String info);

		void buildingProgress(String info);
	}

}

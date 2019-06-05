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
		notifyProcess("========== Build Fontmin ========== \n");
		TYFontParams params = new TYFontParams(fontName, fontType, buildingID);

		// TYMapDataDBAdapter mapDB = new TYMapDataDBAdapter(buildingID);
		// mapDB.connectDB();
		// List<TYMapDataFeatureRecord> records = mapDB.getAllMapDataRecords();
		// List<TYIMapDataFeatureRecord> records = new
		// ArrayList<TYIMapDataFeatureRecord>();
		// records.addAll(mapDB.getAllMapDataRecords());
		// mapDB.disconnectDB();

		notifyProcess(records.size() + " map records\n");

		String labelString = TYExtractLabelString.GetLabelString(records);

		// System.out.println("Label String: " + labelString.length());
		// System.out.println(labelString);
		notifyProcess("Label String: " + labelString + "\n");

		// for (int i = 0; i < labelString.length(); ++i) {
		// System.out.println(labelString.charAt(i));
		// }

		String script = params.getFontScript(labelString);
		notifyProcess("script: " + script + "\n");
		notifyProcess("\tScript Path:" + TYFontSettings.fontminScriptPath + "\n");
		notifyProcess("\tUse Font:" + params.getOriginalFontPath() + "\n");
		notifyProcess("\tTempt Font:" + params.getIntermediateFontPath() + "\n");

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
				notifyProcess("Fontmin Script Success: " + c + "\n");
			} else {
				notifyFailed("Fontmin Script Failed: " + c + "\n");
			}
			// System.out.println("Result: " + c);

		} catch (Exception e) {
			e.printStackTrace();
		}

		File fontFile = new File(params.getIntermediateFontPath());
		notifyProcess("Rename Fontmin: " + fontFile.toString() + " -> " + params.getFontminPath() + "\n");

		fontFile.renameTo(new File(params.getFontminPath()));
		notifyProcess("Output Font:" + params.getFontminPath() + "\n");
		notifyProcess("Finish Fontmin: " + new File(params.getFontminPath()).toString() + "\n");
		notifyProcess("========== Finish Fontmin ========== \n\n");
	}

	void buildGlyphs(String fontName, String fontType) {
		// System.out.println("Build Glyphs");
		notifyProcess("========== Build Glyphs ==========\n");

		TYFontParams params = new TYFontParams(fontName, fontType, buildingID);
		// System.out.println(params.getGlyphFolder());

		File glyphDir = new File(params.getGlyphFolder());
		if (!glyphDir.exists()) {
			glyphDir.mkdirs();
		}

		// String script =
		// TYGlyphsParams.GetGlyphsScript(params.getFontminFileName());
		{
			notifyProcess("*************** Process Fontmin [1-256] ***************\n");
			String script = TYGlyphsParams.GetGlyphsScript(params.getFontminFileName(), TYFontSettings.tempFontFileDir,
					TYFontSettings.outputGlyphsDir, 1, 256);
			notifyProcess("script: " + script + "\n");
			notifyProcess("\tScript Path: " + TYFontSettings.glyphsScriptPath + "\n");
			notifyProcess("\tInput Directory: " + TYFontSettings.tempFontFileDir + "\n");
			notifyProcess("\tOutput Directory: " + TYFontSettings.outputGlyphsDir + "\n");
			notifyProcess("\tFontName: " + params.getFontminFileName() + "\n");
			notifyProcess("\tRange: [1, 256]\n");
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
				notifyProcess("Finish Glyphs\n");

				if (c == 0) {
					// notifyFinish();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			notifyProcess("*************** Finish Fontmin [1-256] ***************\n\n");
		}

		{
			notifyProcess("############### Process Origin [0-1] #################\n");
			String script = TYGlyphsParams.GetGlyphsScript(params.getFontFileName(), TYFontSettings.inputFontFileDir,
					TYFontSettings.tempFontFileDir, 0, 1);
			notifyProcess("script: " + script + "\n");
			notifyProcess("\tScript Path: " + TYFontSettings.glyphsScriptPath + "\n");
			notifyProcess("\tInput Directory: " + TYFontSettings.inputFontFileDir + "\n");
			notifyProcess("\tOutput Directory: " + TYFontSettings.tempFontFileDir + "\n");
			notifyProcess("\tFontName: " + params.getFontFileName() + "\n");
			notifyProcess("\tRange: [0, 1]\n");
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
				notifyProcess("Finish Glyphs\n");

				if (c == 0) {
					notifyFinish();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			notifyProcess("############### Finish Fontmin [0-1] #################\n\n");
		}

		{
			File originAsciiGlyphDir = new File(TYFontSettings.tempFontFileDir, params.getFontName());
			File originAsciiGlyph = new File(originAsciiGlyphDir.toString(), "0-255.pbf");

			File outputAsciiGlyphDir = new File(TYFontSettings.outputGlyphsDir, params.getFontminName());
			File outputAsciiGlyph = new File(outputAsciiGlyphDir.toString(), "0-255.pbf");

			notifyProcess("originAsciiGlyph: " + originAsciiGlyph.toString() + "\n");
			notifyProcess("outputAsciiGlyph: " + outputAsciiGlyph.toString() + "\n");
			notifyProcess(
					"Rename Ascii File: " + originAsciiGlyph.toString() + " -> " + outputAsciiGlyph.toString() + "\n");

			originAsciiGlyph.renameTo(outputAsciiGlyph);
		}
		notifyProcess("========== Finish Glyphs ==========\n");
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

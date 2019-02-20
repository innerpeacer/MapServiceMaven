package cn.platalk.map.vectortile.builder;

import java.io.File;

public class TYVectorTileSettings {
	private static String CURRENT_VERSION = "1.0.1";
	private static int mvtVersion = 2;

	private static int DefaultMinZoom = 14;
	private static int DefaultMaxZoom = 20;

	private static String TILE_ROOT = null;

	public static void SetTileRoot(String root) {
		TILE_ROOT = root;
	}

	public static String GetTileRoot() {
		return TILE_ROOT;
	}

	public static int GetMvtVersion() {
		return mvtVersion;
	}

	public static void UseMvtVersion(int v) {
		mvtVersion = v;
	}

	public static String Version() {
		return CURRENT_VERSION;
	}

	public static String GetCBMDir() {
		return new File(TILE_ROOT, "cbm").toString();
	}

	public static String GetTileDir() {
		return new File(TILE_ROOT, "vectortile").toString();
	}

	public static void SetDefaultMaxZoom(int maxZoom) {
		DefaultMaxZoom = maxZoom;
	}

	public static void setDefaultMinZoom(int minZoom) {
		DefaultMinZoom = minZoom;
	}

	public static int GetDefaultMaxZoom() {
		return DefaultMaxZoom;
	}

	public static int GetDefaultMinZoom() {
		return DefaultMinZoom;
	}
}

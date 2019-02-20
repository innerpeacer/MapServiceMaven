package cn.platalk.map.vectortile.builder;

import com.vividsolutions.jts.geom.Geometry;

class TYVectorTileParams {

	public static final MvtLayerParams DEFAULT_MVT_PARAMS = new MvtLayerParams();

	public static final MvtIGeometryFilter ACCEPT_ALL_FILTER = new MvtIGeometryFilter() {
		@Override
		public boolean accept(Geometry geometry) {
			return true;
		}
	};

	public static final String LAYER_FLOOR = "floor";
	public static final String LAYER_ROOM = "room";
	public static final String LAYER_ASSET = "asset";
	public static final String LAYER_FACILITY = "facility";
	public static final String LAYER_LABEL = "label";

	public static final String LAYER_FILL = "fill";

	public static final String LAYER_EXTRUSION = "extrusion";

	public static final String[] LAYER_LIST_v1 = { LAYER_FLOOR, LAYER_ROOM,
			LAYER_ASSET, LAYER_FACILITY, LAYER_LABEL };

	public static final String[] LAYER_LIST_v2 = { LAYER_FILL, LAYER_FACILITY,
			LAYER_LABEL };
}

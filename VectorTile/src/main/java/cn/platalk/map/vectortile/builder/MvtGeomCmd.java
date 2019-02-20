package cn.platalk.map.vectortile.builder;

enum MvtGeomCmd {
	MoveTo(1, 2), LineTo(2, 2), ClosePath(7, 0);

	private final int cmdId;
	private final int paramCount;

	MvtGeomCmd(int cmdId, int paramCount) {
		this.cmdId = cmdId;
		this.paramCount = paramCount;
	}

	public int getCmdId() {
		return cmdId;
	}

	public int getParamCount() {
		return paramCount;
	}

	public static MvtGeomCmd fromId(int cmdId) {
		final MvtGeomCmd geomCmd;
		switch (cmdId) {
		case 1:
			geomCmd = MoveTo;
			break;
		case 2:
			geomCmd = LineTo;
			break;
		case 7:
			geomCmd = ClosePath;
			break;
		default:
			geomCmd = null;
		}
		return geomCmd;
	}
}

package cn.platalk.map.vectortile.builder;

final class MvtGeomCmdHdr {

	private static final int CLOSE_PATH_HDR = cmdHdr(MvtGeomCmd.ClosePath, 1);

	public static int cmdHdr(MvtGeomCmd cmd, int length) {
		return (cmd.getCmdId() & 0x7) | (length << 3);
	}

	public static int getCmdLength(int cmdHdr) {
		return cmdHdr >> 3;
	}

	public static int getCmdId(int cmdHdr) {
		return cmdHdr & 0x7;
	}

	public static MvtGeomCmd getCmd(int cmdHdr) {
		final int cmdId = getCmdId(cmdHdr);
		return MvtGeomCmd.fromId(cmdId);
	}

	public static int closePathCmdHdr() {
		return CLOSE_PATH_HDR;
	}

	public static final int CMD_HDR_LEN_MAX = (int) (Math.pow(2, 29) - 1);
}

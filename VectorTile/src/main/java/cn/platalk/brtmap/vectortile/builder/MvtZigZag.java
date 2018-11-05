package cn.platalk.brtmap.vectortile.builder;

final class MvtZigZag {

	public static int encode(int n) {
		return (n << 1) ^ (n >> 31);
	}

	public static int decode(int n) {
		return (n >> 1) ^ (-(n & 1));
	}
}

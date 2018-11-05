package cn.platalk.brtmap.vectortile.builder;

final class MvtVec2d {

	public double x, y;

	public MvtVec2d() {
		set(0d, 0d);
	}

	public MvtVec2d(double x, double y) {
		set(x, y);
	}

	public MvtVec2d(MvtVec2d v) {
		set(v);
	}

	public MvtVec2d set(double x, double y) {
		this.x = x;
		this.y = y;

		return this;
	}

	public MvtVec2d set(MvtVec2d v) {
		return set(v.x, v.y);
	}

	public MvtVec2d add(double x, double y) {
		this.x += x;
		this.y += y;

		return this;
	}

	public MvtVec2d add(MvtVec2d v) {
		return add(v.x, v.y);
	}

	public MvtVec2d sub(double x, double y) {
		this.x -= x;
		this.y -= y;

		return this;
	}

	public MvtVec2d sub(MvtVec2d v) {
		return sub(v.x, v.y);
	}

	public MvtVec2d scale(double scalar) {
		this.x *= scalar;
		this.y *= scalar;

		return this;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
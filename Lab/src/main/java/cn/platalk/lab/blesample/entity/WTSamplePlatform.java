package cn.platalk.lab.blesample.entity;

public enum WTSamplePlatform {
	iOS(1), Android(2), H5(3), Unknown(-1);

	private int value;

	private WTSamplePlatform(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static WTSamplePlatform fromValue(int value) {
		switch (value) {
		case 1:
			return iOS;

		case 2:
			return Android;

		case 3:
			return H5;

		default:
			return Unknown;
		}
	}
}

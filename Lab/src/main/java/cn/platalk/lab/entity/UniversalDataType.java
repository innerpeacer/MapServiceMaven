package cn.platalk.lab.entity;

public enum UniversalDataType {
	Motion(1), BleSample(2);

	private int type;

	private UniversalDataType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static UniversalDataType fromType(int type) {
		switch (type) {
		case 1:
			return Motion;

		case 2:
			return BleSample;

		default:
			return null;
		}
	}
}

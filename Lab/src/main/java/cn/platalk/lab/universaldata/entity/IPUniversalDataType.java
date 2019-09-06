package cn.platalk.lab.universaldata.entity;

public enum IPUniversalDataType {
	Motion(1), BleSample(2);

	private int value;

	private IPUniversalDataType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static IPUniversalDataType fromValue(int value) {
		switch (value) {
		case 1:
			return Motion;

		case 2:
			return BleSample;

		default:
			return null;
		}
	}
}

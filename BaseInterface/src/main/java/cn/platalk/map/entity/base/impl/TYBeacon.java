package cn.platalk.map.entity.base.impl;

public class TYBeacon {
	private static final long CONSTANT_HUNDRED_THOUSAND = 100000;

	protected String UUID;
	protected int major;
	protected int minor;
	protected String tag;

	public TYBeacon() {

	}

	public TYBeacon(String uuid, int major, int minor, String tag) {
		this.UUID = uuid;
		this.major = major;
		this.minor = minor;
		this.tag = tag;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Long getBeaconKey() {
		return this.major * CONSTANT_HUNDRED_THOUSAND + this.minor;
	}

	@Override
	public String toString() {
		return "TYBeacon [major=" + major + ", minor=" + minor + "]";
	}
}

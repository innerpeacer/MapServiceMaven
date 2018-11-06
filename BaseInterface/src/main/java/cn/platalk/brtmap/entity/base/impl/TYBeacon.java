package cn.platalk.brtmap.entity.base.impl;

public class TYBeacon {
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

	@Override
	public String toString() {
		return "TYBeacon [major=" + major + ", minor=" + minor + "]";
	}
}

package cn.platalk.lab.entity;

public class IPUniversalData {
	String dataID;
	String dataName;
	byte[] dataContent;
	UniversalDataType dataType;
	String dataDescription;

	public IPUniversalData() {

	}

	public String getDataID() {
		return dataID;
	}

	public void setDataID(String dataID) {
		this.dataID = dataID;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public byte[] getDataContent() {
		return dataContent;
	}

	public void setDataContent(byte[] dataContent) {
		this.dataContent = dataContent;
	}

	public UniversalDataType getDataType() {
		return dataType;
	}

	public void setDataType(UniversalDataType dataType) {
		this.dataType = dataType;
	}

	public String getDataDescription() {
		return dataDescription;
	}

	public void setDataDescription(String dataDescription) {
		this.dataDescription = dataDescription;
	}

}

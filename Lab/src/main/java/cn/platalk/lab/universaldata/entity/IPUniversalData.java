package cn.platalk.lab.universaldata.entity;

public class IPUniversalData {
	String dataID;
	String dataName;
	byte[] dataContent;
	IPUniversalDataType dataType;
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

	public IPUniversalDataType getDataType() {
		return dataType;
	}

	public void setDataType(IPUniversalDataType dataType) {
		this.dataType = dataType;
	}

	public String getDataDescription() {
		return dataDescription;
	}

	public void setDataDescription(String dataDescription) {
		this.dataDescription = dataDescription;
	}

	@Override
	public String toString() {
		return String.format("DataID: %s, Name: %s, Type: %d", dataID, dataName, dataType.getValue());
	}
}

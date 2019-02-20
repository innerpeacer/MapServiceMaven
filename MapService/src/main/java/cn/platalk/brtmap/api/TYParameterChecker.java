package cn.platalk.brtmap.api;

import cn.platalk.utils.TYMatchUtils;

public class TYParameterChecker {

	public static boolean isValidBuildingID(String buildingID) {
		if (buildingID != null && buildingID.length() == 8
				&& TYMatchUtils.isAlphabeticNumeric(buildingID)) {
			return true;
		}
		System.out.println("Invalid BuildingID: " + buildingID);
		return false;
	}
}

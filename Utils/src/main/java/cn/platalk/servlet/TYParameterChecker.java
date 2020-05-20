package cn.platalk.servlet;

import cn.platalk.utils.TYMatchUtils;

public class TYParameterChecker {

	public static boolean isValidBuildingID(String buildingID) {
		return buildingID != null && buildingID.length() == 8 && TYMatchUtils.isAlphabeticNumeric(buildingID);
		// System.out.println("Invalid BuildingID: " + buildingID);
	}
}

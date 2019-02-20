package cn.platalk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TYMatchUtils {
	// public static void main(String[] args) {
	// String buildingID;
	// buildingID = "00210010";
	// System.out.println(isAlphabeticNumeric(buildingID));
	//
	// buildingID = "aH0210010-";
	// System.out.println(isAlphabeticNumeric(buildingID));
	// }

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(str);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isAlphabeticNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
		Matcher matcher = pattern.matcher(str);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
}

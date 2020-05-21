package cn.platalk.map.vectortile.fontbuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;

public class TYExtractLabelString {

	public static String GetLabelString(List<TYIMapDataFeatureRecord> records) {
		Set<Character> charSet = new HashSet<>();

		for (int i = '!'; i < '~'; ++i) {
			charSet.add((char) i);
		}

		for (TYIMapDataFeatureRecord record : records) {
			String nameString = "";
			if (record.getName() != null) {
				nameString += record.getName();
			}

			if (record.getNameEn() != null) {
				nameString += record.getNameEn();
			}

			if (record.getNameOther() != null) {
				nameString += record.getNameOther();
			}

			for (int i = 0; i < nameString.length(); ++i) {
				charSet.add(nameString.charAt(i));
			}
		}

		StringBuilder charBuffer = new StringBuilder();
		for (Character c : charSet) {
			charBuffer.append(c);
		}
		return charBuffer.toString();
	}
}

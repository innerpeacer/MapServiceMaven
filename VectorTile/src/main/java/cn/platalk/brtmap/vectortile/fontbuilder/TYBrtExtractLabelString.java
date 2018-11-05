package cn.platalk.brtmap.vectortile.fontbuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.platalk.brtmap.entity.base.TYIMapDataFeatureRecord;

class TYBrtExtractLabelString {

	public static String GetLabelString(List<TYIMapDataFeatureRecord> records) {
		Set<Character> charSet = new HashSet<Character>();

		for (int i = '!'; i < '~'; ++i) {
			charSet.add((char) i);
		}

		for (TYIMapDataFeatureRecord record : records) {
			String name = record.getName();
			for (int i = 0; i < name.length(); ++i) {
				charSet.add(name.charAt(i));
			}
		}

		StringBuffer charBuffer = new StringBuffer();
		for (Character c : charSet) {
			charBuffer.append(c);
		}
		return charBuffer.toString();
	}
}

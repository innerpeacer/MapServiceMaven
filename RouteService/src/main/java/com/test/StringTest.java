package com.test;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;

public class StringTest {
	public static void main(String[] args) {
		// String str = "[12686220.561437858,2560924.902856261,2]";
		// String str =
		// "[[12686220.561437858,2560924.902856261,2],[12686220.561437858,2560924.902856261,2],[12686220.561437858,2560924.902856261,2]]";
		String str = "12686220.561437858,2560924.902856261,2";
		str = "12686220.561437858,2560924.902856261,2,12686220.561437858,2560924.902856261,2,12686220.561437858,2560924.902856261,2";

		String[] splitted = str.split(",");
		// String[] splitted = str.split("\\[|,|\\]");

		System.out.println(splitted.length + " Parts");

		for (int i = 0; i < splitted.length; ++i) {
			String substr = splitted[i];
			// System.out.println(substr.length());
			System.out.println(substr);
		}
		// System.out.println(Arrays.toString(splitted));

		// String type = "01";
		// System.out.println(type);
		// System.out.println(type.substring(1 - 1, 1));
		// System.out.println(Boolean.parseBoolean("1"));

		String times = "[[00-00, 01-00], [12-00,18-00]]";
		try {
			JSONArray array = new JSONArray(times);
			System.out.println(array);

			for (int i = 0; i < array.length(); i++) {
				JSONArray win = array.getJSONArray(i);
				System.out.println(win);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		System.out.println(hour + ":" + minute);

	}
}

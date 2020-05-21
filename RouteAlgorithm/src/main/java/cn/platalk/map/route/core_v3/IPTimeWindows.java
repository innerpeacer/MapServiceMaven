package cn.platalk.map.route.core_v3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class IPTimeWindows {

	private final List<TimeWindow> winList = new ArrayList<>();

	public IPTimeWindows(String windows) {
		if (windows == null) {
			return;
		}

		try {
			JSONArray array = new JSONArray(windows);
			for (int i = 0; i < array.length(); ++i) {
				JSONArray windowArray = array.getJSONArray(i);
				if (windowArray.length() != 2) {
					continue;
				}

				TimeWindow win = new TimeWindow(windowArray.getString(0), windowArray.getString(1));
				winList.add(win);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public boolean contains(int hour, int minute) {
		if (winList.size() == 0) {
			return true;
		}

		boolean result = false;
		for (TimeWindow win : winList) {
			if (win.contains(hour, minute)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean contains(Calendar c) {
		return contains(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimeWindows:\n");
		for (TimeWindow timeWindow : winList) {
			builder.append("\t").append(timeWindow).append("\n");
		}
		return builder.toString();
	}

	public static class TimeWindow {
		private int startHour = -1;
		private int startMinute = -1;

		private int endHour = -1;
		private int endMinute = -1;

		public TimeWindow(String start, String end) {
			String[] startTimeArray = start.split("-");
			String[] endTimeArray = end.split("-");
			if (startTimeArray.length != 2 || endTimeArray.length != 2) {
				return;
			}

			startHour = Integer.parseInt(startTimeArray[0]);
			startMinute = Integer.parseInt(startTimeArray[1]);

			endHour = Integer.parseInt(endTimeArray[0]);
			endMinute = Integer.parseInt(endTimeArray[1]);
		}

		public boolean contains(int hour, int minute) {
			int start = startHour * 60 + startMinute;
			int end = endHour * 60 + endMinute;
			int time = hour * 60 + minute;
			return (start <= time) && (time <= end);
		}

		@Override
		public String toString() {
			return String.format("TimeWindow: [%02d:%02d - %02d:%02d]", startHour, startMinute, endHour, endMinute);
		}

	}
}

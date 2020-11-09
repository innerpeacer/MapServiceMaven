package cn.platalk.core.map.shp.mapdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.impl.map.TYMapExtent;
import cn.platalk.map.entity.base.impl.map.TYMapInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.core.map.base.TYIShpDataManager;

public class TYJsonMapInfoParser {
	List<TYMapInfo> mapInfos;
	TYIShpDataManager shpDataManager;

	public TYJsonMapInfoParser() {

	}

	public void setShpDataManager(TYIShpDataManager manager) {
		this.shpDataManager = manager;
	}

	public void parseMapInfos() {
		mapInfos = new ArrayList<>();

		FileInputStream inStream;
		try {
			inStream = new FileInputStream(new File(shpDataManager.getMapInfoJsonPath()));
			InputStreamReader inputReader = new InputStreamReader(inStream);
			BufferedReader bufReader = new BufferedReader(inputReader);

			String line;
			StringBuilder jsonStr = new StringBuilder();
			while ((line = bufReader.readLine()) != null)
				jsonStr.append(line);

			JSONObject jsonObject = new JSONObject(jsonStr.toString());
			if (!jsonObject.isNull("MapInfo")) {
				JSONArray array = jsonObject.getJSONArray("MapInfo");
				for (int i = 0; i < array.length(); i++) {
					TYMapInfo mapInfo = new TYMapInfo();
					JSONObject mapInfoObject = array.getJSONObject(i);
					mapInfo.setCityID(mapInfoObject.optString("cityID"));
					mapInfo.setBuildingID(mapInfoObject.optString("buildingID"));
					mapInfo.setMapID(mapInfoObject.optString("mapID"));
					mapInfo.setFloorName(mapInfoObject.optString("floorName"));
					mapInfo.setFloorNumber(mapInfoObject.optInt("floorNumber"));
					mapInfo.setSize_x(mapInfoObject.optDouble("size_x"));
					mapInfo.setSize_y(mapInfoObject.optDouble("size_y"));
					TYMapExtent extent = new TYMapExtent(mapInfoObject.optDouble("xmin"),
							mapInfoObject.optDouble("ymin"), mapInfoObject.optDouble("xmax"),
							mapInfoObject.optDouble("ymax"));
					mapInfo.setMapExtent(extent);
					mapInfos.add(mapInfo);
				}
			}
			inputReader.close();
			notifyFinishParsingMapInfo(mapInfos);
		} catch (IOException | JSONException e) {
			notifyFailedParsingMapInfo(e);
			e.printStackTrace();
		}
	}

	private final List<TYBrtMapInfoJsonParserListener> listeners = new ArrayList<>();

	public void addParserListener(TYBrtMapInfoJsonParserListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeParserListener(TYBrtMapInfoJsonParserListener listener) {
		listeners.remove(listener);
	}

	private void notifyFinishParsingMapInfo(List<TYMapInfo> infos) {
		for (TYBrtMapInfoJsonParserListener listener : listeners) {
			listener.didFinishParsingMapInfo(infos);
		}
	}

	private void notifyFailedParsingMapInfo(Throwable error) {
		for (TYBrtMapInfoJsonParserListener listener : listeners) {
			listener.didFailedParsingMapInfo(error);
		}
	}

	public interface TYBrtMapInfoJsonParserListener {
		void didFinishParsingMapInfo(List<TYMapInfo> mapInfos);

		void didFailedParsingMapInfo(Throwable error);

	}

}

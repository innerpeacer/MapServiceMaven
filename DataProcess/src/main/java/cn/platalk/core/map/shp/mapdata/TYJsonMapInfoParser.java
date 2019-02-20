package cn.platalk.core.map.shp.mapdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.core.map.base.TYIShpDataManager;
import cn.platalk.map.entity.base.impl.TYMapInfo;

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

			String line = "";
			StringBuffer jsonStr = new StringBuffer();
			while ((line = bufReader.readLine()) != null)
				jsonStr.append(line);

			JSONObject jsonObject = new JSONObject(jsonStr.toString());
			if (jsonObject != null && !jsonObject.isNull("MapInfo")) {
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
					mapInfo.setXmin(mapInfoObject.optDouble("xmin"));
					mapInfo.setYmin(mapInfoObject.optDouble("ymin"));
					mapInfo.setXmax(mapInfoObject.optDouble("xmax"));
					mapInfo.setYmax(mapInfoObject.optDouble("ymax"));
					mapInfos.add(mapInfo);
				}
			}
			inputReader.close();
			notifyFinishParsingMapInfo(mapInfos);
		} catch (FileNotFoundException e) {
			notifyFailedParsingMapInfo(e);
			e.printStackTrace();
		} catch (IOException e) {
			notifyFailedParsingMapInfo(e);
			e.printStackTrace();
		} catch (JSONException e) {
			notifyFailedParsingMapInfo(e);
			e.printStackTrace();
		}
	}

	private List<TYBrtMapInfoJsonParserListener> listeners = new ArrayList<TYJsonMapInfoParser.TYBrtMapInfoJsonParserListener>();

	public void addParserListener(TYBrtMapInfoJsonParserListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeParserListener(TYBrtMapInfoJsonParserListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
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
		public void didFinishParsingMapInfo(List<TYMapInfo> mapInfos);

		public void didFailedParsingMapInfo(Throwable error);

	}

}

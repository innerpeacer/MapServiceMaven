package cn.platalk.map.route.core;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.map.entity.base.TYLocalPoint;

public class TYServerMultiRouteResult {
	private TYLocalPoint startPoint;
	private TYLocalPoint endPoint;
	private List<TYLocalPoint> stopPoints;

	private TYServerRouteResult completeResult;
	private List<TYServerRouteResult> detailedResult;
	private List<Integer> indices;
	private List<TYLocalPoint> rearrangedPoints;

	public TYServerMultiRouteResult(TYServerRouteResult crs, List<TYServerRouteResult> details) {
		this.completeResult = crs;
		this.detailedResult = details;
	}

	TYLocalPoint getStartPoint() {
		return startPoint;
	}

	void setStartPoint(TYLocalPoint startPoint) {
		this.startPoint = startPoint;
	}

	TYLocalPoint getEndPoint() {
		return endPoint;
	}

	void setEndPoint(TYLocalPoint endPoint) {
		this.endPoint = endPoint;
	}

	List<TYLocalPoint> getStopPoints() {
		return stopPoints;
	}

	void setStopPoints(List<TYLocalPoint> stopPoints) {
		this.stopPoints = stopPoints;
	}

	boolean hasMiddleStop() {
		return stopPoints != null && stopPoints.size() > 0;
	}

	List<Integer> getIndices() {
		return indices;
	}

	void setIndices(List<Integer> indices) {
		this.indices = indices;
	}

	List<TYLocalPoint> getRearrangedPoints() {
		return rearrangedPoints;
	}

	void setRearrangedPoints(List<TYLocalPoint> rearrangedPoints) {
		this.rearrangedPoints = rearrangedPoints;
	}

	TYServerRouteResult getCompleteResult() {
		return completeResult;
	}

	List<TYServerRouteResult> getDetailedResult() {
		return detailedResult;
	}

	public JSONObject buildJson() {
		JSONObject result = new JSONObject();

		try {
			long now = System.currentTimeMillis() / 1000;
			result.put("code", now * (long) startPoint.getX() + (long) startPoint.getY());
			result.put("start", IPJsonBuilder.buildLocalPoint(startPoint));
			result.put("end", IPJsonBuilder.buildLocalPoint(endPoint));

			JSONArray stopPointArray = new JSONArray();
			if (stopPoints != null && stopPoints.size() != 0) {
				for (int i = 0; i < stopPoints.size(); ++i) {
					stopPointArray.put(IPJsonBuilder.buildLocalPoint(stopPoints.get(i)));
				}
			}

			result.put("stops", stopPointArray);

			JSONArray indicesArray = new JSONArray();
			if (indices != null && indices.size() != 0) {
				for (int i = 0; i < indices.size(); ++i) {
					indicesArray.put(indices.get(i));
				}
			}
			result.put("indices", indicesArray);

			JSONArray rearrangedPointArray = new JSONArray();
			if (rearrangedPoints != null && rearrangedPoints.size() != 0) {
				for (int i = 0; i < rearrangedPoints.size(); ++i) {
					rearrangedPointArray.put(IPJsonBuilder.buildLocalPoint(rearrangedPoints.get(i)));
				}
			}
			result.put("rearrangedStop", rearrangedPointArray);

			JSONObject completeResultJson = completeResult.buildJson();
			completeResultJson.put("start", IPJsonBuilder.buildLocalPoint(startPoint));
			completeResultJson.put("end", IPJsonBuilder.buildLocalPoint(endPoint));

			result.put("completeRoute", completeResultJson);

			JSONArray detailResultArray = new JSONArray();
			for (int i = 0; i < detailedResult.size(); ++i) {
				TYServerRouteResult detail = detailedResult.get(i);
				JSONObject detailResultJson = detail.buildJson();
				if (i == 0) {
					detailResultJson.put("start", IPJsonBuilder.buildLocalPoint(startPoint));
				} else {
					detailResultJson.put("start", IPJsonBuilder.buildLocalPoint(rearrangedPoints.get(i - 1)));
				}

				if (i == detailedResult.size() - 1) {
					detailResultJson.put("end", IPJsonBuilder.buildLocalPoint(endPoint));
				} else {
					detailResultJson.put("end", IPJsonBuilder.buildLocalPoint(rearrangedPoints.get(i)));
				}
				detailResultArray.put(detailResultJson);
			}
			result.put("details", detailResultArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}
}

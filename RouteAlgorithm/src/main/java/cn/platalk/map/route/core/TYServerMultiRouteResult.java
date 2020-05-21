package cn.platalk.map.route.core;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.platalk.map.entity.base.impl.TYLocalPoint;

public class TYServerMultiRouteResult {
	private TYLocalPoint startPoint;
	private TYLocalPoint endPoint;
	private List<TYLocalPoint> stopPoints;

	private final TYServerRouteResult completeResult;
	private final List<TYServerRouteResult> detailedResult;
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
			result.put("start", startPoint.buildJson());
			result.put("end", endPoint.buildJson());

			JSONArray stopPointArray = new JSONArray();
			if (stopPoints != null && stopPoints.size() != 0) {
				for (TYLocalPoint stopPoint : stopPoints) {
					stopPointArray.put(stopPoint.buildJson());
				}
			}

			result.put("stops", stopPointArray);

			JSONArray indicesArray = new JSONArray();
			if (indices != null && indices.size() != 0) {
				for (Integer index : indices) {
					indicesArray.put(index);
				}
			}
			result.put("indices", indicesArray);

			JSONArray rearrangedPointArray = new JSONArray();
			if (rearrangedPoints != null && rearrangedPoints.size() != 0) {
				for (TYLocalPoint rearrangedPoint : rearrangedPoints) {
					rearrangedPointArray.put(rearrangedPoint.buildJson());
				}
			}
			result.put("rearrangedStop", rearrangedPointArray);

			JSONObject completeResultJson = completeResult.buildJson();
			completeResultJson.put("start", startPoint.buildJson());
			completeResultJson.put("end", endPoint.buildJson());

			result.put("completeRoute", completeResultJson);

			JSONArray detailResultArray = new JSONArray();
			for (int i = 0; i < detailedResult.size(); ++i) {
				TYServerRouteResult detail = detailedResult.get(i);
				JSONObject detailResultJson = detail.buildJson();
				if (i == 0) {
					detailResultJson.put("start", startPoint.buildJson());
				} else {
					detailResultJson.put("start", rearrangedPoints.get(i - 1).buildJson());
				}

				if (i == detailedResult.size() - 1) {
					detailResultJson.put("end", endPoint.buildJson());
				} else {
					detailResultJson.put("end", rearrangedPoints.get(i).buildJson());
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

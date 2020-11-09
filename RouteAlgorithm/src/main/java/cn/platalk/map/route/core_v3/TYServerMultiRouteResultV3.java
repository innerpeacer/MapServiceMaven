package cn.platalk.map.route.core_v3;

import java.util.List;

import cn.platalk.map.entity.base.impl.map.TYLocalPoint;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TYServerMultiRouteResultV3 {
	private TYLocalPoint startPoint;
	private TYLocalPoint endPoint;
	public String startRoomID;
	public String endRoomID;

	private List<TYLocalPoint> stopPoints;

	private final TYServerRouteResultV3 completeResult;
	private final List<TYServerRouteResultV3> detailedResult;
	private List<Integer> indices;
	private List<TYLocalPoint> rearrangedPoints;

	public TYServerMultiRouteResultV3(TYServerRouteResultV3 crs, List<TYServerRouteResultV3> details) {
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

	TYServerRouteResultV3 getCompleteResult() {
		return completeResult;
	}

	List<TYServerRouteResultV3> getDetailedResult() {
		return detailedResult;
	}

	public JSONObject buildJson() {
		JSONObject result = new JSONObject();

		try {
			long now = System.currentTimeMillis() / 1000;
			result.put("code", now * (long) startPoint.getX() + (long) startPoint.getY());
			result.put("start", startPoint.buildJson());
			result.put("end", endPoint.buildJson());
			result.put("startRoomID", startRoomID);
			result.put("endRoomID", endRoomID);
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
				TYServerRouteResultV3 detail = detailedResult.get(i);
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

	public String detailedDescription() {
		StringBuilder builder = new StringBuilder();
		builder.append("Multi Route:\n");
		builder.append(stopPoints.size()).append(" stops\n");
		builder.append(detailedResult.size()).append(" sub routes\n");
		for (int i = 0; i < detailedResult.size(); ++i) {
			builder.append("\t Sub ").append(i + 1).append(" : ").append(detailedResult.get(i)).append("\n");
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		return String.format("[Multi Route: %d]", detailedResult.size());
	}
}

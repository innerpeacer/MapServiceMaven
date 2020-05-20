package cn.platalk.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class TYBaseHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public final String RESPONSE_JSON_KEY_STATUS = "success";
	public final String RESPONSE_JSON_KEY_DESCRIPTION = "description";

	protected String errorDescriptionInvalidBuildingID(String buildingID) {
		return String.format("Invalid BuildingID: %s", buildingID);
	}

	protected String errorDescriptionNotExistBuildingID(String buildingID) {
		return String.format("Building Not Exist: %s", buildingID);
	}

	protected void respondError(HttpServletRequest request, HttpServletResponse response, String description)
			throws IOException {
		String callback = request.getParameter("callback");
		response.setContentType("text/json;charset=UTF-8");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(RESPONSE_JSON_KEY_STATUS, false);
		jsonObject.put(RESPONSE_JSON_KEY_DESCRIPTION, description);

		PrintWriter out = response.getWriter();
		if (callback == null) {
			out.print(jsonObject.toString());
		} else {
			out.print(String.format("%s(%s)", callback, jsonObject.toString()));
		}
		out.close();
    }

	protected void respondError(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject)
			throws IOException {
		String callback = request.getParameter("callback");
		response.setContentType("text/json;charset=UTF-8");

		jsonObject.put(RESPONSE_JSON_KEY_STATUS, false);

		PrintWriter out = response.getWriter();
		if (callback == null) {
			out.print(jsonObject.toString());
		} else {
			out.print(String.format("%s(%s)", callback, jsonObject.toString()));
		}
		out.close();
    }

	protected void respondResult(HttpServletRequest request, HttpServletResponse response, Map<String, Object> data)
			throws IOException {
		String callback = request.getParameter("callback");
		response.setContentType("text/json;charset=UTF-8");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(RESPONSE_JSON_KEY_STATUS, true);
		for (String key : data.keySet()) {
			jsonObject.put(key, data.get(key));
		}

		PrintWriter out = response.getWriter();
		if (callback == null) {
			out.print(jsonObject.toString());
		} else {
			out.print(String.format("%s(%s)", callback, jsonObject.toString()));
		}
		out.close();
    }

	protected void respondResult(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject)
			throws IOException {
		String callback = request.getParameter("callback");
		response.setContentType("text/json;charset=UTF-8");

		jsonObject.put(RESPONSE_JSON_KEY_STATUS, true);

		PrintWriter out = response.getWriter();
		if (callback == null) {
			out.print(jsonObject.toString());
		} else {
			out.print(String.format("%s(%s)", callback, jsonObject.toString()));
		}
		out.close();
    }
}

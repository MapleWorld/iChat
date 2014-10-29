package org.csc301team6.server;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogoutServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject jResp;

		jResp = new JSONObject();
		jResp.put("message", "Method not allowed");

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

		response.getWriter().println(jResp.toString());
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String sessionID;
		JSONObject jResp;

		jResp = new JSONObject();

		response.setContentType("application/json");

		sessionID = request.getHeader("SESSIONID");

		if (sessionID == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			jResp.put("success", false);
			jResp.put("message", "No session token provided");
		} else {
			if (SessionDTO.destroySession(sessionID)) {
				response.setStatus(HttpServletResponse.SC_OK);
				jResp.put("success", true);
				jResp.put("message", "Logged out successfully");
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				jResp.put("success", false);
				jResp.put("message",
						"Session token invalid, logout not processed");
			}
		}

		response.getWriter().println(jResp.toString());
	}
}

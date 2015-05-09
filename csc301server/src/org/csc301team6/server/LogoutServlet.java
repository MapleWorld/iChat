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

/*
 * This servlet will accept a request from an authenticated
 * user to destroy the session associated with the request.
 * The request must include a valid SESSIONID HTTP header.
 */
public class LogoutServlet extends HttpServlet {
	
	//GET is not supported for /logout
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

	//Process the logout request
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String sessionID;
		JSONObject jResp;

		jResp = new JSONObject();

		response.setContentType("application/json");

		sessionID = request.getHeader("SESSIONID");

		if (sessionID == null) {
			//Request did not provide the SESSIONID header at all
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			jResp.put("success", false);
			jResp.put("message", "No session token provided");
		} else {
			//There was a SESSIONID header provided, we will attempt to log it off
			if (SessionDTO.destroySession(sessionID)) {
				//Session was valid and is now destroyed
				response.setStatus(HttpServletResponse.SC_OK);
				jResp.put("success", true);
				jResp.put("message", "Logged out successfully");
			} else {
				//Session was not valid, no logout took place
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				jResp.put("success", false);
				jResp.put("message",
						"Session token invalid, logout not processed");
			}
		}

		response.getWriter().println(jResp.toString());
	}
}

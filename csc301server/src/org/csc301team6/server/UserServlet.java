package org.csc301team6.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class UserServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Pattern userBanPattern = Pattern.compile("^\\/users\\/ban\\/(\\d+)$");
		Matcher userBanMatcher = userBanPattern.matcher(request.getRequestURI());
		JSONObject jResp;
		long param;
		String sessionID;
		
		jResp = new JSONObject();
		
		sessionID = request.getHeader("SESSIONID");
		if(sessionID != null) {
			if (userBanMatcher.find()) {
				try {
					param = Long.parseLong(userBanMatcher.group(1));
					
					if(UserDTO.banUserByID(param, sessionID)) {
						response.setStatus(HttpServletResponse.SC_OK);
						jResp.put("message", "User has been successfully banned");
						jResp.put("success", true);
					} else {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						jResp.put("message", "Error banning user");
						jResp.put("success", false);
					}
					
				} catch (NumberFormatException ne) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					jResp.put("message", "Error parsing input parameters");
					jResp.put("success", false);
				} catch (UnauthorizedException ue) {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					jResp.put("message", "Unauthorized");
					jResp.put("success", false);
				}
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				jResp.put("message", "Illegal request");
				
			}
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			jResp.put("message", "Unauthorized: no SESSIONID provided");
			jResp.put("success", false);
		}
		
		response.getWriter().println(jResp.toString());
	}
}

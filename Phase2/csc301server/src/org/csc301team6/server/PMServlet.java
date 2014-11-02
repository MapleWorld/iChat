package org.csc301team6.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PMServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject jResp;

		jResp = new JSONObject();
		jResp.put("message", "Not implemented");

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);

		response.getWriter().println(jResp.toString());
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		BufferedReader br;
		JSONObject jResp;
		JSONObject jo;
		String sessionID = request.getHeader("SESSIONID");
		String line;
		String jsonInput = "";
		String subject;
		String body;
		long userid_to;
		ConfigManager mgr = ConfigManager.getInstance();

		if(sessionID != null) {
			if(request.getRequestURI().equals("/pm/send")){
				try {
					
					br = request.getReader();
					
					while ((line = br.readLine()) != null) {
						jsonInput += line;
					}
					
					jo = new JSONObject(jsonInput);
					
					userid_to = jo.getLong("userid_to");
					subject = jo.getString("subject");
					body = jo.getString("body");
					
					jResp = new JSONObject();
					
					if(subject.length() > mgr.getMaxPMSubjLength()){
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						jResp.put("message", "Subject exceeds maximum permitted length");
						jResp.put("success", false);
					} else if (body.length() > mgr.getMaxPMBodyLength()) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						jResp.put("message", "Body exceeds maximum permitted length");
						jResp.put("success", false);
					} else {
						PMDTO.sendPM(sessionID, userid_to, subject, body);
						
						response.setStatus(HttpServletResponse.SC_OK);
						jResp.put("success", true);
						jResp.put("message", "Message sent successfully");
					}
					
					response.getWriter().println(jResp.toString());
					
				} catch (JSONException je) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					jResp = new JSONObject();
					jResp.put("message", "Error parsing input parameters");
					jResp.put("success", false);
					response.getWriter().println(jResp.toString());
				} catch (UnauthorizedException ue) {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					jResp = new JSONObject();
					jResp.put("message", "Unauthorized");
					jResp.put("success", false);
					response.getWriter().println(jResp.toString());
				}
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				jResp = new JSONObject();
				jResp.put("message", "Illegal request");
				jResp.put("success", false);
				response.getWriter().println(jResp.toString());
			}
		} else {
			//All PM operations must have a SESSIONID provided
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			jResp = new JSONObject();
			jResp.put("message", "Unauthorized");
			jResp.put("success", false);
			response.getWriter().println(jResp.toString());
		}

	}
}

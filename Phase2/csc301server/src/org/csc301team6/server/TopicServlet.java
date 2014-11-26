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

public class TopicServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Pattern topicPattern = Pattern.compile("^\\/topics\\/list\\/(\\d+)$");
		Pattern topicBySubPattern = Pattern.compile("^\\/topics\\/subscriptions$");
		
		Matcher topicMatcher = topicPattern.matcher(request.getRequestURI());
		Matcher topicBySubMatcher = topicBySubPattern.matcher(request.getRequestURI());

		JSONObject jResp;
		long param;

		if (topicMatcher.find()) {
			try {
				param = Long.parseLong(topicMatcher.group(1));
			} catch (Exception e) {
				e.printStackTrace();
				jResp = new JSONObject();
				jResp.put("success", false);
				jResp.put("message", "Error parsing input parameters");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println(jResp.toString());
				return;
			}

			doListTopicsByCategory(request, response, param);
		} else if (topicBySubMatcher.find()) {
			try {
				doListTopicsBySubscription(request, response);
			} catch (UnauthorizedException e) {
				e.printStackTrace();
			}
		} else {
			jResp = new JSONObject();
			jResp.put("message", "Illegal request");
			response.getWriter().println(jResp.toString());
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Pattern newTopicPattern = Pattern.compile("^\\/topics\\/create$");
		Pattern topicSubPattern = Pattern.compile("^\\/topics\\/subscribe$");

		Matcher newThreadMatcher = newTopicPattern.matcher(request
				.getRequestURI());
		Matcher topicSubMatcher = topicSubPattern.matcher(request.getRequestURI());

		JSONObject jResp;

		if (newThreadMatcher.find()) {
			doNewTopic(request, response);
		}else if (topicSubMatcher.find()){
			try{
				doTopicSubscribe(request, response);
			} catch(Exception e1){
				e1.printStackTrace();
			}
		}
		else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			jResp = new JSONObject();
			jResp.put("message", "Illegal request");
			response.getWriter().println(jResp.toString());
		}
	}
	
	private void doListTopicsBySubscription(HttpServletRequest request,
			HttpServletResponse response) throws IOException, UnauthorizedException {
		String sessionID;
		String jsonRespStr;
		JSONObject jsonError;
		
		sessionID = request.getHeader("SESSIONID");
		if (sessionID == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			jsonError = new JSONObject();
			jsonError.put("success", false);
			jsonError.put("message", "No session token provided");
			response.getWriter().println(jsonError);
		} else {
			jsonRespStr = TopicDTO.listTopicsBySubAsJSONString(sessionID);
			
			if (jsonRespStr != null) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().println(jsonRespStr);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				jsonError = new JSONObject();
				jsonError.put("success", false);
				jsonError.put("message", "An error has occurred");
				response.getWriter().println(jsonError);
			}
		}
	}
			
	private void doListTopicsByCategory(HttpServletRequest request,
			HttpServletResponse response, long cat_id) throws IOException {
		String jsonRespStr;
		JSONObject jsonError;

		jsonRespStr = TopicDTO.listTopicsByCategoryAsJSONString(cat_id);

		if (jsonRespStr != null) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(jsonRespStr);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			jsonError = new JSONObject();
			jsonError.put("success", false);
			jsonError.put("message", "An error has occurred");
			response.getWriter().println(jsonError);
		}
	}
	
	private void doTopicSubscribe(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String line;
		String jsonInput = "";
		JSONObject jo;
		BufferedReader br = request.getReader();
		long topic_id;
		String sessionID;
		JSONObject jResp;
		boolean success;

		jResp = new JSONObject();

		response.setContentType("application/json");

		sessionID = request.getHeader("SESSIONID");

		if (sessionID == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			jResp.put("success", false);
			jResp.put("message", "No session token provided");
		} else {
			while ((line = br.readLine()) != null) {
				jsonInput += line;
			}
			try {
				jo = new JSONObject(jsonInput);
				topic_id = jo.getLong("topic_id");
			
				success = TopicDTO.topicSub(sessionID, topic_id);
				if(success){
					response.setStatus(HttpServletResponse.SC_OK);
					jResp.put("success", true);
					jResp.put("message", "Subscribe Success");
				}else{
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					jResp.put("success", false);
					jResp.put("message", "Error parsing request");
				}
			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				jResp.put("success", false);
				jResp.put("message", "Error parsing request");
			} catch (UnauthorizedException ue) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				jResp.put("success", false);
				jResp.put("message", ue.getMessage());
			}
		}
		

		response.getWriter().println(jResp.toString());
	}
		
 
	private HttpServletResponse doNewTopic(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String line;
		String jsonInput = "";
		JSONObject jo;
		BufferedReader br = request.getReader();
		int cat_id;
		String topic_name;
		String sessionID;
		JSONObject jResp;
		long topic_id = -1;

		jResp = new JSONObject();

		response.setContentType("application/json");

		sessionID = request.getHeader("SESSIONID");

		if (sessionID == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			jResp.put("success", false);
			jResp.put("message", "No session token provided");
		} else {

			while ((line = br.readLine()) != null) {
				jsonInput += line;
			}

			try {
				jo = new JSONObject(jsonInput);
				cat_id = jo.getInt("category");
				topic_name = jo.getString("name");
				topic_id = TopicDTO.createTopic(sessionID, topic_name, cat_id);

				if (topic_id < 0) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					jResp.put("success", false);
					jResp.put("message", "Error creating topic");
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
					jResp.put("success", true);
					jResp.put("message", "Topic created successfully");
					jResp.put("topic_id", topic_id);
				}

			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				jResp.put("success", false);
				jResp.put("message", "Error parsing request");
			} catch (UnauthorizedException ue) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				jResp.put("success", false);
				jResp.put("message", ue.getMessage());
			}
		}

		response.getWriter().println(jResp.toString());
		return response;
	}

}

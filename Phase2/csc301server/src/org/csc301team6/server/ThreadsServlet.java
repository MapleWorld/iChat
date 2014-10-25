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

public class ThreadsServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        JSONObject jResp;
        
        long param_id;
        long page_num;
        
		Pattern viewThreadPattern = Pattern.compile("^\\/threads\\/view\\/(\\d+)\\/(\\d+)$");
		Pattern listByCatPattern = Pattern.compile("^\\/threads\\/by_category\\/(\\d+)\\/(\\d+)");
		Pattern listByTopicPattern = Pattern.compile("^\\/threads\\/by_topic\\/(\\d+)\\/(\\d+)");
		
		Matcher viewThreadMatcher = viewThreadPattern.matcher(request.getRequestURI());
		Matcher listByCatMatcher = listByCatPattern.matcher(request.getRequestURI());
		Matcher listByTopicMatcher = listByTopicPattern.matcher(request.getRequestURI());
		
		if (viewThreadMatcher.find()) {
			try {
				param_id = Long.parseLong(viewThreadMatcher.group(1));
				page_num = Long.parseLong(viewThreadMatcher.group(2));
			} catch (Exception e) {
				//Unable to parse thread_id/page_num params
				e.printStackTrace();
				jResp = new JSONObject();
				jResp.put("success", false);
				jResp.put("message", "Error parsing input parameters");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println(jResp.toString());	
				return;
			}
			
			doViewThread(request, response, param_id, page_num);
			
		} else if (listByCatMatcher.find()) {
			try {
				param_id = Long.parseLong(listByCatMatcher.group(1));
				page_num = Long.parseLong(listByCatMatcher.group(2));
			} catch (Exception e) {
				//Unable to parse thread_category/page_num params
				e.printStackTrace();
				jResp = new JSONObject();
				jResp.put("success", false);
				jResp.put("message", "Error parsing input parameters");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println(jResp.toString());	
				return;
			}
			
			doListByCategory(request, response, param_id, page_num);
			
		} else if(listByTopicMatcher.find()) {
			try {
				param_id = Long.parseLong(listByTopicMatcher.group(1));
				page_num = Long.parseLong(listByTopicMatcher.group(2));
			} catch (Exception e) {
				//Unable to parse thread_id/page_num params
				e.printStackTrace();
				jResp = new JSONObject();
				jResp.put("success", false);
				jResp.put("message", "Error parsing input parameters");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println(jResp.toString());	
				return;
			}
			
			doListByTopic(request, response, param_id, page_num);
			
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        jResp = new JSONObject();
	        jResp.put("message", "Illegal request");
	        response.getWriter().println(jResp.toString());
		}
        
    }
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Pattern newThreadPattern = Pattern.compile("^\\/threads\\/new$");
		Pattern replyThreadPattern = Pattern.compile("^\\/threads\\/reply$");
		Matcher newThreadMatcher = newThreadPattern.matcher(request.getRequestURI());
		Matcher replyThreadMatcher = replyThreadPattern.matcher(request.getRequestURI());
		JSONObject jResp;

		if(newThreadMatcher.find()){
			doNewThread(request, response);
		} else if(replyThreadMatcher.find()) {
			doReplyThread(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        jResp = new JSONObject();
	        jResp.put("message", "Illegal request");
	        response.getWriter().println(jResp.toString());
		}
    }
	
	private void doReplyThread(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ConfigManager mgr = ConfigManager.getInstance();
		String line;
		String jsonInput = "";
		JSONObject jo;
		BufferedReader br = request.getReader();
		long thread_id;
		String reply_body;
		String sessionID;
		JSONObject jResp;
		long reply_id;
        
		jResp = new JSONObject();
		
		response.setContentType("application/json");
        
        sessionID = request.getHeader("SESSIONID");
        
    	if(sessionID == null){
    		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    		jResp.put("success", false);
    		jResp.put("message", "No session token provided");
    	} else {
    		
    		while((line = br.readLine()) != null){
        		jsonInput += line;
        	}
        	
        	try {
        		jo = new JSONObject(jsonInput);
        		thread_id = jo.getLong("thread_id");
        		reply_body = jo.getString("body");
        		
        		if(reply_body.length() > mgr.getMaxReplyLength()) {
            		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            		jResp.put("success", false);
            		jResp.put("message", "Reply exeeds maximum permitted length");
        		} else {
	        		reply_id = ThreadsDTO.addReply(sessionID, thread_id, reply_body);
	        		
	            	if(reply_id < 0) {
	            		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            		jResp.put("success", false);
	            		jResp.put("message", "Error adding reply");
	            	} else {
	            		response.setStatus(HttpServletResponse.SC_OK);
	            		jResp.put("success", true);
	            		jResp.put("message", "Reply added successfully");
	            		jResp.put("thread_id", thread_id);
	            	}
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
	
	private HttpServletResponse doNewThread(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String line;
    	String jsonInput = "";
    	JSONObject jo;
    	JSONArray jTopics;
    	BufferedReader br = request.getReader();
    	int cat_id;
    	int[] topic_ids;
    	String thread_title;
    	String thread_body;
    	String sessionID;
    	JSONObject jResp;
    	long thread_id = -1;
    	
    	jResp = new JSONObject();
    	
        response.setContentType("application/json");
        
        sessionID = request.getHeader("SESSIONID");
        
    	if(sessionID == null){
    		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    		jResp.put("success", false);
    		jResp.put("message", "No session token provided");
    	} else {
    		
    		while((line = br.readLine()) != null){
        		jsonInput += line;
        	}
        	
        	try {
        		jo = new JSONObject(jsonInput);
        		cat_id = jo.getInt("category");
        		thread_title = jo.getString("title");
        		thread_body = jo.getString("body");
        		jTopics = jo.getJSONArray("topic_ids");
        		
        		if(jTopics.length() > 0) {
        		
        			topic_ids = new int[jTopics.length()];
        		
        			for(int idx = 0; idx < jTopics.length(); idx++){
        				topic_ids[idx] = jTopics.getInt(idx);
        			}
        		} else {
        			topic_ids = null;
        		}
        		
        		thread_id = ThreadsDTO.createThread(sessionID, thread_title, thread_body, cat_id, topic_ids);
        		
            	if(thread_id < 0) {
            		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            		jResp.put("success", false);
            		jResp.put("message", "Error creating thread");
            	} else {
            		response.setStatus(HttpServletResponse.SC_OK);
            		jResp.put("success", true);
            		jResp.put("message", "Thread created successfully");
            		jResp.put("thread_id", thread_id);
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
	
	private HttpServletResponse doViewThread(HttpServletRequest request, 
												HttpServletResponse response,
												long thread_id,
												long page_num) throws IOException {
		
		String jsonResp;
		JSONObject jsonError;
		
		jsonResp = ThreadsDTO.getThreadPageAsJSONString(thread_id, page_num);
		
		if(jsonResp != null) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(jsonResp);
		} else {
			//For some reason, no result was returned for this thread id/page
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			jsonError = new JSONObject();
			jsonError.put("success", false);
			jsonError.put("message", "Invalid thread id or page number");
			response.getWriter().println(jsonError);
		}
		
		return response;
	}
	
	private HttpServletResponse doListByCategory(HttpServletRequest request,
												HttpServletResponse response,
												long category_id,
												long page_num) throws IOException {
		String jsonResp;
		JSONObject jsonError;
		
		jsonResp = ThreadsDTO.listThreadsByCategoryAsJsonString(category_id, page_num);
		
		if(jsonResp != null) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(jsonResp);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			jsonError = new JSONObject();
			jsonError.put("success", false);
			jsonError.put("message", "An error has occurred");
			response.getWriter().println(jsonError);
		}
		return response;
	}
	
	private HttpServletResponse doListByTopic(HttpServletRequest request, 
												HttpServletResponse response,
												long topic_id,
												long page_num) throws IOException {
		String jsonResp;
		JSONObject jsonError;
		
		jsonResp = ThreadsDTO.listThreadsByTopicAsJSONString(topic_id, page_num);
		
		if(jsonResp != null) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(jsonResp);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			jsonError = new JSONObject();
			jsonError.put("success", false);
			jsonError.put("message", "An error has occurred");
			response.getWriter().println(jsonError);
		}
		return response;
	}
}
